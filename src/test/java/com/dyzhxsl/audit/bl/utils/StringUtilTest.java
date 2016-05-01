package com.dyzhxsl.audit.bl.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testIsChinese() {
		boolean isChinese = StringUtil.isChinese("asdf");
		if (isChinese) {
			System.out.println("Contains Chinese");
		} else {
			System.out.println("No Chinese");
		}
		assertTrue(isChinese);
	}

}
