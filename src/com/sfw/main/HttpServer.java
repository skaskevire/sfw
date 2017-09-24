package com.sfw.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.sfw.processor.SocketProcessor;
import com.sfw.resource.InternalSystemResource;

public class HttpServer {
	private InternalSystemResource internalSystemResource;
	private ServerSocket ss;
	private List<String> prohibitedUserAdresses;
	
	public HttpServer(InternalSystemResource internalSystemResource, List<String> prohibitedAddresses)
	{
		this.internalSystemResource = internalSystemResource;
		this.prohibitedUserAdresses = prohibitedAddresses;
	}
    public void start(int port) throws Throwable 
    {
        ss = new ServerSocket(port);
        while (true) {
            Socket s = ss.accept();
            if(!prohibitedUserAdresses.contains(s.getInetAddress().getCanonicalHostName()))
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