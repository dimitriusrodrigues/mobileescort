package com.mobileescort.mobileescort;

import com.mobileescort.mobileescort.clientWS.UsuarioREST;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.utils.AlertDialogManager;
import com.mobileescort.mobileescort.utils.MainActivity;

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
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		btLogar = (Button) findViewById(R.id.btLogar);
		etNome = (EditText) findViewById(R.id.etNome);
		etPassword = (EditText) findViewById(R.id.etPassword);
		tvNovoCad = (TextView) findViewById(R.id.tvNovoCad);
		
		tvNovoCad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Login.this,CadastroUsuario.class);
				startActivity(it);
				
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
	                	 
	                	 Intent it = new Intent(Login.this,HomeCondutor.class);
	     				startActivity(it);
	                	 
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
