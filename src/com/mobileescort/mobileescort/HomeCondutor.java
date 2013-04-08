package com.mobileescort.mobileescort;

import com.mobileescort.mobileescort.utils.RegisterActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class HomeCondutor extends Activity {

	ImageButton btCadastro;
	ImageButton btRota;
	ImageButton btNotificar;
	ImageButton btPresenca;
	
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
				Intent it = new Intent(HomeCondutor.this,RotasActivity.class);
				startActivity(it);
			}
		});
        
        btNotificar = (ImageButton) findViewById(R.id.btNotificar);
        
        btNotificar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(HomeCondutor.this,Notificar.class);
				startActivity(it);
			}
		});
        
        btPresenca = (ImageButton) findViewById(R.id.btPresenca);
        
        btPresenca.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(HomeCondutor.this,PresencaActivity.class);
				startActivity(it);
			}
		});
        
	}
}
