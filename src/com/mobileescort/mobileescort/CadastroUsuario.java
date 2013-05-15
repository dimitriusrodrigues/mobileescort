package com.mobileescort.mobileescort;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.clientWS.UsuarioREST;
import com.mobileescort.mobileescort.model.Rota;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.utils.AlertDialogManager;

public class CadastroUsuario extends Activity {

	Button btSalvar;
	EditText etNome;
	EditText etCelular;
	String activityOrigem;
	int id_rota;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		Bundle params = intent.getExtras();  
		if(params!=null) { 
			activityOrigem = params.getString("origem");
			id_rota = params.getInt("id_rota");
		}
		
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
	                	usuario = usuarioREST.getUsuario( usuario.getNome() , usuario.getPassword());
	                	if (usuario != null) {
	                		Login.repositorio.salvarUsuario(usuario);
	        				if (activityOrigem.equals("UsuariosActivity")) {
		                		Rota rota = Login.repositorio.buscarRota(id_rota);
		                		List<Usuario> listUsuarios = new ArrayList<Usuario>();
		                		listUsuarios = rota.getUsuarios();
		                		listUsuarios.add(usuario);
		    	                rota.setUsuarios(listUsuarios);
		    	                RotaREST rotaREST = new RotaREST();
		    					String res = rotaREST.inserirRota(rota);
		    					if (res.equals("OK")) {
		    						Login.repositorio.salvarRota(rota);
		    	                	finish();
		    	                 }
		    					 else {
		    	                	 alert.showAlertDialog(CadastroUsuario.this,
		    	 	      					"Insert Failed","Falha ao inserir novo passageiro na rota", false);
		    	                 }
	        				}else {
	        					finish();
	        				}	
	                	} 
	                	else {
	                		throw new Exception("Erro ao buscar usuário");
	                	}
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
