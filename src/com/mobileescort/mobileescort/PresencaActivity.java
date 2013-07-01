package com.mobileescort.mobileescort;

import java.util.ArrayList;
import java.util.List;

import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.model.Rota;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.model.Viagem;
import com.mobileescort.mobileescort.utils.AlertDialogManager;
import com.mobileescort.mobileescort.utils.SessionManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PresencaActivity extends Activity {

	ListView lvPresenca;
	List<String> listStatus = new ArrayList<String>();
	int id_rota;
	int id_viagem;
	Viagem viagem;
	boolean carregar = true;
    RotaREST rotaRest = new RotaREST();
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	OnItemClickListener onItemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
			
			Usuario presenca = (Usuario) adapter.getItemAtPosition(position);
			marcarPresenca(presenca, position);
			
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_presenca);
		
		viagem = Login.repositorio.buscarViagem();
		if (viagem == null) {
			alert.showAlertDialog(PresencaActivity.this, getString(R.string.title_msg_presencafailed), getString(R.string.body_msg_presencainvalid), false);
			carregar = false;
		} else {
			id_rota = viagem.getId_rota();
		}
		
		lvPresenca = (ListView) findViewById(R.id.listView1);
				
		//adapterBase();
	}

	protected void marcarPresenca(Usuario presenca, int position) {
		String perfil;
		try {
			perfil = presenca.getPerfil();
			//  Presente : Quando responsável envia notificação			
			if (perfil.equals("R") || perfil.equals("U")) {
				listStatus.set(position, "Presente");
				if (presenca.getPerfil().equals("R")) {
					perfil = "P";
					rotaRest.enviarMensagemUsuario(viagem.getId_rota(), presenca.getId_usuario(), SessionManager.MSG_PRESENTE);
				} else {
					perfil = "S";
				}
			} else {
				listStatus.set(position, "Ausente");
				//  Ausente : Quando responsável envia notificação 
				if (presenca.getPerfil().equals("P")) {
					perfil = "R";
				} else {
					perfil = "U";
				}
				rotaRest.enviarMensagemUsuario(viagem.getId_rota(), presenca.getId_usuario(), SessionManager.MSG_AUSENTE);

			}
			presenca.setPerfil(perfil);
			Login.repositorio.salvarUsuario(presenca);
			
		} catch (Exception e) {
        	 alert.showAlertDialog(PresencaActivity.this,
     					"Send Notification Failed",
     					e.getMessage(), false);
		}
		
		adapterBase();
		
	}

	private void adapterBase(){
		
		List<Usuario> listPresenca = new ArrayList<Usuario>();
		
		try{

	        if (!Login.session.checkLogin()) {
	        	alert.showAlertDialog(PresencaActivity.this,
	      					"Session Failed","Id do Motorista não encontrado..", false);
	        	finish();
	        }
	    
	        Rota rota = Login.repositorio.buscarRota(id_rota);
	        listPresenca = rota.getUsuarios();
        	for (Usuario usuario : listPresenca ) {
        		if (usuario.getPerfil().equals("R") ||usuario.getPerfil().equals("U")) {
        			listStatus.add("Ausente");
        		} else {
        			listStatus.add("Presente");
        		}
	        }
			
		 } catch (Exception e) {
        	 alert.showAlertDialog(PresencaActivity.this,
     					getString(R.string.title_msg_presencafailed),
     					getString(R.string.body_msg_presencalistfailed) + " " + e.getMessage(), false);
		}
		//adapter
		PresencaAdapter adapter = new PresencaAdapter(getBaseContext(), listPresenca, listStatus);
		lvPresenca.setAdapter(adapter);
		
		lvPresenca.setOnItemClickListener(onItemClickListener);
		
	}

	protected void onStart() {
		super.onStart();
		if (carregar) {
			adapterBase();
		}	
				
	}


}
