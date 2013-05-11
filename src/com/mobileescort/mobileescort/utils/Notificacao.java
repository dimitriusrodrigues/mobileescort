/**
 * 
 */
package com.mobileescort.mobileescort.utils;

import com.mobileescort.mobileescort.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * @author Dimitrius
 *
 */
public class Notificacao {

	/**
	 * Metodo responsavel por disparar uma notificação na barra de status.
	 * 
	 * @param titulo
	 * @param mensagem
	 * @param context
	 */
	public static void mostraNotificacao(String titulo, String mensagem,Context context) {

		// Tempo em que a Notificação sera disparada
		long tempoDefinido = System.currentTimeMillis();

		// Objeto Notification
		Notification notification = new Notification(R.drawable.ic_launcher,titulo, tempoDefinido);

		// Intent que sera disparada quando o usuario clicar sobre a Notificaçao
		Intent intent = new Intent(context, Notification.class);//Activiti que vai quando abrir a Mensagem
		intent.putExtra("mensagem_recebida", mensagem);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		// Configurando os dados da Notificaçao
		notification.setLatestEventInfo(context, titulo, mensagem,pendingIntent);

		// Oculta a notificaçao apos o usuario clicar sobre ela
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		// Define alertas de acordo com o padrao definido no dispositivo
		notification.defaults = Notification.DEFAULT_ALL;

		// Agenda a Notificaçao
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
		
	}

}
