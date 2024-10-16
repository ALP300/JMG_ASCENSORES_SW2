package com.example.jmg_ascensores;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.DatePicker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Mantenimiento extends AppCompatActivity {
    private EditText editTextFechaInicio;
    private EditText editTextFechaFin;
    private Spinner spinnerTareas;
    private Button buttonRegistrar;
    private Connection conexion;
    private String codigoCliente;
    private List<String> listaTareas = new ArrayList<>();
    private List<Integer> listaIdsTareas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_mantenimiento);

        // Inicializar componentes de UI
        editTextFechaInicio = findViewById(R.id.editTextFechaInicio);
        editTextFechaFin = findViewById(R.id.editTextFechaFin);
        spinnerTareas = findViewById(R.id.spinnerTareas);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);

        // Recibir el código del cliente
        codigoCliente = getIntent().getStringExtra("codigo_cliente");

        // Inicialmente, no cargues las tareas hasta que la conexión esté lista
        buttonRegistrar.setEnabled(false); // Desactiva el botón mientras no haya conexión

        // Ejecutar tarea para obtener la conexión
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection connection) {
                conexion = connection; // Guardar la conexión en la variable

                if (conexion == null) {
                    Toast.makeText(Mantenimiento.this, "Error al conectar a la base de datos", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Database", "Conexión establecida correctamente");
                    cargarTareas(); // Solo ahora cargamos las tareas
                    buttonRegistrar.setEnabled(true); // Activamos el botón de registrar
                }
            }
        }.execute();

        // Configurar DatePickers
        editTextFechaInicio.setOnClickListener(v -> mostrarDatePicker(editTextFechaInicio));
        editTextFechaFin.setOnClickListener(v -> mostrarDatePicker(editTextFechaFin));

        // Configurar el botón para registrar
        buttonRegistrar.setOnClickListener(v -> {
            if (conexion == null) {
                Toast.makeText(Mantenimiento.this, "Conexión no disponible", Toast.LENGTH_SHORT).show();
                return;
            }
            registrarMantenimiento(); // Llamar a registrar si hay conexión
        });
    }

    private void cargarTareas() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                if (conexion == null) {
                    Log.e("DatabaseError", "Conexión no establecida al cargar tareas");
                    return null;  // Devuelve null si la conexión es nula
                }

                List<String> tareas = new ArrayList<>();
                try {
                    String query = "SELECT id_tarea, nombre_tarea FROM tareas";
                    PreparedStatement stmt = conexion.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        listaIdsTareas.add(rs.getInt("id_tarea"));  // Guardar los ids
                        tareas.add(rs.getString("nombre_tarea"));  // Guardar los nombres
                    }
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return tareas;
            }

            @Override
            protected void onPostExecute(List<String> tareas) {
                if (tareas != null && !tareas.isEmpty()) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Mantenimiento.this, android.R.layout.simple_spinner_item, tareas);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTareas.setAdapter(adapter);
                } else {
                    Toast.makeText(Mantenimiento.this, "Error al cargar tareas o no hay tareas disponibles", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    // Método para mostrar el DatePicker
    private void mostrarDatePicker(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String fecha = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear; // Formato DD/MM/YYYY
                    editText.setText(fecha);
                }, year, month, day);
        datePickerDialog.show();
    }

    // Método para registrar mantenimiento
    private void registrarMantenimiento() {
        String fechaInicio = editTextFechaInicio.getText().toString().trim();
        String fechaFin = editTextFechaFin.getText().toString().trim();
        int tareaSeleccionadaPos = spinnerTareas.getSelectedItemPosition();  // Obtener la posición de la tarea seleccionada
        int idTareaSeleccionada = listaIdsTareas.get(tareaSeleccionadaPos);  // Obtener el ID de la tarea seleccionada

        if (codigoCliente == null || fechaInicio.isEmpty() || fechaFin.isEmpty() || idTareaSeleccionada == 0) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Llamar a la tarea de registrar mantenimiento
        new RegistrarMantenimientoTask(conexion, this).execute(codigoCliente, fechaInicio, fechaFin, String.valueOf(idTareaSeleccionada));
    }
}
