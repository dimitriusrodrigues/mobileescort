/**
 * 
 */
package com.mobileescort.mobileescort;

import com.google.android.gcm.GCMRegistrar;
import com.mobileescort.mobileescort.utils.SessionManager;

import android.content.Context;
import android.util.Log;

/**
 * @author dimitrius
 *
 */
public class GCM {
	
	 /**
     * Tag used on log messages.
     */
    static final String TAG = "GCM";
    
    
	/**
	 * M�todo respons�vel por ativar o uso do GCM.
	 * @param context
	 */
	public static void ativa(Context context) {

		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);

		final String regId = GCMRegistrar.getRegistrationId(context);
	
		if (regId.equals("")) {
			GCMRegistrar.register(context, SessionManager.SENDER_ID);
			Log.i(TAG, "Servi�o GCM ativado.");
			//return regId;
		} else {
			Log.i(TAG, "O servi�o GCM j� est� ativo. ID: " + regId);
			//return regId;
		}
		
	}

	/**
	 * M�todo respons�vel por desativar o uso do GCM.
	 * @param context
	 * @return 
	 */
	public static void desativa(Context context) {
		GCMRegistrar.unregister(context);
		Log.i(TAG, "Servi�o GCM desativado.");
		//return "Desativado";
	}

	/**
	 * M�todo respons�vel por verificar se o aplicativo 
	 * est� ou n�o registrado para uso do GCM.
	 * @param context
	 * @return
	 */
	public static boolean isAtivo(Context context) {
		return GCMRegistrar.isRegistered(context);
	}

}
