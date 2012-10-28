package com.example.expensiv;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expensiv.db.Expenses;
import com.example.expensiv.db.ExpensesDatasource;

public class AddNewExpense extends Activity {

	private ExpensesDatasource datasource;
	EditText title ; //= (EditText) findViewById(R.id.txt_title);
	EditText cost ; //= (EditText) findViewById(R.id.txt_cost);
	DatePicker date ; //= (DatePicker) findViewById(R.id.dp_expenseDate);
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
		if (intent != null && intent.hasExtra("msg_id")) {			
			String msg_id = intent.getExtras().getString("msg_id");
			
			if (msg_id != null && msg_id.length() > 0) {
				this.msg_id = msg_id;
				//Toast.makeText(this, "fetch data from msg id " + msg_id,
				//		Toast.LENGTH_LONG).show();
				
				Log.e("shashank", "will auto fill for msg id " + msg_id);				
				autofillFromSms(msg_id);
				
			}
		}		

		setCurrentDateOnDatePicker();
		

	}

	
	private void initFieldsById() {
		this.title = (EditText) findViewById(R.id.txt_title);
		this.cost = (EditText) findViewById(R.id.txt_cost);
		this.date = (DatePicker) findViewById(R.id.dp_expenseDate);
		this.category = (EditText) findViewById(R.id.txt_category);
		this.subCategory = (EditText) findViewById(R.id.txt_sub_category);
		
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

	public void setCurrentDateOnDatePicker() {
		DatePicker datepicker = (DatePicker) findViewById(R.id.dp_expenseDate);
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		datepicker.init(year, month, day, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				Log.d("shashank", view.getDayOfMonth() + "/" + view.getMonth()
						+ "/" + view.getYear());
			}
		});
	}

	// // xml onClick handler ////
	public void saveToDB(View view) {
		Log.d("shashank", "onClick(View view) called");

		if (view.getId() == R.id.save) {			

			String strTitle = title.getText().toString();
			String strCost = cost.getText().toString();
			String strDate = date.getDayOfMonth() + "/" + (date.getMonth() + 1)
					+ "/" + date.getYear();
			String strCategory = category.getText().toString();
			String strSubCategory = subCategory.getText().toString();

			Log.d("shashank", "saving value " + title.getText().toString());
			try {
				// datasource.createExpense(title.getText().toString());
				Expenses addedExpense = datasource.createExpense(strTitle,
						strDate, strCost, strCategory, strSubCategory, msg_id);
				Log.d("shashank", "saved to db");

				getAlertDialogOk(addedExpense).show();

			} catch (Exception e) {
				e.printStackTrace();
				getAlertDialogError().show();
			}
		}

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
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	// // xml onClick handler for menus////

	// /// dialog boxes ////
	public AlertDialog getAlertDialogOk(Expenses addedExpense) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				addedExpense.getTitle() + " - " + addedExpense.getCost()
						+ " - " + addedExpense.getDate()).setTitle("Added");
		builder.setPositiveButton(R.string.viewAll,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// user clicked ADD MORE
						Intent intent = new Intent(AddNewExpense.this,
								MainActivity.class);
						startActivity(intent);
						finish();
					}
				});
		builder.setNegativeButton(R.string.addMore,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// user clicked ADD MORE
						Intent intent = new Intent(AddNewExpense.this,
								AddNewExpense.class);
						startActivity(intent);
						finish();
					}
				});
		return builder.create();
	}

	public AlertDialog getAlertDialogError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Error in creating").setTitle("Error");
		return builder.create();
	}

	// /// dialog boxes ////

}
