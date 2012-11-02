package com.example.expensiv;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.expensiv.db.Expenses;
import com.example.expensiv.db.ExpensesDatasource;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	String [] myStringArray = {"asdfasd","asdfasdf","qwetrqwerqwe"};
	private ExpensesDatasource datasource;
	private int month = Calendar.getInstance().get(Calendar.MONTH);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if(getIntent().hasExtra("forMonth")){
        	month = Integer.parseInt(getIntent().getStringExtra("forMonth"));
        }
        
        datasource = new ExpensesDatasource(this);
        datasource.open();
        
        datasource.updateDateFormat();
        
        List<Expenses> values = datasource.getAllExpensesForMonth(month);
        
        //final ArrayAdapter<Expenses> adapter = 
        //		new ArrayAdapter<Expenses>(this,
        //								   android.R.layout.simple_list_item_1,
        //								   values);
        
        final ExpenseItemAdapter adapter = 
        		new ExpenseItemAdapter(this,values);
        
    	//ArrayAdapter<String> adapter =
    	//new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray);
    	
        ListView listview = (ListView)findViewById(R.id.ListView1);
        listview.setAdapter(adapter);
        
        //// handle click on list item ////
        listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				Expenses expense = (Expenses)adapter.getItem(position);
				//Toast.makeText(MainActivity.this, "" + position + " - " + expense.getTitle() + " - " + expense.getId(), Toast.LENGTH_LONG).show();
				openExpenseForEdit(expense);
			}
        	
		});
        //// handle click on list item ////
        
        
        TextView totalExpense = (TextView)findViewById(R.id.totalExpense);
        totalExpense.setText(new SimpleDateFormat("MMM").format(new Date(1970, month,1)));
        totalExpense.append(" Total Expense - " + datasource.getTotalForMonth(month));
        
        //sendSMS("TD-12345", String message)
        
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

  
    
    
    //// xml onClick handlers ////
    public void addNewExpense(View view) {
    	Intent intent = new Intent(this, AddNewExpense.class);
    	startActivity(intent);    	
	}
    
    public void prev(View view) {
    	Intent intent = new Intent(this, MainActivity.class);
    	intent.putExtra("forMonth", "" + (month-1));
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);    	
	}
    
    public void next(View view) {
    	Intent intent = new Intent(this, MainActivity.class);
    	intent.putExtra("forMonth", "" + (month+1));
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);    	
	}    
    
    public void addNewExpenseBySms(View view){
    	Intent intent = new Intent(this, ReadSmsSenders.class);
    	startActivity(intent);
    }
    
    public void viewCategoryWise(View view){
    	Intent intent = new Intent(this, CategoryDetails.class);
    	startActivity(intent);
    }
    ////xml onClick handlers ////
    
    
    //// related to menu //// 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    ////related to menu ////
    
    
    //// xml onClick handlers for menus ////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.menu_add:
        	addNewExpense(item);
            return true;
        case R.id.menu_category_wise:
        	showCategoryWise(item);
            return true;
        case R.id.menu_read_sms:
        	showReadSms(item);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void addNewExpense(MenuItem menuItem) {
    	Intent intent = new Intent(this, AddNewExpense.class);
    	startActivity(intent);    	
	}
    
    public void showCategoryWise(MenuItem menuItem) {
    	Intent intent = new Intent(this, CategoryDetails.class);
    	startActivity(intent);    	
	}
    
    public void showReadSms(MenuItem menuItem) {
    	Intent intent = new Intent(this, ReadSmsSenders.class);
    	startActivity(intent);    	
	}   
    //// xml onClick handlers for menus ////
    
    
    //// helper methods ////
    public void openExpenseForEdit(Expenses expense) {
    	Intent intent = new Intent(this, EditExpense.class);
    	intent.putExtra("expense_id", expense.getId());
    	startActivity(intent);    	
	}
    //// not used this one yet ////
    private void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
 
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
            new Intent(SENT), 0);
 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
            new Intent(DELIVERED), 0);
 
        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));
 
        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));        
 
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
        
    }
    
    //// helper methods ////
    
}
