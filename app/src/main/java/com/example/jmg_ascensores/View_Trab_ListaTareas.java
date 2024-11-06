package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class View_Trab_ListaTareas extends AppCompatActivity {

    private RecyclerView lstCli;
    private String codAscen, codMant,codTrab;
    private Button btnTerminar;
    private DB_Tarea_Update dbTerminar = new DB_Tarea_Update();
    private DB_Ascensor_Update dbAsc = new DB_Ascensor_Update();
    private Integer num;
    private DB_Mantenimiento_Update maup = new DB_Mantenimiento_Update();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trab_lista_tareas);

        initViews();
        loadCodAscen();
        loadTareas();
    }

    private void initViews() {
        lstCli = findViewById(R.id.lstTareasTrab);
        lstCli.setLayoutManager(new LinearLayoutManager(this));
        btnTerminar = findViewById(R.id.btnTerminar);
        progressBar = findViewById(R.id.progressBar);  // Asegúrate de que el ID coincida con el que has usado en tu XML

    }

    private void loadCodAscen() {
        codAscen = getIntent().getStringExtra("codAsc");
        codMant = getIntent().getStringExtra("codMant");
        codTrab = getIntent().getStringExtra("codTrab");
        num = Integer.parseInt(codAscen);
        Log.d("Database", "codAsc: " + codAscen);
    }

    private void loadTareas() {
        new Thread(() -> {
            try {
                ArrayList<Ent_TareaItem> listTar = new DB_InfoTareasWhere(this).execute(codAscen).get();
                Log.d("Database", "lst: " + listTar);
                runOnUiThread(() -> setupAdapter(listTar));
            } catch (ExecutionException | InterruptedException e) {
                Log.e("Database", "Error al cargar tareas", e);
            }
        }).start();
    }

    private void setupAdapter(ArrayList<Ent_TareaItem> listTar) {
        Adapter_Tarea adapter = new Adapter_Tarea(listTar);
        lstCli.setAdapter(adapter);
        btnTerminar.setOnClickListener(v -> handleTerminarClick(adapter));
    }

    private void handleTerminarClick(Adapter_Tarea adapter) {
        runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));
        new Thread(() -> {
            try {
                List<Integer> listId = adapter.getListk();
                for (Integer x : listId) {
                    dbTerminar.actualizarTarea(x);
                }

                // Reconsulta las tareas
                ArrayList<Ent_TareaItem> listTar = new DB_InfoTareasWhere(this).execute(codAscen).get();
                if (listTar.isEmpty()) {
                    dbAsc.actualizarAscen(num);
                    handlePostUpdate();
                } else {
                    restartActivity();
                }
            } catch (ExecutionException | InterruptedException e) {
                Log.e("Database", "Error al terminar tareas", e);
            } finally {
                // Oculta el ProgressBar después de completar la tarea
                runOnUiThread(() -> progressBar.setVisibility(View.GONE));
            }
        }).start();
    }

    private void handlePostUpdate() {
        runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

        new Thread(() -> {
            try {
                Boolean v = new DB_InfoDetalleClientWhereVerify(this).execute(codMant).get();
                //Log.d("Database", v+"");
                if (!v) {
                    //Log.d("Database", "FALSOOO");
                    maup.actualizarManten(codMant,"confirmar");
                    runOnUiThread(() -> {
                        Intent intent = new Intent(this, View_Trab_ListaEmpresas.class);
                        Log.d("Database", "codTrab: "+codTrab);
                        intent.putExtra("codTrab", codTrab);
                        startActivity(intent);
                    });
                }
            } catch (ExecutionException | InterruptedException e) {
                Log.e("Database", "Error en la verificación de detalles", e);
            }finally {
                // Oculta el ProgressBar después de completar la tarea
                runOnUiThread(() -> progressBar.setVisibility(View.GONE));
            }
        }).start();
    }

    private void restartActivity() {
        runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));
        runOnUiThread(() -> {
            Intent intent = new Intent(this, View_Trab_ListaTareas.class);
            intent.putExtra("codAsc", codAscen);
            startActivity(intent);
        });
        runOnUiThread(() -> progressBar.setVisibility(View.GONE));
    }
}