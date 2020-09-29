package com.ohlc.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trade {
	
	@JsonProperty("sym")
	private String symbol;
	
	@JsonIgnore
	@JsonProperty("T")
	private String type;
	
	@JsonProperty("P")
	private double price;
	
	@JsonProperty("Q")
	private double quantity;
	
	@JsonIgnore
	@JsonProperty("TS")
	private long timeStamp;
	
	@JsonIgnore
	@JsonProperty("side")
	private String side;
	
	@Setter(value=AccessLevel.NONE)
	@JsonProperty("TS2")
	private Instant timeStamp2;
	
	public void setTimeStamp2(long timeStamp2) {
		this.timeStamp2 = Instant.ofEpochMilli(timeStamp2);
	}

}
