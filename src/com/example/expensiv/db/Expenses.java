package com.example.expensiv.db;

import com.example.expensiv.shared.Common;

public class Expenses {
	
	private long id;
	private String date;
	private String cost;
	private String title;
	private String category;
	private String subCategory;
	private String msg_id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String sub_category) {
		this.subCategory = sub_category;
	}
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	
	@Override
	public String toString() {
	return(title + " - " + cost + " - " + (date.length()>9?Common.getCalendarFromUnixTimestamp(date):date) + "\n" + category + " - " + subCategory);
	}
}
