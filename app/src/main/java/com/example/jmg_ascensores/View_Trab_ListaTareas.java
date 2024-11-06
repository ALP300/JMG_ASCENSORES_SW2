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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class View_Trab_ListaTareas extends AppCompatActivity {

    private RecyclerView lstCli;
    private String codAscen, codMant, codTrab,codCli;
    private Button btnTerminar;
    private DB_Tarea_Update dbTerminar = new DB_Tarea_Update();
    private DB_Ascensor_Update dbAsc = new DB_Ascensor_Update();
    private Integer num;
    private DB_Mantenimiento_Update maup = new DB_Mantenimiento_Update();
    private ProgressBar progressBar;

    private ExecutorService executorService;
    private CountDownLatch countDownLatch;  // Para sincronizar los hilos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trab_lista_tareas);

        initViews();
        loadCodAscen();
        executorService = Executors.newCachedThreadPool();  // Usamos un ExecutorService para manejar los hilos
        loadTareas();
    }

    private void initViews() {
        lstCli = findViewById(R.id.lstTareasTrab);
        lstCli.setLayoutManager(new LinearLayoutManager(this));
        btnTerminar = findViewById(R.id.btnTerminar);
        progressBar = findViewById(R.id.progressBar);
    }

    private void loadCodAscen() {
        codAscen = getIntent().getStringExtra("codAsc");
        codMant = getIntent().getStringExtra("codMant");
        codTrab = getIntent().getStringExtra("codTrab");
        codCli = getIntent().getStringExtra("codCli");
        num = Integer.parseInt(codAscen);
        Log.d("Database", "codAsc: " + codAscen);
    }

    private void loadTareas() {
   // Muestra el ProgressBar mientras cargamos las tareas

        // Inicializamos el CountDownLatch, con el número de tareas a esperar
        countDownLatch = new CountDownLatch(1);

        executorService.submit(() -> {
            try {
                ArrayList<Ent_TareaItem> listTar = new DB_InfoTareasWhere(this).execute(codAscen).get();
                Log.d("Database", "lst: " + listTar);
                runOnUiThread(() -> setupAdapter(listTar));
            } catch (ExecutionException | InterruptedException e) {
                Log.e("Database", "Error al cargar tareas", e);
            } finally {
                countDownLatch.countDown();  // Disminuimos el contador cuando la tarea finalice
            }
        });
    }

    private void setupAdapter(ArrayList<Ent_TareaItem> listTar) {
        Adapter_Tarea adapter = new Adapter_Tarea(View_Trab_ListaTareas.this,listTar);
        lstCli.setAdapter(adapter);
        btnTerminar.setOnClickListener(v -> handleTerminarClick(adapter));
    }

    private void handleTerminarClick(Adapter_Tarea adapter) {
  // Muestra el ProgressBar al hacer click

        showProgressBar(true); // Incrementamos el contador porque vamos a iniciar otra tarea
        countDownLatch = new CountDownLatch(1);

        executorService.submit(() -> {
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
                    showProgressBar(false);
                }
            } catch (ExecutionException | InterruptedException e) {
                Log.e("Database", "Error al terminar tareas", e);
            } finally {
                countDownLatch.countDown();  // Disminuimos el contador cuando la tarea finalice
            }
        });
    }

    private void handlePostUpdate() {
        showProgressBar(true);  // Muestra el ProgressBar al realizar la actualización

        // Incrementamos el contador porque vamos a iniciar otra tarea
        countDownLatch = new CountDownLatch(1);

        executorService.submit(() -> {
            try {
                Boolean v = new DB_InfoDetalleClientWhereVerify(this).execute(codMant).get();
                if (!v) {
                    maup.actualizarManten(codMant, "confirmar");
                    runOnUiThread(() -> {
                        Intent intent = new Intent(this, View_Trab_ListaEmpresas.class);
                        Log.d("Database", "codTrab: " + codTrab);
                        intent.putExtra("codTrab", codTrab);
                        startActivity(intent);
                    });
                } else {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(this, View_Trab_ListaAscen.class);
                        Log.d("Database", "codTrab: " + codTrab);
                        intent.putExtra("codCli", codCli);
                        intent.putExtra("codMant", codMant);
                        intent.putExtra("codTrab", codTrab);
                        startActivity(intent);
                    });
                }
            } catch (ExecutionException | InterruptedException e) {
                Log.e("Database", "Error en la verificación de detalles", e);
            } finally {
                countDownLatch.countDown();  // Disminuimos el contador cuando la tarea finalice
            }
        });
    }

    private void restartActivity() {
        showProgressBar(true);  // Muestra el ProgressBar mientras reiniciamos la actividad

        executorService.submit(() -> {
            try {
                Intent intent = new Intent(this, View_Trab_ListaTareas.class);
                intent.putExtra("codAsc", codAscen);
                startActivity(intent);
            } catch (Exception e) {
                Log.e("Database", "Error al reiniciar la actividad", e);
            } finally {
                countDownLatch.countDown();  // Disminuimos el contador cuando la tarea finalice
            }
        });
    }

    private void showProgressBar(final boolean visible) {
        runOnUiThread(() -> progressBar.setVisibility(visible ? View.VISIBLE : View.GONE));
    }

    // Espera hasta que todos los hilos hayan terminado
    private void waitForTasksToFinish() {
        try {
            countDownLatch.await();  // Espera hasta que el contador llegue a cero
            showProgressBar(false);  // Oculta el ProgressBar cuando todos los hilos hayan terminado
        } catch (InterruptedException e) {
            Log.e("Database", "Error al esperar por los hilos", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cierra el ExecutorService cuando la actividad se destruye
        executorService.shutdown();
    }
    }