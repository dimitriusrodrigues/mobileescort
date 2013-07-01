package com.mobileescort.mobileescort;

import com.mobileescort.mobileescort.utils.CommonUtilities;
import com.mobileescort.mobileescort.utils.SessionManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class ExibirNotificacao extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		
		NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        
        notificationManager.cancel(R.string.app_name);
		
		TextView lblMessage = (TextView) findViewById(R.id.lblMessage);
		
		Intent i = getIntent();
		String message = i.getExtras().getString("mensagem");
		
		String typeMessage =  CommonUtilities.classificaMensagem(message);
		
		if (typeMessage.equals(SessionManager.MSG_ATUALIZA_POSICAO) ){
			Double[] valores = CommonUtilities.formataMensagemPosicao(message);
			lblMessage.append("Latitude : " + valores[0].toString() + "\n");
			lblMessage.append("Longitude: " + valores[1].toString() + "\n");
		} else {
			lblMessage.append(message + "\n");
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
