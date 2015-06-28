package com.moodykettle.unittests.network.protocol.http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
//import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class RequestProcessor implements Runnable {
	
	private Socket connection = null;
	private String docRoot = "";
	private Vector<String> defaultIndexFile = null;
	private ConcurrentMap<String, String> supportedFiles = null;
	
	private String msg = "";
	private String URI = "\\";
	//private String URL = "";
	
	class FILE_OBJ {
		public String content = "";
		public byte[] binaryContent = null;
		public String content_type = "";
		public String length = "";
		
		FILE_OBJ(String content, byte[] binaryContent, String content_type, String length)
		{
			this.content = content;
			this.binaryContent = binaryContent;
			this.content_type = content_type;
			this.length = length;
		}
	}
	
	RequestProcessor(Socket connection, String docRoot, Vector<String> defaultIndexFile, ConcurrentMap<String, String> supportedFiles)
	{
		this.connection = connection;
		this.docRoot = docRoot;
		this.defaultIndexFile = defaultIndexFile;	
		this.supportedFiles = supportedFiles;
	}

	private String getURI(String initialRequestLine)
	{
		String URI = null;
		if(initialRequestLine.startsWith("GET"))
		{
			int end = initialRequestLine.indexOf("HTTP");
			if(end > -1)
			{
				URI = initialRequestLine.substring(3, end).trim().replace("/", "\\");			
			}
		}
		if(initialRequestLine.startsWith("POST"))
		{
			int end = initialRequestLine.indexOf("HTTP");
			if(end > -1)
			{
				URI = initialRequestLine.substring(4, end).trim().replace("/", "\\");			
			}
		}		
		return URI;
	}
	
	private String getRequestedFilePathName(String docRoot, String URI)
	{
		String filePathName = "";
		File f = null;
		
		//case1: : http://localhost/
		if(URI.endsWith("\\"))
		{
			for(int i = 0; i < defaultIndexFile.size(); i++)
			{				
				f = new File(docRoot+URI +defaultIndexFile.elementAt(i));
				if(f.exists()) 
				{
					filePathName = docRoot+URI +defaultIndexFile.elementAt(i);
					return filePathName;
					//break;
				}
			}
		}
		else
		{
			//case 2: http://localhost
			String tmpURI = URI + "\\";
			for(int i = 0; i < defaultIndexFile.size(); i++)
			{				
				f = new File(docRoot+tmpURI +defaultIndexFile.elementAt(i));
				if(f.exists()) 
				{
					filePathName = docRoot+tmpURI +defaultIndexFile.elementAt(i);
					return filePathName;
					//break;
				}
			}
					
			//case 3: http://localhost/?g=a&h=10
			if(URI.contains("?"))
			{
				int end = URI.indexOf("?");
				String tmpURI2 = URI.substring(0, end);
				filePathName = getRequestedFilePathName(docRoot, tmpURI2);
				return filePathName;
			}
			
			//case 4: http://localhost/subpage1.html
			String exts[] = supportedFiles.keySet().toArray(new String[0]);
			for(int j = 0; j < exts.length; j++)
			{
				if(URI.contains(exts[j]))
				{
					int end = URI.indexOf(exts[j])+ exts[j].length();
					//check if URI is longer then the end index
					//if this is the case then validate the extension
					if(URI.length() > (end))
					{
						char c = URI.charAt(end);
						if(c == '\\' || c == '/' || c == '#' || c == '?' || c == '&')
						{
							filePathName = docRoot + URI.substring(0, end);					
							return filePathName;
						}
						else
						{
							//
						}
					}
					else
					{
						filePathName = docRoot + URI.substring(0, end);					
						return filePathName;
					}															
				}
			}
		}
		return filePathName;
	}
	
	private String getTextFileContent(String fileNamePath)
	{
		String content = "";
		File f = new File(fileNamePath);
		Scanner in = null;
		try
		{
			if(f.exists())
			{
				in = new Scanner(new FileReader(f));					
				while(in.hasNext())
				{
					content += in.nextLine() + "\n";
				}
				//length = ""+content.length();
				//fo = new FILE_OBJ(content, content_type, length);
				in.close();
				System.out.println(content);				
				return content;
			}
			return null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			in.close();
			return null;
		}	
	}
	
	private byte[] getBinaryFileContent(String fileNamePath)
	{
		FileInputStream in = null;
		try
		{
			File f = new File(fileNamePath);			
			in = new FileInputStream(f);
			long fileLength = f.length();			
			if(fileLength > Integer.MAX_VALUE)
			{
				return null;
			}
			byte bytes[] = new byte[(int)fileLength];
			in.read(bytes);
			in.close();
			
			/*
			int c = -1;
			Vector<Byte> v = new Vector<Byte>();
			do
			{
				c = in.read();
				if(c != -1)
				{
					v.add(new Byte((byte)c));
				}
				else
				{
					break;
				}
			}
			while(c != -1);
			in.close();
			
			//byte[] buf = new byte[]{0x12, 0x23};
		    //String s = new sun.misc.BASE64Encoder().encode(buf);

			Byte b[] = v.toArray(new Byte[0]);				
			//return byteArrayToHexString(b);
			byte bytes[] = new byte[b.length];
			for(int i = 0; i < b.length; i++)
			{
				bytes[i] = b[i].byteValue();
			}
			*/
			
			return bytes;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			try 
			{
				in.close();
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return null;
	}
	
	private String getContentTypeByFileExtension(String filePathName, ConcurrentMap<String, String> supportedFiles)
	{
		File f = new File(filePathName);
		String fileName = f.getName();
		if(!fileName.isEmpty())
		{
			String ext = fileName.substring(fileName.lastIndexOf('.'));
			if(supportedFiles.containsKey(ext))
			{			
				return supportedFiles.get(ext);			
			}
		}
		return null;
	}
	
	private FILE_OBJ getRequestedFile(String URI, String docRoot, ConcurrentMap<String, String> supportedFiles)
	{
		String filePathName = null;
		//boolean fileExist = false;
		String content = null;
		byte binaryContent[] = null;
		String content_type = null;
		String length = null;
		//FILE_OBJ fo = null;
		//File f = null;
		
		filePathName = getRequestedFilePathName(docRoot, URI);
		content_type = getContentTypeByFileExtension(filePathName, supportedFiles);
		if(content_type.contains("text"))
		{
			content = getTextFileContent(filePathName);
			length = ""+content.length();
			binaryContent = null;
		}
		else
		{
			//content = getBinaryFileContent(filePathName);
			content = null;
			binaryContent = getBinaryFileContent(filePathName);	
			if(binaryContent != null)
			{
				length = ""+binaryContent.length;
				//content = new sun.misc.BASE64Encoder().encode(binaryContent);				
			}
		}		
				
		return new FILE_OBJ(content, binaryContent, content_type, length);		
	}
	
	private void sendResponse(DataOutputStream outcomingMsg, FILE_OBJ fo)
	{
		try 
		{
			outcomingMsg.writeBytes("HTTP/1.0 200 OK\n");
			outcomingMsg.writeBytes("Date: Fri, 31 Dec 1999 23:59:59 GMT\n");
			outcomingMsg.writeBytes("Content-Type: "+fo.content_type+"\n");
			outcomingMsg.writeBytes("Content-Length: "+fo.length+"\n");
			outcomingMsg.writeBytes("\n");
			
			if(fo.content_type.contains("text"))
			{
				outcomingMsg.writeBytes(fo.content);
			}
			else
			{
				outcomingMsg.write(fo.binaryContent);
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			Scanner incomingMsg = new Scanner(connection.getInputStream());
			DataOutputStream outcomingMsg = new DataOutputStream(connection.getOutputStream());
			String initialRequestLine = incomingMsg.nextLine();
			while(true)
			{				
				String line = incomingMsg.nextLine();
				msg += line + "\n";
				
				if(line.isEmpty())
				{
					msg = initialRequestLine + "\n" + msg;
					System.out.println(msg);
					
					URI = getURI(initialRequestLine);					
					FILE_OBJ fo = getRequestedFile(URI, docRoot, supportedFiles);									
					sendResponse(outcomingMsg, fo);
									
					connection.close();
					break;
				}
			}
			outcomingMsg.close();
			incomingMsg.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
