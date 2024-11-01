package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class View_Trab_ListaTareas extends AppCompatActivity {

    private RecyclerView lstCli;
    private Connection connection;
    private String codAscen;
    private Button btnTerminar;
    private DB_Tarea_Terminar db_terminar = new DB_Tarea_Terminar();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trab_lista_tareas); // Asegúrate de que esto coincida con tu archivo de diseño
        lstCli = findViewById(R.id.lstTareasTrab);
        lstCli.setLayoutManager(new LinearLayoutManager(this));
        btnTerminar = findViewById(R.id.btnTerminar);
        codAscen = getIntent().getStringExtra("codAsc");

        Log.d("Database", "codAsc: "+codAscen);
        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection != null) {
                    try {
                        // Ejecutar la consulta para obtener los ascensores del cliente
                        ArrayList<Ent_TareaItem> listTar = new DB_InfoTareasWhere(connection, View_Trab_ListaTareas.this).execute(codAscen).get();// Agregar todos los ascensores a la lista
                        // Configurar el adaptador
                        Log.d("Database", "listTar: "+listTar.get(0).getCodTar());
                        Adapter_Tarea adapter = new Adapter_Tarea(listTar);
                        lstCli.setAdapter(adapter);


                        btnTerminar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new Thread(() -> {
                                    List<Integer> listId = adapter.getListk();

                                    for (Integer x :listId) {
                                        db_terminar.actualizarManten(x);
                                    }

                                    Intent intent = new Intent(View_Trab_ListaTareas.this, View_Trab_ListaTareas.class);
                                    intent.putExtra("codAsc",codAscen);
                                    startActivity(intent);
                                }).start();
                            }
                        });
                        connection.close();
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
