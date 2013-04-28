package com.mobileescort.mobileescort;

import java.util.ArrayList;
import java.util.List;

import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.model.Rota;
import com.mobileescort.mobileescort.utils.AlertDialogManager;
import com.mobileescort.mobileescort.utils.SessionManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class RotasActivity extends Activity{
	
	ListView lvRotas; 
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Session Manager Class
	SessionManager session;	
	
	OnItemClickListener onItemClickListener = new OnItemClickListener(){
	
		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

			Rotas rotas = (Rotas) adapter.getItemAtPosition(position);
		
			Toast.makeText(getBaseContext(), rotas.getDescricao(), Toast.LENGTH_SHORT).show();
	
			Intent it = new Intent(RotasActivity.this,UsuariosActivity.class);
			startActivity(it);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_rotas);
		
		lvRotas = (ListView) findViewById(R.id.listView1);
		
		adapterBase();
		
		ImageButton btAddRotas = (ImageButton) findViewById(R.id.btAddRotas);
        
        btAddRotas.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(RotasActivity.this,CriarRota.class);
				startActivity(it);
			}
		});
	}
	
	private void adapterBase(){
		
		List<Rotas> listRotas = new ArrayList<Rotas>();
		List<Rota> listRotasWS = new ArrayList<Rota>();
		
		RotaREST rotaRest = new RotaREST();
		
		try{

			// Session class instance
	        session = new SessionManager(getApplicationContext());
	        if (!session.checkLogin()) {
	        	alert.showAlertDialog(RotasActivity.this,
	      					"List Failed","Id do Motorista não encontrado..", false);
	        	finish();
	        }

			listRotasWS = rotaRest.getListaRota(session.getIdMotorista());
			
			for (int i = 0; i < listRotasWS.size(); i++) {
				 			 
				Rotas rot1 = new Rotas();
				rot1.setDescricao(listRotasWS.get(i).getDescricao());
				rot1.setCodigo(listRotasWS.get(i).getId_rota().toString());
				listRotas.add(rot1);
	         }
			
		 } catch (Exception e) {
        	 alert.showAlertDialog(RotasActivity.this,
     					"List Rotas Failed",
     					e.getMessage(), false);
		}
		
		//adapter
		RotasAdapter adapter = new RotasAdapter(getBaseContext(), listRotas);
		lvRotas.setAdapter(adapter);
		
		lvRotas.setOnItemClickListener(onItemClickListener);
	}
	
	protected void onStart() {
		super.onStart();
		adapterBase();
	}
}
