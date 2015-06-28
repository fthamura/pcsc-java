package com.moodykettle.unittests.file.binary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class BinaryFileReaderWriter {

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
	
			FileInputStream in = new FileInputStream(f);
			int c = -1;
			Vector<Integer> v = new Vector<Integer>();
			do
			{
				c = in.read();
				if(c != -1)
				{
					v.add(new Integer(c));
				}
				else
				{
					break;
				}
			}
			while(c != -1);
			in.close();
						
			System.out.println("Output file name: ");
			fileName = sc.nextLine();
			f = new File(fileName);
	
			FileOutputStream out = new FileOutputStream(f);
			Iterator<Integer> i = v.iterator();
			while(i.hasNext())
			{
				Integer integer = i.next();
				out.write(integer.intValue());
			}
			out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
