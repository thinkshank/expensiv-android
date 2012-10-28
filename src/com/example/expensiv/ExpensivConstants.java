package com.example.expensiv;

import java.util.HashMap;

public class ExpensivConstants 
{
	private static HashMap<String, String> banks = new HashMap<String, String>();
	
	
	
	/**returns a hashmap (String bank_msg_no, String bank_name) >**/
	public static HashMap<String, String> getBankHashMap(){
		banks.put("5555", "Standard Chartered");
		banks.put("6666", "ICICI Bank");
		banks.put("7777", "SBI Bank");
		banks.put("8888", "Axis Bank");
		return banks;		
	}	
	
}
