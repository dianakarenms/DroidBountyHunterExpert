package edu.training.droidbountyhunter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.RemoteViews;

import java.util.Calendar;

import edu.training.utils.PictureTools;

/**
 * Implementation of App Widget functionality.
 */
public class FugitivosWidget extends AppWidgetProvider {

    // Definición del CONTENT_URI
    private static final String uri = "content://edu.training.droidbountyhunter";

    public static final Uri CONTENT_URI = Uri.parse(uri);

    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_FOTO = "photo";

    public static String capturado = "0";

    public static String CLOCK_WIDGET_UPDATE = "edu.training.droidbountyhunter.ACTUALIZAR_SEG_WIDGET";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Actualizar el widget

        // Iteramos la lista de widgets en ejecución
        for(int i = 0; i < appWidgetIds.length; i++)
        {
            // ID del widget actual
            int widgetId = appWidgetIds[i];

            // Actualizamos el widget actual
            actualizarWidget(context, appWidgetManager, widgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent.getAction().equals("edu.training.droidbountyhunter.ACTUALIZAR_WIDGET")) {
            // Obtener el ID del widget a actualizar
            int widgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            // Obtenemos el widget manager de nuestro contexto
            AppWidgetManager widgetManager =
                    AppWidgetManager.getInstance(context);

            // se actualiza que se va a mostrar si fugitivos o capturados.
            if(capturado.equals("0"))
                capturado = "1";
            else
                capturado = "0";

            // Actualizamos el widget
            if(widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                actualizarWidget(context,widgetManager,widgetId);
            }
        }

        if(CLOCK_WIDGET_UPDATE.equals(intent.getAction())) {
            // Obtèn el widget manager y el id para este widget provider,
            // tras ello llama al mètodo de actualizaciòn del reloj compratido
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
            for (int appWidgetID : ids) {
                actualizarWidget(context, appWidgetManager, appWidgetID);
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        // El widget provider està habilitado, iniciamos el temporizador para
        // actualiar el widget cada 15 segundos
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 1);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 15000,
                createClockTickIntent(context));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        // El Widget Provider està deshabilitado, apagamos el temporizador
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createClockTickIntent(context));
    }

    private void actualizarWidget(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        // Obtenemos la lista de controles del widget actual
        RemoteViews controles =
                new RemoteViews(context.getPackageName(), R.layout.widget_principal);

        try {
            ContentResolver cr = context.getContentResolver();
            Cursor cur = cr.query(CONTENT_URI, null, capturado, null, null);
            String Nombre = "", Path = "";
            if (cur.moveToFirst()) {
                Nombre = cur.getString(cur.getColumnIndex(COLUMN_NAME_NAME));
                Path = cur.getString(cur.getColumnIndex(COLUMN_NAME_FOTO));
            }
            cur.close();
            // Actualizamos el mensaje en el control del widget
            controles.setTextViewText(R.id.lblNombre, Nombre);
            // Actualizamos  la etiqueta de fugitivos, capturados
            if(capturado.equals("0")) {
                controles.setTextViewText(R.id.lblFugitivoCapturado, "Fugitivos");
                Bitmap bit_map = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                controles.setImageViewBitmap(R.id.imgFoto, bit_map);
            } else if(Path.equals("")) {
                controles.setTextViewText(R.id.lblFugitivoCapturado, "Capturados");
                Bitmap bit_map = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                controles.setImageViewBitmap(R.id.imgFoto, bit_map);
            } else {
                controles.setTextViewText(R.id.lblFugitivoCapturado, "Capturados");
                Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(Path, 50, 50);
                controles.setImageViewBitmap(R.id.imgFoto, bit_map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent("edu.training.droidbountyhunter.ACTUALIZAR_WIDGET");
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, widgetId,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

        controles.setOnClickPendingIntent(R.id.btnCambiar, pendingIntent);

        // Notificamos al manager de la actualización del widget actual
        appWidgetManager.updateAppWidget(widgetId, controles);
    }

    private PendingIntent createClockTickIntent(Context context) {
        Intent intent = new Intent(CLOCK_WIDGET_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}
