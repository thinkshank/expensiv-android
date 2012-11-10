package com.example.expensiv;

import java.util.Scanner;

import com.example.expensiv.shared.Const;

import android.util.Log;

public class SmsParser {

	public static String getCostFromMsg(String str) {
		Scanner scanner = new Scanner(str);
		String inr = scanner.findInLine(Const.PATTERN_INR_RS);
		if (inr != null && inr.length() > 0) {
			Log.e("shashank", " found INR string : " + inr);
			//strip all non-digits like . and , e.g. INR40,000.00
			inr= inr.replaceAll(Const.PATTERN_NON_DIGITS_OR_DOT, "");
			//strip trailing '.'s  e.g. .112.12 that can be returned from Rs. 100.00 
			inr= inr.replaceAll(Const.PATTERN_TRAILING_DOT,"");
			
			Scanner numscanner = new Scanner(inr);
			String num = numscanner.findInLine(Const.PATTERN_DIGITS_OR_DOT_ONLY);
			Log.e("shashank", "extracted String : " + num);
			if (num != null && num.length() > 0) {
				double d = Double.valueOf(num);
				Log.e("shashank", "Double representation " + d);
				return num;
			}
		}
		return null;
	}
}
