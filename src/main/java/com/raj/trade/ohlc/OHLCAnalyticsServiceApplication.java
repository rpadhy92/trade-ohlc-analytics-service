package com.raj.trade.ohlc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.raj.trade.ohlc.process.manager.OHLCAnalyticsProcessManager;

/**
 * The Class OHLCAnalyticsServiceApplication.
 * Application class to start the service
 */
@SpringBootApplication
public class OHLCAnalyticsServiceApplication implements ApplicationRunner{

	@Autowired
	private OHLCAnalyticsProcessManager ohlcAnalyticsProcessManager;
	
	public static void main(String[] args) {
		SpringApplication.run(OHLCAnalyticsServiceApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		ohlcAnalyticsProcessManager.startOhlcProcess();
	}

}
