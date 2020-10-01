package com.raj.trade.ohlc.data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.raj.trade.ohlc.model.OHLCSubscription;
import com.raj.trade.ohlc.model.Trade;

/**
 * The Class OHLCThreadCommData.
 * Maintains queues for communication between threads
 */
public class OHLCThreadCommData {

	private static BlockingQueue<Trade> tradeQueue = new LinkedBlockingQueue<>();

	private static BlockingQueue<OHLCSubscription> subscriptionQueue = new LinkedBlockingQueue<>(10);

	public static BlockingQueue<OHLCSubscription> getSubscriptionQueue() {
		return subscriptionQueue;
	}
	
	public static BlockingQueue<Trade> getTradeQueue() {
		return tradeQueue;
	}

}
