package com.moodykettle.unittests.utilitys;

import java.io.IOException;

public final class MyUtility {
	
	public static byte[] base64ToByteArray(String s)
	{
		try 
		{
			return new sun.misc.BASE64Decoder().decodeBuffer(s);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static String byteArrayToBase64(byte[] bytes)
	{
		return new sun.misc.BASE64Encoder().encode(bytes);
	}
	
	public static String byteArrayToHexString(byte[] b) 
	{
	    StringBuffer sb = new StringBuffer(b.length * 2);
	    for (int i = 0; i < b.length; i++) {
	      int v = b[i] & 0xff;
	      if (v < 16) {
	        sb.append('0');
	      }
	      sb.append(Integer.toHexString(v));
	    }
	    return sb.toString().toUpperCase();
	}	
	
	public static String byteArrayToHexString(Byte[] b) 
	{
	    StringBuffer sb = new StringBuffer(b.length * 2);
	    for (int i = 0; i < b.length; i++) {
	      int v = b[i].byteValue() & 0xff;
	      if (v < 16) {
	        sb.append('0');
	      }
	      sb.append(Integer.toHexString(v));
	    }
	    return sb.toString().toUpperCase();
	}

}
