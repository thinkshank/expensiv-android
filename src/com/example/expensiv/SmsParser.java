package com.example.expensiv;

import java.util.HashMap;
import java.util.Scanner;

import com.example.expensiv.shared.Const;

import android.inputmethodservice.ExtractEditText;
import android.util.Log;

public class SmsParser {
	
	public static HashMap<String, String> id_name = new HashMap<String, String>();
	public static HashMap<String, String> number_id = new HashMap<String, String>();
	static{
		number_id.put("9920979434", Const.BANK_ID_DEFAULT);
		id_name.put(Const.BANK_ID_DEFAULT, "SHASHANK");
		
		number_id.put("9920449704", Const.BANK_ID_SONYU);
		id_name.put(Const.BANK_ID_SONYU, "SONYU");
		
		number_id.put("FROMSC", Const.BANK_ID_STANC);
		id_name.put(Const.BANK_ID_STANC, "STANDARD-CHARTERED");
		
		number_id.put("ICICIB", Const.BANK_ID_ICICI);
		id_name.put(Const.BANK_ID_ICICI, "ICICI");		
	}
	
	private SmsExtractor extractor;
	private String bankid; 
	
	public SmsParser()
	{
			
	}
	
	public String setBank(String senderphone){
		for(String num : number_id.keySet()){
			if(num!=null)
				num.replace(" ", "");
			
			if(senderphone.contains(num)){
				this.bankid = number_id.get(num);
				this.extractor = SmsExtractor.getExtractor(this.bankid);
				return id_name.get(this.bankid);
			}
		}
		
		return null;
	}
	
	public String getBankName(){
		if(this.bankid!=null){
			return id_name.get(this.bankid);
		}		
		return null;
	}
	
	
	public String getCostFromMsg(String str) {
		
		return this.extractor.getCostFromMsg(str);
	}
	
	public String getCategory(String str){
		return this.extractor.getCategory(str);
	}
	
	public String getSubCategory(String str){
		return this.extractor.getSubCategory(str);
	}
	
	public String getDate(String str){
		return this.extractor.getDate(str);
	}
	
	// debit credit withdrawal
	public String getType(String str){
		return this.extractor.getType(str);
	}
	
	public String getTitle(String str){
		return this.extractor.getTitle(str);
	}
	
}
