package com.example.expensiv.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class MySqlLiteHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "db.expensiv";
	public static final int DB_VERSION = 5;

	public static final String TABLE_EXPENSES = "expenses";
	public static final String EXPENSES_ID = "id";
	public static final String EXPENSES_DATE = "date";
	public static final String EXPENSES_COST = "cost";
	public static final String EXPENSES_TITLE = "title";
	public static final String EXPENSES_CATEGORY = "category";
	public static final String EXPENSES_SUB_CATEGORY = "sub_category";
	public static final String EXPENSES_MSG_ID = "msg_id";
	public static final String EXPENSES_DEBIT_CREDIT = "debit_credit";
	
	public static final String TABLE_BANK_SMS = "BANK_SMS";
	public static final String BANK_SMS_ID = "ID";
	public static final String BANK_SMS_SERVICE_PROVIDER = "SERVICE_PROVIDER";
	public static final String BANK_SMS_TITLE = "TITLE";
	public static final String BANK_SMS_PHONE_NO= "PHONE_NO";
	public static final String BANK_SMS_DESCRIPTION= "DESCRIPTION";
	public static final String BANK_SMS_SMS_TEXT= "SMS_TEXT";
	public static final String BANK_SMS_CREATED_BY = "CREATED_BY";
	
	public static final String TABLE_BANK = "BANK";
	public static final String BANK_ID = "ID";
	public static final String BANK_NAME = "NAME";
	public static final String BANK_SMS_CODE = "SMS_CODE";

	private static final String CREATE_TABLE_EXPENSES = "" + "CREATE TABLE IF NOT EXISTS "
			+ TABLE_EXPENSES + "( " + EXPENSES_ID
			+ " integer primary key autoincrement " + " , " + EXPENSES_DATE
			+ " text " + " , " + EXPENSES_COST + " text " + " , "
			+ EXPENSES_TITLE + " text " + " , " + EXPENSES_CATEGORY + " text "
			+ " , " + EXPENSES_SUB_CATEGORY + " text " + " , "
			+ EXPENSES_DEBIT_CREDIT + " text default D" + " , "
			+ EXPENSES_MSG_ID + " text default NULL" + " ); ";

	private static final String INSERT_TEST_DATA = "" + "INSERT INTO "
			+ TABLE_EXPENSES + " values ( " + " NULL " + "," + "'" + new Date()
			+ "'," + "'500'" + " , " + "'Dummy entry " + Math.random() + "' , "
			+ "'Dummy category'" + " , " + "'Dummy sub category'" + " ); ";

	public MySqlLiteHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		Log.w("shashank", "Instantiating ... " + DB_NAME + " version ("
				+ DB_VERSION + ")");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_EXPENSES);
		Log.e("shashank", CREATE_TABLE_EXPENSES);
		// db.execSQL(INSERT_TEST_DATA);
		
		Log.i("shashank", "dropping table BANK_SMS if exists");
		db.execSQL(SQLStatements.DROP_BANK_SMS);
		Log.i("shashank", "creating table BANK_SMS if not exists");
		db.execSQL(SQLStatements.CREATE_BANK_SMS);
		Log.i("shashank", "inserting data");
		ArrayList<String> insertlist = new SQLStatements().getBankSMSInsertSQL();
		for (String s : insertlist)
			{db.execSQL(s);}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("shashank", "old version - " + oldVersion);
		Log.i("shashank", "new version - " + newVersion);
		
		Log.w(MySqlLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + "This will destroy all old data");

		// e.g. 17Nov2012
		String dateStamp = new SimpleDateFormat("ddMMMyyyy").format(new Date());
				
		// create table expenses__BACKUP_3 as select * from expenses;
		db.execSQL("CREATE TABLE " + TABLE_EXPENSES + "_BACKUP_" + oldVersion
				+ "_" + dateStamp + " AS SELECT * FROM " + TABLE_EXPENSES);

		
		Log.w(MySqlLiteHelper.class.getName(), "Backed up old database version "
				+ oldVersion + "_" + dateStamp + " as " + TABLE_EXPENSES + "_BACKUP_"
				+ oldVersion);
		

		if (oldVersion==3 && newVersion == 4) {
			/*
			 * db.execSQL("ALTER TABLE " + TABLE_EXPENSES + " RENAME TO " +
			 * TABLE_EXPENSES + "_TEMP");
			 */

			// add the column debit_credit
			db.execSQL("ALTER TABLE " + TABLE_EXPENSES + " ADD COLUMN "
					+ EXPENSES_DEBIT_CREDIT + " text default D" + " ; ");

			// update all null debit_credit to 'D'
			db.execSQL("UPDATE " + TABLE_EXPENSES + " SET "
					+ EXPENSES_DEBIT_CREDIT + " = 'D' " + " WHERE "
					+ EXPENSES_DEBIT_CREDIT + " is null ;");

			/*
			 * db.execSQL("CREATE TABLE " + TABLE_EXPENSES + "( " + EXPENSES_ID
			 * + " integer primary key autoincrement " + " , " + EXPENSES_DATE +
			 * " text " + " , " + EXPENSES_COST + " text " + " , " +
			 * EXPENSES_TITLE + " text " + " , " + EXPENSES_CATEGORY + " text "
			 * + " , " + EXPENSES_SUB_CATEGORY + " text " + " , " +
			 * EXPENSES_DEBIT_CREDIT + " text default D" + " , " +
			 * EXPENSES_MSG_ID + " text default NULL" + " ); ");
			 * 
			 * db.execSQL(" INSERT INTO " + TABLE_EXPENSES + " SELECT "
			 * +TABLE_EXPENSES+ ".* , 'D' as " + EXPENSES_DEBIT_CREDIT +
			 * " FROM " + TABLE_EXPENSES + "_TEMP ;");
			 */

			/* db.execSQL(" DROP TABLE " + TABLE_EXPENSES + "_TEMP"); */
		} 
		if (newVersion == 5) {
			Log.i("shashank", "executing code for newversion = 5" );
			Log.i("shashank", "dropping table if exists");
			db.execSQL(SQLStatements.DROP_BANK_SMS);
			Log.i("shashank", "creating table if not exists");
			db.execSQL(SQLStatements.CREATE_BANK_SMS);
			Log.i("shashank", "inserting data");
			ArrayList<String> insertlist = new SQLStatements().getBankSMSInsertSQL();
			for (String s : insertlist)
				{db.execSQL(s);}
			}
		else {
			
			//db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
			onCreate(db);
			
		}

	}

}
