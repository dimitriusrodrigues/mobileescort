package com.mobileescort.mobileescort;

import android.app.Activity;
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
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
				//CORRIGIR
				usuario.setPassword(etCelular.getText().toString());
				//CORRIGIR
				usuario.setPerfil("M");
				
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
	     			// TODO Auto-generated catch block
	     			alert.showAlertDialog(CadastroUsuario.this,
	      					"Insert Failed",
	      					e.getMessage(), false);
	     		}

			}
		});
	
	}

	

}
