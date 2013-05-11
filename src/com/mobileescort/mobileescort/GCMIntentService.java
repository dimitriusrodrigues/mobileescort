package com.mobileescort.mobileescort;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.mobileescort.mobileescort.utils.Notificacao;

/**
 * Service responsavel por tratar os eventos do GCM.
 * 
 * @author Dimitrius
 *
 */
public class GCMIntentService extends GCMBaseIntentService {
	
	 /**
     * Tag used on log messages.
     */
    static final String TAG = "GCMIntentService";
    
	/**
	 * Metodo executado quando o aplicativo se registra no GCM para 
	 * o recebimento de mensagens da Nuvem.
	 */
	@Override
	protected void onRegistered(Context context, String regId) {

		/*
		 * Mostramos no console o ID de registro no GCM para usa-lo 
		 * posteriormente, no aplicativo cliente, para o envio de mensagens
		 * da Nuvem para o dispositivo Android.
		 */
		String mensagem = "ID de registro no GCM: " + regId;
		Log.i(TAG, mensagem);
		
		//new SaveUpdateGCMTask(this,regId).execute();
	}

	/**
	 * Metodo executado quando algum erro ocorre na comunicaçao
	 * com o GCM. 
	 */
	@Override
	protected void onError(Context context, String errorMessage) {
		Log.e(TAG, "Erro: " + errorMessage);
	}

	/**
	 * Metodo executado quando uma nova mensagem e recebida 
	 * da Nuvem atraves do GCM.
	 */
	@Override
	protected void onMessage(Context context, Intent intent) {
		
		/*
		 * Recuperamos a mensagem recebida através do Extras
		 * da Intent do GCM que invocou este Service.
		 */
		String mensagem = intent.getExtras().getString("mensagem");
		
		/*
		 * Disparamos uma Notificaçao para avisar o usuario sobre a 
		 * nova mensagem recebida da Nuvem.
		 */		
		if (mensagem != null && !"".equals(mensagem)){
			Notificacao.mostraNotificacao("Atualização", mensagem, context);
		}
	}

	/**
	 * Metodo executado quando o aplicativo se desregistra do GCM.
	 */
	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.i(TAG, "GCM Desativado.");
	}
	
}

