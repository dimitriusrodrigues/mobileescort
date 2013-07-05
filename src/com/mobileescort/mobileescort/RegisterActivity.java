package com.mobileescort.mobileescort;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;
import com.mobileescort.mobileescort.R;
import com.mobileescort.mobileescort.utils.AlertDialogManager;
import com.mobileescort.mobileescort.utils.ConnectionDetector;

import com.mobileescort.mobileescort.utils.WakeLocker;

import static com.mobileescort.mobileescort.utils.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.mobileescort.mobileescort.utils.CommonUtilities.SENDER_ID;
import static com.mobileescort.mobileescort.utils.CommonUtilities.EXTRA_MESSAGE;
import static com.mobileescort.mobileescort.utils.CommonUtilities.displayMessage;

public class RegisterActivity extends Activity {

	// label to display gcm messages
	TextView lblMessage;
	
	public Intent retorno = new Intent();
	
	public static String registro;
	public static Double longitude;
	public static Double latitude;
	private String localizacao;
	private Handler handler;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Connection detector
	ConnectionDetector cd;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		cd = new ConnectionDetector(getApplicationContext());

		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(RegisterActivity.this,
					getString(R.string.title_msg_registerfailed),
					getString(R.string.body_msg_registerinternetfailed), false);
			finish();
		}

		Intent i = getIntent();
		
		localizacao = i.getStringExtra("localizacao");
		handler = new Handler();
		buscarLatitudeLongitude();
		
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);

		lblMessage = (TextView) findViewById(R.id.lblMessage);
		
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));
		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM			
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
		        displayMessage(this, getString(R.string.gcm_alreadyregistered));
		        GCMRegistrar.unregister(this);
		        displayMessage(this, getString(R.string.gcm_unregistered));
		        GCMRegistrar.register(this, SENDER_ID);
			}else {
				displayMessage(this, getString(R.string.gcm_notregistered));
				GCMRegistrar.setRegisteredOnServer(this, true);
                displayMessage(this, getString(R.string.server_registered));
                RegisterActivity.registro = regId;
			}
		}

	}		

	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			
			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */
			
			// Showing received message
			lblMessage.append(newMessage + "\n");			
			
			// Releasing wake lock
			WakeLocker.release();
		}
	};

	@Override
	protected void onDestroy() {
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}
	
	private void buscarLatitudeLongitude() {
        displayMessage(this, "Start process to search localization");

        GeocodingTask processo = new GeocodingTask(this);

        processo.execute();
	}
	
	class GeocodingTask extends AsyncTask<String, Void, Void> {

		   Context mContext;

		   public GeocodingTask(Context context) {
		        super();
		        mContext = context;
		   }

		@Override
		protected Void doInBackground(String... params) {
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
			 
	        List<Address> addresses = null;

	        try {
	           addresses = geocoder.getFromLocationName(localizacao, 1);

	        } catch (final IOException e) {}
	                       
	        if (addresses != null) {

	           final Address endereco = addresses.get(0);
	           handler.post(new Runnable() {

	                public void run() {
	                	latitude = endereco.getLatitude();
	    				longitude = endereco.getLongitude();
	    				Log.i("Localizacao", "Localization Find: (" + latitude + "," + longitude  + ")" );
	    				displayMessage(mContext, "Localization Find: (" + latitude + "," + longitude  + ")" );
	                }
	           }); 

	        }
	        return null;
		}
	}
	
	@Override
	public void finish() {
		retorno.putExtra("latitude", latitude);
		retorno.putExtra("longitude", longitude);
		retorno.putExtra("registro", registro);
		setResult(2, retorno);
	    super.finish();
	}
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}

}

