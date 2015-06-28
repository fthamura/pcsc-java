package com.moodykettle.unittests.regex;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class myRegex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String r,s;
		while(true)
		{
			Scanner sc = new Scanner(System.in);
			System.out.println("Input regex: ");
			r = sc.nextLine();
			if(r.isEmpty())break;
			Pattern pattern = Pattern.compile(r);
			System.out.println("Input string: ");
			s = sc.nextLine();			
			Matcher matcher = pattern.matcher(s);
			if(matcher.matches())
			{
				System.out.println("Matches.");
			}
			else
			{
				System.out.println("Does not match.");
			}
			if(s.isEmpty())break;
		}
	}

}
