package com.mobileescort.mobileescort;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mobileescort.mobileescort.clientWS.RotaREST;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.utils.AlertDialogManager;
import com.mobileescort.mobileescort.utils.SessionManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class NotificarUsuario extends Activity {
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	int year;
	int month;
	int day;
	int hh;
	int mm;
	int id_usuario;
	DatePicker dpResult;
	TimePicker tpResult;
	Calendar informada;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_usuario);
		
		if (!Login.session.checkLogin()) {
        	alert.showAlertDialog(NotificarUsuario.this,
      					getString(R.string.title_msg_sessionfailed),getString(R.string.body_msg_sessionnotfound), false);
        	finish();
        }
		id_usuario = Login.session.getIdMotorista();
		
		Button btConfirmar = (Button) findViewById(R.id.btConfirmar);
		
		btConfirmar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				dpResult = (DatePicker) findViewById(R.id.datePicker1);
				tpResult = (TimePicker) findViewById(R.id.timePicker1);
				tpResult.is24HourView();
				
				year = dpResult.getYear();
				month = dpResult.getMonth();
				day = dpResult.getDayOfMonth();
				hh = tpResult.getCurrentHour();
				mm = tpResult.getCurrentMinute();
				
				
				informada = Calendar.getInstance( );
				informada.set( Calendar.DAY_OF_MONTH, day );  
				informada.set( Calendar.MONTH, month );  
				informada.set( Calendar.YEAR, year );  
				informada.set( Calendar.HOUR_OF_DAY, hh );
				informada.set( Calendar.MINUTE, mm );
				  
				Calendar atual = Calendar.getInstance( );  
				  
				// Testa as duas pra ver se são iguais  
				if ( informada.getTimeInMillis( ) < atual.getTimeInMillis( ) ) {  
					alert.showAlertDialog(NotificarUsuario.this,
	     					getString(R.string.title_msg_notificationsend),
	     					getString(R.string.body_msg_notificationvalidate), false);
				} else {
					novaMensagem(NotificarUsuario.this);
				}
				
				
			}
		});
		
	}
	
	private void novaMensagem(Context context) {
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
	    dialog.setIcon(R.drawable.ic_launcher);
	    dialog.setTitle(context.getString(R.string.title_msg_notificarausencia));
	    dialog.setMessage(context.getString(R.string.body_msg_notificarausencia));
        
	    dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
	    	
			public void onClick(DialogInterface dialog, int which) {
				
			}
	    });
		
	    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				int id_rota = 3 ;
				Date data = new Date();
				data.setTime(informada.getTimeInMillis());
				Usuario usuario = Login.repositorio.buscarUsuario(id_usuario);
				String msg = new String( data.toGMTString() + usuario.getNome());
				setNewMessage(id_rota, id_usuario, msg);
				
			}
	    	
	    });
	    dialog.show();
	}
	
	 /**
	 * @param newMessage the newMessage to set
	 */
	private void setNewMessage(int id_rota, int id_usuario, String informada) {
		
		RotaREST rotaRest = new RotaREST();
		try {
			rotaRest.enviarMensagemparaCondutor(id_rota, id_usuario, SessionManager.MSG_AUSENCIA_PROGRAMADA+informada);
		} catch (Exception e) {
			alert.showAlertDialog(NotificarUsuario.this,
 					getString(R.string.title_msg_notificationsend),
 					getString(R.string.body_msg_notificationfailed) + " " + e.getMessage(), false);
		}
	}
	
	protected void onStart() {
		super.onStart();
	}
		
}
