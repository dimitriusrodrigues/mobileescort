package com.mobileescort.mobileescort;


import java.util.HashMap;


import com.mobileescort.mobileescort.clientWS.UsuarioREST;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.utils.AlertDialogManager;
import com.mobileescort.mobileescort.GCM;
import com.mobileescort.mobileescort.utils.SessionManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Cadastro extends Activity {
	
	Button btEnviar;
	EditText etNome;
	EditText etCelular;
	EditText etEmail;
	EditText etPassword;
	EditText etEndereco;
	EditText etCidade;
	
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
        etNome = (EditText) findViewById(R.id.etNome);
        etCelular = (EditText) findViewById(R.id.etCelular);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etEndereco = (EditText) findViewById(R.id.etEndereco);
        etCidade= (EditText) findViewById(R.id.etCidade);
        
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
				registro= getRegistro();
				latitude = getLatitude();
				longitude = getLongitude();
				usuario.setPerfil(perfil);
				usuario.setRegistro(registro);
				usuario.setLatitude(latitude);
				usuario.setLongitude(longitude);
				
				try {
					String resposta = usuarioREST.inserirUsuario(usuario);
					if (resposta.equals("OK")) {
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

			private Double getLongitude() {
				// TODO Buscar Longitude
				return 0.0;
			}

			private Double getLatitude() {
				// TODO Buscar Latitude
				return 0.0;
			}

			private String getRegistro() {
				if (GCM.isAtivo(getApplicationContext())) {
					GCM.desativa(getApplicationContext());
				} 
				
				GCM.ativa(getApplicationContext());
				return "";
						
			}	
		});
	}
	
	
}
