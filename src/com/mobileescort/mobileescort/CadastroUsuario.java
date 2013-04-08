package com.mobileescort.mobileescort;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.mobileescort.mobileescort.clientWS.UsuarioREST;
import com.mobileescort.mobileescort.model.Usuario;

public class CadastroUsuario extends Activity {

	Button btSalvar;
	EditText etNome;
	EditText etCelular;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro_usuario);


		btSalvar = (Button) findViewById(R.id.btSalvar);
		
		etNome = (EditText) findViewById(R.id.etNome);
		etCelular = (EditText) findViewById(R.id.etPassword);
        
		btSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*Intent it = new Intent(CadastroUsuario.this,UsuariosActivity.class);
				startActivity(it);*/
				Usuario usuario = new Usuario();
				usuario.setNome(etNome.getText().toString());
				usuario.setCelular(etCelular.getText().toString());
				//CORRIGIR
				usuario.setPassword(etCelular.getText().toString());
				//CORRIGIR
				usuario.setPerfil("U");
				
	             UsuarioREST usuarioREST = new UsuarioREST();
	             try {
	                 String resposta = usuarioREST.inserirUsuario(usuario);
	                 
	             } catch (Exception e) {
	                 e.printStackTrace();

	             }

			}
		});
	
	}

	

}
