package com.moodykettle.unittests.inheritance;

public class MyClass {

	public String str = "myClass";
	private String str2 = "privateMyClass";
	protected String str3 = "protectedMyClass";
	
	public int i = init();
	
	public MyClass()
	{
		System.out.println(this+" con");
	}
	
	private String getPrivate()
	{
		return str2;
	}

	protected String getProtected()
	{
		return str3;
	}
	
	private int init()
	{
		System.out.println(this+" init");
		return 45;
	}
}
