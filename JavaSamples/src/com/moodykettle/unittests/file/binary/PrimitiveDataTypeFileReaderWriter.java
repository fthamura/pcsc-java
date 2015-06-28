package com.moodykettle.unittests.file.binary;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class PrimitiveDataTypeFileReaderWriter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			Scanner sc = new Scanner(System.in);
			System.out.println("Output file name: ");
			String fileName = sc.nextLine();
			File f = new File(fileName);

			FileOutputStream fout = new FileOutputStream(f);
			DataOutputStream dout = new DataOutputStream(fout);
			
			dout.writeUTF("Robcio");
			dout.writeInt(45);
			dout.writeDouble(68.6);
			
			dout.close();
			fout.close();

			//--------------------------------
			
			System.out.println("Input file name: ");
			fileName = sc.nextLine();
			f = new File(fileName);

			FileInputStream fin = new FileInputStream(f);
			DataInputStream din = new DataInputStream(fin);
			
			String string = din.readUTF();
			int integer = din.readInt();
			double doub = din.readDouble();
			
			System.out.println("String: ["+string+"]");
			System.out.println("int: ["+integer+"]");
			System.out.println("double: ["+doub+"]");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
