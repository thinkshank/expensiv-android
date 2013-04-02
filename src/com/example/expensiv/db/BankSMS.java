package com.example.expensiv.db;



public class BankSMS {
	
	private long ID;
	private String serviceProvider;
	private String title;
	private String phoneNo;
	private String description;
	private String smsText;
	private String createdBy;
	
	
	public long getID() {
		return ID;
	}


	public void setID(long iD) {
		ID = iD;
	}


	public String getServiceProvider() {
		return serviceProvider;
	}


	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getSmsText() {
		return smsText;
	}


	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


		@Override
	public String toString() {
	return(serviceProvider + (title!=null? " - " + title : ""));
	}
}
