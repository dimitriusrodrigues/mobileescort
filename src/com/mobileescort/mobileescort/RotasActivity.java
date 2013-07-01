package com.mobileescort.mobileescort;

import java.util.ArrayList;
import java.util.List;

import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.model.Rota;
import com.mobileescort.mobileescort.model.Viagem;
import com.mobileescort.mobileescort.utils.AlertDialogManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class RotasActivity extends Activity{
	
	ListView lvRotas; 
	int id_viagem;
	Viagem viagem;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	OnItemClickListener onItemClickListener = new OnItemClickListener(){
	
		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

			Rota rota = (Rota) adapter.getItemAtPosition(position);
	
			Intent it = new Intent(RotasActivity.this,UsuariosActivity.class);
			Bundle params = new Bundle();
            params.putInt("id_rota", rota.getId_rota());
            it.putExtras(params);
			startActivity(it);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_rotas);
		
		lvRotas = (ListView) findViewById(R.id.listView1);
		
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
		
		List<Rota> listRotasWS = new ArrayList<Rota>();
		
		RotaREST rotaRest = new RotaREST();
		
		try{
			
	        if (!Login.session.checkLogin()) {
	        	alert.showAlertDialog(RotasActivity.this,
	      					getString(R.string.title_msg_sessionfailed),getString(R.string.body_msg_sessionnotfound), false);
	        	finish();
	        }

			listRotasWS = rotaRest.getListaRota(Login.session.getIdMotorista());
			
			for (int i = 0; i < listRotasWS.size(); i++) {
				 			 
				Rota rota = new Rota();
				rota.setId_rota(listRotasWS.get(i).getId_rota());
				rota.setDescricao(listRotasWS.get(i).getDescricao());
				rota.setMotorista(listRotasWS.get(i).getMotorista());
				rota.setUsuarios(listRotasWS.get(i).getUsuarios());
				Login.repositorio.salvarRota(rota);
	         }
			
		 } catch (Exception e) {
        	 alert.showAlertDialog(RotasActivity.this,
     					getString(R.string.title_msg_rotasfailed),
     					e.getMessage(), false);
        	 finish();
		}
		
		
		RotasAdapter adapter = new RotasAdapter(getBaseContext(), listRotasWS);
		lvRotas.setAdapter(adapter);
		
		lvRotas.setOnItemClickListener(onItemClickListener);
	}
	
	protected void onStart() {
		super.onStart();
		adapterBase();
		viagem = Login.repositorio.buscarViagem();
		if (viagem != null) {
			finish();
		}
	}
}
