package com.mobileescort.mobileescort;

import com.mobileescort.mobileescort.clientWS.UsuarioREST;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.utils.AlertDialogManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Cadastro extends Activity {
	
	//teste
	// 18042013 DimitriusRodrigues : Alterado nome dos botões.
	
	Button btEnviar;
	EditText etNome;
	EditText etCelular;
	EditText etEmail;
	EditText etPassword;
	
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
        
        //CORRIGIR
        try {
			usuario = usuarioREST.getUsuario("Antonio", "84489104");
			if (usuario != null) {
				etNome.setText(usuario.getNome());
				etCelular.setText(usuario.getCelular());
				etEmail.setText(usuario.getEmail());
				etPassword.setText(usuario.getPassword());
				
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			alert.showAlertDialog(Cadastro.this,
 					"Search Failed",
 					e.getMessage(), false);
		}
        
        btEnviar.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				usuario.setNome(etNome.getText().toString());
				usuario.setCelular(etCelular.getText().toString());
				//CORRIGIR
				usuario.setPassword(etCelular.getText().toString());
				usuario.setPerfil("M");
				//CORRIGIR
				usuario.setRegistro("REGISTRO");
				try {
					String resposta = usuarioREST.inserirUsuario(usuario);
	            } catch (Exception e) {
	            	alert.showAlertDialog(Cadastro.this,
	     					"Insert Failed",
	     					e.getMessage(), false);
	            }
			}	
		});
    }
}
