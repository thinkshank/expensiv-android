package com.example.expensiv.db;

public class ExpensesSubCategoryWise extends Expenses {

	private String Subcategory;
	private String SubcategorySum;
	
	
	
	public String getSubcategory() {
		return Subcategory;
	}



	public void setSubcategory(String subcategory) {
		Subcategory = subcategory;
	}



	public String getSubcategorySum() {
		return SubcategorySum;
	}



	public void setSubcategorySum(String subcategorySum) {
		SubcategorySum = subcategorySum;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return(Subcategory + " - " + SubcategorySum);
	}
	

}
