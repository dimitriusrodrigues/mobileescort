package com.mobileescort.mobileescort;

import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class Notification extends Activity {
	
	TextView lblMessage;
	String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		
		Context context = getApplicationContext();
		
		lblMessage = (TextView) findViewById(R.id.lblMessage);
		Intent i = getIntent();
		message = i.getExtras().getString("mensagem");
		lblMessage.append(message + "\n");
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        
        notificationManager.cancel(R.string.app_name);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}

}
