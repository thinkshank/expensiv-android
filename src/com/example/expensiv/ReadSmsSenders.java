package com.example.expensiv;

import java.util.ArrayList;
import java.util.Arrays;
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

public class ReadSmsSenders extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_sms_senders);
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
        
        //// make senders list ////
        Uri sms = Uri.parse("content://sms/inbox");
        Cursor cursor = managedQuery(sms, null, null, null, null);
        cursor.moveToFirst();
        
        String [] cols = cursor.getColumnNames();
        TextView textView = (TextView)findViewById(R.id.smsSummary);
        textView.setText("Select a sender");
        //textView.setText(Arrays.toString(cols));
        //textView.append("\n Count rows : "+cursor.getCount());
        Log.d("shashank",Arrays.toString(cols));
        
        ArrayList<SenderAsString> senders = new ArrayList<SenderAsString>();
        HashSet<String> distinctBanks = new HashSet<String>();
        
        for (;!cursor.isAfterLast();cursor.moveToNext()){
        	//String msg = "";
        	String from = cursor.getString(cursor.getColumnIndex("address"));
        	//msg += from;
        	Log.e("shashank", "from : " + from);
        	SenderAsString sender = new SenderAsString();
        	sender.setNumber(from);
        	if(/*ExpensivConstants.getBankHashMap().containsKey(from) &&*/ distinctBanks.add(from)){
        		if(ExpensivConstants.getBankHashMap().get(from)!=null && ExpensivConstants.getBankHashMap().get(from).length()>0){
        			//msg += " - " + ExpensivConstants.getBankHashMap().get(from);
        			sender.setName(ExpensivConstants.getBankHashMap().get(from));
        		}
        		senders.add(sender);
        		Log.e("shashank", sender.toString());
        	}        	
        }
        //// make senders list ////
        
        //Toast.makeText(this, Arrays.toString(cols), Toast.LENGTH_LONG).show();
        final ArrayAdapter<SenderAsString> adapter = new ArrayAdapter<SenderAsString>(this, android.R.layout.simple_list_item_1,senders);
        
        ListView listSenders = (ListView)findViewById(R.id.smsList);
        listSenders.setAdapter(adapter);        
        
        //// listener for sender list ////
        listSenders.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				String sendernumber = ((SenderAsString)adapter.getItem(position)).getNumber();
				Log.e("shashank" , "selected sender " + sendernumber);
				//display = display.substring(0, display.indexOf("-")).trim();
				//Toast.makeText(ReadSmsSenders.this, display, Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent(ReadSmsSenders.this, ReadSms.class);
				intent.putExtra("sender_number", sendernumber);
				startActivity(intent);
			}		
		});
        //// listener for sender list ////
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_read_sms, menu);
        return true;
    }
    
    private class SenderAsString{
    	private String name;
    	private String number;
    	
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}    	
    	
		@Override
		public String toString() {
			String s = "";
			
			if(Common.has(this.number)){
				s+=this.number;
			}
			
			return s;
			
		}
    }
    
    
}
