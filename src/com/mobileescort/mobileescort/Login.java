package com.mobileescort.mobileescort;

import java.util.HashMap;

import com.mobileescort.mobileescort.utils.DatabaseHandler;
import com.mobileescort.mobileescort.utils.SessionManager;
import com.mobileescort.mobileescort.utils.UserFunctions;
import com.mobileescort.mobileescort.clientWS.UsuarioREST;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.utils.AlertDialogManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Login extends Activity {
	
	Button btLogar;
	EditText etNome;
	EditText etPassword;
	TextView tvNovoCad;
	TextView tvLogout;
	
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Session Manager Class
	SessionManager session;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// Session class instance
        session = new SessionManager(getApplicationContext());
        
		etNome = (EditText) findViewById(R.id.etNome);
		etPassword = (EditText) findViewById(R.id.etPassword);
		
		if (session.checkLogin()) {
	        // get user data from session
	        HashMap<String, String> user = session.getUserDetails();
	        String name = user.get(SessionManager.KEY_NAME);
	        String password = user.get(SessionManager.KEY_PASSWORD);

			etNome.setText(name);
			etPassword.setText(password);
		}
		
		btLogar = (Button) findViewById(R.id.btLogar);		
		tvNovoCad = (TextView) findViewById(R.id.tvNovoCad);
        tvLogout = (TextView) findViewById(R.id.tvLogout);

		
		tvNovoCad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Login.this,CadastroUsuario.class);
				Bundle params = new Bundle();
                params.putString("origem", "Login");
                it.putExtras(params);
				startActivity(it);
				
			}
		});
		
		/**
         * Logout button click event
         * */
        tvLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// Clear the session data
				// This will clear all session data and 
				// redirect user to LoginActivity
				session.logoutUser();
				etNome.setText(session.KEY_NAME);
				etPassword.setText(session.KEY_PASSWORD);

			}
		});
		
        btLogar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				 Usuario usuario = new Usuario();
				
	             UsuarioREST usuarioREST = new UsuarioREST();
	             try {
	                 usuario = usuarioREST.getUsuario(etNome.getText().toString() , etPassword.getText().toString());
	                 
	                 if (usuario == null) {
	                	 
	                	 alert.showAlertDialog(Login.this,
	     					"Login Failed",
	     					"Usuario not found", false);
	                 } else{
	                	 
	                	 session.createLoginSession(usuario);

	                	 DatabaseHandler dbh = new DatabaseHandler(Login.this);
	                	 Usuario usuarioTmp = dbh.getUsuario(session.getIdMotorista());
	                	 if (usuarioTmp.getNome() == null){
	                		 dbh.addUser(usuario); 
	                	 } else {
	                		 dbh.update(usuario);
	                	 }
	                	 
	                	 // TODO Trocar por Enun
	                	 if (session.getPerfil().equals("M")) {
	                		 Intent it = new Intent(Login.this,HomeCondutor.class);
		     				 startActivity(it);
	                	 }else {
	                		 Intent it = new Intent(Login.this,Home.class);
		     				 startActivity(it);	 
	                	 }
	                	 
	                 }
	                	 
	                 
	             } catch (Exception e) {
	            	 alert.showAlertDialog(Login.this,
		     					"Login Failed",
		     					e.getMessage(), false);

	             }
	             
			}
		});
	}

}
        
