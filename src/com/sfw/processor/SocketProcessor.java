package com.sfw.processor;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.sfw.resource.InternalSystemResource;

public class SocketProcessor implements Runnable {

    private Socket s;
    private InputStream is;
    private OutputStream os;
    private InternalSystemResource internalSystemResource;

    public SocketProcessor(Socket s, InternalSystemResource internalSystemResource) throws Throwable {
        this.s = s;
        this.is = s.getInputStream();
        this.os = s.getOutputStream();
        this.internalSystemResource = internalSystemResource;
    }

    public void run() {
        try {
            writeResponse(internalSystemResource.sendDataToInternalSystem(is));
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                s.close();
            } catch (Throwable t) {
            	 t.printStackTrace();
            }
        }
        System.err.println("Client processing finished");
    }

    private void writeResponse(String s) throws Throwable {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + s.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + s;
        os.write(result.getBytes());
        os.flush();
    }
}