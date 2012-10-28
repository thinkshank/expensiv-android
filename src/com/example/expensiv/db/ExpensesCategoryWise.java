package com.example.expensiv.db;

public class ExpensesCategoryWise extends Expenses {

	private String Category;
	private String CategorySum;
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public String getCategorySum() {
		return CategorySum;
	}
	public void setCategorySum(String categorySum) {
		CategorySum = categorySum;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return(Category + " - " + CategorySum);
	}
	

}
