package com.sfw.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.sfw.main.util.BlackList;
import com.sfw.processor.SocketProcessor;
import com.sfw.resource.InternalSystemResource;

public class SimpleHttpFirewallServer {
	private InternalSystemResource internalSystemResource;
	private ServerSocket ss;
	
	public SimpleHttpFirewallServer(InternalSystemResource internalSystemResource)
	{
		this.internalSystemResource = internalSystemResource;
	}
    public void start(int port) throws Throwable 
    {
        ss = new ServerSocket(port);
        while (true) {
            Socket s = ss.accept();
            if(!BlackList.contains(s.getInetAddress().getHostAddress()))
            {
                Thread socketProcessorThread = new Thread(new SocketProcessor(s, internalSystemResource));
                socketProcessorThread.start();
            }
            else
            {
            	 s.close();
            }
        }
    }

	public void stop() throws IOException
    {
    	ss.close();
    }
}