package com.example.expensiv.shared;

import android.content.Context;
import android.content.Intent;

import com.example.expensiv.AddNewExpense;
import com.example.expensiv.CategoryDetails;
import com.example.expensiv.EditExpense;
import com.example.expensiv.MainActivity;
import com.example.expensiv.ReadSmsSenders;

public class Intents {

	public static Intent MainActivity(Context caller ){
		Intent intent = new Intent(caller, MainActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	return intent;		
	}
	
	public static Intent ReadSmsSenders(Context caller ){
		Intent intent = new Intent(caller, ReadSmsSenders.class);
    	return intent;		
	}
	
	
	public static Intent CategoryDetails(Context caller ){
		Intent intent = new Intent(caller, CategoryDetails.class);
    	return intent;		
	}
	
	
	public static Intent AddNewExpense(Context caller ){
		Intent intent = new Intent(caller, AddNewExpense.class);
    	return intent;		
	}
	
	
	public static Intent EditExpense(Context caller ){
		Intent intent = new Intent(caller, EditExpense.class);
    	return intent;		
	}
	
	public static Intent DateWidgets1(Context caller ){
		Intent intent = new Intent(caller, DateWidgets1.class);
    	return intent;		
	}
}
