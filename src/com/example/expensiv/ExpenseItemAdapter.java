package com.example.expensiv;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.example.expensiv.db.Expenses;
import com.example.expensiv.shared.Common;
import com.example.expensiv.shared.Const;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ExpenseItemAdapter extends ArrayAdapter {

	Context context;
	List data;

	public ExpenseItemAdapter(Context context, List data) {
		super(context, R.layout.view_expense_item_row, data);
		this.context = context;
		this.data = data;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		// Log.e("shashank", "inflater  - "+inflater);
		View row = inflater.inflate(R.layout.view_expense_item_row, parent,
				false);
		Log.e("shashank", "" + row);

		TextView cost = (TextView) row.findViewById(R.id.part_cost);
		TextView titleDate = (TextView) row.findViewById(R.id.part_title_date);
		TextView details = (TextView) row.findViewById(R.id.part_details);
		TextView header = (TextView) row.findViewById(R.id.part_header);

		Expenses expense = (Expenses) this.data.get(position);
		cost.setText(expense.getCost());
		Log.e("shashank", expense.getCost() +" debcred - " + expense.getDebitCredit());
		if(Const.CREDIT.equals(expense.getDebitCredit())){
			cost.setTextColor(Color.parseColor("#00BB3F"));
		}
		else if(Const.WITHDRAW.equals(expense.getDebitCredit())){
			cost.setTextColor(Color.parseColor("#000000"));
		}
		// titleDate.setText(expense.getTitle() + " - " +
		// Common.getDateCompatible(expense.getDate()));
		titleDate.setText(expense.getTitle());
		details.setText(expense.getCategory() + " > "
				+ expense.getSubCategory());

		Calendar cal = Common.getCalendarFromUnixTimestamp(expense.getDate());

		Expenses lastExpense = null;
		Calendar lastcal = null;
		if (position > 0) {
			lastExpense = (Expenses) this.data.get(position - 1);
			lastcal = Common
					.getCalendarFromUnixTimestamp(lastExpense.getDate());
		}

		int currentDate = cal.get(Calendar.DAY_OF_MONTH);

		int lastdate = 0;
		if (lastcal != null) {
			lastdate = lastcal.get(Calendar.DAY_OF_MONTH);
		}

		Log.e("shashank", "currentdate" + currentDate);
		Log.e("shashank", "lastdate" + lastdate);

		if (currentDate != lastdate) {
			header.setText(Common.getDateReadable(expense.getDate()));
		} else {
			header.setVisibility(View.GONE);
		}

		return row;

	}

}
