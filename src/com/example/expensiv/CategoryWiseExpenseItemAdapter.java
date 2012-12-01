package com.example.expensiv;

import java.util.List;

import com.example.expensiv.db.ExpensesCategoryWise;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoryWiseExpenseItemAdapter extends ArrayAdapter {
	Context context;
	List data;

	public CategoryWiseExpenseItemAdapter(Context context,
			int textViewResourceId, List data) {
		super(context, textViewResourceId, data);
		this.context = context;
		this.data = data;
		Log.e("shashank", this.getClass().getName() + " CONSTRUCTOR ");

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Log.e("shashank" , "getView" );
		// TODO Auto-generated method stub
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View row = inflater.inflate(
				R.layout.view_categorywise_expense_item_row, parent, false);

		ExpensesCategoryWise expensecategorywise = (ExpensesCategoryWise) this.data
				.get(position);

		TextView category = (TextView) row.findViewById(R.id.part_title);
		category.setText(expensecategorywise.getCategory());
		Log.e("shashank" , "category" + expensecategorywise.getCategory());
		
		TextView debit_credits = (TextView) row
				.findViewById(R.id.part_debits_credits);
		if ("D".equals(expensecategorywise.getType())) {
			debit_credits.setText(expensecategorywise.getDebits());
			debit_credits.setTextColor(Color.parseColor("#990033")); // red
		} else {
			debit_credits.setText(expensecategorywise.getCredits());
			debit_credits.setTextColor(Color.parseColor("#00BB3F")); // green
		}

		return row;
	}
}
