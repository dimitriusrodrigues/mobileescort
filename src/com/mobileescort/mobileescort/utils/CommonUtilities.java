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
package com.mobileescort.mobileescort.utils;

import com.mobileescort.mobileescort.R;

import android.content.Context;
import android.content.Intent;


/**
 * Helper class providing methods and constants common to other classes in the
 * app.
 */
public final class CommonUtilities {
	
    /**
     * Base URL of the Demo Server (such as http://my_host:8080/gcm-demo)
     */
	public static final String SERVER_URL = SessionManager.URL_WS;

    /**
     * Google API project id registered to use GCM.
     */
	public static final String SENDER_ID = SessionManager.SENDER_ID;

    /**
     * Tag used on log messages.
     */
    static final String TAG = "CommonUtilities";

    /**
     * Intent used to display a message in the screen.
     */
    public static final String DISPLAY_MESSAGE_ACTION =
            "com.mobileescort.mobileescort.DISPLAY_MESSAGE";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    public static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
    
    public static Double[] formataMensagemPosicao(String mensagem) {
    	
    	StringBuilder buffer = new StringBuilder(mensagem);
        Double[] valores = new Double[2];
        char[] latitude = new char[buffer.length()];
        char[] longitude = new char[buffer.length()];
        buffer.delete(0, SessionManager.MSG_ATUALIZA_POSICAO.length());
        int pos = buffer.indexOf(",");
        buffer.getChars(0, pos - 1, latitude, 0);
        buffer.getChars(pos + 1, buffer.length(), longitude, 0);
        String slatitude = new String(latitude);
        String slongitude = new String(longitude);
        
    	valores[0] = Double.parseDouble(slatitude);
    	valores[1] = Double.parseDouble(slongitude);
		return valores;
    	
    }
    
    public static int formataMensagemRotaIniciada(String mensagem) {
    	
    	StringBuilder buffer = new StringBuilder(mensagem);
    	buffer.delete(0, SessionManager.MSG_ATUALIZA_ROTAINICIADA.length());
    	String sid_rota = new String(buffer);
        int id_rota = Integer.parseInt(sid_rota);
		return id_rota;
    }
    
    public static int formataMensagemRotaFinalizada(String mensagem) {
    	
    	StringBuilder buffer = new StringBuilder(mensagem);
    	buffer.delete(0, SessionManager.MSG_ATUALIZA_ROTAFINALIZADA.length());
    	String sid_rota = new String(buffer);
        int id_rota = Integer.parseInt(sid_rota);
		return id_rota;
    }
    
    public static String formataMensagem(Context context, String mensagem) {
    	
    	StringBuilder buffer = new StringBuilder(mensagem);
    	String typemsg = classificaMensagem(mensagem);
    	String msg;
    	
    	if (typemsg.equals(SessionManager.MSG_ATUALIZA_POSICAO) ||
    		typemsg.equals(SessionManager.MSG_AVISO_OUTROS) ||
    		typemsg.equals(SessionManager.MSG_ATUALIZA_ROTAINICIADA) ||	
    		typemsg.equals(SessionManager.MSG_ATUALIZA_ROTAFINALIZADA) ||
    		typemsg.equals(SessionManager.MSG_AUSENTE) ||
    		typemsg.equals(SessionManager.MSG_PRESENTE)||
    		typemsg.equals(SessionManager.MSG_AUSENCIA_PROGRAMADA) ) { 
    		buffer.delete(0, typemsg.length());
    		msg = new String(buffer);
    	} else {
    		if ( typemsg.equals(SessionManager.MSG_AVISO_ACIDENTE) ) {
    			msg = context.getString(R.string.notificar_acidente);
    		} else {
    			if ( typemsg.equals(SessionManager.MSG_AVISO_HOSPITAL) ) {
    				msg = context.getString(R.string.notificar_hospital);
        		} else {
        			if ( typemsg.equals(SessionManager.MSG_AVISO_MECANICO) ) {
        				msg = context.getString(R.string.notificar_mecanico);
            		} else {
            			if ( typemsg.equals(SessionManager.MSG_AVISO_TRANSITO) ) {
            				msg = context.getString(R.string.notificar_transito);
                		} else {
                			if ( typemsg.equals(SessionManager.MSG_AVISO_POSTO) ) {
                				msg = context.getString(R.string.notificar_abastecimento);
                    		} else {
                       			msg = context.getString(R.string.notificar_undefined);
                    		}

                		}
            		}
        		}
    		}
    	}
    	return msg;
    }
    
    public static String classificaMensagem(String mensagem) {
    	if (mensagem.contains(SessionManager.MSG_ATUALIZA_POSICAO)){ 
    		return SessionManager.MSG_ATUALIZA_POSICAO;
    	} else {
    		if (mensagem.contains(SessionManager.MSG_AVISO_OUTROS)) {
    			return SessionManager.MSG_AVISO_OUTROS;
    		} else {
    			if (mensagem.contains(SessionManager.MSG_ATUALIZA_ROTAINICIADA)) {
        			return SessionManager.MSG_ATUALIZA_ROTAINICIADA;
        		} else {
        			if (mensagem.contains(SessionManager.MSG_ATUALIZA_ROTAFINALIZADA)) {
            			return SessionManager.MSG_ATUALIZA_ROTAFINALIZADA;
            		} else {
            			if (mensagem.contains(SessionManager.MSG_AUSENTE)) {
                			return SessionManager.MSG_AUSENTE;
                		} else {
                			if (mensagem.contains(SessionManager.MSG_PRESENTE)) {
                    			return SessionManager.MSG_PRESENTE;
                    		} else {
                    			if (mensagem.contains(SessionManager.MSG_AUSENCIA_PROGRAMADA)) {
                        			return SessionManager.MSG_AUSENCIA_PROGRAMADA;
                        		} else {
                        			return mensagem;
                        		}
                    		}
                		}
            		}
        		}
    		}	
    	}
    }

}
