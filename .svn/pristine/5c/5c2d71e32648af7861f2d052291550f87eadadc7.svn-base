/**
 * 
 */

package com.moodykettle;

import com.moodykettle.problems.fraction.*;
import com.moodykettle.unittests.inheritance.*;
import com.moodykettle.unittests.regex.serialnumber.*;

/**
 * @author Rafal Jackiewicz
 *
 */

public class TestContainer {
	
	public static void test001()
	{
		FractionProblem.fractionProblem();
		System.out.println("-------------------------");
	}

	public static void test002()
	{
		SerialNumberValidator.onlyWordCharactersInString("000123");
		SerialNumberValidator.onlyWordCharactersInString("A000123");
		SerialNumberValidator.onlyWordCharactersInString("!000123");
		SerialNumberValidator.onlyDigitInString("000123");
		SerialNumberValidator.onlyDigitInString("A000123");
		SerialNumberValidator.onlyDigitInString("!000123");
		System.out.println("------------------------");
	}

	public static void test003()
	{
		MyClass a = new MyClass();
		System.out.println(a.str);
		MyClass b = a;
		b.str = "yourClass";
		System.out.println(a.str);
		OurClass c = new OurClass();		
		System.out.println(c.str);
		System.out.println("------------------------");
	}

	/**
	 * This is main class
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//test001();
		//test002();
		test003();
		
		
	}

}
