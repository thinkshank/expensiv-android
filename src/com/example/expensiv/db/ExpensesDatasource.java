package com.example.expensiv.db;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.example.expensiv.Common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class ExpensesDatasource {
	
	private SQLiteDatabase database;
	private MySqlLiteHelper dbhelper;
	private String[] allCols = 
		{MySqlLiteHelper.EXPENSES_ID,
		MySqlLiteHelper.EXPENSES_DATE,
		MySqlLiteHelper.EXPENSES_COST,
		MySqlLiteHelper.EXPENSES_TITLE,
		MySqlLiteHelper.EXPENSES_CATEGORY,
		MySqlLiteHelper.EXPENSES_SUB_CATEGORY,		
		MySqlLiteHelper.EXPENSES_MSG_ID
		};
	
	public ExpensesDatasource(Context context){
		dbhelper = new MySqlLiteHelper(context);
	}
	
	public void open() throws SQLException{
		database = dbhelper.getWritableDatabase();
	}
	
	public void close(){
		dbhelper.close();
	}
	
	
	public Expenses createExpense(String title, String date, String cost, String category, String subCategory, String msg_id ){
		ContentValues values = new ContentValues();
		//values.put(MySqlLiteHelper.COL_ID, "NULL");
		values.put(MySqlLiteHelper.EXPENSES_TITLE, has(title)?title:"");
		//values.put(MySqlLiteHelper.EXPENSES_DATE, has(date)?date:new SimpleDateFormat("dd/MMM/yyyy").format(new Date()));
		values.put(MySqlLiteHelper.EXPENSES_DATE, has(date)?date:new SimpleDateFormat("dd/MMM/yyyy").format(new Date()));
		values.put(MySqlLiteHelper.EXPENSES_COST, has(cost)?cost:"0");
		values.put(MySqlLiteHelper.EXPENSES_SUB_CATEGORY, has(subCategory)?subCategory:"");
		values.put(MySqlLiteHelper.EXPENSES_CATEGORY, has(category)?category:"");
		if(has(msg_id)){
			values.put(MySqlLiteHelper.EXPENSES_MSG_ID, msg_id);
		}
		
		long insertId;
		
			insertId = database.insert(MySqlLiteHelper.TABLE_EXPENSES, 
									   MySqlLiteHelper.EXPENSES_ID, 
											values);
			Log.e("shashank", "insert returned " + insertId);
				
		Cursor cursor = database.query(MySqlLiteHelper.TABLE_EXPENSES, 
										allCols,
										MySqlLiteHelper.EXPENSES_ID + " = " + insertId, 
										null,
										null,
										null,
										null);
		cursor.moveToFirst();
		Expenses expense = cursorToExpense(cursor);
		cursor.close();
		return expense;
	}
	
	public Expenses updateExpense(long id, String title, String date, String cost, String category, String subCategory){
		ContentValues values = new ContentValues();
		//values.put(MySqlLiteHelper.COL_ID, "NULL");
		values.put(MySqlLiteHelper.EXPENSES_TITLE, has(title)?title:"");
		values.put(MySqlLiteHelper.EXPENSES_DATE, has(date)?date:new SimpleDateFormat("dd/MMM/yyyy").format(new Date()));
		values.put(MySqlLiteHelper.EXPENSES_COST, has(cost)?cost:"0");
		values.put(MySqlLiteHelper.EXPENSES_SUB_CATEGORY, has(subCategory)?subCategory:"");
		values.put(MySqlLiteHelper.EXPENSES_CATEGORY, has(category)?category:"");
		long insertId;
		
			insertId = database.update(MySqlLiteHelper.TABLE_EXPENSES, values, MySqlLiteHelper.EXPENSES_ID + " = " + id, null);
					
			Log.e("shashank", "update returned " + insertId);
				
		Cursor cursor = database.query(MySqlLiteHelper.TABLE_EXPENSES, 
										allCols,
										MySqlLiteHelper.EXPENSES_ID + " = " + id, 
										null,
										null,
										null,
										null);
		cursor.moveToFirst();
		Expenses expense = cursorToExpense(cursor);
		cursor.close();
		return expense;
	}
	
	public void deleteExpense(Expenses expense){
		long id = expense.getId();
		System.out.println("Expense with id " + id + " is deleted");
		deleteExpense(id);
	}
	
	public void deleteExpense(long id){
		database.delete(MySqlLiteHelper.TABLE_EXPENSES, MySqlLiteHelper.EXPENSES_ID + " = " + id, null);
	}
	
	public void deleteExpense(String str_id){
		long id = Long.valueOf(str_id);
		System.out.println("Expense with id " + id + " is deleted");
		deleteExpense(id);
	}
	
	private Expenses cursorToExpense(Cursor cursor){
		Expenses expense = new Expenses();
		expense.setId(cursor.getInt(cursor.getColumnIndex(MySqlLiteHelper.EXPENSES_ID)));
		expense.setTitle(cursor.getString(cursor.getColumnIndex(MySqlLiteHelper.EXPENSES_TITLE)));
		expense.setCategory(cursor.getString(cursor.getColumnIndex(MySqlLiteHelper.EXPENSES_CATEGORY)));
		expense.setSubCategory(cursor.getString(cursor.getColumnIndex(MySqlLiteHelper.EXPENSES_SUB_CATEGORY)));
		expense.setCost(cursor.getString(cursor.getColumnIndex(MySqlLiteHelper.EXPENSES_COST)));
		expense.setDate(cursor.getString(cursor.getColumnIndex(MySqlLiteHelper.EXPENSES_DATE)));
		expense.setMsg_id(cursor.getString(cursor.getColumnIndex(MySqlLiteHelper.EXPENSES_MSG_ID)));
		return expense;
	}
	
	public List<Expenses> getAllExpenses(){
		List<Expenses> expenses = new ArrayList<Expenses>();
		
		Cursor cursor = database.query(MySqlLiteHelper.TABLE_EXPENSES, 
									   allCols, 
									   null,
									   null,
									   null,
									   null,
									   null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Expenses expense = cursorToExpense(cursor);
			expenses.add(expense);
			cursor.moveToNext();
		}
		
		cursor.close();
		return expenses;
	}
	
	public List<Expenses> getAllExpensesForMonth(int month){
		List<Expenses> expenses = new ArrayList<Expenses>();
		
		Calendar from = Common.getFirstDayOfMonth(month);
		Calendar to = Common.getLastDayOfMonth(month);
		
		Log.e("shashank", "where : " + MySqlLiteHelper.EXPENSES_DATE + " >= " + from.getTimeInMillis() + " AND " + MySqlLiteHelper.EXPENSES_DATE + " <= " + to.getTimeInMillis() );
		Log.e("shashank", " from : " + Common.getCalendarFromUnixTimestamp(from.getTimeInMillis()).getTime());
		Log.e("shashank", " to : " + Common.getCalendarFromUnixTimestamp(to.getTimeInMillis()).getTime());
		
		
		Cursor cursor = database.query(MySqlLiteHelper.TABLE_EXPENSES, 
									   allCols, 
									   MySqlLiteHelper.EXPENSES_DATE + " >= " + from.getTimeInMillis() + " AND " + MySqlLiteHelper.EXPENSES_DATE + " <= " + to.getTimeInMillis() ,
									   null,
									   null,
									   null,
									   null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Expenses expense = cursorToExpense(cursor);
			expenses.add(expense);
			cursor.moveToNext();
		}
		
		cursor.close();
		return expenses;
	}
	
	public Expenses getExpenseById(long id){
				
		Cursor cursor = database.query(MySqlLiteHelper.TABLE_EXPENSES, 
									   allCols, 
									   MySqlLiteHelper.EXPENSES_ID + " = " + id,
									   null,
									   null,
									   null,
									   null);
		cursor.moveToFirst();
		return cursorToExpense(cursor);
	}
	
	public long getTotal(){
		Cursor cursor = database.rawQuery("SELECT SUM(" + MySqlLiteHelper.EXPENSES_COST + ") from " + MySqlLiteHelper.TABLE_EXPENSES, null);
		cursor.moveToFirst();
		return cursor.getLong(0);
	}
	
	public long getTotalForMonth(int month){
		
		Calendar from = Common.getFirstDayOfMonth(month);
		Calendar to = Common.getLastDayOfMonth(month);
		
		Cursor cursor = database.rawQuery("SELECT SUM(" + MySqlLiteHelper.EXPENSES_COST + ") from " + MySqlLiteHelper.TABLE_EXPENSES + " where " + MySqlLiteHelper.EXPENSES_DATE + " >= " + from.getTimeInMillis() + " AND " + MySqlLiteHelper.EXPENSES_DATE + " <= " + to.getTimeInMillis() , null);
		cursor.moveToFirst();
		return cursor.getLong(0);
	}
	
	public ArrayList<ExpensesCategoryWise> getTotalCategoryWise(){
		String sql = "select " + MySqlLiteHelper.EXPENSES_CATEGORY+", sum(" + MySqlLiteHelper.EXPENSES_COST+") from " + MySqlLiteHelper.TABLE_EXPENSES+" group by " + MySqlLiteHelper.EXPENSES_CATEGORY+";";
		Log.d("shashank", sql);
		Cursor cursor = database.rawQuery(sql, null);
		cursor.moveToFirst();
		Log.d("shashank", "returned rows " +cursor.getCount());
		//hash map to store <category, sum of cost> and pass the data 		
		//HashMap<String, String> map = new HashMap<String, String>();
		
		ArrayList<ExpensesCategoryWise> categoryWiseExpenses 
			= new ArrayList<ExpensesCategoryWise>();
		
		while(!cursor.isAfterLast()){
			//map.put(cursor.getString(0), cursor.getString(1));
			ExpensesCategoryWise expensesCategoryWise = new ExpensesCategoryWise();
			expensesCategoryWise.setCategory(cursor.getString(0));
			expensesCategoryWise.setCategorySum(cursor.getString(1));
			categoryWiseExpenses.add(expensesCategoryWise);
			cursor.moveToNext();
		}		
		return categoryWiseExpenses;
	}
	
	
	public ArrayList<ExpensesSubCategoryWise> getTotalSubCategoryWise(String category){
		String sql = " select " + MySqlLiteHelper.EXPENSES_SUB_CATEGORY+"," + 
					 " sum(" + MySqlLiteHelper.EXPENSES_COST+")" + 
					 " from " + MySqlLiteHelper.TABLE_EXPENSES+
					 " where " + MySqlLiteHelper.EXPENSES_CATEGORY + " = '" + category + "'" + 
					 " group by " + MySqlLiteHelper.EXPENSES_SUB_CATEGORY;
		Log.d("shashank", sql);
		Cursor cursor = database.rawQuery(sql, null);
		cursor.moveToFirst();
		Log.d("shashank", "returned rows " +cursor.getCount());
		//hash map to store <category, sum of cost> and pass the data 		
		//HashMap<String, String> map = new HashMap<String, String>();
		
		ArrayList<ExpensesSubCategoryWise> subCategoryWiseExpenses 
			= new ArrayList<ExpensesSubCategoryWise>();
		
		while(!cursor.isAfterLast()){
			//map.put(cursor.getString(0), cursor.getString(1));
			ExpensesSubCategoryWise expensesSubCategoryWise = new ExpensesSubCategoryWise();
			expensesSubCategoryWise.setSubcategory(cursor.getString(0));
			expensesSubCategoryWise.setSubcategorySum(cursor.getString(1));
			subCategoryWiseExpenses.add(expensesSubCategoryWise);
			cursor.moveToNext();
		}		
		return subCategoryWiseExpenses;
	}
	
	
	
	public void deleteAll(){
		database.execSQL("delete from " + MySqlLiteHelper.TABLE_EXPENSES + ";");
	}
	
	public void updateDateFormat(){
		Cursor cursor = database.query(MySqlLiteHelper.TABLE_EXPENSES, 
				new String[]{MySqlLiteHelper.EXPENSES_ID, MySqlLiteHelper.EXPENSES_DATE}, 
				   null,
				   null,
				   null,
				   null,
				   null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			String date = cursor.getString(1);
			Log.e("shashank","date : " + date);
			if(date.contains("/")){
				try{
				Date dateObject = new SimpleDateFormat("dd/mm/yyyy").parse(date);
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateObject);
				Log.e("shashank", "converted : " + date + " -> " + dateObject.toString());
				String sql= "update " + MySqlLiteHelper.TABLE_EXPENSES + " set " + MySqlLiteHelper.EXPENSES_DATE + " = " + cal.getTimeInMillis() + " where " + MySqlLiteHelper.EXPENSES_ID + " = " + cursor.getString(0);
				Log.e("shashank", "executing ... " +sql);
				database.execSQL(sql);
				
				}catch (Exception e) {
					e.printStackTrace();
					cursor.moveToNext();
				}
			}
			cursor.moveToNext();
		}
	}
	
	public void dropTable(){
		database.execSQL("drop table " + MySqlLiteHelper.TABLE_EXPENSES + ";");
	}
	
	public boolean has(String str){
		if(str!=null && str.length() > 0)
			{return true;}
		return false;
		
	}
	

}
