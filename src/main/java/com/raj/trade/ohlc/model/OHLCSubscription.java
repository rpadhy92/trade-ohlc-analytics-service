package com.raj.trade.ohlc.model;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class OHLCSubscription.
 * Client subscription class
 */
@Setter
@Getter
public class OHLCSubscription {
	
	private String event;
	
	private String symbol;
	
	private int interval;
	
}
