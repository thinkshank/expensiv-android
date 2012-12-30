package com.example.expensiv;

import java.util.Scanner;
import java.util.regex.Pattern;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.example.expensiv.shared.Const;

public class TestMe //extends TestCase
{
	
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
			
			String icici1 = "Dear customer, Your ac xxxxxxxx9058 is credited with INR51,929 on 29 Dec. Info. SALARY. Your Net available bal is INR 53,423.39";
			
			String icici2 = "Your ac xxxxxxxx9058 is debited with INR1,200 ATM*CASH WDL*28-12-12*10:. Avbl Bal INR1,494.39. blah blah blah";
			
			String icici3 = "Dear customer, You have made a Debit Card purchase of INR 4699.50 in 22 Dec. Info.IPS*S MART SHAR. Your Net available bal is INR 2694.39";
					
	/*public TestMe(String name){
		
		super(name);	
		
	}*/
	
	public TestMe() {
	
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
	
	public void testICICICredit(){
		String str = icici1;
		SmsExtractorICICI i= new SmsExtractorICICI();
		System.out.println(i.getCostFromMsg(str));
		
	}
	
	public static void main(String[] args) {
		TestMe t = new TestMe();
		System.out.println("testICICICredit() ");
		System.out.println(t.icici1);		
		t.testICICICredit();
	}
}
