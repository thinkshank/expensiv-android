package com.example.expensiv;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.expensiv.db.Expenses;
import com.example.expensiv.db.ExpensesDatasource;
import com.example.expensiv.shared.Common;
import com.example.expensiv.shared.Const;
import com.example.expensiv.shared.ExpensivError;
import com.example.expensiv.shared.Intents;

public class AddNewExpense extends Activity {

	private static final String EXTRA_MSG_ID = Const.EXTRA_MSG_ID;
	private static final String EXTRA_SET_MONTH = Const.EXTRA_SET_MONTH;
	private static final String EXTRA_FOR_MONTH = Const.EXTRA_FOR_MONTH;
	private ExpensesDatasource datasource;
	EditText title ; //= (EditText) findViewById(R.id.txt_title);
	EditText cost ; //= (EditText) findViewById(R.id.txt_cost);
	DatePicker date ; //= (DatePicker) findViewById(R.id.dp_expenseDate);
	//EditText txtdate; 
	EditText category ; //= (EditText) findViewById(R.id.txt_category);
	EditText subCategory ; //= (EditText) findViewById(R.id.txt_sub_category);
	String msg_id = null;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_expense);
		
		datasource = new ExpensesDatasource(this);
		datasource.open();
		
		initFieldsById();
		
		Intent intent = this.getIntent();
		if (intent != null && intent.hasExtra(EXTRA_MSG_ID)) {			
			String msg_id = intent.getExtras().getString(EXTRA_MSG_ID);
			
			if (msg_id != null && msg_id.length() > 0) {
				this.msg_id = msg_id;
				//Toast.makeText(this, "fetch data from msg id " + msg_id,
				//		Toast.LENGTH_LONG).show();
				
				Log.e("shashank", "will auto fill for msg id " + msg_id);				
				autofillFromSms(msg_id);
				
			}
		}		

		DatePicker datepicker = (DatePicker) findViewById(R.id.dp_expenseDate);
		if(intent.hasExtra(EXTRA_SET_MONTH)){
			int monthToSet = Integer.parseInt(intent.getStringExtra(EXTRA_SET_MONTH));
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, monthToSet);
			Common.setDateOnDatePicker(datepicker, cal);
		}else{
			Common.setCurrentDateOnDatePicker(datepicker);
		}
		
		populateCatogoryAutoComplete();
		
	/*	txtdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = Intents.DateWidgets1(AddNewExpense.this);
				startActivityForResult(intent, RESULT_OK);				
			}
		});*/
		

	}

	
	private void initFieldsById() {
		this.title = (EditText) findViewById(R.id.txt_title);
		this.cost = (EditText) findViewById(R.id.txt_cost);
		this.date = (DatePicker) findViewById(R.id.dp_expenseDate);
		this.category = (EditText) findViewById(R.id.txt_category);
		this.subCategory = (EditText) findViewById(R.id.txt_sub_category);
		//this.txtdate = (EditText) findViewById(R.id.txt_date);
	}


	private void autofillFromSms(String msg_id) {
		
		Uri sms = Uri.parse("content://sms/inbox");
        Cursor cursor = managedQuery(sms, null, "_id = " + msg_id , null, null);
        cursor.moveToFirst();
        Log.e("shashank" , " Found msg for id " + msg_id + " : " + cursor.getCount());
        if(cursor!=null && cursor.getCount() > 0){
        	cursor.moveToFirst();
        	String smsBody = cursor.getString(cursor.getColumnIndex("body"));
        	title .setText(smsBody);
        	String smsSender = cursor.getString(cursor.getColumnIndex("address"));
        	category.setText(smsSender);
        	
        	
            String costFromMsg = SmsParser.getCostFromMsg(smsBody);
            if(costFromMsg!=null && costFromMsg.length()>0){
            	
        		cost.setText(costFromMsg);
    		}
    		
    		
        }
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_add_new_expense, menu);
		return true;
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	// // xml onClick handler ////
	public void saveToDB(View view) {
		Log.d("shashank", "onClick(View view) called");

		if (view.getId() == R.id.save) {			

			String strTitle = title.getText().toString();
			String strCost = cost.getText().toString();
			String strDate = Common.getUnixTimestampFromDatepicker(date); 
			String strCategory = category.getText().toString();
			String strSubCategory = subCategory.getText().toString();
			
			ExpensivError error = validate(strTitle, strCost, strDate, strCategory, strSubCategory);
			if(error!=null){
				getAlertDialogError(error.getErrorMessage()).show();
			}else{
				Log.d("shashank", "saving value " + title.getText().toString());
				try {
					Expenses addedExpense =datasource.createExpense(strTitle, strDate, strCost, strCategory, strSubCategory, msg_id);
					Log.d("shashank", "saved to db");

					getAlertDialogOk(addedExpense).show();

				} catch (Exception e) {
					e.printStackTrace();
					getAlertDialogError().show();
				}
			}

			
		}

	}
	
	private ExpensivError validate(String title, String cost, String date, String category, String subcategory){
		boolean ok = true;
		String error = "";
		if(!Common.has(title) || !Common.has(cost)){
			error = "Oww.. you missed out on the cost and title.";
			ok = false;
			return new ExpensivError(error);
		}
		
		if(!Common.has(category) && !Common.has(subcategory)){
			error = "You should at least give a category or a sub-category";
			ok = false;
			return new ExpensivError(error);
		}
		
		try{
			double costForValidation = Double.parseDouble(cost);
		}
		catch(Exception e){
			e.printStackTrace();
			error = "Enter only numbers for cost";
			ok = false;
			return new ExpensivError(error);
		}		
	
		return null;
	}
	// //xml onClick handler ////

	// // xml onClick handler for menus////
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_viewAll:
			showViewAll(item);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void showViewAll(MenuItem menuitem) {
		Intent intent = Intents.MainActivity(this);
		startActivity(intent);
	}

	// // xml onClick handler for menus////

	// /// dialog boxes ////
	public AlertDialog getAlertDialogOk(Expenses addedExpense) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				addedExpense.getTitle() + " - " + addedExpense.getCost()
						+ " - " + Common.getReadableStringFromUnixTimestamp(addedExpense.getDate())).setTitle("Added");
		final int forMonth = Common.getCalendarFromUnixTimestamp(addedExpense.getDate()).get(Calendar.MONTH);
				
		builder.setPositiveButton(R.string.viewAll,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// user clicked ADD MORE
						Intent intent = Intents.MainActivity(AddNewExpense.this);
						intent.putExtra(EXTRA_FOR_MONTH, ""+forMonth);
						startActivity(intent);
						finish();
					}
				});
		builder.setNegativeButton(R.string.addMore,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// user clicked ADD MORE
						Intent intent = Intents.AddNewExpense(AddNewExpense.this);
						startActivity(intent);
						finish();
					}
				});
		return builder.create();
	}

	public AlertDialog getAlertDialogError(){
		return getAlertDialogError(null);
	}
	
	public AlertDialog getAlertDialogError(String errormessage) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if(Common.has(errormessage)){
			builder.setMessage(errormessage).setTitle("Error");
		}else{
			builder.setMessage("Error in creating").setTitle("Error");	
		}
		
		return builder.create();
	}

	// /// dialog boxes ////
	
	// populate spinner
	private void populateCatogoryAutoComplete(){
		AutoCompleteTextView autoCategory = (AutoCompleteTextView)findViewById(R.id.auto_category);		
		//String[] categories = new String[]{"food", "travel", "shopping"};
		ArrayList<String> categories = datasource.getDistinctCategories();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categories);
		autoCategory.setAdapter(adapter);
	}
	
	

	  // where we display the selected date and time
    private TextView mTimeDisplay;


   

    private void updateDisplay(int hourOfDay, int minute) {
        mTimeDisplay.setText(
                    new StringBuilder()
                    .append(pad(hourOfDay)).append(":")
                    .append(pad(minute)));
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
	
}
