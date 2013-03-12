package com.mobileescort.mobileescort;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Rotas extends Activity {

	ImageButton btAddRota;
	Button btAnchieta;
	Button btAuxiliadora;
	Button btBomConselho;
	Button btChampagnat;
	Button btDomFeliciano;
	Button btFarroupilha;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rotas);
	
		btAddRota = (ImageButton) findViewById(R.id.btAddRota);
	    
	    btAddRota.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent it = new Intent(Rotas.this,Cadastro.class);
				startActivity(it);
			}
		});
	}
	
	public void iniciarAnchieta (View v){
		
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(R.drawable.ic_launcher);
        dialog.setTitle("Iniciar Rota");
        dialog.setMessage("Deseja iniciar rota?");
                
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener(){
        	
			public void onClick(DialogInterface dialog, int which) {
				
				Toast.makeText(getBaseContext(), "Rota não inicianda!", Toast.LENGTH_SHORT).show();	
			}
        });
        
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
        	
			public void onClick(DialogInterface dialog, int which) {
				
				Toast.makeText(getBaseContext(), "Iniciando Rota", Toast.LENGTH_SHORT).show();
			
				btAnchieta = (Button) findViewById(R.id.btAnchieta);
			    
						Intent it = new Intent(Rotas.this,Cadastro.class);
						startActivity(it);	
			}
        });
        dialog.show();
    }
	
	public void iniciarAuxiliadora (View v){
			
	        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	        dialog.setIcon(R.drawable.ic_launcher);
	        dialog.setTitle("Iniciar Rota");
	        dialog.setMessage("Deseja iniciar rota?");
	                
	        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener(){
	        	
				public void onClick(DialogInterface dialog, int which) {
					
					Toast.makeText(getBaseContext(), "Rota não inicianda!", Toast.LENGTH_SHORT).show();
				}
	        });
	        
	        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
	        	
				public void onClick(DialogInterface dialog, int which) {
					
					Toast.makeText(getBaseContext(), "Iniciando Rota", Toast.LENGTH_SHORT).show();
				
					btAuxiliadora = (Button) findViewById(R.id.btAuxiliadora);
				   
							Intent it = new Intent(Rotas.this,Cadastro.class);
							startActivity(it);		
				}
	        });
	        dialog.show();
	}

	public void iniciarBomConselho (View v){
		
	    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    dialog.setIcon(R.drawable.ic_launcher);
	    dialog.setTitle("Iniciar Rota");
	    dialog.setMessage("Deseja iniciar rota?");
	            
	    dialog.setNegativeButton("Não", new DialogInterface.OnClickListener(){
	    	
			public void onClick(DialogInterface dialog, int which) {
				
				Toast.makeText(getBaseContext(), "Rota não inicianda!", Toast.LENGTH_SHORT).show();	
			}
	    });
	    
	    dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
	    	
			public void onClick(DialogInterface dialog, int which) {
				
				Toast.makeText(getBaseContext(), "Iniciando Rota", Toast.LENGTH_SHORT).show();
			
				btBomConselho = (Button) findViewById(R.id.btBomConselho);
			    
						Intent it = new Intent(Rotas.this,Cadastro.class);
						startActivity(it);
			}
	    });
	    dialog.show();
	}
	
	public void iniciarChampagnat (View v){
		
	    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    dialog.setIcon(R.drawable.ic_launcher);
	    dialog.setTitle("Iniciar Rota");
	    dialog.setMessage("Deseja iniciar rota?");
	            
	    dialog.setNegativeButton("Não", new DialogInterface.OnClickListener(){
	    	
			public void onClick(DialogInterface dialog, int which) {
				
				Toast.makeText(getBaseContext(), "Rota não inicianda!", Toast.LENGTH_SHORT).show();	
			}
	    });
	    
	    dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
	    	
			public void onClick(DialogInterface dialog, int which) {
				
				Toast.makeText(getBaseContext(), "Iniciando Rota", Toast.LENGTH_SHORT).show();
			
				btChampagnat = (Button) findViewById(R.id.btChampagnat);
			    
						Intent it = new Intent(Rotas.this,Cadastro.class);
						startActivity(it);
			}
	    });
	    dialog.show();
	}
	
	public void iniciarDomFeliciano (View v){
		
	    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    dialog.setIcon(R.drawable.ic_launcher);
	    dialog.setTitle("Iniciar Rota");
	    dialog.setMessage("Deseja iniciar rota?");
	            
	    dialog.setNegativeButton("Não", new DialogInterface.OnClickListener(){
	    	
			public void onClick(DialogInterface dialog, int which) {
				
				Toast.makeText(getBaseContext(), "Rota não inicianda!", Toast.LENGTH_SHORT).show();	
			}
	    });
	    
	    dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
	    	
			public void onClick(DialogInterface dialog, int which) {
				
				Toast.makeText(getBaseContext(), "Iniciando Rota", Toast.LENGTH_SHORT).show();
			
				btDomFeliciano = (Button) findViewById(R.id.btDomFeliciano);
			    
						Intent it = new Intent(Rotas.this,Cadastro.class);
						startActivity(it);
			}
	    });
	    dialog.show();
	}
	
	public void iniciarFarroupilha (View v){
		
	    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    dialog.setIcon(R.drawable.ic_launcher);
	    dialog.setTitle("Iniciar Rota");
	    dialog.setMessage("Deseja iniciar rota?");
	            
	    dialog.setNegativeButton("Não", new DialogInterface.OnClickListener(){
	    	
			public void onClick(DialogInterface dialog, int which) {
				
				Toast.makeText(getBaseContext(), "Rota não inicianda!", Toast.LENGTH_SHORT).show();	
			}
	    });
	    
	    dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
	    	
			public void onClick(DialogInterface dialog, int which) {
				
				Toast.makeText(getBaseContext(), "Iniciando Rota", Toast.LENGTH_SHORT).show();
			
				btFarroupilha = (Button) findViewById(R.id.btFarroupilha);
			    
						Intent it = new Intent(Rotas.this,Cadastro.class);
						startActivity(it);
			}
	    });
	    dialog.show();
	}
}