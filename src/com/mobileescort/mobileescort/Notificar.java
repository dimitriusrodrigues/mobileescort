package com.mobileescort.mobileescort;

import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.model.Rota;
import com.mobileescort.mobileescort.model.Viagem;
import com.mobileescort.mobileescort.utils.AlertDialogManager;
import com.mobileescort.mobileescort.utils.SessionManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.EditText;

public class Notificar extends Activity {
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	int id_rota;
	int id_viagem;
	Viagem viagem;
	Rota rota;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notificar);
		Intent intent = getIntent();
		Bundle params = intent.getExtras();  
		if(params!=null) { 
			id_rota = params.getInt("id_rota");
			id_viagem = params.getInt("id_viagem");
			viagem = Login.repositorio.buscarViagem(id_rota);
			rota = Login.repositorio.buscarRota(id_rota);
		} else {
			finish();
		}
		
		ImageButton btTransito = (ImageButton) findViewById(R.id.btTransito);
        
		btTransito.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				RotaREST rotaRest = new RotaREST();
				try {
					rotaRest.enviarMensagem(viagem.getId_rota(), SessionManager.MSG_AVISO_TRANSITO);
				} catch (Exception e) {
		        	 alert.showAlertDialog(Notificar.this,
		     					getString(R.string.title_msg_notificationsend),
		     					getString(R.string.body_msg_notificationfailed) + " " + e.getMessage(), false);
				}
				
			}
		});
		
		ImageButton btMecanico = (ImageButton) findViewById(R.id.btMecanico);
        
		btMecanico.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RotaREST rotaRest = new RotaREST();
				try {
					rotaRest.enviarMensagem(viagem.getId_rota(), SessionManager.MSG_AVISO_MECANICO);
				} catch (Exception e) {
					alert.showAlertDialog(Notificar.this,
	     					getString(R.string.title_msg_notificationsend),
	     					getString(R.string.body_msg_notificationfailed) + " " + e.getMessage(), false);
				}
				
			}
		});
		
		ImageButton btHospital = (ImageButton) findViewById(R.id.btHospital);
        
		btHospital.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RotaREST rotaRest = new RotaREST();
				try {
					rotaRest.enviarMensagem(viagem.getId_rota(), SessionManager.MSG_AVISO_HOSPITAL);
				} catch (Exception e) {
					alert.showAlertDialog(Notificar.this,
	     					getString(R.string.title_msg_notificationsend),
	     					getString(R.string.body_msg_notificationfailed) + " " + e.getMessage(), false);
				}
				
			}
		});
		
		ImageButton btAcidente = (ImageButton) findViewById(R.id.btAcidente);
        
		btAcidente.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RotaREST rotaRest = new RotaREST();
				try {
					rotaRest.enviarMensagem(viagem.getId_rota(), SessionManager.MSG_AVISO_ACIDENTE);
				} catch (Exception e) {
					alert.showAlertDialog(Notificar.this,
	     					getString(R.string.title_msg_notificationsend),
	     					getString(R.string.body_msg_notificationfailed) + " " + e.getMessage(), false);
				}
			}
		});
		
		ImageButton btPosto = (ImageButton) findViewById(R.id.btPosto);
        
		btPosto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RotaREST rotaRest = new RotaREST();
				try {
					rotaRest.enviarMensagem(viagem.getId_rota(), SessionManager.MSG_AVISO_POSTO);
				} catch (Exception e) {
					alert.showAlertDialog(Notificar.this,
	     					getString(R.string.title_msg_notificationsend),
	     					getString(R.string.body_msg_notificationfailed) + " " + e.getMessage(), false);
				}
			}
		});
		
		ImageButton btMensagem = (ImageButton) findViewById(R.id.btMensagem);
        
		btMensagem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				novaMensagem(Notificar.this);
				
			}
		});
		
	}
	
	private void novaMensagem(Context context) {
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
	    dialog.setIcon(R.drawable.ic_launcher);
	    dialog.setTitle("Mensagem");
	    dialog.setMessage("Digite a Mensagem:");
	    
	    final EditText txMensagem = new EditText(context);
        dialog.setView(txMensagem);
	    dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
	    	
			public void onClick(DialogInterface dialog, int which) {
				
			}
	    });
		
	    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				setNewMessage(txMensagem.getText().toString());
				
			}
	    	
	    });
	    dialog.show();
	}
	
	 /**
	 * @param newMessage the newMessage to set
	 */
	private void setNewMessage(String newMessage) {
		if (!newMessage.equals("")) {
			RotaREST rotaRest = new RotaREST();
			try {
				rotaRest.enviarMensagem(viagem.getId_rota(), SessionManager.MSG_AVISO_OUTROS+newMessage);
			} catch (Exception e) {
				alert.showAlertDialog(Notificar.this,
     					getString(R.string.title_msg_notificationsend),
     					getString(R.string.body_msg_notificationfailed) + " " + e.getMessage(), false);
			}
		}
	}
	
	protected void onStart() {
		super.onStart();
	}
		
}
