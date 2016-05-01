package com.dyzhxsl.audit.bl.utils;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class EncryptUtil {

	public static String encode(String input) throws IOException {
		String result = null;

		byte[] outputBytes = Base64.encodeBase64(input.getBytes());
		result = StringUtils.defaultIfEmpty(IOUtils.toString(outputBytes, "UTF-8"), StringUtils.EMPTY);

		return result;
	}

	public static String decode(String input) throws IOException {
		String result = null;

		byte[] outputBytes = Base64.decodeBase64(input.getBytes());
		result = StringUtils.defaultIfEmpty(IOUtils.toString(outputBytes, "UTF-8"), StringUtils.EMPTY);

		return result;
	}

}
