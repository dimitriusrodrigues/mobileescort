package com.mobileescort.mobileescort;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;

import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.clientWS.UsuarioREST;
import com.mobileescort.mobileescort.model.Rota;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.utils.AlertDialogManager;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class CadastroUsuario extends Activity {

	Button btSalvar;
	EditText etNome;
	EditText etCelular;
	Spinner spPerfil;

	String activityOrigem;
	String perfil;
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
        spPerfil = (Spinner) findViewById(R.id.spPerfil);
		
		if (activityOrigem.equals("Login")) {
			perfil = "M";
			spPerfil.setClickable(false);
			spPerfil.setSelection(2);
		} else {
			Resources res = getResources();
			String[] opcoes = res.getStringArray(R.array.perfil_array_user);
			ArrayAdapter<String> aPerfil = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opcoes);
			spPerfil.setAdapter(aPerfil);
			perfil = "U";
			spPerfil.setSelection(1);
			spPerfil.setClickable(true);
		}
		
		addListenerOnSpinnerItemSelection();
		
		btSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Usuario usuario = new Usuario();
				usuario.setNome(etNome.getText().toString());
				usuario.setCelular(etCelular.getText().toString());
				usuario.setPassword(etCelular.getText().toString());
				usuario.setRegistro("");
				usuario.setPerfil(perfil);
				
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
	                } else {
	                	if (resposta.equals("NOK")) {
	                		// Incluindo um usuário que já esta cadastrado no servidor, somente vincula na rota 
	                		if (activityOrigem.equals("UsuariosActivity")) {
	                			usuario = usuarioREST.getUsuario( usuario.getNome() , usuario.getPassword());
	    	                	if (usuario != null ) {
	    	                		// Usuario é um motorista e não pode ser passageiro.
	    	                		if (usuario.getPerfil().equals("M")) {
    		    	                	alert.showAlertDialog(CadastroUsuario.this,
    		    	 	      					"Insert Failed","Usuário informado é um motorista e não pode ser vinculado como passageiro da rota.", false);
	    	                		}
	    	                		else {
	    		                		Rota rota = Login.repositorio.buscarRota(id_rota);
	    		                		List<Usuario> listUsuarios = new ArrayList<Usuario>();
	    		                		listUsuarios = rota.getUsuarios();
	    		    	                // Verifica se o usuario já pertence a rota
	    		    	                boolean found = false; 
	    		    	                for (Usuario passageiro: listUsuarios ) {
	    		    	                	if (passageiro.getCelular().equalsIgnoreCase(usuario.getCelular())) {
	    		    	                		found = true;
	    		    	                		break;
	    		    	                	}
	    		    	                }
	    		    	                // Somente vincula se não encontrou nesta rota
	    		    	                if (!found) {
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
	    		    	                } else {
	    		    	                	alert.showAlertDialog(CadastroUsuario.this,
	    		    	 	      					"Insert Failed","Usuário já pertence a rota.", false);
	    		    	                }
	    	                		}
	    	                	} 
	    	                	else {
	    	                		alert.showAlertDialog(CadastroUsuario.this,
		    	 	      					"Insert Failed","Número informado já em uso para outro usuário. Informe outro número de celular.", false);
	    	                	}
	                		}
	                		else {
			                	alert.showAlertDialog(CadastroUsuario.this,
			                			"Insert Failed","Número informado já em uso. Informe outro número de celular.", false);
	                		}
		                }
		                else {
		                	alert.showAlertDialog(CadastroUsuario.this,
		                			"Insert Failed","Falha ao inserir novo usuário", false);
		                }
	                }
	             } catch (Exception e) {
	     			
	     			alert.showAlertDialog(CadastroUsuario.this,
	      					"Insert Failed",
	      					e.getMessage(), false);
	     		}

			}
		});
	
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
			// TODO Auto-generated method stub
			
		}
	}

}
