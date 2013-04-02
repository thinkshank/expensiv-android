package com.example.expensiv;

import com.example.expensiv.shared.Const;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSBroadcastReceiver extends BroadcastReceiver {

        private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
        private static final String TAG = "SMSBroadcastReceiver";
        
        
        @Override
        public void onReceive(Context context, Intent intent) {
             Log.i(TAG, "Intent recieved: " + intent.getAction());

                if (intent.getAction().equalsIgnoreCase(SMS_RECEIVED) ) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        Object[] pdus = (Object[])bundle.get("pdus");
                        final SmsMessage[] messages = new SmsMessage[pdus.length];
                        for (int i = 0; i < pdus.length; i++) {
                            messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        }
                        if (messages.length > -1) {
                            Log.i(TAG, "Message recieved: " + messages[0].getMessageBody() );
                            Log.i(TAG, "Message sender: " + messages[0].getOriginatingAddress());
                            Log.i(TAG, "Message index ICC: " + messages[0].getIndexOnIcc() );
                            Log.i(TAG, "Message index SIM : " + messages[0].getIndexOnSim() );
                            // Toast.makeText(this, "Message recieved" , Toast.LENGTH_LONG).show();
                            // Intent intent = new Intent(ReadSms.this, AddNewExpense.class);
                            // Intent i = new Intent(context, AddNewExpense.class);
                            String sender = messages[0].getOriginatingAddress();
                            String body   = messages[0].getMessageBody();
                            
                            // if the sender is known, then process it automatically.
                            // if(SmsParser.number_id.containsKey(sender))
                            
                            for(String num : SmsParser.number_id.keySet()){
                    			if(num!=null)
                    				num.replace(" ", "");
                    			
                    			if(sender.contains(num)){
                    				Intent i = new Intent(context, AutoCreateService.class);
                                    // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.putExtra(Const.EXTRA_SMS_SENDER, sender);
                                    i.putExtra(Const.EXTRA_SMS_BODY, body);
                                    context.startService(i);
                                    break;
                    			}
                    			else{
                                	Log.e("shashank", "no match found for " + sender + " and " +num) ;
                                }
                            }                           
                            
                            
                        }
                    }
                }
           }
    }