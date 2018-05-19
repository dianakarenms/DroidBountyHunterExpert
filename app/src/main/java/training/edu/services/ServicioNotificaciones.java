package training.edu.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import training.edu.data.DBProvider;
import training.edu.droidbountyhunter.Home;
import training.edu.droidbountyhunter.R;
import training.edu.models.Fugitivo;
import training.edu.utils.NotifyManager;

public class ServicioNotificaciones extends Service {
    private static ServicioNotificaciones instance = null;
    private Timer timer;

    @Override
    public void onCreate() {
        Toast.makeText(this, "Servicio creado", Toast.LENGTH_SHORT).show();
        instance = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // This timer schedules the notification to be always sent every minute
//        Toast.makeText(this, "Servicio Arrancado ", Toast.LENGTH_SHORT).show();
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                EnviarNotificacion();
//            }
//        }, 0, 1000*60);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EnviarNotificacion();
            }
        }, 1000*60);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Servicio detenido", Toast.LENGTH_SHORT).show();
        instance = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void EnviarNotificacion() {
        try {
            String mensaje = "";
            DBProvider database = new DBProvider(this);
            ArrayList<Fugitivo> fugitivosSinNotificar = database.ObtenerFugifivosNotificacion();
            ArrayList<String[]> logsSinNotificar = database.ObtenerLogsEliminacion();
            int added = fugitivosSinNotificar.size();
            int deleted = logsSinNotificar.size();
            if(added > 0) {
                mensaje += "Añadiste " + added;
            } else if(deleted > 0) {
                mensaje += "Eliminaste " + deleted;
            } else {
                mensaje = "";
            }
            if(mensaje.length() > 0) {
                // Se crea una Notificación
                NotifyManager manager = new NotifyManager();
                manager.enviarNotificacion(this, Home.class, mensaje, "Notificación DroidBountyHunter", R.mipmap.ic_launcher, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isRunning() {
        return instance != null;
    }
}
