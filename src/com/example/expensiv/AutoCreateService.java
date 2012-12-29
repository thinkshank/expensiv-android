package com.example.expensiv;

import java.util.ArrayList;
import java.util.List;

import com.example.expensiv.db.Expenses;
import com.example.expensiv.db.ExpensesDatasource;
import com.example.expensiv.shared.Common;
import com.example.expensiv.shared.Const;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class AutoCreateService extends Service {
  private final IBinder mBinder = new MyBinder();
  
  private String smsbody; 
  private String smssender;

  

@Override
  public int onStartCommand(Intent intent, int flags, int startId) {

	Log.e("Shashank", "onStartCommand");
    Context context= getApplicationContext();
    if(intent.hasExtra(Const.EXTRA_SMS_BODY) && intent.hasExtra(Const.EXTRA_SMS_SENDER))
    {
    	smsbody = (String)intent.getStringExtra(Const.EXTRA_SMS_BODY);
        Log.e("shashank" , "smsbody : " +  smsbody);
        smssender = (String)intent.getStringExtra(Const.EXTRA_SMS_SENDER);
        Log.e("shashank" , "smssender : " +  smssender );
    }else {
    	Log.e("shashank", "Intent missing smsbody or smssender");
    }
    
    //// create only if sender and body present
    if(Common.has(smsbody) && Common.has(smssender)){
    	Expenses createdexpense = createExpense(smssender, smsbody);
    	if(createdexpense!=null){
    		Log.e("shashank", "Expense created ");
    	}else{
    		Log.e("shashank", "Expense not created ");
    	}
        
        // generateNotification(context, "testmessage");
    }else{
    	Log.e("shashank", "No expense created. missing smsbody or smssender");
    }
    
    return Service.START_NOT_STICKY;
    
  }

	private Expenses createExpense(String sender, String smsbody) {
		ExpensesDatasource datasource = new ExpensesDatasource(getApplicationContext());
		
		SmsParser smsparser = new SmsParser();
		smsparser.setBank(sender);
		
		String title = null;
		String date = null;
		String cost = null;
		String category = null;
		String subCategory = null;
		String debitcredit=null;
		
		title = smsparser.getTitle(smsbody);
		date = smsparser.getDate(smsbody);
		cost = smsparser.getCostFromMsg(smsbody);
		category = smsparser.getCategory(smsbody);
		subCategory = smsparser.getSubCategory(smsbody);
		debitcredit = smsparser.getType(smsbody);
		
		if(Common.has(title) && Common.has(cost) && (Common.has(category) || Common.has(subCategory))){
			datasource.open();
			Expenses expenses = datasource.createExpense(title, date, cost, category, subCategory, null, debitcredit);
			datasource.close();
			generateNotification(getApplicationContext(), "Expense added : " + expenses.getCost() + " - " + Common.getDateReadable(expenses.getDate()));
			return expenses;
		}else{
			//Toast.makeText(getApplicationContext(), "auto entry failed", Toast.LENGTH_LONG).show();
			Log.e("shashank", "No expense created. title, cost, and category/subcategory requirement not matched");
			return null;
		}
				
	}

  @Override
  public IBinder onBind(Intent arg0) {
    return mBinder;
  }

  public class MyBinder extends Binder {
    AutoCreateService getService() {
      return AutoCreateService.this;
    }
  }
  

  private static void generateNotification(Context context, String message) {
      int icon = R.drawable.ic_launcher;
      long when = System.currentTimeMillis();
      NotificationManager notificationManager = (NotificationManager)
              context.getSystemService(Context.NOTIFICATION_SERVICE);
      Notification notification = new Notification(icon, message, when);

      String title = context.getString(R.string.app_name);

      Intent notificationIntent = new Intent(context, MainActivity.class);
      // set intent so it does not start a new activity
      notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
              Intent.FLAG_ACTIVITY_SINGLE_TOP);
      PendingIntent intent =
              PendingIntent.getActivity(context, 0, notificationIntent, 0);
      notification.setLatestEventInfo(context, title, message, intent);
      notification.flags |= Notification.FLAG_AUTO_CANCEL;

      // Play default notification sound
      //notification.defaults |= Notification.DEFAULT_SOUND;

      // Vibrate if vibrate is enabled
      //notification.defaults |= Notification.DEFAULT_VIBRATE;
      notificationManager.notify(0, notification);      

  }
  
} 