package com.moodykettle.unittests.database;

import java.io.*;
//import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;

import oracle.jdbc.driver.OracleCallableStatement;
//import oracle.jdbc.driver.OraclePreparedStatement;
import oracle.jdbc.driver.OracleStatement;
import oracle.jdbc.driver.OracleTypes;

public class MyDBManager {

	public static void registerJDBCDriver(String jdbcDriver)
	{
		try
		{
			//register the jdbc driver (in our case the Oracle Driver)
			Class.forName(jdbcDriver).newInstance();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static Connection establishConnection(String connectionString, String userName, String passPhrase)
	{
		Connection conn = null;
		try
		{
			//establish connection			
			conn = DriverManager.getConnection(connectionString, userName, passPhrase);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	
	/*
	 * example how to call stored procedure
	 */
	public static String GetPassPhraseExpiryByUser(Connection conn, String userName)
	{
		java.sql.Date returnedDate = null;
		try
		{
			//prepare the interface to execute SQL stored procedures
			OracleCallableStatement cs = (OracleCallableStatement)conn.prepareCall("{call UGPAUpdUser.GetPassPhraseExpiryByUser(?,?,?,?)}");
			cs.setString(1, userName); // userName
			cs.registerOutParameter(2, OracleTypes.DATE); // expiry Date
			cs.registerOutParameter(3, OracleTypes.NUMBER); // Number (Oracle Error)			
			cs.registerOutParameter(4, OracleTypes.VARCHAR); // Description (Oracle Error)			
			cs.execute();
			
			// check for returned date or an error
			returnedDate = cs.getDate(2);
			System.out.println("Returned date: "+returnedDate.toString());
			long errorCode = cs.getLong(3);
			if(errorCode == 0)
			{
				java.util.Date todaysDate = new java.util.Date();
				if ((returnedDate == null) || (returnedDate.before(todaysDate)) || (returnedDate.equals(todaysDate)))
				{
					System.out.println("WARNING - PASSPHRASE EXPIRED!");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return returnedDate.toString();
	}
	
	/*
	 * example how to execute a sql query
	 */
	public static void executeSQLQuery(Connection conn, String sql)
	{
		try
		{
			System.out.println("SQL: ["+sql+"]");
			OracleStatement ps = (OracleStatement)conn.createStatement();
			ResultSet rs = ps.executeQuery(sql);			
			//int type = rs.getType();
			System.out.println("+-------+----------------------------------------+");				
			System.out.println("| ID \t| NAME \t\t\t\t\t |");
			System.out.println("+-------+----------------------------------------+");			
			while (rs.next()) 
			{
				//String coffeeName = rs.getString("COF_NAME");
				int applDataelementID = rs.getInt("APPL_DATAELEMENT_ID");
				String name = rs.getString("NAME");
				//float price = rs.getFloat("PRICE");
				//int sales = rs.getInt("SALES");
				//int total = rs.getInt("TOTAL");
				//System.out.println(coffeeName + "\t" + supplierID + "\t" + price + "\t" + sales + "\t" + total);
				String tab = "\t\t\t";
				//if(name.length() > 19 && name.length() < 21) tab = "\t\t";
				if(name.length() > 20) tab = "\t\t";
				if(name.length() < 12) tab = "\t\t\t\t";
				if(name.length() == 12) tab = "\t\t\t\t";
				if(name.length() < 6) tab = "\t\t\t\t\t";
				System.out.println("| "+applDataelementID+" \t| "+name+" "+tab+" |");
			}
			System.out.println("+-------+----------------------------------------+");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			Scanner sc = new Scanner(System.in);
			System.out.println("Input propertie file: ");
			String str = sc.nextLine();
			Properties prop = new Properties();
			File f = new File(str);
			FileInputStream fin = new FileInputStream(f);
			prop.load(fin);
			String connectionString = prop.getProperty("connection_string");
			String jdbcDriver = prop.getProperty("jdbc_driver");
			String userName = prop.getProperty("user_name");
			String passPhrase = prop.getProperty("pass_phrase");
			System.out.println("connection_string: "+connectionString);
			System.out.println("jdbc_driver: "+jdbcDriver);
			System.out.println("user_name: "+userName);
			System.out.println("pass_phrase: "+passPhrase);
			
			registerJDBCDriver(jdbcDriver);
			
			Connection conn = establishConnection(connectionString, userName, passPhrase);
			
			//use stored procedure to check pass phrase expire date by user
			String date = GetPassPhraseExpiryByUser(conn, userName);
			
			//display table content by executing sql query
			executeSQLQuery(conn, "SELECT * FROM CI_APPL_DATAELEMENT");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
