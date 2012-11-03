package com.example.expensiv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import com.example.expensiv.db.Sms;
import com.example.expensiv.shared.Common;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ReadSms extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_sms);
        Intent intent = getIntent();
        String sendernumber = null;
        TextView textView = (TextView)findViewById(R.id.smsSummary);
        
        if(intent.hasExtra("sender_number")){
        	sendernumber = intent.getStringExtra("sender_number");        	
            textView.setText("Messages from " + sendernumber);
        }
        else{
        	textView.setText("Sender not selected");
        }
        //[_id, thread_id, address, person, date, protocol, read, status, type, reply_path_present, subject, body, service_center, locked, error_code, seen]
        
        /*PRAGMA table_info(sms);
        0|_id|INTEGER|0||1
        1|thread_id|INTEGER|0||0
        2|address|TEXT|0||0
        3|person|INTEGER|0||0
        4|date|INTEGER|0||0
        5|protocol|INTEGER|0||0
        6|read|INTEGER|0|0|0
        7|status|INTEGER|0|-1|0
        8|type|INTEGER|0||0
        9|reply_path_present|INTEGER|0||0
        10|subject|TEXT|0||08
        11|body|TEXT|0||0
        12|service_center|TEXT|0||0
        13|locked|INTEGER|0|0|0
        14|error_code|INTEGER|0|0|0
        15|seen|INTEGER|0|0|0*/
		
		ListView findViewById = (ListView)findViewById(R.id.smsList);		
        String [] projection = new String[]{"_id", 
        									"address",
        									"strftime(\"%m-%d-%Y\", date, \'unixepoch\') as date",
        									"body",
        									"date as date_raw"};
		//// make sms list ////
		Uri Uri_sms = Uri.parse("content://sms/inbox");
        Cursor cursor2 = managedQuery(Uri_sms, projection, "address = '" + sendernumber + "'", null, null);
        cursor2.moveToFirst();
        
        ArrayList<Sms> msgs = new ArrayList<Sms>();
        
        for (;!cursor2.isAfterLast();cursor2.moveToNext()){
        				        	
        	Sms sms = cursorToSms(cursor2);
        	
        	msgs.add(sms);			        	
        }
        
        //// make sms list ////
        
        
        //// create and set adapter //// 
        final ArrayAdapter<Sms> adapter2 = new ArrayAdapter<Sms>(ReadSms.this, android.R.layout.simple_list_item_1,msgs);
        findViewById.setAdapter(adapter2);
        //// create and set adapter ////
        
        
        //// set Item Click Listener ////
        findViewById.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				String msg_id = ((Sms)adapter2.getItem(position)).getId();
				Log.e("shashank","selected message id " + msg_id);			
				Intent intent = new Intent(ReadSms.this, AddNewExpense.class);
				intent.putExtra("msg_id", msg_id);
				startActivity(intent);							
			}
        });
        //// set Item Click Listener ////        
    }

	private Sms cursorToSms(Cursor cursor2) {
		Sms sms = new Sms();			        	
		
		String sms_id = cursor2.getString(cursor2.getColumnIndex("_id"));
		sms.setId(sms_id);
		
		String smsbody = cursor2.getString(cursor2.getColumnIndex("body")) ;
		sms.setSmsBody(smsbody);
		
		String from = cursor2.getString(cursor2.getColumnIndex("address"));
		sms.setFrom(from);
		
		
		
		String date = cursor2.getString(cursor2.getColumnIndex("date"));
		long date_raw = cursor2.getLong(cursor2.getColumnIndex("date_raw"));
		Log.e("shashank", "date_raw : " + date_raw);
		Log.e("shashank", "msg : " + sms_id + " date : " + date );
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date_raw);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		Log.e("shashank", "year-month-day :" + year + " - " + month + " - " + day); 
		//sms.setReceiveTime(new Date(date));
		Log.e("shashank", ""+ Common.getUnixTimestamp(2012, 9, 30, 0, 0));
		
		sms.setReceiveTime((Common.getCalendarFromUnixTimestamp(date_raw)).getTime());
		return sms;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_read_sms, menu);
        return true;
    }
    
}
