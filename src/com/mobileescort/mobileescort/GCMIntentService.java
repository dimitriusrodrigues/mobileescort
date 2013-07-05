/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobileescort.mobileescort;

import static com.mobileescort.mobileescort.utils.CommonUtilities.SENDER_ID;
import static com.mobileescort.mobileescort.utils.CommonUtilities.displayMessage;
import static com.mobileescort.mobileescort.utils.CommonUtilities.formataMensagem;
import static com.mobileescort.mobileescort.utils.CommonUtilities.classificaMensagem;
import static com.mobileescort.mobileescort.utils.CommonUtilities.formataMensagemPosicao;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.mobileescort.mobileescort.banco.RepositorioMobileEscort;
import com.mobileescort.mobileescort.banco.RepositorioMobileEscortScript;
import com.mobileescort.mobileescort.model.Usuario;
import com.mobileescort.mobileescort.model.Viagem;
import com.mobileescort.mobileescort.utils.SessionManager;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

    private static final String TAG = "GCMIntentService";
    
    public static RepositorioMobileEscort repositorio ;
    
    public GCMIntentService() {
        super(SENDER_ID);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, getString(R.string.gcm_registered));
        RegisterActivity.registro = new String(registrationId);        
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, getString(R.string.gcm_unregistered));
        displayMessage(context, "executou o onUnregistered");
        if (GCMRegistrar.isRegisteredOnServer(context)) {
        	GCMRegistrar.setRegisteredOnServer(context, false);
            displayMessage(context, getString(R.string.server_unregistered));
        } else {
            // This callback results from the call to unregister made on
            // ServerUtilities when the registration to the server failed.
            Log.i(TAG, "Ignoring unregister callback");
        }
    	RegisterActivity.registro = new String("");
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String message = intent.getExtras().getString("mensagem");
        repositorio = new RepositorioMobileEscortScript(this);
        generateNotification(context, message);
    }

    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        //displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.notificacao;
        long when = System.currentTimeMillis();
        boolean notificar = false;
        
        String title = context.getString(R.string.app_name);
        
        String mensagemFormatada  = formataMensagem(context, message);
        String bodymensagem = mensagemFormatada;
        
        if (classificaMensagem(message) == SessionManager.MSG_ATUALIZA_ROTAINICIADA) {
        	iniciaRota(mensagemFormatada);
        	notificar = true;
        	bodymensagem = context.getString(R.string.notificar_rotainiciada);
        } else {
        	if (classificaMensagem(message) == SessionManager.MSG_ATUALIZA_POSICAO) {
            	atualizaPosicaoRota(message);
            	notificar = true;
            } else {
            	if (classificaMensagem(message) == SessionManager.MSG_ATUALIZA_ROTAFINALIZADA) {
                	finalizaRota();
                	notificar = true;
                	bodymensagem = context.getString(R.string.notificar_rotafinalizada);
                } else {
                	if (classificaMensagem(message) == SessionManager.MSG_AUSENTE) {
                    	notificaAusencia(mensagemFormatada);
                    	notificar = true;
                    	bodymensagem = context.getString(R.string.notificar_ausencia);
                    } else {
                    	if (classificaMensagem(message) == SessionManager.MSG_PRESENTE) {
                        	notificaPresenca(mensagemFormatada);
                        	notificar = true;
                        	bodymensagem = context.getString(R.string.notificar_presenca);
                        } else {
                        	if (classificaMensagem(message) == SessionManager.MSG_AUSENCIA_PROGRAMADA) {
                            	String retornoMensagem = notificaAusenciaProgramada(mensagemFormatada);
                            	bodymensagem = context.getString(R.string.notificar_ausencia_programada) + retornoMensagem;
                            	notificar = true;
                            } else {
                            	notificar = true;
                            	bodymensagem = mensagemFormatada;
                            }
                        }
                    }
                }
            }
        }
        
        if (notificar) {
        	NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification(icon, bodymensagem, when);
            Intent intent = new Intent(context, ExibirNotificacao.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("mensagem", bodymensagem);
            PendingIntent pendingintent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setLatestEventInfo(context, title, bodymensagem, pendingintent);
            notification.defaults = Notification.DEFAULT_ALL;
        	notificationManager.notify(R.string.app_name, notification);	
        } 
    }

	private static String notificaAusenciaProgramada(String mensagemFormatada) {
		// 
		return mensagemFormatada;
		
	}

	private static void notificaPresenca(String mensagemFormatada) {
		int id_usuario = Integer.parseInt(mensagemFormatada);
		Usuario usuario = repositorio.buscarUsuario(id_usuario);
		if (usuario.getPerfil().equals("U") ) {
			usuario.setPerfil("S");
		} else {
			if (usuario.getPerfil().equals("R") ) {
				usuario.setPerfil("P");
			}
		}
    	repositorio.salvarUsuario(usuario);
	}

	private static void notificaAusencia(String mensagemFormatada) {
		int id_usuario = Integer.parseInt(mensagemFormatada);
		Usuario usuario = repositorio.buscarUsuario(id_usuario);
		if (usuario.getPerfil().equals("S")) {
			usuario.setPerfil("U");
		} else {
			if (usuario.getPerfil().equals("P")) {
				usuario.setPerfil("R");
			}
		}
    	repositorio.salvarUsuario(usuario);
	}

	private static void finalizaRota() {
		 Viagem viagem = repositorio.buscarViagem();
		if (viagem != null) {
			viagem.setLatitude(0.0);
			viagem.setLongitude(0.0);
			viagem.setId_status("finalizada");
			repositorio.salvarViagem(viagem);
		}	
	}

	private static void atualizaPosicaoRota(String message) {
		
		Double[] posicao = formataMensagemPosicao(message);
    	Viagem viagem = repositorio.buscarViagem();
		if (viagem != null) {
			viagem.setLatitude(posicao[0]);
			viagem.setLongitude(posicao[1]);
			repositorio.salvarViagem(viagem);
		} else {
			double d = posicao[3];
			int id_rota = (int) d;
	    	viagem = repositorio.buscarViagem(id_rota);
			if (viagem == null) {
				viagem = new Viagem();
				viagem.setId_rota(id_rota);
				viagem.setId_status("Iniciada");
				viagem.setLatitude(0.0);
				viagem.setLongitude(0.0);
				repositorio.salvarViagem(viagem);
			}
		}
		
	}

	private static void iniciaRota(String mensagemFormatada) {
		int id_rota = Integer.parseInt(mensagemFormatada);
    	Viagem viagem = repositorio.buscarViagem(id_rota);
		if (viagem == null) {
			viagem = new Viagem();
			viagem.setId_rota(id_rota);
			viagem.setId_status("Iniciada");
			viagem.setLatitude(0.0);
			viagem.setLongitude(0.0);
			repositorio.salvarViagem(viagem);
		}
	}

}
