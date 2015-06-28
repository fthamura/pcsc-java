package com.moodykettle.problems.fraction;

import java.text.NumberFormat;

public class FractionProblem {
	public static void fractionProblem()
	{
		float f = 0.1F;
		for(int i = 1; i < 23; i++)
		{
			f = f + 0.1F;
		}
		NumberFormat nf = java.text.NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(20);
		nf.setMinimumFractionDigits(20);
		String s = nf.format(f);
		System.out.println(s);
		
	}
}
