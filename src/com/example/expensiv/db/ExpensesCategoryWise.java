package com.example.expensiv.db;

public class ExpensesCategoryWise {

	private String Category;
	private String Debits; 	// if any
	private String Credits; 	// if any
	private String Type; 		// D for debits and C for credits

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getDebits() {
		return Debits;
	}

	public void setDebits(String debits) {
		Debits = debits;
	}

	public String getCredits() {
		return Credits;
	}

	public void setCredits(String credits) {
		Credits = credits;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}	

}
