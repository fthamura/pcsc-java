package com.moodykettle.unittests.network;

//import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
//import java.net.UnknownHostException;
import java.util.Scanner;

import com.moodykettle.unittests.network.MyServer.MyServerThread;

public class MyClient {

	public IncomingMsgProcessor inmsgProc = null;
	
	public void startIncomingMsgProcessor(Socket s)
	{		
		inmsgProc = new IncomingMsgProcessor(s);
		Thread t = new Thread(inmsgProc);
		t.start();
	}
	
	public class IncomingMsgProcessor implements Runnable
	{
		Socket s = null;
		boolean work = true;
		
		IncomingMsgProcessor(Socket s)
		{
			this.s = s;
		}
		
		public void setWork(boolean work)
		{
			this.work = work;			
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try
			{
				Scanner in = new Scanner(s.getInputStream());
				while(work)
				{
					while(in.hasNext())
					{
						String msg = in.nextLine();
						System.out.println("(IN) Msg from Server: "+msg);
					}					
				}
				System.out.println("Ending thread...");
				in.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try 
		{
			MyClient client = new MyClient();
						
			Socket s = new Socket("localhost", 1234);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			Scanner in = new Scanner(s.getInputStream());
			
			client.startIncomingMsgProcessor(s);
			
			while(true)
			{
				Scanner sc = new Scanner(System.in);
				String str = sc.nextLine();
				out.println(str);
				if(str.equalsIgnoreCase("bye"))
				{
					break;
				}
				//String msg = in.nextLine();
				//System.out.println("from server: "+msg);
			}
			client.inmsgProc.setWork(false);
			out.close();
			in.close();
			s.close();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
