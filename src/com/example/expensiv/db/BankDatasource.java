package com.example.expensiv.db;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.example.expensiv.shared.Common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class BankDatasource {
	
	
	private SQLiteDatabase database;
	private MySqlLiteHelper dbhelper;
	private String[] allCols = 
		{
		MySqlLiteHelper.BANK_ID,
		MySqlLiteHelper.BANK_NAME,
		MySqlLiteHelper.BANK_SMS_CODE
		};
	
	private SqlLiteColumn[] arr_cols = 
		{
			new SqlLiteColumn(MySqlLiteHelper.BANK_ID, "VARCHAR", true, true, true),
			new SqlLiteColumn(MySqlLiteHelper.BANK_NAME, "VARCHAR"),
			new SqlLiteColumn(MySqlLiteHelper.BANK_SMS_CODE, "VARCHAR")
		};
		
	public String getCreateStatement(){
		String sql = "CREATE TABLE IF NOT EXISTS " + MySqlLiteHelper.TABLE_BANK + 
				"    (";
		for (int i = 0; i<arr_cols.length; i++){
			SqlLiteColumn col = arr_cols[i];
			sql += col.getSQL();
			sql += i!=(arr_cols.length-1)?", ":"";
		}
		sql +="    )";
		return sql;
	}
	
	public BankDatasource(){
		// used for test cases
	}

	public BankDatasource(Context context){
		dbhelper = new MySqlLiteHelper(context);
	}
	
	
	
	
	public void open() throws SQLException{
		database = dbhelper.getWritableDatabase();
		Log.w("shashank", "opened db connection : "  + database.getPath());
	}
	
	
	
	public void close(){
		dbhelper.close();
	}
	
	
	public List<BankSMS> getAllBankSMS(){
		List<BankSMS> banksmslist = new ArrayList<BankSMS>();
		
		Cursor cursor = database.query(MySqlLiteHelper.TABLE_BANK_SMS, 
									   allCols, 
									   null,
									   null,
									   null,
									   null,
									   MySqlLiteHelper.BANK_SMS_SERVICE_PROVIDER );
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			BankSMS banksms = cursorToBankSMS(cursor);
			banksmslist.add(banksms);
			cursor.moveToNext();
		}
		cursor.close();
		return banksmslist;
	}

	public List<BankSMS> getDistinctBanks(){
		List<BankSMS> banksmslist = new ArrayList<BankSMS>();
		
		Cursor cursor = database.query(MySqlLiteHelper.TABLE_BANK_SMS, 
									   new String [] {" distinct " + MySqlLiteHelper.BANK_SMS_SERVICE_PROVIDER}, 
									   null,
									   null,
									   null,
									   null,
									   MySqlLiteHelper.BANK_SMS_SERVICE_PROVIDER );
		cursor.moveToFirst();
		Log.i("shashank", " got rows : " + cursor.getCount());
		while(!cursor.isAfterLast()){
			BankSMS banksms = cursorToBankSMS(cursor);
			banksmslist.add(banksms);
			cursor.moveToNext();
		}
		cursor.close();
		return banksmslist;
	}
	
	
	public List<BankSMS> getAllCommandsForBank(String bankname){
		List<BankSMS> banksmslist = new ArrayList<BankSMS>();
		
		Cursor cursor = database.query(MySqlLiteHelper.TABLE_BANK_SMS, 
									   allCols, 
									   MySqlLiteHelper.BANK_SMS_SERVICE_PROVIDER + " = '" + bankname +"'",
									   null,
									   null,
									   null,
									   null );
		cursor.moveToFirst();
		Log.i("shashank", " got rows : " + cursor.getCount());
		while(!cursor.isAfterLast()){
			BankSMS banksms = cursorToBankSMS(cursor);
			banksmslist.add(banksms);
			cursor.moveToNext();
		}
		cursor.close();
		return banksmslist;
	}
	
	private BankSMS cursorToBankSMS(Cursor cursor){
		BankSMS banksms = new BankSMS();
		if(cursor.getColumnIndex(MySqlLiteHelper.BANK_SMS_ID)!=-1)
			{banksms.setID(cursor.getInt(cursor.getColumnIndex(MySqlLiteHelper.BANK_SMS_ID)));}
		if(cursor.getColumnIndex(MySqlLiteHelper.BANK_SMS_SERVICE_PROVIDER)!=-1)
			{banksms.setServiceProvider(cursor.getString(cursor.getColumnIndex(MySqlLiteHelper.BANK_SMS_SERVICE_PROVIDER)));}
		if(cursor.getColumnIndex(MySqlLiteHelper.BANK_SMS_TITLE)!=-1)
			{banksms.setTitle(cursor.getString(cursor.getColumnIndex(MySqlLiteHelper.BANK_SMS_TITLE)));}
		if(cursor.getColumnIndex(MySqlLiteHelper.BANK_SMS_PHONE_NO)!=-1)
			{banksms.setPhoneNo(cursor.getString(cursor.getColumnIndex(MySqlLiteHelper.BANK_SMS_PHONE_NO)));}		
		if(cursor.getColumnIndex(MySqlLiteHelper.BANK_SMS_DESCRIPTION) != -1)
			{banksms.setDescription(cursor.getString(cursor.getColumnIndex(MySqlLiteHelper.BANK_SMS_DESCRIPTION)));}
		if(cursor.getColumnIndex(MySqlLiteHelper.BANK_SMS_SMS_TEXT) != -1)
			{banksms.setSmsText(cursor.getString(cursor.getColumnIndex(MySqlLiteHelper.BANK_SMS_SMS_TEXT)));}
		
		return banksms;
	}
	
	public void refreshBankSMS(List<String> insertstmts){
		if(database==null || !database.isOpen())
		  {open();}
		Log.i("shashank", "dropping table bank_sms : " + SQLStatements.DROP_BANK_SMS);
		database.execSQL(SQLStatements.DROP_BANK_SMS);
		
		Log.i("shashank", "creating table bank_sms : " + SQLStatements.CREATE_BANK_SMS);
		database.execSQL(SQLStatements.CREATE_BANK_SMS);
		
		Log.i("shashank", "inserting rows into bank_sms");
		int countadded =0;
			for(String s : insertstmts){
				database.execSQL(s);
				countadded++;
			}
		Log.i("shashank"," rows inserted : " + countadded);
		
	}	

}
