package com.example.expensiv.db;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.expensiv.shared.Common;

public class Sms {

    private String id;
    private String phoneNumber;
    private Date receiveTime;
    private String smsBody;
    private String smsSubject;
    //private Person sender;
    private String contactId;
    private String from; 
    private Boolean isSave=false;

    public Sms() {
    }
    

	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public Date getReceiveTime() {
		return receiveTime;
	}


	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}


	public String getSmsBody() {
		return smsBody;
	}


	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}


	public String getSmsSubject() {
		return smsSubject;
	}


	public void setSmsSubject(String smsSubject) {
		this.smsSubject = smsSubject;
	}


	public String getContactId() {
		return contactId;
	}


	public void setContactId(String contactId) {
		this.contactId = contactId;
	}


	public Boolean getIsSave() {
		return isSave;
	}


	public void setIsSave(Boolean isSave) {
		this.isSave = isSave;
	}
    
	@Override
	public String toString() {
		String s  = "";
				
		if(Common.has(this.smsBody)){
			s+= this.smsBody;
		}
		
		if(this.receiveTime!=null){
			s+= " - " + new SimpleDateFormat("dd-MMM-yyyy").format(receiveTime);
		}
		
		return s;
	}
    
}
