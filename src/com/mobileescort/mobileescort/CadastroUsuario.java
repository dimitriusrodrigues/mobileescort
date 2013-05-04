package com.mobileescort.mobileescort;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.mobileescort.mobileescort.clientWS.UsuarioREST;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.utils.AlertDialogManager;

public class CadastroUsuario extends Activity {

	Button btSalvar;
	EditText etNome;
	EditText etCelular;
	String activityOrigem;
	
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		Bundle params = intent.getExtras();  
		if(params!=null) { activityOrigem = params.getString("origem"); }
		
		setContentView(R.layout.activity_cadastro_usuario);


		btSalvar = (Button) findViewById(R.id.btSalvar);
		
		etNome = (EditText) findViewById(R.id.etNome);
		etCelular = (EditText) findViewById(R.id.etCelular);
        
		btSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*Intent it = new Intent(CadastroUsuario.this,UsuariosActivity.class);
				startActivity(it);*/
				
				Usuario usuario = new Usuario();
				usuario.setNome(etNome.getText().toString());
				usuario.setCelular(etCelular.getText().toString());
				//TODO Ajustar o password
				usuario.setPassword(etCelular.getText().toString());
				
				if (activityOrigem.equals("Login")) {
					usuario.setPerfil("M");
				} else {
					usuario.setPerfil("U");
				}
				
				UsuarioREST usuarioREST = new UsuarioREST();
	            try {
	                String resposta = usuarioREST.inserirUsuario(usuario);
	                if (resposta.equals("OK")) {
	                	finish();
	                }
	                else {
	                	alert.showAlertDialog(CadastroUsuario.this,
	                			"Insert Failed","Falha ao inserir novo usuário", false);
	                }
	             } catch (Exception e) {
	     			
	     			alert.showAlertDialog(CadastroUsuario.this,
	      					"Insert Failed",
	      					e.getMessage(), false);
	     		}

			}
		});
	
	}

	

}
