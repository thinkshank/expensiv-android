package com.example.expensiv;

import java.util.Scanner;
import java.util.regex.Pattern;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.example.expensiv.shared.Const;

public class TestMe extends TestCase{
	
	String str1 = "dear customer,"+
			"your account 247xxxx9197 has been debited on 25/11/12 by INR 900.00 towards ATM cash withdrawal - StanChart";

			String str2 = "dear customer,"+
			"your account 247xxxx9197 has been debited on 25/11/12 by INR 24.00 towards purchases on your debit card- StanChart";

			String str3 = "dear customer,"+
			"your account 247xxxx9197 has been debited on 25/11/12 by INR 24.00 towards your Online Payment request- StanChart";


			String str4 = "dear customer,"+
			"your account 247xxxx9197 has been debited on 25/11/12 by INR 24.00 towards Funds Transfer- StanChart";
			
			String str5 = "dear customer,"+
					"your account 247xxxx9197 has been credited on 25/11/12 by INR 24.00 towards Funds Transfer- StanChart";
	
			String str6 ="your one-time password is 123123 for Direct Debit Online Paymeent. do no share with anyone";
	public TestMe(String name){
		
		super(name);
		
		
	}
	
	public static Test suite(){
		return new TestSuite(TestMe.class);
	}
	
	public void testIsDebitSMS(){
		String s = str5;
		Scanner scanner = new Scanner(s);
		System.out.println(s);
		System.out.println(scanner.findInLine(Const.StandardChartered.PATTERN_IS_DEBIT));
		
	}
	
	public void testSmsParser(){
		String str = str1;
		SmsParser smsParser = new SmsParser();
		System.out.println(smsParser.setBank("9920979434"));
		System.out.println(smsParser.getCostFromMsg(str));
	}
	
}
