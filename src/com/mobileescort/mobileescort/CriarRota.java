package com.mobileescort.mobileescort;

import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.model.Rota;
import com.mobileescort.mobileescort.utils.AlertDialogManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CriarRota extends Activity {

	EditText etDescricao;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_criar_rota);
		
		Button btCriarRota = (Button) findViewById(R.id.btCriarRota);
		etDescricao = (EditText) findViewById(R.id.etDescricao);
		
		btCriarRota.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				try {
					Rota rota = new Rota();			
					rota.setDescricao(etDescricao.getText().toString());
					// TO DO
					rota.setId_motorista(5);
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
