package com.dyzhxsl.audit.bl.utils;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class SMSSender {
	private static Logger auditLogger = Logger.getLogger("auditInfo");

	public static void sendSMS(String[] phoneNumbers, String msg) throws Exception {
		String user = "dyzhxsl";
		String password = "MzYxMjQyMQ==";
		StringBuilder data = new StringBuilder();
		data.append("User=").append(user);
		data.append("&Password=").append(EncryptUtil.decode(password));
		for (String phoneNumber : phoneNumbers) {
			data.append("&PhoneNumbers[]=").append(phoneNumber);
		}
		data.append("&Message=").append(msg);

		URL url = new URL("https://app.eztexting.com/sending/messages?format=json");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data.toString());
		wr.flush();

		int responseCode = conn.getResponseCode();
		boolean isSuccesResponse = responseCode < 400;

		InputStream responseStream = isSuccesResponse ? conn.getInputStream() : conn.getErrorStream();
		if (responseStream != null) {
			String responseString = IOUtils.toString(responseStream);
			responseStream.close();
			auditLogger.info(responseString);
		}

		wr.close();
	}

}
