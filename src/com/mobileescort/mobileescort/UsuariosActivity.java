package com.mobileescort.mobileescort;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class UsuariosActivity extends Activity {

	ListView lvUsuarios;
	Button btCadUsuario;
	Button btIniciarRota;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_usuarios);
		
		lvUsuarios = (ListView) findViewById(R.id.listView1);
		
		adapterBase();
		
		btCadUsuario = (Button) findViewById(R.id.btCadUsuario);
        
		btCadUsuario.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(UsuariosActivity.this,CadastroUsuario.class);
				startActivity(it);
			}
		});
		
		btIniciarRota = (Button) findViewById(R.id.btIniciarRota);
        
		btIniciarRota.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				iniciarRota();
			}
		});
	}
	
	public void iniciarRota(){
	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    dialog.setIcon(R.drawable.ic_launcher);
    dialog.setTitle("Iniciar Rota");
    dialog.setMessage("Deseja iniciar rota?");
            
    dialog.setNegativeButton("Não", new DialogInterface.OnClickListener(){
    	
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			Toast.makeText(getBaseContext(), "Rota não inicianda!", Toast.LENGTH_SHORT).show();	
		}
    });
	
    dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
    	
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			Toast.makeText(getBaseContext(), "Iniciando Rota", Toast.LENGTH_SHORT).show();
		
			btIniciarRota = (Button) findViewById(R.id.btIniciarRota);
		    
			Intent it = new Intent(UsuariosActivity.this,GoogleMapsActivity.class);
			startActivity(it);
		}
    });
    dialog.show();
	}
	
	private void adapterBase(){
		
		List<Usuarios> listUsuarios = new ArrayList<Usuarios>();
		
		Usuarios usu1 = new Usuarios();
		usu1.setNome("Ana Rocha");
		usu1.setTelefone("99811234");
		listUsuarios.add(usu1);
		
		Usuarios usu2 = new Usuarios();
		usu2.setNome("Bia Falcão");
		usu2.setTelefone("92343564");
		listUsuarios.add(usu2);
		
		Usuarios usu3 = new Usuarios();
		usu3.setNome("Carlos Novaes");
		usu3.setTelefone("93358626");
		listUsuarios.add(usu3);
		
		Usuarios usu4 = new Usuarios();
		usu4.setNome("Dimitrius R.");
		usu4.setTelefone("84489104");
		listUsuarios.add(usu4);
		
		Usuarios usu5 = new Usuarios();
		usu5.setNome("Maria Aparecida");
		usu5.setTelefone("99654543");
		listUsuarios.add(usu5);
		
		Usuarios usu6 = new Usuarios();
		usu6.setNome("Willian Dufau");
		usu6.setTelefone("93341995");
		listUsuarios.add(usu6);
		
		//adapter
		UsuariosAdapter adapter = new UsuariosAdapter(getBaseContext(), listUsuarios);
		lvUsuarios.setAdapter(adapter);
		
		//lvUsuarios.setOnItemClickListener(onItemClickListener);
	}


}
