package com.mobileescort.mobileescort;

import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.model.Rota;
import com.mobileescort.mobileescort.model.Viagem;
import com.mobileescort.mobileescort.utils.AlertDialogManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Notificar extends Activity {
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	int id_rota;
	int id_viagem;
	Viagem viagem;
	Rota rota;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notificar);
		
		viagem = Login.repositorio.buscarViagem();
		if (viagem == null) {
			Toast.makeText(getBaseContext(), "Não existe uma rota iniciada", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		ImageButton btTransito = (ImageButton) findViewById(R.id.btTransito);
        
		btTransito.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				RotaREST rotaRest = new RotaREST();
				try {
					//TODO Arrumar Id Rota.
					rotaRest.enviarMenesagem(viagem.getId_rota(), getString(R.string.notificar_transito));
				} catch (Exception e) {
		        	 alert.showAlertDialog(Notificar.this,
		     					"Send Notification Failed",
		     					e.getMessage(), false);
				}
				
			}
		});
		
		ImageButton btMecanico = (ImageButton) findViewById(R.id.btMecanico);
        
		btMecanico.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RotaREST rotaRest = new RotaREST();
				try {
					rotaRest.enviarMenesagem(viagem.getId_rota(), getString(R.string.notificar_mecanico));
				} catch (Exception e) {
		        	 alert.showAlertDialog(Notificar.this,
		     					"Send Notification Failed",
		     					e.getMessage(), false);
				}
				
			}
		});
		
		ImageButton btHospital = (ImageButton) findViewById(R.id.btHospital);
        
		btHospital.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RotaREST rotaRest = new RotaREST();
				try {
					rotaRest.enviarMenesagem(viagem.getId_rota(), getString(R.string.notificar_hospital));
				} catch (Exception e) {
		        	 alert.showAlertDialog(Notificar.this,
		     					"Send Notification Failed",
		     					e.getMessage(), false);
				}
				
			}
		});
		
		ImageButton btAcidente = (ImageButton) findViewById(R.id.btAcidente);
        
		btAcidente.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RotaREST rotaRest = new RotaREST();
				try {
					rotaRest.enviarMenesagem(viagem.getId_rota(), getString(R.string.notificar_acidente));
				} catch (Exception e) {
		        	 alert.showAlertDialog(Notificar.this,
		     					"Send Notification Failed",
		     					e.getMessage(), false);
				}
			}
		});
		
		ImageButton btPosto = (ImageButton) findViewById(R.id.btPosto);
        
		btPosto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RotaREST rotaRest = new RotaREST();
				try {
					rotaRest.enviarMenesagem(viagem.getId_rota(), getString(R.string.notificar_abastecimento));
				} catch (Exception e) {
		        	 alert.showAlertDialog(Notificar.this,
		     					"Send Notification Failed",
		     					e.getMessage(), false);
				}
			}
		});
		
		ImageButton btMensagem = (ImageButton) findViewById(R.id.btMensagem);
        
		btMensagem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RotaREST rotaRest = new RotaREST();
				try {
					rotaRest.enviarMenesagem(viagem.getId_rota(), "Digitar a mensagem");
				} catch (Exception e) {
		        	 alert.showAlertDialog(Notificar.this,
		     					"Send Notification Failed",
		     					e.getMessage(), false);
				}
			}
		});
	}
}
