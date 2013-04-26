package com.mobileescort.mobileescort;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Home extends Activity {
	
	ImageButton btCadastro;
	ImageButton btRota;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		btCadastro = (ImageButton) findViewById(R.id.btCadastro);
        
        btCadastro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Home.this,Cadastro.class);
				startActivity(it);
			}
		});
        
        btRota = (ImageButton) findViewById(R.id.btRota);
        
        btRota.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Home.this,Rotas.class);
				startActivity(it);
			}
		});
        
	}



}
