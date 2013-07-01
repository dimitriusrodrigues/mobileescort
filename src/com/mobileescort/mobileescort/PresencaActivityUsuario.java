package com.mobileescort.mobileescort;

import java.util.ArrayList;
import java.util.List;

import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.model.Viagem;
import com.mobileescort.mobileescort.utils.AlertDialogManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PresencaActivityUsuario extends Activity {

	ListView lvPresenca;
	
	int id_usuario;
	int id_viagem;
	Viagem viagem;
    RotaREST rotaRest = new RotaREST();
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	OnItemClickListener onItemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_presenca);
		
		viagem = Login.repositorio.buscarViagem();
		id_usuario = Login.session.getIdMotorista();
		
		lvPresenca = (ListView) findViewById(R.id.listView1);
				
	}

	private void adapterBase(){
		
		List<Usuario> listPresenca = new ArrayList<Usuario>();
		List<String> listStatus = new ArrayList<String>();
		
		try{

	        if (!Login.session.checkLogin()) {
	        	alert.showAlertDialog(PresencaActivityUsuario.this,
	      					getString(R.string.title_msg_sessionfailed),getString(R.string.body_msg_sessionnotfound), false);
	        	finish();
	        }
	    
	        Usuario usuario = Login.repositorio.buscarUsuario(id_usuario);
    		if (usuario.getPerfil().equals("R") ||usuario.getPerfil().equals("U")) {
   				listStatus.add("Ausente");
    		} else {
   				listStatus.add("Presente");
    		}
    		
    		listPresenca.add(usuario);
			
		 } catch (Exception e) {
        	 alert.showAlertDialog(PresencaActivityUsuario.this,
     					getString(R.string.title_msg_presencafailed),
     					getString(R.string.body_msg_presencalistfailed) + " " + e.getMessage(), false);
		}
		//adapter
		PresencaAdapter adapter = new PresencaAdapter(getBaseContext(), listPresenca, listStatus);
		lvPresenca.setAdapter(adapter);
		
	}

	protected void onStart() {
		super.onStart();
		adapterBase();
				
	}


}
