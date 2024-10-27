package com.example.jmg_ascensores;

import android.os.Bundle;

import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class View_ProxManten extends AppCompatActivity {

    private ListView listProxMant;
    private Connection connection;
    private String codCli;
    private Ent_Cliente clientex;
    private TextView txtEmp ;
    private TextView txtCod ;
    private TextView txtUbi ;
    private TextView txtFec ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prox_mant); // Asegúrate de que este es el diseño correcto
        codCli = getIntent().getStringExtra("codCli");

        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection != null) {
                    try {
                        clientex = new DB_Cliente(connection, View_ProxManten.this).execute(codCli).get();
                        if (clientex != null){
                            txtEmp = findViewById(R.id.txtEmp);
                            txtCod = findViewById(R.id.txtCod);
                            txtUbi = findViewById(R.id.txtUbic);
                            txtFec = findViewById(R.id.txtFec);
                            txtEmp.setText(clientex.getNombre_empresa());
                            txtCod.setText(clientex.getCodigo());
                            txtUbi.setText(clientex.getUbicacion());
                            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                            Date fechaActual = calendar.getTime();
                            long ddf = clientex.getFec().getTime()-fechaActual.getTime();
                            // Convertir la diferencia a días
                            double diasDifDecimal = (double) ddf / (1000 * 60 * 60 * 24);
                            long diasDif = (long) Math.ceil(diasDifDecimal);
                            txtFec.setText(diasDif+" "+clientex.getFec()+" "+fechaActual);
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.execute();

    }



}
