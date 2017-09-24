package com.sfw.main;

import java.util.Arrays;

import com.sfw.resource.impl.InternalSystemResourceImpl;

public class Main
{
	public static void main(String [] args)
	{
		HttpServer server = new HttpServer(new InternalSystemResourceImpl(), Arrays.asList("0:0:0:0:0:0:0:1"));
		try
		{
			server.start(8081);
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
}
