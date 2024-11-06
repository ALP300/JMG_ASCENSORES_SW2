package com.example.jmg_ascensores;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.util.List;

public class View_Trab_ListaAscen extends AppCompatActivity {

    private Connection connection; // La conexión a la base de datos
    private ListView lstAsc;
    private String codCli,codMant,codTrab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trab_lista_ascen);

        // Obtener referencia al botón
        codCli = getIntent().getStringExtra("codCli");
        codMant = getIntent().getStringExtra("codMant");
        codTrab= getIntent().getStringExtra("codTrab");
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
                        asc = new DB_InfoAscenWhere(connection, View_Trab_ListaAscen.this).execute(codCli).get();
                        // Configurar el adaptador
                        Adapter_BtnAscensores adapter = new Adapter_BtnAscensores(View_Trab_ListaAscen.this,asc,codMant, codTrab,codCli);
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

