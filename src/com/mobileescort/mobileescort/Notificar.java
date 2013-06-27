package com.mobileescort.mobileescort;

import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.model.Rota;
import com.mobileescort.mobileescort.model.Viagem;
import com.mobileescort.mobileescort.utils.AlertDialogManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


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
		
		viagem = Login.repositorio.buscarViagem();
		if (viagem == null) {
			alert.showAlertDialog(getApplicationContext(), getString(R.string.title_msg_notificationfailed), getString(R.string.body_msg_notificationinvalid), false);
			finish();
		}
		
		ImageButton btTransito = (ImageButton) findViewById(R.id.btTransito);
        
		btTransito.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				RotaREST rotaRest = new RotaREST();
				try {
					rotaRest.enviarMenesagem(viagem.getId_rota(), getString(R.string.notificar_transito));
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
					rotaRest.enviarMenesagem(viagem.getId_rota(), getString(R.string.notificar_mecanico));
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
					rotaRest.enviarMenesagem(viagem.getId_rota(), getString(R.string.notificar_hospital));
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
					rotaRest.enviarMenesagem(viagem.getId_rota(), getString(R.string.notificar_acidente));
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
					rotaRest.enviarMenesagem(viagem.getId_rota(), getString(R.string.notificar_abastecimento));
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
				/*RotaREST rotaRest = new RotaREST();
				try {
					rotaRest.enviarMenesagem(viagem.getId_rota(), "Digitar a mensagem");
				} catch (Exception e) {
		        	 alert.showAlertDialog(Notificar.this,
		     					"Send Notification Failed",
		     					e.getMessage(), false);
				}*/
				sendNotification(getBaseContext(), "Digitar a mensagem");
			}
		});
	}
	
	private static void sendNotification(Context context, String message) {
        int icon = R.drawable.notificacao;
        long when = System.currentTimeMillis();
        String title = context.getString(R.string.app_name);
       
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        Intent notificationIntent = new Intent(context, Notification.class);
        notificationIntent.putExtra("mensagem", message);
        // set intent so it does not start a new activity
        //notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
        //        Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        notification.setLatestEventInfo(context, title, message, intent);
        //notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(R.string.app_name, notification);
    }
}
