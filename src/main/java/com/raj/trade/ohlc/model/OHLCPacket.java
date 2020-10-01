package com.raj.trade.ohlc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class OHLCPacket.
 */
@Getter
@Setter
@Builder
public class OHLCPacket implements Cloneable {

	@JsonProperty("o")
	private double open;

	@JsonProperty("h")
	private double high;

	@JsonProperty("l")
	private double low;

	@JsonProperty("c")
	private double close;

	@JsonProperty("volume")
	private double volume;

	@JsonProperty("event")
	private String event;

	@JsonProperty("symbol")
	private String symbol;

	@JsonProperty("bar_num")
	private int barNumber;

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
