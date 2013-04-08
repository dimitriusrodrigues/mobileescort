package com.mobileescort.mobileescort.utils;

import android.content.Context;

public class UserFunctions {
	
	private static String loginURL = "http://10.0.2.2/ah_login_api/";
	private static String registerURL = "http://10.0.2.2/ah_login_api/";
	
	private static String login_tag = "login";
	private static String register_tag = "register";
	
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}
	
}