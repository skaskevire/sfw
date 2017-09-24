package com.sfw.resource.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sfw.resource.InternalSystemResource;

public class InternalSystemResourceImpl implements InternalSystemResource
{

	@Override
	public String sendDataToInternalSystem(InputStream is) throws IOException
	{
		 BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        while(true) {
	            String s = br.readLine();
	            if(s == null || s.trim().length() == 0) {
	                break;
	            }
	        }
	        
	        return "323";
	}

}
