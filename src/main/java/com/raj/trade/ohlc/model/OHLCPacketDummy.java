package com.raj.trade.ohlc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OHLCPacketDummy {

	@JsonProperty("event")
	private String event;

	@JsonProperty("symbol")
	private String symbol;

	@JsonProperty("bar_num")
	private int barNumber;
	
	public static OHLCPacketDummy getOHLCPacketDummy(OHLCPacket ohlcPacket) {
		OHLCPacketDummy ohlcPacketDummy = new OHLCPacketDummy();
		ohlcPacketDummy.event = ohlcPacket.getEvent();
		ohlcPacketDummy.symbol = ohlcPacket.getSymbol();
		ohlcPacketDummy.barNumber = ohlcPacket.getBarNumber();
		return ohlcPacketDummy;
	}
}
