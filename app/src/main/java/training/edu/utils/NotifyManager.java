package training.edu.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;



public class NotifyManager {

    public void playNotification(Context context, Class<?> cls, String textNotification, String titleNotification, int drawable){

		 /*NOTIFICATION VARS*/
        NotificationManager mNotificationManager;
        int SIMPLE_NOTIFICATION_ID = 1;
        Notification notifyDetails;
		 /*NOTIFICATION INICIO*/
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifyDetails = new Notification(drawable,titleNotification,System.currentTimeMillis());
        long[] vibrate = {100,100,200,300};
        notifyDetails.vibrate = vibrate;
        notifyDetails.defaults = Notification.DEFAULT_ALL;
        notifyDetails.flags |= Notification.FLAG_AUTO_CANCEL;

		 /*NOTIFICATION FIN*/

        CharSequence contentTitle = titleNotification;
        CharSequence contentText = textNotification;

        Intent notifyIntent = new Intent(context, cls );

        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent intent = PendingIntent.getActivity(context, 0, notifyIntent, 0);

        notifyDetails.tickerText = textNotification;

        try {
            mNotificationManager.notify(SIMPLE_NOTIFICATION_ID, notifyDetails);
        } catch(Exception e){

        }
    }

    public void enviarNotificacion(Context context, Class<?> cls, String textNotification, String titleNotification, int drawable, int idNotification){

        long[] vibrate = {100,100,200,300};
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(drawable)
                        .setContentTitle(titleNotification)
                        .setContentText(textNotification)
                        .setVibrate(vibrate)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, cls);
        int SIMPLE_NOTIFICATION_ID = 1;
        if(idNotification != 0)
            SIMPLE_NOTIFICATION_ID = idNotification;
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // se manda el id para notificar cual es la notificacion que se actualizar
        mNotificationManager.notify(SIMPLE_NOTIFICATION_ID, mBuilder.build());
    }
}