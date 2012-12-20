package com.example.expensiv;

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

                if (intent.getAction() == SMS_RECEIVED) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        Object[] pdus = (Object[])bundle.get("pdus");
                        final SmsMessage[] messages = new SmsMessage[pdus.length];
                        for (int i = 0; i < pdus.length; i++) {
                            messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        }
                        if (messages.length > -1) {
                            Log.i(TAG, "Message recieved: " + messages[0].getMessageBody() );
                            Log.i(TAG, "Message index ICC: " + messages[0].getIndexOnIcc() );
                            Log.i(TAG, "Message index SIM : " + messages[0].getIndexOnSim() );
                            // Toast.makeText(this, "Message recieved" , Toast.LENGTH_LONG).show();
                            // Intent intent = new Intent(ReadSms.this, AddNewExpense.class);
                            Intent i = new Intent(context, AddNewExpense.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i); 
                            
                        }
                    }
                }
           }
    }