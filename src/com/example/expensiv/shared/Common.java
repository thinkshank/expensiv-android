package com.example.expensiv.shared;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.Toast;

public class Common {
	
	
	public static boolean has(String str){
		if(str!=null && str.length() > 0)
			{return true;}
		return false;
		
	}
	
	public static long getUnixTimestamp(int year, int month, int day, int hour, int minute) {

	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.YEAR, year);
	    c.set(Calendar.MONTH, month);
	    c.set(Calendar.DAY_OF_MONTH, day);
	    c.set(Calendar.HOUR, hour);
	    c.set(Calendar.MINUTE, minute);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);

	    //return  (c.getTimeInMillis() / 1000L);
	    return  (c.getTimeInMillis());
	}
	
	public static Calendar getCalendarFromUnixTimestamp(long timestamp){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timestamp);
		
		return c;
	}
	
	public static Calendar getCalendarFromUnixTimestamp(String timestamp){
		return getCalendarFromUnixTimestamp(Long.valueOf(timestamp));
	}
	
	public static String getReadableStringFromUnixTimestamp(String timestamp){
		Calendar cal = getCalendarFromUnixTimestamp(Long.valueOf(timestamp));
		return new SimpleDateFormat(Const.DAY_DATE_MONTH).format(cal.getTime()) ;
	}
	

	public static String getUnixTimestampFromDatepicker(DatePicker datepicker){
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, datepicker.getYear());
		cal.set(Calendar.MONTH, datepicker.getMonth());
		cal.set(Calendar.DAY_OF_MONTH, datepicker.getDayOfMonth());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return Long.valueOf(cal.getTimeInMillis()).toString();
	}
	
	
	public static String getUnixTimestampFromCalendar(Calendar calendar){
		
		Calendar cal = calendar;
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return Long.valueOf(cal.getTimeInMillis()).toString();
	}
	
	public static String getUnixTimestampFromDateObject(Date date){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return getUnixTimestampFromCalendar(cal);
	}
	
	
    
	public static Calendar getFirstDayOfThisMonth(){
		// current month and year
		return getFirstDayOfMonth(Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.YEAR));
	}
	
	public static Calendar getFirstDayOfMonth(int month, int year){
	
	Calendar calFrom = Calendar.getInstance();
	calFrom.set(Calendar.YEAR, year);
	calFrom.set(Calendar.MONTH, month);
	calFrom.set(Calendar.DAY_OF_MONTH,1);
	calFrom.set(Calendar.HOUR_OF_DAY,0);
	calFrom.set(Calendar.MINUTE,0);
	calFrom.set(Calendar.SECOND,0);
	calFrom.set(Calendar.MILLISECOND, 0);
	return calFrom;
	}
	
	public static Calendar getLastDayOfThisMonth(){
		return getLastDayOfMonth(Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.YEAR));
	}
	
	public static Calendar getLastDayOfMonth(int month, int year){
	Calendar calTo = Calendar.getInstance();
	calTo.set(Calendar.YEAR, year);
	calTo.set(Calendar.MONTH, month);
	calTo.set(Calendar.DAY_OF_MONTH,1);
	calTo.set(Calendar.HOUR_OF_DAY,0);
	calTo.set(Calendar.MINUTE,0);
	calTo.set(Calendar.SECOND,0);
	calTo.set(Calendar.MILLISECOND,0);
	calTo.add(Calendar.MONTH, 1);
	calTo.add(Calendar.MILLISECOND, -1000);
	return calTo;
	}
	
	
	
	public int getSwipeDirection(MotionEvent e1, MotionEvent e2, float velocityX,	float velocityY) {
		// ignore swipe thats too 'vertical'
		if(Math.abs(e1.getY() - e2.getY()) > Const.SWIPE_MAX_OFF_PATH ){
			//Toast.makeText(this.categoryDetails, "youre off path vertically" + "\n " + this.categoryDetails.eventToString(e1) + " : " + this.categoryDetails.eventToString(e2),  Toast.LENGTH_LONG).show();
			return Const.SWIPE_ERROR;
		}
		if(Math.abs(velocityX) <Const.SWIPE_THRESHOLD_VELOCITY){
			//Toast.makeText(this.categoryDetails, "too slow", Toast.LENGTH_LONG).show();
			return Const.SWIPE_ERROR;
		}
		
		// right to left swipe
		if(Math.abs(e1.getX() - e2.getX()) > Const.SWIPE_MIN_DISTANCE ) {
			if(e2.getX() > e1.getX()){
			//Toast.makeText(this.categoryDetails, "Right to left", Toast.LENGTH_LONG).show();}
			return Const.SWIPE_RIGHT_LEFT;}
			else{
				//Toast.makeText(this.categoryDetails, "Left to right", Toast.LENGTH_LONG).show();
				return Const.SWIPE_LEFT_RIGHT;
			}
		}
		
		else {
			//Toast.makeText(this.categoryDetails, "too short", Toast.LENGTH_LONG).show();
			return Const.SWIPE_ERROR;
		}
		
	}

	public static String getDateReadable(String dateString){
		if(dateString.contains("/")){
			return dateString;
						
		}else{
			return (new SimpleDateFormat(Const.DAY_DATE_MONTH).format(getCalendarFromUnixTimestamp(dateString).getTime()));
		}
			
	}

	public static void setCurrentDateOnDatePicker(DatePicker datepicker) {
		
		setDateOnDatePicker(datepicker,  Calendar.getInstance());
		
	}
	
	public static void setDateOnDatePicker(DatePicker datepicker, Calendar cal) {
		
		
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		datepicker.init(year, month, day, new OnDateChangedListener() {
	
			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				Log.d("shashank", view.getDayOfMonth() + "/" + view.getMonth()
						+ "/" + view.getYear());
			}
		});
	}
	
	public static String debitCreditToCD(String dropdowntext)
	{
		return "Credit".equals(dropdowntext)?"C":"D";
	}
	
	public static String debitCreditFromCD(String singlechar)
	{
		return "C".equals(singlechar)?"Credit":"Debit";
	}
}
