package com.sfw.resource.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.sfw.resource.InternalSystemResource;

public class InternalSystemResourceImpl implements InternalSystemResource {
	
	private String proxiedResourceURL;
	public InternalSystemResourceImpl(String proxiedResourceURL)
	{
		this.proxiedResourceURL = proxiedResourceURL;
	}
	@Override
	public String sendDataToInternalSystem(InputStream is) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String requestMethod = getRequestHeader(br);
		Map<String, String> requestHeaders = getRequestHeaders(br);
		String requestBody = getRequestBody(br, requestHeaders);

		URL obj = new URL(proxiedResourceURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod(requestMethod);
		con.setDoOutput(true);

		for (Entry<String, String> requestHeader : requestHeaders.entrySet())
		{
			con.setRequestProperty(requestHeader.getKey(), requestHeader.getValue());
		}
		con.setRequestProperty("Accept-Encoding", "UTF-8");

		if (requestHeaders.get("Content-Length") != null)
		{
			try (DataOutputStream wr = new DataOutputStream(con.getOutputStream()))
			{
				wr.write(requestBody.getBytes());
			}
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null)
		{
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

	private Map<String, String> getRequestHeaders(BufferedReader br) throws IOException
	{
		Map<String, String> requestHeaders = new HashMap<String, String>();
		while (true)
		{
			String s = br.readLine();

			if (s == null || s.trim().length() == 0)
			{
				break;
			}

			requestHeaders.put(s.split(":")[0].trim(), s.split(":")[1].trim());

		}

		return requestHeaders;
	}

	private String getRequestHeader(BufferedReader br) throws IOException
	{
		return br.readLine().split(" ")[0];
	}

	private String getRequestBody(BufferedReader br, Map<String, String> requestHeaders) throws IOException
	{
		StringBuilder bodyBuilder = new StringBuilder();

		int readSize = 0;
		if (requestHeaders.get("Content-Length") != null)
		{
			while (readSize < Integer.valueOf(requestHeaders.get("Content-Length")))
			{
				String s = br.readLine();
				readSize += s.length() + 1;

				bodyBuilder.append(s);
			}
		}

		return bodyBuilder.toString();

	}

}
