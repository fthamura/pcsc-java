package com.moodykettle.unittests.network.protocol.http;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

public class MyWebServer {
	public static int port = 8080;
	public static String docRoot = System.getProperty("user.dir")+"\\docRoot";
	public static Vector<String> defaultIndexFile = new Vector<String>();
	public static ConcurrentMap<String, String> supportedFiles = new ConcurrentHashMap<String, String>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			defaultIndexFile.add("index.htm");
			defaultIndexFile.add("index.html");
			
			supportedFiles.put(".html", "text/html");
			supportedFiles.put(".htm", "text/html");
			supportedFiles.put(".jpg", "image/jpeg");
			supportedFiles.put(".jpeg", "image/jpeg");
			
			System.out.println();
			System.out.println("Port: "+port);
			System.out.println("Document Root: "+docRoot);
			System.out.println("Default index document: "+defaultIndexFile.toString());
			System.out.println("Superted files: "+supportedFiles.toString());
			System.out.println();
			
			ServerSocket ss = new ServerSocket(MyWebServer.port);			
			
			while(true)
			{
				Socket connection = ss.accept();
				Thread t = new Thread(new RequestProcessor(connection, docRoot, defaultIndexFile, supportedFiles));
				t.start();
			}
			/*
			*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
