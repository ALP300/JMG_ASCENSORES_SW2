package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class View_Adm_AsignarCliente extends AppCompatActivity {

    private ListView lstCli;
    private Connection connection;
    private Integer codTrab;
    private Button btnAñadir;
    private DatabaseHelper dbHelper =new DatabaseHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asignar_cliente); // Asegúrate de que esto coincida con tu archivo de diseño
        lstCli = findViewById(R.id.lstClientes);
        btnAñadir= findViewById(R.id.btnAñad);
        codTrab = Integer.parseInt(getIntent().getStringExtra("trab_id"));
        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection != null) {
                    try {
                        // Ejecutar la consulta para obtener los ascensores del cliente
                        List<Ent_Cliente> clients = new DB_InfoClientsNull(connection, View_Adm_AsignarCliente.this).execute().get();// Agregar todos los ascensores a la lista
                        // Configurar el adaptador
                        Adapter_clientes adapter = new Adapter_clientes(View_Adm_AsignarCliente.this, clients);
                        lstCli.setAdapter(adapter);
                        connection.close();
                        btnAñadir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new Thread(() -> {
                                    List<String> listId = adapter.getClientId();
                                    for (String x :listId) {
                                        dbHelper.actualizarRegistro(codTrab, x);

                                    }
                                    Intent intent = new Intent(View_Adm_AsignarCliente.this, View_Adm_AsignarTrabajador.class);
                                    startActivity(intent);
                                }).start();
                            }
                        });

                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }.execute();


    }
}
