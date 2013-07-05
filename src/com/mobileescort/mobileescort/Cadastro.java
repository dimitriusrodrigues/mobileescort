package com.mobileescort.mobileescort;


import java.util.HashMap;
import com.mobileescort.mobileescort.clientWS.UsuarioREST;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.utils.AlertDialogManager;
import com.mobileescort.mobileescort.utils.SessionManager;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Cadastro extends Activity {
	
	Button btEnviar;
	Button btRegistrar;
	
	EditText etNome;
	EditText etCelular;
	EditText etEmail;
	EditText etPassword;
	EditText etEndereco;
	EditText etCidade;
	CheckBox cbRegistrado;
	Spinner spPerfil;
	
	String registro, perfil;
	Double latitude, longitude;
	
	// Session Manager Class
	SessionManager session;
	
    final UsuarioREST usuarioREST = new UsuarioREST();
    Usuario usuario = new Usuario();
    
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        
        btEnviar = (Button) findViewById(R.id.btEnviar);
        btRegistrar = (Button) findViewById(R.id.btRegistrar);
        etNome = (EditText) findViewById(R.id.etNome);
        etCelular = (EditText) findViewById(R.id.etCelular);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etEndereco = (EditText) findViewById(R.id.etEndereco);
        etCidade= (EditText) findViewById(R.id.etCidade);
        cbRegistrado = (CheckBox) findViewById(R.id.cbRegistro);
        spPerfil = (Spinner) findViewById(R.id.spPerfil);
        
        session = new SessionManager(getApplicationContext());
        if (session.checkLogin()) {
	        try {
		        HashMap<String, String> user = session.getUserDetails();
				usuario = usuarioREST.getUsuario(user.get(SessionManager.KEY_NAME), user.get(SessionManager.KEY_PASSWORD));
				if (usuario != null) {
					etNome.setText(usuario.getNome());
					etCelular.setText(usuario.getCelular());
					etPassword.setText(usuario.getPassword());
					if(usuario.getEmail() != null){
						etEmail.setText(usuario.getEmail());
					}
					if(usuario.getEndereco() != null){
						etEndereco.setText(usuario.getEndereco());
					}
					if(usuario.getCidade() != null){
						etCidade.setText(usuario.getCidade());
					}
					perfil = usuario.getPerfil();
					spPerfil.setSelection(getPerfil(perfil));
					spPerfil.setClickable(false);
					
					registro = usuario.getRegistro();
					latitude = usuario.getLatitude();
					longitude = usuario.getLongitude();
					
					if (registro != null && !registro.equals("")) {
						cbRegistrado.setPressed(true);
						btRegistrar.setClickable(true);
					}else {
						cbRegistrado.setPressed(false);
						btRegistrar.setClickable(true);
					}
					
					
				}
			} catch (Exception e) {
				
				alert.showAlertDialog(Cadastro.this,
	 					getString(R.string.title_msg_cadastrofailed),
	 					getString(R.string.body_msg_cadastronotfound) + " " + e.getMessage(), false);
				finish();
			}
			
		} else {
        	alert.showAlertDialog(Cadastro.this,
   					getString(R.string.title_msg_sessionfailed),getString(R.string.body_msg_sessionnotfound), false);
        	
        	finish();
		}
        
        addListenerOnSpinnerItemSelection();
        
        btRegistrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Cadastro.this,RegisterActivity.class);
				it.putExtra("localizacao", etEndereco.getText() + " " + etCidade.getText());
				startActivityForResult(it, 2);
			}
		});
                
        btEnviar.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				usuario.setNome(etNome.getText().toString());
				usuario.setCelular(etCelular.getText().toString());
				usuario.setPassword(etPassword.getText().toString());
				usuario.setCidade(etCidade.getText().toString());
				usuario.setEndereco(etEndereco.getText().toString());
				usuario.setEmail(etEmail.getText().toString());
				usuario.setPerfil(perfil);
				usuario.setRegistro(registro);
				usuario.setLatitude(latitude);
				usuario.setLongitude(longitude);
				
				try {
					String resposta = usuarioREST.atualizarUsuario(usuario);
					if (resposta.equals("OK")) {
						Login.repositorio.salvarUsuario(usuario);	
	                	finish();
	                 } else {
	                	 if (resposta.equals("NOK")) {
	                		 alert.showAlertDialog(Cadastro.this,
			 	      					getString(R.string.title_msg_cadastrofailed),getString(R.string.body_msg_cadastropasswordfailed), false);
	                	 } else {
		                	 alert.showAlertDialog(Cadastro.this,
		                			 getString(R.string.title_msg_cadastrofailed),getString(R.string.body_msg_cadastroupdatefailed), false);
		                 } 
	                 }
					 
	            } catch (Exception e) {
	            	alert.showAlertDialog(Cadastro.this,
	     					getString(R.string.title_msg_cadastrofailed),
	     					getString(R.string.body_msg_cadastroerror)+ " " + e.getMessage(), false);
	            }
			}

		});
        
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		registro = data.getExtras().getString("registro");
		latitude = data.getExtras().getDouble("latitude");
		longitude = data.getExtras().getDouble("longitude");
		
		if (registro != null && !registro.equals("")) {
			cbRegistrado.setPressed(true);
			btRegistrar.setClickable(false);
		}
		
	}
	
	public void addListenerOnSpinnerItemSelection() {
		spPerfil = (Spinner) findViewById(R.id.spPerfil);
		spPerfil.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	 }
	
	public class CustomOnItemSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			switch (pos) {
				case 0:
					perfil = "R";
					break;
				case 1:
					perfil = "U";
					break;
				case 2:
					perfil = "M";
					break;
			}
			
		}
			 
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		
		}
	}
	
	private int getPerfil(String perfil) {
		int pos = 0;
		
		if (perfil.equals("R") || perfil.equals("P")) {
			pos = 0;
		} 
		else { 
			if (perfil.equals("U") || perfil.equals("S")) {
				pos = 1;
			}
			else {
				pos = 2;
			}
		}	
		return pos;
	}
	
}
