package com.raj.trade.ohlc.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.raj.trade.ohlc.data.OHLCThreadCommData;
import com.raj.trade.ohlc.model.OHLCSubscription;
import com.raj.trade.ohlc.model.Trade;
import com.raj.trade.ohlc.utils.JsonParser;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class TradeInputDataReader. Read trades from file, send Trades to
 * OHLCThreadCommData.tradeQueue which will be consumed for creating OHLC
 * packets
 */
@Slf4j
@Component
public class TradeInputDataReader {

	/**
	 * Read trade data for client subscription stocks.
	 *
	 * @param filePath the file path
	 */
	public void readTradeDataForClientSubscriptionStocks() {
		while (true) {
			try {
				log.debug("checking for user subscription...");
				OHLCSubscription subscription = OHLCThreadCommData.getSubscriptionQueue().take();
				if (Objects.nonNull(subscription)) {
					log.debug("Processing client subscription for stock - {}", subscription.getSymbol());
					readTradeData(subscription.getSymbol());
				}
			} catch (Exception ex) {
				log.error("Error while fetching data from subscription queue", ex);
			}
		}
	}

	/**
	 * Read trade data.
	 *
	 * @param filePath    the file path
	 * @param stockSymbol the stock symbol
	 * @throws IOException
	 */
	private void readTradeData(String stockSymbol) throws IOException {
		String jsonString = null;
		try (InputStream inputStream = getClass().getResourceAsStream("/trade-data/trades.json");
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			while ((jsonString = reader.readLine()) != null) {
				try {
					Trade trade = (Trade) JsonParser.fromJson(jsonString, Trade.class);
					if (Objects.nonNull(trade) && trade.getSymbol().equals(stockSymbol)) {
						OHLCThreadCommData.getTradeQueue().put(trade);
					}
				} catch (InterruptedException ex) {
					log.error("Error while inserting to queue", ex);
				} catch (JsonProcessingException ex) {
					log.error("Error while parsing jsonString to object", ex);
				}
			}
		} catch (IOException ex) {
			log.error("Invalid file, Unable to find the file from specifed path", ex);
		} catch (Exception ex) {
			log.error("Error while Reading trade data from input file", ex);
		}
	}
}
