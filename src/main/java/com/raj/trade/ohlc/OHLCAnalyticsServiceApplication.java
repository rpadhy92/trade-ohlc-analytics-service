package com.raj.trade.ohlc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.raj.trade.ohlc.reader.TradeInputDataReader;

@SpringBootApplication
public class OHLCAnalyticsServiceApplication implements ApplicationRunner{

	@Autowired
	private TradeInputDataReader tradeInputDataReader;
	
	public static void main(String[] args) {
		SpringApplication.run(OHLCAnalyticsServiceApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		tradeInputDataReader.readTradeData();
	}

}