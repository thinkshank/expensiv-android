package com.example.expensiv;

import java.text.SimpleDateFormat;
import java.util.List;



import com.example.expensiv.db.Expenses;
import com.example.expensiv.shared.Common;


import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ExpenseItemAdapter extends ArrayAdapter 
{

	Context context;
	List data;
	
	public ExpenseItemAdapter(Context context,  List data) {
		super(context,R.layout.view_expense_item_row, data);
		this.context = context;
		
		this.data = data;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		Log.e("shashank", "inflater  - "+inflater);
		View row  = inflater.inflate(R.layout.view_expense_item_row, parent, false);
		Log.e("shashank", ""+row);
		
		TextView cost = (TextView)row.findViewById(R.id.part_cost);
		TextView titleDate = (TextView)row.findViewById(R.id.part_title_date);
		TextView details = (TextView)row.findViewById(R.id.part_details);
		
		Expenses expense = (Expenses)this.data.get(position);
		cost.setText(expense.getCost());
		titleDate.setText(expense.getTitle() + " - " + Common.getDateCompatible(expense.getDate()));
		details.setText(expense.getCategory() + " > " + expense.getSubCategory());
			 	
		return row;
		
	}
	
	

}
