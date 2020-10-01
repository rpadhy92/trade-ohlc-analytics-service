package com.raj.trade.ohlc.processor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.raj.trade.ohlc.data.OHLCThreadCommData;
import com.raj.trade.ohlc.model.OHLCPacket;
import com.raj.trade.ohlc.model.OHLCPacketDummy;
import com.raj.trade.ohlc.model.Trade;
import com.raj.trade.ohlc.utils.JsonParser;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class OHLCPacketCreator. computes OHLC packets based on 15 seconds
 * (interval)
 */
@Component
@Slf4j
public class OHLCPacketCreator {

	private Trade startTrade;
	private Instant startTime, endTime;
	private OHLCPacket ohlcPacket;
	private int barNumber = 1;
	private boolean isBarStartTrade;
	private int pollCounter;

	/**
	 * Creates the ohlc packets by analysing trades.
	 */
	public void createOhlcPacket() {
		while (true) {
			try {
				Trade trade = OHLCThreadCommData.getTradeQueue().poll();
				if (Objects.isNull(trade)) {
					if (pollCounter < 300) {
						pollCounter++;
						continue;
					}
					if (Objects.nonNull(ohlcPacket) && OHLCThreadCommData.getTradeQueue().size() == 0) {
						PrintCloseOHLC();
						resetStarterChecksToInitial();
					}
					pollCounter = 0;
				} else {
					log.debug("Received message - {}, {}, {} ,{}", trade.getSymbol(), trade.getPrice(),
							trade.getQuantity(), LocalDateTime.ofInstant(trade.getTimeStamp2(), ZoneOffset.UTC));

					if (Objects.isNull(startTrade)) {
						startTrade = trade;
						startTime = trade.getTimeStamp2();
						endTime = startTime.plusMillis(1);
						isBarStartTrade = true;
						processTrade(trade);
						printOHLC(trade);
					} else if (Objects.nonNull(startTrade) && Objects.isNull(trade)) {
						PrintCloseOHLC();
						startTrade = null;
					} else if (!trade.getSymbol().equals(startTrade.getSymbol())) {
						PrintCloseOHLC();
						startTrade = trade;
						startTime = trade.getTimeStamp2();
						endTime = startTime.plusSeconds(15);
						isBarStartTrade = true;
						barNumber = 1;
						processTrade(trade);
						printOHLC(trade);
					} else {
						isBarStartTrade = false;
						processTradeByTimeCheck(trade);
					}
				}
			} catch (JsonProcessingException ex) {
				log.error("Error while parsing object to json String", ex);
			} catch (Exception ex) {
				log.error("Error while processing OHLC", ex);
			}
		}
	}

	private void processTradeByTimeCheck(Trade trade) throws JsonProcessingException {
		if (trade.getTimeStamp2().isAfter(endTime.plusSeconds(86400))) {
			startTime = endTime.plusSeconds(86400);
			endTime = startTime.plusSeconds(15);
		}

		if (trade.getTimeStamp2().isBefore(endTime)) {
			processTrade(trade);
			printOHLC(trade);
		} else {
			if (trade.getPrice() == 6160.8) {
				log.debug("debug.");
			}
			PrintCloseOHLC();
			barNumber++;
			startTime = endTime;
			endTime = startTime.plusSeconds(15);
			resetOHLCForNextBar();
			processTradeByTimeCheck(trade);
		}
	}

	private void processTrade(Trade trade) {
		if (isBarStartTrade) {
			ohlcPacket = OHLCPacket.builder().build();
			ohlcPacket.setSymbol(trade.getSymbol());
			ohlcPacket.setOpen(trade.getPrice());
			ohlcPacket.setLow(trade.getPrice());
			ohlcPacket.setEvent("ohlc_notify");
		}
		ohlcPacket.setHigh(Math.max(ohlcPacket.getHigh(), trade.getPrice()));
		ohlcPacket.setLow(Math.min(ohlcPacket.getLow(), trade.getPrice()));
		ohlcPacket.setClose(trade.getPrice());
		ohlcPacket.setVolume(ohlcPacket.getVolume() + trade.getQuantity());
		ohlcPacket.setBarNumber(barNumber);
	}

	private void printOHLC(Trade Trade) throws JsonProcessingException {
		OHLCPacket ohlcPacketPrint;
		try {
			ohlcPacketPrint = (OHLCPacket) ohlcPacket.clone();
			ohlcPacketPrint.setClose(0);
			log.info("{} ", JsonParser.toJson(ohlcPacketPrint));
		} catch (CloneNotSupportedException ex) {
			log.error("Error while cloning OHLCPacket object", ex);
		}
	}

	private void PrintCloseOHLC() throws JsonProcessingException {
		if (Objects.nonNull(ohlcPacket)) {
			if (ohlcPacket.getOpen() == 0) {
				OHLCPacketDummy ohlcPacketDummy = OHLCPacketDummy.getOHLCPacketDummy(ohlcPacket);
				log.info("{}", JsonParser.toJson(ohlcPacketDummy));
			} else {
				log.info("{}", JsonParser.toJson(ohlcPacket));
			}
		}
	}

	private void resetOHLCForNextBar() {
		isBarStartTrade = true;
		ohlcPacket.setBarNumber(barNumber);
		ohlcPacket.setOpen(0);
		ohlcPacket.setHigh(0);
		ohlcPacket.setLow(0);
		ohlcPacket.setClose(0);
		ohlcPacket.setVolume(0);
	}

	private void resetStarterChecksToInitial() {
		startTrade = null;
		startTime = null;
		endTime = null;
		ohlcPacket = null;
		barNumber = 1;
		isBarStartTrade = true;
	}
}
