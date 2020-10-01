package com.raj.trade.ohlc.process.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.raj.trade.ohlc.processor.OHLCPacketCreator;
import com.raj.trade.ohlc.reader.TradeInputDataReader;

/**
 * The Class OHLCAnalyticsProcessManager.
 * Manages triggering of the processes
 */
@Service
public class OHLCAnalyticsProcessManager {
	
	@Autowired
	private TradeInputDataReader tradeInputDataReader;
	
	@Autowired
	private OHLCPacketCreator ohlcPacketCreator; 
	
	ExecutorService service = Executors.newFixedThreadPool(2);
	
	/**
	 * Start ohlc process.
	 * Triggers trade reader task & OHLC packet creator tasks
	 */
	public void startOhlcProcess() {
		Runnable tradeReaderTask = () -> {
			tradeInputDataReader.readTradeDataForClientSubscriptionStocks();
		};
		
		Runnable ohlcPacketCreatorTask = () -> {
			ohlcPacketCreator.createOhlcPacket();
		};
		
		service.submit(tradeReaderTask);
		service.submit(ohlcPacketCreatorTask);
	}
}
