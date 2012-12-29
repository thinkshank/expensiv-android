package com.example.expensiv;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.MailTo;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensiv.db.Expenses;
import com.example.expensiv.db.ExpensesDatasource;
import com.example.expensiv.shared.Const;
import com.example.expensiv.shared.Intents;

public class MainActivity extends Activity {
	
	private static final String EXTRA_FOR_MONTH = Const.EXTRA_FOR_MONTH;
	private static final String EXTRA_SET_MONTH = Const.EXTRA_SET_MONTH;
	
	private static final String EXTRA_FOR_YEAR = Const.EXTRA_FOR_YEAR;
	private static final String EXTRA_SET_YEAR = Const.EXTRA_SET_YEAR;
			
	String [] myStringArray = {"asdfasd","asdfasdf","qwetrqwerqwe"};
	private ExpensesDatasource datasource;
	private int month = Calendar.getInstance().get(Calendar.MONTH);
	private int year = Calendar.getInstance().get(Calendar.YEAR);
	  
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        
        
        if(getIntent().hasExtra(EXTRA_FOR_YEAR)){
        	year = Integer.parseInt(getIntent().getStringExtra(EXTRA_FOR_YEAR));
        }
        
        if(getIntent().hasExtra(EXTRA_FOR_MONTH)){
        	month = Integer.parseInt(getIntent().getStringExtra(EXTRA_FOR_MONTH));        	
        }
     /*   // swipe right on "all months"
    	if(month>12){
    		month = 0;
    		year = year + 1 ;
    	}
    	
    	// swipe left on "all months"
    	else if(month<0){
    		month = 12;
    		year = year - 1 ;
    	}*/
       
        
        Log.e("shashank", "month : " + month);
        Log.e("shashank", "year: " + year);
        
        datasource = new ExpensesDatasource(this);
        datasource.open();
        
        datasource.updateDateFormat();
        
        List<Expenses> values = null; 
        
        if(month==12){
        	values = datasource.getAllExpenses();
        }else{
        values = datasource.getAllExpensesForMonth(month, year);
        }
        
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
        
        
        TextView currentMonth= (TextView)findViewById(R.id.currentMonth);
        
        TextView totalExpense = (TextView)findViewById(R.id.totalExpense);
        
        if(month == 12){
        	currentMonth.setText(" All months ");
        	double debits = datasource.getTotalDebits();
        	double credits = datasource.getTotalCredits();        	
        	totalExpense.setText(" Debits - "+ debits);
        	totalExpense.append(" | Credits - "+ credits);
        	if((credits - debits) >= 0.0)
        	{totalExpense.append(" | Balance - "+ (credits - debits));}        	
        }else{
        	currentMonth.setText(new SimpleDateFormat("MMMM-yy").format(new Date(year, month,1)));
        	double debits = datasource.getTotalDebitsForMonth(month,year);
        	double credits = datasource.getTotalCreditsForMonth(month,year);
        	
        	totalExpense.setText(" Debits - "+ debits);
        	totalExpense.append(" | Credits - "+ credits);
        	if((credits - debits) >= 0.0)
        	{totalExpense.append(" | Balance - "+ (credits - debits));}
        	//totalExpense.append(" | Total - "+ (credits - debits));
        		
        }
        
        
        totalExpense.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(MainActivity.this, "show list of months", Toast.LENGTH_LONG).show();
				getChooseMonthDialog().show();
				
			}
		});
        
        
        currentMonth.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(MainActivity.this, "show list of months", Toast.LENGTH_LONG).show();
				getChooseMonthDialog().show();
				
			}
		});
        
        
        final GestureDetector gestureDetector = 
     		   new GestureDetector(new MyGestureDetector());
        
        View.OnTouchListener gestureListener = 
     		   new View.OnTouchListener() {
 				
 				@Override
 				public boolean onTouch(View v, MotionEvent event) {
 					if(gestureDetector.onTouchEvent(event)){ 						
 						return true;
 					}
 					return false;
 				}
 			};
 			listview.setOnTouchListener(gestureListener);
 			listview.getRootView().setOnTouchListener(gestureListener);
 			
 			
 			
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
    	
    	List<Expenses> values = null; 
        
        if(month==12){
        	values = datasource.getAllExpenses();
        }else{
        values = datasource.getAllExpensesForMonth(month, year);
        }
        
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
    	super.onResume();
    }

   
    
    
    //// xml onClick handler 
    public void addNewExpense(View view) {
    	Intent intent = Intents.AddNewExpense(this);
    	if (month!=12){
    		intent.putExtra(EXTRA_SET_MONTH, ""+month);}
    	else{
    		intent.putExtra(EXTRA_SET_MONTH, ""+ Calendar.getInstance().get(Calendar.MONTH));}
    	
    	startActivity(intent);    	
	}
    
    
    
    ////xml onClick handler
    public void prev(View view) {
    	int prevMonth;
    	int prevYear;
    	Intent intent = Intents.MainActivity(this);
    	
    	// swipe left on "all months" should give "jan" last year
    	if(month==12){
    		prevMonth = 11;
    		prevYear = year - 1 ;
    		intent.putExtra(EXTRA_FOR_MONTH, "" + (prevMonth));
    		intent.putExtra(EXTRA_FOR_YEAR, "" + (prevYear));
    	}
    	// swipe left on "jan"  shud giv "all months"
    	else if(month==0){
    		prevMonth = 12;
    		intent.putExtra(EXTRA_FOR_MONTH, "" + (prevMonth));
    		intent.putExtra(EXTRA_FOR_YEAR, "" + (year));
    	}
    	// swipe left on "feb-dec". prev month of same year
    	else{
    		prevMonth = month - 1;
    		intent.putExtra(EXTRA_FOR_MONTH, "" + (prevMonth));
    		intent.putExtra(EXTRA_FOR_YEAR, "" + (year));
    	}
    	
    	startActivity(intent);
    	
	}
    
    
    ////xml onClick handler
    public void next(View view) {
    	
    	int nextMonth; 
    	int nextYear;
    	Intent intent = Intents.MainActivity(this);
    	// swipe right on "all months"
    	if(month==12){
    		nextMonth= 0;
    		nextYear = year + 1 ;
    		intent.putExtra(EXTRA_FOR_MONTH, "" + (nextMonth));
    		intent.putExtra(EXTRA_FOR_YEAR, "" + (nextYear));
    	}
    	// swipe right on others
    	// note that swipe right on dec i.e. 11 
    	// will lead to correct month i.e. 12
    	else{
    		nextMonth = month + 1;
    		intent.putExtra(EXTRA_FOR_MONTH, "" + (nextMonth));
    		intent.putExtra(EXTRA_FOR_YEAR, "" + (year));
    	}
    	
    	
    	
    	
    	startActivity(intent);    	
	}
    
    
    ////xml onClick handler
    public void addNewExpenseBySms(View view){
    	Intent intent = Intents.ReadSmsSenders(this);
    	startActivity(intent);
    }
    
    
    
    ////xml onClick handler
    public void viewCategoryWise(View view){
    	Intent intent = Intents.CategoryDetails(this);
    	startActivity(intent);
    }
    
    
    
    //// related to menu //// 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
        
    }
    
        
    
    //// menu xml onClick handler
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
    	Intent intent = Intents.AddNewExpense(this);
    	startActivity(intent);    	
	}
    
    
    
    public void showCategoryWise(MenuItem menuItem) {
    	Intent intent = Intents.CategoryDetails(this);
    	startActivity(intent);    	
	}
    
    
    
    public void showReadSms(MenuItem menuItem) {
    	Intent intent = Intents.ReadSmsSenders(this);
    	startActivity(intent);    	
	}   
       
    
        
    //// helper methods ////
    public void openExpenseForEdit(Expenses expense) {
    	Intent intent = Intents.EditExpense(this);
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
    
    
    
    
    
    //// swipe
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{
    
        
    	MyGestureDetector() {
    
    	}

    	private static final int SWIPE_MIN_DISTANCE = 50;
        private static final int SWIPE_MAX_OFF_PATH = 250;
        private static final int SWIPE_THRESHOLD_VELOCITY = 50;
        
    	@Override
    	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
    			float velocityY) {
    		Log.e("shashank", e1.getX() + ", " + e1.getY() + " : " +e2.getX() + ", " + e2.getY() );
    		// ignore swipe thats too 'vertical'
    		if(Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH ){
    			//Toast.makeText(MainActivity.this, "youre off path vertically",  Toast.LENGTH_LONG).show();
    			return false;
    		}
    		if(Math.abs(velocityX) <SWIPE_THRESHOLD_VELOCITY){
    			//Toast.makeText(MainActivity.this, "too slow", Toast.LENGTH_LONG).show();
    			return false;
    		}
    		
    		// right to left swipe
    		if(Math.abs(e1.getX() - e2.getX()) > SWIPE_MIN_DISTANCE ) {
    			
    			if(e1.getX() > e2.getX()){
    				// right to left 
    				//Toast.makeText(MainActivity.this, "Loading next month...", Toast.LENGTH_SHORT).show();
    				next(null);
    				return true;
    				}
    			else{
    				// left to right
    				//Toast.makeText(MainActivity.this, "Loading last month...", Toast.LENGTH_SHORT).show();
    				prev(null);
    				
    				return true;
    			}
    		}
    		
    		
    		
    		return false;
    	}
    }
    
    
    //// date picker dialog
    private DatePickerDialog getDatePickerDialog(){
    	DatePickerDialog datePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
    		
    		@Override
    		public void onDateSet(DatePicker view, int year, int monthOfYear,
    				int dayOfMonth) {
    			// TODO Auto-generated method stub
    			
    		}
    	}, 2012, 11, 12);
    	
    	return datePickerDialog;
    	
    }
    
    ////date dialog with month list
    private AlertDialog getChooseMonthDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Select month");
    	final String[] months = new String[]{"Jan","Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "All"};
    	
    	builder.setSingleChoiceItems(months, month, new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, months[which], Toast.LENGTH_LONG).show();
				Intent intent = Intents.MainActivity(MainActivity.this);
				intent.putExtra(EXTRA_FOR_MONTH, ""+which);
				startActivity(intent);
				
			}
		});
    	
    	/*builder.setItems(months, new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, months[which], Toast.LENGTH_LONG).show();
				Intent intent = new Intent(MainActivity.this, MainActivity.class);
				intent.putExtra("forMonth", ""+which);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});*/
    	return builder.create();
    	
    			
    }
    
        
    
}
