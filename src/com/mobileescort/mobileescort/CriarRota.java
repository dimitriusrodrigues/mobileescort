package com.mobileescort.mobileescort;

import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.model.Rota;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.utils.AlertDialogManager;
import com.mobileescort.mobileescort.utils.SessionManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CriarRota extends Activity {

	EditText etDescricao;
	EditText etNome;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_criar_rota);
		
		Button btCriarRota = (Button) findViewById(R.id.btCriarRota);
		etDescricao = (EditText) findViewById(R.id.etDescricao);
		etNome = (EditText) findViewById(R.id.etNome);
		
		if (Login.session.checkLogin()) {
			etNome.setText(Login.session.getUserDetails().get(SessionManager.KEY_NAME));
		}	
		btCriarRota.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				try {
					Rota rota = new Rota();			
					rota.setDescricao(etDescricao.getText().toString());
					
			        if (Login.session.checkLogin()) {
			        	// TODO Verificar esta inclusão
			        	/*
			        	DatabaseHandler dbh = new DatabaseHandler(CriarRota.this);
			        	Usuario motorista = dbh.getUsuario(session.getIdMotorista());
			        	*/
			        	Usuario motorista = Login.repositorio.buscarUsuario(Login.session.getIdMotorista());
			        	rota.setMotorista(motorista);
			        	
			        }else {
			        	alert.showAlertDialog(CriarRota.this,
	 	      					"Create Failed","Motorista não encontrado..", false);
			        	
			        	finish();
			        }
					
					RotaREST rotaREST = new RotaREST();
					String resposta = rotaREST.inserirRota(rota);
					
					if (resposta.equals("OK")) {
	                	finish();
	                 }
					 else {
	                	 alert.showAlertDialog(CriarRota.this,
	 	      					"Insert Failed","Falha ao inserir nova rota", false);
	                 }
					
				} catch (Exception e) {
	            	 alert.showAlertDialog(CriarRota.this,
		     					"Criar Rota Failed",
		     					e.getMessage(), false);

	             }
				
			}
		});
	}
	
}
