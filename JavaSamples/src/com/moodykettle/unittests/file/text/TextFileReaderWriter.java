package com.moodykettle.unittests.file.text;

import java.io.*;
import java.util.Scanner;

public class TextFileReaderWriter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			Scanner sc = new Scanner(System.in);
			System.out.println("Input file name: ");
			String fileName = sc.nextLine();
			File f = new File(fileName);
			
			//BufferedReader in = new BufferedReader(new FileReader(f));
			Scanner in = new Scanner(new FileReader(f));
			
			String line = "";
			StringBuffer sb = new StringBuffer(); 
			do
			{
				//line = in.readLine();
				if(in.hasNext()) line = in.nextLine();
				else break;
				
				if(line != null)
				{
					sb.append(line);
					sb.append(System.getProperty("line.separator"));
				}
				else
				{
					break;
				}
				
				
			}
			while(line != null);
			in.close();
			System.out.println(sb.toString());
			
			//--------------------------------------
						
			System.out.println("Output file name: ");
			fileName = sc.nextLine();
			f = new File(fileName);
			PrintWriter out = new PrintWriter(new FileWriter(f), true);
			out.print(sb);
			//out.print("test");
			out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
