package training.edu.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, ServicioNotificaciones.class);
        if(!ServicioNotificaciones.isRunning()) context.startService(serviceIntent);
    }
}
