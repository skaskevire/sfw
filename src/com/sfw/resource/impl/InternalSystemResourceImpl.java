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

public class InternalSystemResourceImpl implements InternalSystemResource
{
	@Override
	public String sendDataToInternalSystem(InputStream is) throws IOException
	{
		 BufferedReader br = new BufferedReader(new InputStreamReader(is));
		 int readSize = 0;
		 
		String fl =  br.readLine();

		 String requestMethod = fl.split(" ")[0];
		 Map<String, String> requestHeaders = new HashMap<String, String>();
	        while(true) {
	            String s = br.readLine();            
	            
	            if(s == null || s.trim().length() == 0) {
	                break;
	            }
	            
	            requestHeaders.put(s.split(":")[0].trim(), s.split(":")[1].trim());

	        }
	        
	        StringBuilder bodyBuilder = new StringBuilder();
	       
	        if(requestHeaders.get("Content-Length") != null)
	        {
	        	while(readSize < Integer.valueOf(requestHeaders.get("Content-Length"))) {
		            String s = br.readLine();            
		            readSize += s.length() + 1;
		            
		            bodyBuilder.append(s);
		        }
	        }        
	        
	        String url = "http://chesordben006:62108/OrderService";

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod(requestMethod);
			con.setDoOutput(true);
			
			for (Entry<String, String>requestHeader : requestHeaders.entrySet()) {
				con.setRequestProperty(requestHeader.getKey(),  requestHeader.getValue());
			}
			con.setRequestProperty("Accept-Encoding", "UTF-8");

			 if(requestHeaders.get("Content-Length") != null)
		     {
				try( DataOutputStream wr = new DataOutputStream( con.getOutputStream())) {
				  wr.write(bodyBuilder.toString().getBytes() );
				}
		     }
			

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	        
	        return response.toString();
	}
}
