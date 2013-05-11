package com.mobileescort.mobileescort.utils;

import java.util.HashMap;

import com.mobileescort.mobileescort.model.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final String TAG = "DataBaseHandler";
	
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 4;

	// Database Name
	private static final String DATABASE_NAME = "mobileescort";

	// Login table name
	private static final String TABLE_LOGIN = "usuario";

	// Login Table Columns names
	private static final String KEY_ID = "id_usuario";
	private static final String KEY_REGISTRO = "registro";
	private static final String KEY_NAME = "nome";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_CELULAR = "celular";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_PERFIL = "perfil";
	private static final String KEY_CIDADE = "cidade";
	private static final String KEY_ENDERECO = "endereco";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";
	

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_REGISTRO + " TEXT,"
				+ KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE,"
				+ KEY_CELULAR + " TEXT,"
				+ KEY_PASSWORD + " TEXT,"
				+ KEY_PERFIL + " TEXT,"
				+ KEY_CIDADE + " TEXT,"
				+ KEY_ENDERECO + " TEXT,"
				+ KEY_LATITUDE + " DOUBLE,"
				+ KEY_LONGITUDE + " DOUBLE" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);
	}
	

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(Usuario usuario) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		values.put(KEY_ID , usuario.getId_usuario() );
		values.put(KEY_REGISTRO, usuario.getRegistro());				
		values.put(KEY_NAME, usuario.getNome());
		values.put(KEY_EMAIL, usuario.getEmail());
		values.put(KEY_CELULAR, usuario.getCelular()); 
		values.put(KEY_PASSWORD, usuario.getPassword());
		values.put(KEY_PERFIL, usuario.getPerfil());
		values.put(KEY_CIDADE, usuario.getCidade());
		values.put(KEY_ENDERECO, usuario.getEndereco());
		values.put(KEY_LATITUDE, usuario.getLatitude());
		values.put(KEY_LONGITUDE, usuario.getLongitude());

		
		// Inserting Row
		db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection
	}
	
	public boolean deleteUser(long id){
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		  int qt = db.delete(TABLE_LOGIN, KEY_ID + "=" + id, null);
		  return qt > 0;
	 }
	
	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String,String> user = new HashMap<String,String>();
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
		 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
        	user.put("name", cursor.getString(2));
        	user.put("email", cursor.getString(3));
        	user.put("celular", cursor.getString(4));
        	user.put("registro", cursor.getString(5));
        }
        cursor.close();
        db.close();
		// return user
		return user;
	}

	/**
	 * Getting user login status
	 * return true if rows are there in table
	 * */
	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		
		// return row count
		return rowCount;
	}
	
	/**
	 * Re crate database
	 * Delete all tables and create them again
	 * */
	public void resetTables(){
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_LOGIN, null, null);
		db.close();
	}
	
		 
	public Usuario getUsuario(long id) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor mCursor = db.query(true, TABLE_LOGIN, null, KEY_ID + "=" + id, null, null, null, null, null);
		Usuario usuario = new Usuario();
		
		if(mCursor != null){
			mCursor.moveToFirst();
			if(mCursor.getCount() > 0){
				usuario.setId_usuario( mCursor.getInt(mCursor.getColumnIndex(KEY_ID)) );
				usuario.setRegistro(mCursor.getString(mCursor.getColumnIndex(KEY_REGISTRO)));
				usuario.setNome(mCursor.getString(mCursor.getColumnIndex(KEY_NAME)) );
				usuario.setEmail(mCursor.getString(mCursor.getColumnIndex(KEY_EMAIL)));
				usuario.setCelular(mCursor.getString(mCursor.getColumnIndex(KEY_CELULAR)));
				usuario.setPassword(mCursor.getString(mCursor.getColumnIndex(KEY_PASSWORD)));
				usuario.setPerfil(mCursor.getString(mCursor.getColumnIndex(KEY_PERFIL)));
				usuario.setCidade(mCursor.getString(mCursor.getColumnIndex(KEY_CIDADE)));
				usuario.setEndereco(mCursor.getString(mCursor.getColumnIndex(KEY_ENDERECO)));
				usuario.setLatitude(mCursor.getDouble(mCursor.getColumnIndex(KEY_LATITUDE)));
				usuario.setLongitude(mCursor.getDouble(mCursor.getColumnIndex(KEY_LONGITUDE)));
			}	
		}
		
		db.close();
		mCursor.close();
		
		return usuario;
		
	}
	
	public void update(Usuario usuario){
		
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		values.put(KEY_ID , usuario.getId_usuario() );
		values.put(KEY_REGISTRO, usuario.getRegistro());				
		values.put(KEY_NAME, usuario.getNome());
		values.put(KEY_EMAIL, usuario.getEmail());
		values.put(KEY_CELULAR, usuario.getCelular()); 
		values.put(KEY_PASSWORD, usuario.getPassword());
		values.put(KEY_PERFIL, usuario.getPerfil());
		values.put(KEY_CIDADE, usuario.getCidade());
		values.put(KEY_ENDERECO, usuario.getEndereco());
		values.put(KEY_LATITUDE, usuario.getLatitude());
		values.put(KEY_LONGITUDE, usuario.getLongitude());
		
		String id = String.valueOf(usuario.getId_usuario());
		long result = db.update(TABLE_LOGIN, values, "id_usuario = ?", new String[]{id});
		
		Log.d(TAG, "linhas alteradas: " + result);
		
	}
	
	
	

}
