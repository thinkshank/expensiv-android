package com.example.expensiv.shared;

public class ExpensivError {

	String errorCode;
	String errorMessage;
	String hint;
	
	public ExpensivError(String errorMessage){
		setErrorMessage(errorMessage);
	}
	
	public ExpensivError(String errorMessage, String hint){
		setErrorMessage(errorMessage);
		setHint(hint);
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	
}
