package com.example.expensiv;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.expensiv.db.Expenses;
import com.example.expensiv.db.ExpensesDatasource;
import com.example.expensiv.shared.Common;
import com.example.expensiv.shared.Intents;

public class EditExpense extends Activity {
	
	private ExpensesDatasource datasource;
	Spinner debitcredit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        Bundle extras = getIntent().getExtras();
        long expenseIdToEdit = extras.getLong("expense_id");
        
        datasource = new ExpensesDatasource(this);
        datasource.open();
        
        Expenses expenseToEdit = datasource.getExpenseById(expenseIdToEdit);
        
        EditText id = (EditText )findViewById(R.id.hidden_id);
        EditText cost = (EditText )findViewById(R.id.txt_cost);
        EditText title = (EditText )findViewById(R.id.txt_title);
        DatePicker date = (DatePicker)findViewById(R.id.dp_editExpenseDate);
        EditText category = (EditText )findViewById(R.id.txt_category);
        EditText subcategory = (EditText )findViewById(R.id.txt_sub_category);
        debitcredit = (Spinner)findViewById(R.id.edit_debitcredit);
        
        
        id.setText(Long.toString(expenseToEdit.getId()));
        cost.setText(expenseToEdit.getCost());
        title.setText(expenseToEdit.getTitle());
        Calendar cal = Common.getCalendarFromUnixTimestamp(expenseToEdit.getDate());
        Common.setDateOnDatePicker(date, cal);
        category.setText(expenseToEdit.getCategory());
        subcategory.setText(expenseToEdit.getSubCategory());
        Log.e("shashank","debitcredit" + expenseToEdit.getDebitCredit());
        
        ArrayAdapter<CharSequence> adapterDebitCredit = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item, new String[] {
						"Debit", "Credit" });
		debitcredit.setAdapter(adapterDebitCredit);
		debitcredit.setSelection(adapterDebitCredit.getPosition(Common.debitCreditFromCD(expenseToEdit.getDebitCredit())));        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_expense, menu);
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
    
    //// xml onClick handler ////
    public void saveToDB(View view){
    	Log.d("shashank", "onClick(View view) called");    	
    	
		if(view.getId() == R.id.save){
			EditText title = (EditText)findViewById(R.id.txt_title);
			EditText cost = (EditText)findViewById(R.id.txt_cost);
			DatePicker date = (DatePicker)findViewById(R.id.dp_editExpenseDate);
			EditText category = (EditText)findViewById(R.id.txt_category);
			EditText subCategory = (EditText)findViewById(R.id.txt_sub_category);
			
			String strTitle = title.getText().toString();
			String strCost = cost.getText().toString();
			String strDate = Common.getUnixTimestampFromDatepicker(date);
			String strCategory = category.getText().toString();
			String strSubCategory = subCategory.getText().toString();
			String strDebitCredit = Common.debitCreditToCD(debitcredit.getSelectedItem().toString());
			
			Log.d("shashank", "saving value " + title.getText().toString() );
			try{
			//datasource.createExpense(title.getText().toString());
			long id = getIntent().getExtras().getLong("expense_id");
			
			Expenses addedExpense = datasource.updateExpense(id, strTitle, strDate, strCost, strCategory, strSubCategory,strDebitCredit);
			Log.d("shashank", "saved to db");
			
			getAlertDialogOk(addedExpense).show();
			
			}
			catch(Exception e){
				e.printStackTrace();
				getAlertDialogError().show();
			}
		}
    		
    }
    
    
    public void delete(View view){
    	EditText id = (EditText )findViewById(R.id.hidden_id);
    	Toast.makeText(this, "deleting... "+ id.getText(), Toast.LENGTH_LONG).show();
    	
    	if(view.getId() == R.id.delete){
			
			
						
			
			
			try{
			datasource.deleteExpense(id.getText().toString());			
			

			
			Toast.makeText(this, "deleted "+ id.getText() + " !!! ", Toast.LENGTH_LONG).show();
			Intent intent = Intents.MainActivity(this);//new Intent(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			}
			catch(Exception e){
				e.printStackTrace();
				getAlertDialogError().show();
			}
		}
    	
    }
    ////xml onClick handler ////
    
    
    
    //// xml onClick handler for menus////
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
    
    public void showViewAll(MenuItem menuitem){
    	Intent intent = Intents.MainActivity(this);//new Intent(this, MainActivity.class);
    	startActivity(intent);
    }
    //// xml onClick handler for menus////
    
    
    
    ///// dialog boxes ////
    public AlertDialog getAlertDialogOk(Expenses addedExpense){
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(addedExpense.getTitle() + " - " + 
				   addedExpense.getCost() + " - " + 
				   Common.getReadableStringFromUnixTimestamp(addedExpense.getDate()))
		.setTitle("Updated");
		builder.setPositiveButton(R.string.viewAll, new DialogInterface.OnClickListener() {				
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// user clicked ADD MORE
				Intent intent = new Intent(EditExpense.this, MainActivity.class);
		 	startActivity(intent);
		 	finish();
			}
		});
		builder.setNegativeButton(R.string.addMore, new DialogInterface.OnClickListener() {				
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// user clicked ADD MORE
				Intent intent = new Intent(EditExpense.this, EditExpense.class);
		 	startActivity(intent);
		 	finish();
			}
		});
		return builder.create();
    }
    
    
    public AlertDialog getAlertDialogError(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Error in creating")
		.setTitle("Error");
		return builder.create();
    }
    
    ///// dialog boxes ////
    
}
