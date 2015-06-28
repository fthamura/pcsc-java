package com.moodykettle.unittests.file.xml;

public class Employee {
	public String name;
	public int id;
	public int age;
	public String type;
	
	Employee()
	{
		this.name = "";
		this.id = 0;
		this.age = 0;
		this.type = "";
	}
	
	Employee(String name,int id,int age,String type)
	{
		this.name = name;
		this.id = id;
		this.age = age;
		this.type = type;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}

	public void setAge(int age)
	{
		this.age = age;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String toString()
	{		
		StringBuffer sb = new StringBuffer();
		
		sb.append("-----------------");
		sb.append(System.getProperty("line.separator"));
		
		sb.append("NAME: "+name);
		sb.append(System.getProperty("line.separator"));

		sb.append("ID: "+id);
		sb.append(System.getProperty("line.separator"));
		
		sb.append("AGE: "+age);
		sb.append(System.getProperty("line.separator"));
	
		sb.append("TYPE: "+type);
		sb.append(System.getProperty("line.separator"));
		
		sb.append("-----------------");
		sb.append(System.getProperty("line.separator"));
		
		return sb.toString();
	}
}
