package com.example.jmg_ascensores;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.util.List;

public class View_Trab_ListaMant extends AppCompatActivity {

    private Connection connection; // La conexión a la base de datos
    private ListView lstAsc;
    private String codCli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trab_lista_manten);

        // Obtener referencia al botón
        codCli = getIntent().getStringExtra("codCli");
        Log.d("Database", "cod: "+codCli);
        // Establecer el OnClickListener
         lstAsc = findViewById(R.id.lstBtnAsc);

        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection != null) {
                    try {
                        // Ejecutar la consulta para obtener los ascensores del cliente
                        List<Ent_Ascensor> asc;
                        asc = new DB_AscCli(connection, View_Trab_ListaMant.this).execute(codCli).get();
                        // Configurar el adaptador
                        Adapter_BtnAscensores adapter = new Adapter_BtnAscensores(View_Trab_ListaMant.this,asc);
                        lstAsc.setAdapter(adapter);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Log.d("Database", "Conexión nula");
                }
            }
        }.execute();
    }
}

