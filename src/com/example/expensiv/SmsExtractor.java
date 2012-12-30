package com.example.expensiv;

import java.util.Scanner;

import android.util.Log;

import com.example.expensiv.shared.Const;

public abstract class SmsExtractor{
	
	
	public static SmsExtractor getExtractor(String bankid){
		
		if(bankid.equals(Const.BANK_ID_DEFAULT)){
			Log.e("shashank", "using extractor SmsExtractorStandardChartered");
			return new SmsExtractorStandardChartered();
		}		
		if(bankid.equals(Const.BANK_ID_STANC)){
			Log.e("shashank", "using extractor SmsExtractorStandardChartered");
			return new SmsExtractorStandardChartered();
		}
		else if(bankid.equals(Const.BANK_ID_ICICI)){
			Log.e("shashank","using SmsExtractorICICI()");
			return new SmsExtractorICICI();
		}
		
		Log.e("shashank", "using extractor SmsExtractorDefault");
		return new SmsExtractorDefault(bankid);
	}
	
	public abstract String getBankId();

	public String getCostFromMsg(String str) {
		
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
			System.out.println("num : " + num );
			Log.e("shashank", "extracted String : " + num);
			if (num != null && num.length() > 0) {
				double d = Double.valueOf(num);
				Log.e("shashank", "Double representation " + d);
				return num;
			}
		}
		return null;
	}
	
	public abstract String getCategory(String smsbody);
	
	public abstract String getSubCategory(String smsbody);
	
	public abstract String getDate(String smsbody);
	
	// debit credit withdrawal
	public abstract String getType(String smsbody);
	
	public abstract  String getTitle(String smsbody);
	
}
