package com.example.expensiv.shared;


public class Const {
	
	public static final int SWIPE_MIN_DISTANCE = 120;
    public static final int SWIPE_MAX_OFF_PATH = 250;
    public static final int SWIPE_THRESHOLD_VELOCITY = 200;
    
    public static final int SWIPE_ERROR = 0;
    public static final int SWIPE_LEFT_RIGHT = 1;
    public static final int SWIPE_RIGHT_LEFT = 2;
    public static final int SWIPE_UP_DOWN = 3;
    public static final int SWIPE_DOWN_UP = 4;

    public static final String DAY_DATE_MONTH = "E, dd-MMM";
    
    public static final String EXTRA_FOR_MONTH = "forMonth";
	public static final String EXTRA_MSG_ID = "msg_id";
	public static final String EXTRA_SET_MONTH = "setMonth";
	
	 // \s represents whitespace
    // \d represents digit
    public static final String PATTERN_INR_DIGITS = "[Ii][Nn][Rr]\\s*.\\s*[\\d.,]+";
    public static final String PATTERN_RS_DIGITS = "[Rr][Ss]\\s*.\\s*[\\d.,]+";
    public static final String PATTERN_INR_RS = "(" + PATTERN_INR_DIGITS + "|" + PATTERN_RS_DIGITS + ")";
    public static final String PATTERN_NON_DIGITS_OR_DOT = "[^0-9.]";
    public static final String PATTERN_DIGITS_OR_DOT_ONLY = "[0-9.]+";
    public static final String PATTERN_TRAILING_DOT = "(^\\.)|(\\.$)";

}
