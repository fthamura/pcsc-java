package com.moodykettle.unittests.utilitys.jni;

public class Console {
	
	  public static final short FOREGROUND_BLACK = 0x0;
	  public static final short FOREGROUND_BLUE  = 0x1;
	  public static final short FOREGROUND_GREEN = 0x2;
	  public static final short FOREGROUND_RED   = 0x4;
	  public static final short FOREGROUND_WHITE = 0x7;
	  public static final short FOREGROUND_INTENSITY = 0x8;

	  public static final short BACKGROUND_BLUE  = 0x10;
	  public static final short BACKGROUND_GREEN = 0x20;
	  public static final short BACKGROUND_RED   = 0x40;
	  public static final short BACKGROUND_INTENSITY = 0x80;
	
	
	/**
	 * Clears console output screen
	 * 
	 */
	native void cls();
	
	/**
	 * Retrieves current colors from the output console
	 * 
	 */
	native short getCurrentColors();
	
	/**
	 * Set current colors for the output console
	 * 
	 */
	native void setCurrentColors(short colors);
	
	static
	{
		System.loadLibrary("jni_console");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Console c = new Console();		
		c.cls();
		System.out.println("from java");
		c.cls();
		System.out.println("second from java");
		c.cls();
		short currentColors = c.getCurrentColors();
		System.out.println("currentColors = "+currentColors);
		short colors = FOREGROUND_GREEN+BACKGROUND_RED;
		System.out.println("colors = "+colors);
		c.setCurrentColors(colors);
		System.out.println("third from java");
	}

}
