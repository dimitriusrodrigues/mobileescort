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
        
        btRegistrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Cadastro.this,RegisterActivity.class);
				// Registering user on our server					
				// Sending details to RegisterActivity
				it.putExtra("localizacao", etEndereco.getText() + " " + etCidade.getText());
				startActivityForResult(it, 2);
			}
		});

        
		// Session class instance
        session = new SessionManager(getApplicationContext());
        if (session.checkLogin()) {
	        try {
		        // get user data from session
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
	 					"Search Failed",
	 					e.getMessage(), false);
			}
			
		} else {
        	alert.showAlertDialog(Cadastro.this,
   					"Cadastro Failed","Sessão não localizada!", false);
        	
        	finish();
		}
        
                
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
					String resposta = usuarioREST.inserirUsuario(usuario);
					if (resposta.equals("OK")) {
						Login.repositorio.salvarUsuario(usuario);	
	                	finish();
	                 }
					 else {
	                	 alert.showAlertDialog(Cadastro.this,
	 	      					"Insert Failed","Falha ao inserir novo usuário", false);
	                 }
	            } catch (Exception e) {
	            	alert.showAlertDialog(Cadastro.this,
	     					"Insert Failed",
	     					e.getMessage(), false);
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
	
}
