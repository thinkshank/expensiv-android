package com.example.expensiv;

import java.util.Calendar;

public class SmsExtractorDefault extends SmsExtractor {
	private String bankid;
	
	public SmsExtractorDefault(String bankid) {
	this.bankid = bankid;	
	}

	@Override
	public String getBankId() {
		return this.bankid;
	}

	@Override
	public String getCategory(String smsbody) {
		
		return null;
	}

	@Override
	public String getSubCategory(String smsbody) {
		
		return null;
	}

	@Override
	public String getDate(String smsbody) {
		
		return null;
	}

	@Override
	public String getType(String smsbody) {
		
		return null;
	}

	@Override
	public String getTitle(String smsbody) {
		
		return smsbody;
	}
	
	

}
