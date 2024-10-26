package com.example.jmg_ascensores;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Mantenimiento extends AppCompatActivity {
    private EditText editTextFechaInicio;
    private EditText editTextFechaFin;
    private Button buttonRegistrar;
    private Connection conexion;
    private String codigoCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_mantenimiento);

        // Inicializar componentes de UI
        editTextFechaInicio = findViewById(R.id.editTextFechaInicio);
        editTextFechaFin = findViewById(R.id.editTextFechaFin);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);

        // Recibir el código del cliente desde el Intent
        codigoCliente = getIntent().getStringExtra("codigo_cliente");

        // Desactiva el botón mientras no haya conexión
        buttonRegistrar.setEnabled(false);

        // Ejecutar tarea para obtener la conexión
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection connection) {
                conexion = connection; // Guardar la conexión en la variable
                if (conexion == null) {
                    Toast.makeText(Mantenimiento.this, "Error al conectar a la base de datos", Toast.LENGTH_SHORT).show();
                } else {
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

    private void registrarMantenimiento() {
        String fechaInicio = editTextFechaInicio.getText().toString().trim();
        String fechaFin = editTextFechaFin.getText().toString().trim();

        if (codigoCliente == null || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Llamar a la tarea de registrar mantenimiento
        new RegistrarMantenimientoTask(conexion, this).execute(codigoCliente, fechaInicio, fechaFin);
    }

    // Método para convertir una fecha de String a java.sql.Date
    private static Date convertirFecha(String fecha) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date fechaUtil = formato.parse(fecha);
            return new Date(fechaUtil.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Clase AsyncTask para registrar mantenimiento
    private static class RegistrarMantenimientoTask extends AsyncTask<String, Void, Boolean> {
        private Connection conexion;
        private Mantenimiento actividad;

        public RegistrarMantenimientoTask(Connection conexion, Mantenimiento actividad) {
            this.conexion = conexion;
            this.actividad = actividad;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String codigoCliente = params[0];
            String fechaInicio = params[1];
            String fechaFin = params[2];

            try {
                // Convertir fechas de String a java.sql.Date
                Date fechaInicioDate = Mantenimiento.convertirFecha(fechaInicio);
                Date fechaFinDate = Mantenimiento.convertirFecha(fechaFin);

                if (fechaInicioDate == null || fechaFinDate == null) {
                    return false; // Si alguna conversión falla, el registro también falla
                }

                String query = "INSERT INTO mantenimiento (codigo_cliente, fecha_inicio, fecha_fin) VALUES (?, ?, ?)";
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setString(1, codigoCliente);
                stmt.setDate(2, fechaInicioDate);
                stmt.setDate(3, fechaFinDate);
                stmt.executeUpdate();
                return true; // Indica que el registro fue exitoso
            } catch (SQLException e) {
                Log.e("DatabaseError", "Error al registrar mantenimiento", e);
                return false; // Indica que hubo un error
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(actividad, "Mantenimiento registrado exitosamente", Toast.LENGTH_SHORT).show();
                actividad.finish(); // Cerrar actividad si el registro es exitoso
            } else {
                Toast.makeText(actividad, "Error al registrar mantenimiento", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
