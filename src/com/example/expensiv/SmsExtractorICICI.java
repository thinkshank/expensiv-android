package com.example.expensiv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import android.util.Log;

import com.example.expensiv.shared.Common;
import com.example.expensiv.shared.Const;

public class SmsExtractorICICI extends SmsExtractorDefault {

  	public static final String id = Const.BANK_ID_ICICI;
	public static final String PATTERN_DATE = "\\d+/\\d+/\\d+";
	public static final String DATE_FORMAT_FOR_PARSING = "dd/MM/yy";
	public static final String PATTERN_IS_DEBIT = "(?i)(.debited.|(.card.purchase.))";
	public static final String PATTERN_IS_CREDIT = "(?i)credited";
	public static final String PATTERN_IS_ATM = "(?i)ATM";
	public static final String PATTERN_IS_WITHDRAWAL ="(?i)(withdrawal|wdl)";
	    
	public SmsExtractorICICI() {
		super(id);
	}

	@Override
	public String getBankId() {
		
		return id;
	}
	
	
	@Override
	public String getType(String msgbody) {
		if (!Common.has(msgbody)){
			return null;
		}
		
		Scanner scanner = new Scanner(msgbody);
		String debit = scanner.findInLine(PATTERN_IS_DEBIT);
		String credit = scanner.findInLine(PATTERN_IS_CREDIT);
		if(debit!=null){
			Log.e("shashank", " getType : " + Const.DEBIT);
			return Const.DEBIT;
		}else if(credit!=null){
			Log.e("shashank", " getType : " + Const.CREDIT);
			return Const.CREDIT;
		}
		return null;
	}
	
	@Override
	public String getSubCategory(String str) {
		if (!Common.has(str) || !Common.has(getType(str))){
			return null;
		}
		
		if(getType(str).equals(Const.DEBIT)){
			Scanner scanner = new Scanner(str);
			String isAtm = scanner.findInLine(PATTERN_IS_ATM);
			String isWithdrawal = scanner.findInLine(PATTERN_IS_WITHDRAWAL);
			if(Common.has(isAtm)&& Common.has(isWithdrawal)){
				return "WITHDRAWAL";
			}
		}
		return null;
	}

	@Override
	public String getCategory(String smsbody) {
		if (!Common.has(getType(smsbody)) || !Common.has(getType(smsbody))){
			return null;
		}
		
		if(getType(smsbody).equals(Const.DEBIT)){
			return "DEBIT";
		}else if(getType(smsbody).equals(Const.CREDIT)){
			return "CREDIT";
		}
		return null;
	}

	@Override
	public String getDate(String smsbody) {
		if (!Common.has(getType(smsbody))){
			return null;
		}
		
		Scanner s = new Scanner(smsbody);
		String ddmmyy = s.findInLine(PATTERN_DATE);
		
		//// try parsing the date and return parsed date or return current date 
		if(ddmmyy!=null){						
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_FOR_PARSING);
			try {
				Date date = sdf.parse(ddmmyy);
				return Common.getUnixTimestampFromDateObject(date);
			} catch (ParseException e) {
				return Common.getUnixTimestampFromCalendar(Calendar.getInstance());
			}
		}
		return null;
	}
}
