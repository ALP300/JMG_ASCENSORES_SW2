package com.example.jmg_ascensores;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class Ascensor_Cliente extends AppCompatActivity {
    private String codCli;
    private Connection connection; // La conexión a la base de datos
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_asc); // Asegúrate de que este sea el layout correcto
        codCli = getIntent().getStringExtra("codCli");

        ListView listView = findViewById(R.id.lstInfAsc);
        List<EntAsc> items = new ArrayList<>();
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection != null) {
                    try {
                        List<EntAsc> ascens = new DB_AscCli(connection, Ascensor_Cliente.this).execute(codCli).get();
                        for(EntAsc init : ascens) {
                            items.add(init);
                        }
                        Adapter_ascensor adapter = new Adapter_ascensor(Ascensor_Cliente.this, items);
                        listView.setAdapter(adapter);
                        Log.i(TAG, items.get(1).getMarca()+"qqqqqqqqqq");
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.execute();
        // Agregar algunos elementos a la lista
//        items.add("Elemento 1");




    }


}

