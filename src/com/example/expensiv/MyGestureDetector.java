package com.example.expensiv;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.Toast;

class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{
	/**
	 * 
	 */
	private final CategoryDetails categoryDetails;

	/**
	 * @param categoryDetails
	 */
	MyGestureDetector(CategoryDetails categoryDetails) {
		this.categoryDetails = categoryDetails;
	}

	private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// ignore swipe thats too 'vertical'
		if(Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH ){
			Toast.makeText(this.categoryDetails, "youre off path vertically" + "\n " + this.categoryDetails.eventToString(e1) + " : " + this.categoryDetails.eventToString(e2),  Toast.LENGTH_LONG).show();
			return false;
		}
		if(Math.abs(velocityX) <SWIPE_THRESHOLD_VELOCITY){
			Toast.makeText(this.categoryDetails, "too slow", Toast.LENGTH_LONG).show();
			return false;
		}
		
		// right to left swipe
		if(Math.abs(e1.getX() - e2.getX()) > SWIPE_MIN_DISTANCE ) {
			if(e2.getX() > e1.getX()){
			Toast.makeText(this.categoryDetails, "Right to left", Toast.LENGTH_LONG).show();}
			else{
				Toast.makeText(this.categoryDetails, "Left to right", Toast.LENGTH_LONG).show();	
			}
		}
		
		else {
			Toast.makeText(this.categoryDetails, "too short", Toast.LENGTH_LONG).show();
		}
		
		return false;
	}
}