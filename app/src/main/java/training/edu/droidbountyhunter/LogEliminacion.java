package training.edu.droidbountyhunter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import training.edu.data.DBProvider;

public class LogEliminacion extends AppCompatActivity {

    ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_log_eliminacion);
        setTitle("Log de Eliminación");

        /*DBProvider database = new DBProvider(this);
        ArrayList<String[]> logs = database.ObtenerLogsEliminacion();
        String[] logsRow = new String[logs.size()];
        for(int index = 0; index < logs.size(); index++) {
            logsRow[index] = logs.get(index)[0] + (" --> " + logs.get(index)[1]);
        }
        ListView lista = (ListView) findViewById(R.id.list);
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, logsRow);
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(LogEliminacion.this,
                        String.valueOf(position) + " " + adaptador.getItem(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
