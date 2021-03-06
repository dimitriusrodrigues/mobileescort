package com.mobileescort.mobileescort;

import com.mobileescort.mobileescort.model.Viagem;
import com.mobileescort.mobileescort.utils.AlertDialogManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class HomeCondutor extends Activity {

	ImageButton btCadastro;
	ImageButton btRota;
	ImageButton btNotificar;
	ImageButton btPresenca;
	int id_rota;
	int id_viagem;
	Viagem viagem;
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_condutor);
		
		btCadastro = (ImageButton) findViewById(R.id.btCadastro);
        
        btCadastro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(HomeCondutor.this,Cadastro.class);
				startActivity(it);
			}
		});
        
        btRota = (ImageButton) findViewById(R.id.btRota);
        
        btRota.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				viagem = Login.repositorio.buscarViagem();
				if (viagem == null) {
					Intent it = new Intent(HomeCondutor.this,RotasActivity.class);
					startActivity(it);
				} else {
					id_rota = viagem.getId_rota();
					Intent it = new Intent(HomeCondutor.this,GoogleMapsActivity.class);
					it.putExtra("id_viagem", viagem.getId_viagem());
					it.putExtra("id_rota", viagem.getId_rota());
					startActivity(it);
				}
			}
		});
        
        btNotificar = (ImageButton) findViewById(R.id.btNotificar);
        
        btNotificar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				viagem = Login.repositorio.buscarViagem();
				if (viagem == null) {
					alert.showAlertDialog(HomeCondutor.this, getString(R.string.title_msg_notificationfailed), getString(R.string.body_msg_notificationinvalid), false);
				} else {
					Intent it = new Intent(HomeCondutor.this,Notificar.class);
					it.putExtra("id_viagem", viagem.getId_viagem());
					it.putExtra("id_rota", viagem.getId_rota());
					startActivity(it);
				}	
			}
		});
        
        btPresenca = (ImageButton) findViewById(R.id.btPresenca);
        
        btPresenca.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				viagem = Login.repositorio.buscarViagem();
				if (viagem == null) {
					alert.showAlertDialog(HomeCondutor.this, getString(R.string.title_msg_presencafailed), getString(R.string.body_msg_presencainvalid), false);
				} else {
					id_rota = viagem.getId_rota();
					Intent it = new Intent(HomeCondutor.this,PresencaActivity.class);
					startActivity(it);
				}
			}
		});
        
	}
}
