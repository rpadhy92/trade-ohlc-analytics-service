package com.raj.trade.ohlc.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.raj.trade.ohlc.model.Trade;
import com.raj.trade.ohlc.utils.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TradeInputDataReader {
	
	public void readTradeData() {
		String filePath = "src/main/resources/trade-data/trades.json";
		try {
			Stream<String> lines = Files.lines(Paths.get(filePath));
			lines.forEach(jsonString -> {
				try {
					Trade trade = (Trade) JsonParser.fromJson(jsonString, Trade.class);
					log.info(trade.getSymbol());
				} catch (JsonProcessingException ex) {
					log.error("Error while parsing jsonString to object",ex);
					System.exit(0);
				}
			});
		} catch(IOException ex) {
			log.error("File not found",ex);
		} catch(Exception ex) {
			log.error("Error while Reading trade data",ex);
		}
	}
}
