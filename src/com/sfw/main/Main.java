package com.sfw.main;

import com.sfw.main.util.BlackList;
import com.sfw.resource.impl.InternalSystemResourceImpl;

public class Main
{
	public static void main(String [] args)
	{
		new Thread(new BlackList("d:/blacklist.txt", 1000l)).start();

		SimpleHttpFirewallServer server = new SimpleHttpFirewallServer(new InternalSystemResourceImpl("http://chesordben006:62108/OrderService"));
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
