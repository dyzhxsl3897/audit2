package com.dyzhxsl.audit.bl.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class EncryptUtilTest {

	@Test
	public void testDecode() throws IOException {
		String testString = "abcde";
		String encoded = EncryptUtil.encode(testString);
		String decoded = EncryptUtil.decode(encoded);
		System.out.println(decoded);
		assertEquals(testString, decoded);
	}

}
