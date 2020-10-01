package com.raj.trade.ohlc.data;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class OHLCThreadCommDataTest {

	@Test
	public void getSubscriptionQueue_shouldReturnSubscriptionQueue() {
		Assert.assertNotNull(OHLCThreadCommData.getSubscriptionQueue());
	}
	
	public void getTradeQueue_shouldReturnTradeQueueObject() {
		Assert.assertNotNull(OHLCThreadCommData.getTradeQueue());
	}
}
