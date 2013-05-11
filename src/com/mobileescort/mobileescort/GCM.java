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
	 * Método responsável por ativar o uso do GCM.
	 * @param context
	 */
	public static void ativa(Context context) {

		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);

		final String regId = GCMRegistrar.getRegistrationId(context);
	
		if (regId.equals("")) {
			GCMRegistrar.register(context, SessionManager.SENDER_ID);
			Log.i(TAG, "Serviço GCM ativado.");
			//return regId;
		} else {
			Log.i(TAG, "O serviço GCM já está ativo. ID: " + regId);
			//return regId;
		}
		
	}

	/**
	 * Método responsável por desativar o uso do GCM.
	 * @param context
	 * @return 
	 */
	public static void desativa(Context context) {
		GCMRegistrar.unregister(context);
		Log.i(TAG, "Serviço GCM desativado.");
		//return "Desativado";
	}

	/**
	 * Método responsável por verificar se o aplicativo 
	 * está ou não registrado para uso do GCM.
	 * @param context
	 * @return
	 */
	public static boolean isAtivo(Context context) {
		return GCMRegistrar.isRegistered(context);
	}

}
