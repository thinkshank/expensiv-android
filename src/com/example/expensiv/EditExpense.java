package com.example.expensiv;

import java.util.ArrayList;
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
import com.example.expensiv.shared.Const;
import com.example.expensiv.shared.Intents;

public class EditExpense extends Activity {
	
	private ExpensesDatasource datasource;
	Spinner debitcredit;
	private EditText category;
	private EditText subCategory;
	private DatePicker date;
	private EditText title;
	private EditText cost;
	private EditText id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        Bundle extras = getIntent().getExtras();
        long expenseIdToEdit = extras.getLong("expense_id");
        
        datasource = new ExpensesDatasource(this);
        datasource.open();
        
        Expenses expenseToEdit = datasource.getExpenseById(expenseIdToEdit);
        
        id = (EditText )findViewById(R.id.hidden_id);
        cost = (EditText )findViewById(R.id.txt_cost);
        title = (EditText )findViewById(R.id.txt_title);
        date = (DatePicker)findViewById(R.id.dp_editExpenseDate);
        category = (EditText )findViewById(R.id.txt_category);
        subCategory = (EditText )findViewById(R.id.txt_sub_category);
        debitcredit = (Spinner)findViewById(R.id.edit_debitcredit);
        
        
        id.setText(Long.toString(expenseToEdit.getId()));
        cost.setText(expenseToEdit.getCost());
        title.setText(expenseToEdit.getTitle());
        Calendar cal = Common.getCalendarFromUnixTimestamp(expenseToEdit.getDate());
        Common.setDateOnDatePicker(date, cal);
        category.setText(expenseToEdit.getCategory());
        subCategory.setText(expenseToEdit.getSubCategory());
        Log.e("shashank","debitcredit" + expenseToEdit.getDebitCredit());
        
        ArrayAdapter<CharSequence> adapterDebitCredit = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item, new String[] {
						Const.DEBIT_TEXT, Const.CREDIT_TEXT, Const.WITHDRAW_TEXT });
        adapterDebitCredit.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		debitcredit.setAdapter(adapterDebitCredit);
		debitcredit.setSelection(adapterDebitCredit.getPosition(Common.debitCreditFromCode(expenseToEdit.getDebitCredit())));        
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
			String strDebitCredit = Common.debitCreditToCode(debitcredit.getSelectedItem().toString());
			
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
    
    ////xml onClick handler ////
	public void showCategoryChoices(View view){
		getCategoryChoicesDialog().show();
	}
	
	//// xml onClick handler ////
	public void showSubCategoryChoices(View view){
		getSubCategoryChoicesDialog().show();
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
				// user clicked "View All"
				Intent intent = new Intent(EditExpense.this, MainActivity.class);
		 	startActivity(intent);
		 	finish();
			}
		});
		//// no need to Add More after editing an entry 
		/*builder.setNegativeButton(R.string.addMore, new DialogInterface.OnClickListener() {				
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// user clicked ADD MORE
				Intent intent = new Intent(EditExpense.this, EditExpense.class);
		 	startActivity(intent);
		 	finish();
			}
		});*/
		return builder.create();
    }
    
    
    public AlertDialog getAlertDialogError(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Error in creating")
		.setTitle("Error");
		return builder.create();
    }
    
    private AlertDialog getCategoryChoicesDialog(){
		  Log.e("shashank", "getChooseCategoryDialog() called");
		   
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	
	    	
	    	ArrayList<String> temp = null;
	    	if (Common.has(getCategoryText())){
	    		Log.d("shashank", "getDistinctCategoriesLike");
	    		temp = datasource.getDistinctCategoriesLike(getCategoryText()); 
			}else{
				Log.d("shashank", "getDistinctCategories");
				temp = datasource.getDistinctCategories();
			}
	    	
	    	final ArrayList<String> categoryChoices  = temp;
	    	

	    	String title = ""; 
	    	if(categoryChoices != null && categoryChoices.size() > 0)
	    	{title = "Select category";}
	    	else
	    	{title = "No exisitng categories";}
	    	builder.setTitle(title);
	    	
	    	final ArrayAdapter<String> adapter =
	    	new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryChoices);
	    	
	    	builder.setAdapter(adapter, new DialogInterface.OnClickListener() {		
				
				public void onClick(DialogInterface dialog, int which) {					
					setCategoryText(adapter.getItem(which));
				}
			});	    	
	    	
	    	return builder.create();	    	    			
	    }
	  
	  private AlertDialog getSubCategoryChoicesDialog(){
		  Log.e("shashank", "getSubCategoryChoicesDialog() called");
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setTitle("Select sub category");
	    	
	    	ArrayList<String> temp = null;
	    	if (Common.has(getSubCategoryText())){
	    		Log.d("shashank", "getDistinctSubCategoriesLike");
	    		temp = datasource.getDistinctSubCategoriesLike(getSubCategoryText()); 
			}else{
				Log.d("shashank", "getDistinctSubCategories");
				temp = datasource.getDistinctSubCategories();
			}
	    	
	    	final ArrayList<String> categoryChoices = temp;
	    	
	    	final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryChoices);
	    		    	
	    	builder.setAdapter(adapter, new DialogInterface.OnClickListener() {		
				
				public void onClick(DialogInterface dialog, int which) {					
					setSubCategoryText(adapter.getItem(which));
				}
			});	    	
	    	
	    	return builder.create();
	    	    			
	    }
    ///// dialog boxes ////
    
	  private void setCategoryText(String textToSet){
			category.setText(textToSet);
		}
	  
	  private String getCategoryText(){
			return category.getText().toString();
		}
	  
	  private void setSubCategoryText(String textToSet){
			subCategory.setText(textToSet);
		}
	  
	  private String getSubCategoryText(){
			return subCategory.getText().toString();
		}
	  
}
