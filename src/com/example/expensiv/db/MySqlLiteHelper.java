package com.example.expensiv.db;
import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySqlLiteHelper extends SQLiteOpenHelper 
{
	public static final String DB_NAME = "db.expensiv";
	public static final int DB_VERSION = 3;
	
	public static final String TABLE_EXPENSES = "expenses";
	
	public static final String EXPENSES_ID = "id";
	public static final String EXPENSES_DATE = "date";
	public static final String EXPENSES_COST = "cost";
	public static final String EXPENSES_TITLE = "title";
	public static final String EXPENSES_CATEGORY = "category";
	public static final String EXPENSES_SUB_CATEGORY = "sub_category";
	public static final String EXPENSES_MSG_ID = "msg_id";
	
	private static final String CREATE_TABLE_EXPENSES = ""+
	"CREATE TABLE " + TABLE_EXPENSES + 
	"( " + 
	EXPENSES_ID + " integer primary key autoincrement " + " , " +
	EXPENSES_DATE + " text " + " , " + 
	EXPENSES_COST + " text " + " , " +
	EXPENSES_TITLE + " text " + " , " +
	EXPENSES_CATEGORY + " text " + " , " +
	EXPENSES_SUB_CATEGORY+ " text " + " , " +
	EXPENSES_MSG_ID + " text default NULL" +
	" ); ";
	
	private static final String INSERT_TEST_DATA = ""+
			"INSERT INTO " + TABLE_EXPENSES +
			" values ( " + 
			" NULL " + "," +
			"'" + new Date() + "'," + 
			"'500'" + " , " +
			"'Dummy entry " + Math.random() + "' , " +
			"'Dummy category'"+ " , " +
			"'Dummy sub category'" +
			" ); ";
	
	public MySqlLiteHelper(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_EXPENSES);
		Log.e("shashank", CREATE_TABLE_EXPENSES);
		//db.execSQL(INSERT_TEST_DATA);		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySqlLiteHelper.class.getName(),
			  "Upgrading database from version " + oldVersion + " to " + newVersion + "This will destroy all old data");
		db.execSQL(" DROP TABLE IF EXISTS " + TABLE_EXPENSES);
		onCreate(db);
	}
	
	
}
