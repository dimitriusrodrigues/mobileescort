package com.mobileescort.mobileescort.utils;

import java.util.HashMap;

import com.mobileescort.mobileescort.Login;
import com.mobileescort.mobileescort.model.Usuario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;
	
	// Editor for Shared preferences
	Editor editor;
	
	// Context
	Context _context;
	
	// Shared pref mode
	int PRIVATE_MODE = 0;
	
	// Sharedpref file name
	private static final String PREF_NAME = "MobileEscortPref";
	
	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";
	
	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "Nome";
	
	// Email address (make variable public to access from outside)
	public static final String KEY_PASSWORD = "Celular";
	
	// URL 
	public static final String URL_WS = "http://10.0.0.102:8080/wsMobileEscort/api/";
	//public static final String URL_WS = "http://54.214.27.148:8080/wsMobileEscort/api/";
	//public static final String URL_WS = "http://10.0.2.2:8080/wsMobileEscort/api/";
	
	// Sender Id
	//public static final String SENDER_ID = "960215357691";
	public static final String SENDER_ID = "427819336164";
	
	// ID 
	public static final String KEY_IDMOTORISTA = "IdMotorista";
	
	// Perfil do usuario
	public static final String KEY_PERFIL = "Perfil";
	
	// Constructor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	/**
	 * Create login session
	 * */
	public void createLoginSession(Usuario usuario){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		
		// Storing name in pref
		editor.putString(KEY_NAME, usuario.getNome());
		
		// Storing celular/password in pref
		editor.putString(KEY_PASSWORD, usuario.getPassword());
		
		// Storing idMotorista in pref
		editor.putInt(KEY_IDMOTORISTA, usuario.getId_usuario());
		
		// Storing Perfil in pref
		editor.putString(KEY_PERFIL, usuario.getPerfil());
		
		// commit changes
		editor.commit();
		
	}	
	
	/**
	 * Check login method wil check user login status
	 * If false it will redirect user to login page
	 * Else won't do anything
	 * */
	public boolean checkLogin(){
		// Check login status
		if(!this.isLoggedIn()){
			return false;
		}
		return true;
		
	}
	
	
	
	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String >();
				
		// user name
		user.put(KEY_NAME, pref.getString(KEY_NAME, null));
		
		// user email 
		user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
		
		// user id
		user.put(KEY_IDMOTORISTA, Integer.toString(pref.getInt(KEY_IDMOTORISTA, 0)));
		
		// return user
		return user;
	}
	
	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
		
		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, Login.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// Staring Login Activity
		_context.startActivity(i);
	}
	
	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}
	
	/**
	 * Quick check for login
	 * **/
	// Get Id Motorista
	public int getIdMotorista(){
		return pref.getInt(KEY_IDMOTORISTA, 0);
	}
	
	/**
	 * Quick check for login
	 * **/
	// Get Id Motorista
	public String getPerfil(){
		return pref.getString(KEY_PERFIL, null);
	}

	
}
