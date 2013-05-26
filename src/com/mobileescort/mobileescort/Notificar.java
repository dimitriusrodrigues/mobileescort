package com.mobileescort.mobileescort;

import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.utils.AlertDialogManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class Notificar extends Activity {
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notificar);
		
		ImageButton btTransito = (ImageButton) findViewById(R.id.btTransito);
        
		btTransito.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent it = new Intent(Notificar.this,PresencaActivity.class);
				//startActivity(it);
				
				//Toast.makeText(getBaseContext(), "Tr�nsito", Toast.LENGTH_SHORT).show();
				RotaREST rotaRest = new RotaREST();
				try {
					//TODO Arrumar Id Rota.
					rotaRest.enviarMenesagem(11, getString(R.string.notificar_transito));
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
				//Intent it = new Intent(Notificar.this,PresencaActivity.class);
				//startActivity(it);
				//Toast.makeText(getBaseContext(), "Mec�nico", Toast.LENGTH_SHORT).show();
				RotaREST rotaRest = new RotaREST();
				try {
					//TODO Arrumar Id Rota.
					rotaRest.enviarMenesagem(11, getString(R.string.notificar_mecanico));
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
				//Intent it = new Intent(Notificar.this,PresencaActivity.class);
				//startActivity(it);
				//Toast.makeText(getBaseContext(), "Hospital", Toast.LENGTH_SHORT).show();
				RotaREST rotaRest = new RotaREST();
				try {
					//TODO Arrumar Id Rota.
					rotaRest.enviarMenesagem(11, getString(R.string.notificar_hospital));
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
				//Intent it = new Intent(Notificar.this,PresencaActivity.class);
				//startActivity(it);
				//Toast.makeText(getBaseContext(), "Acidente", Toast.LENGTH_SHORT).show();
				
				RotaREST rotaRest = new RotaREST();
				try {
					//TODO Arrumar Id Rota.
					rotaRest.enviarMenesagem(11, getString(R.string.notificar_acidente));
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
				//Intent it = new Intent(Notificar.this,PresencaActivity.class);
				//startActivity(it);
				//Toast.makeText(getBaseContext(), "Posto", Toast.LENGTH_SHORT).show();
				
				RotaREST rotaRest = new RotaREST();
				try {
					//TODO Arrumar Id Rota.
					rotaRest.enviarMenesagem(11, getString(R.string.notificar_abastecimento));
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
				//Intent it = new Intent(Notificar.this,PresencaActivity.class);
				//startActivity(it);
				//Toast.makeText(getBaseContext(), "Mensagem", Toast.LENGTH_SHORT).show();
				
				RotaREST rotaRest = new RotaREST();
				try {
					//TODO Arrumar Id Rota.
					rotaRest.enviarMenesagem(11, "");
				} catch (Exception e) {
		        	 alert.showAlertDialog(Notificar.this,
		     					"Send Notification Failed",
		     					e.getMessage(), false);
				}
			}
		});
	}
}
