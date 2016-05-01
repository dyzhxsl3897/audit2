package com.dyzhxsl.audit.bl.utils;

import org.junit.Test;

public class SMSSenderTest {

	@Test
	public void testSendSMS() throws Exception {
		SMSSender.sendSMS(new String[] { "2398770632" }, "Test Msg!");
	}

}
