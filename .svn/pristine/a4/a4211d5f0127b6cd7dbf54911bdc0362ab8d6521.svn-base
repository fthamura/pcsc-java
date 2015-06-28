package com.moodykettle.unittests.regex.serialnumber;

public class SerialNumberValidator {
	public static boolean onlyWordCharactersInArray(char[] text)
	{
		System.out.print("onlyWordCharactersInArray("+new String(text)+"):");
		boolean ret = true;
		
		for(int i = 0;i < text.length;i++)
		{
			String tmp = new String(text, i, 1);
			if(!tmp.matches("\\w"))
			{
				System.out.println("false");
				return false;
			}		
		}
		System.out.println(ret);
		return ret;
	}
  
	public static boolean onlyWordCharactersInString(String text)
	{
		System.out.print("onlyWordCharactersInString("+text+"):");
		boolean ret = true;
		
		for(int i = 0;i < text.length();i++)
		{
			String tmp = text.substring(i, i+1);
			if(!tmp.matches("\\w"))
			{
				System.out.println("false");
				return false;
			}		
		}
		System.out.println(ret);
		return ret;
	}
  
	public static boolean onlyDigitInString(String text)
	{		
		System.out.print("onlyDigitInString("+text+"):");
		boolean ret = true;
		
		for(int i = 0;i < text.length();i++)
		{
			String tmp = text.substring(i, i+1);
			if(!tmp.matches("\\d"))
			{
				System.out.println("false");
				return false;
			}		
		}
		System.out.println(ret);
		return ret;
	}

}
