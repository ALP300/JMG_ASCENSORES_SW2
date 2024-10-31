package com.example.jmg_ascensores;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DB_Mantenimiento extends AppCompatActivity {
    private EditText editTextFechaInicio;
    private EditText editTextFechaFin;
    private Button buttonRegistrar;
    private Connection conexion;
    private static String codigoCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_mantenimiento);

        // Inicializar componentes de la UI
        editTextFechaInicio = findViewById(R.id.editTextFechaInicio);
        editTextFechaFin = findViewById(R.id.editTextFechaFin);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);

        // Recuperar el código del cliente desde el Intent
        codigoCliente = getIntent().getStringExtra("codigo_cliente");

        // Desactivar el botón inicialmente hasta que se establezca la conexión
        buttonRegistrar.setEnabled(false);

        // Ejecutar tarea para establecer la conexión con la base de datos
        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection connection) {
                conexion = connection;
                if (conexion == null) {
                    Toast.makeText(DB_Mantenimiento.this, "Error al conectar a la base de datos", Toast.LENGTH_SHORT).show();
                } else {
                    buttonRegistrar.setEnabled(true);
                }
            }
        }.execute();

        // Configurar DatePickers
        editTextFechaInicio.setOnClickListener(v -> mostrarDatePicker(editTextFechaInicio));
        editTextFechaFin.setOnClickListener(v -> mostrarDatePicker(editTextFechaFin));

        // Configurar el botón de registrar
        buttonRegistrar.setOnClickListener(v -> {
            if (conexion == null) {
                Toast.makeText(DB_Mantenimiento.this, "Conexión no disponible", Toast.LENGTH_SHORT).show();
                return;
            }
            registrarMantenimiento();
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
                    String fecha = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
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

    // Método para convertir fecha de String a java.sql.Date
    public static Date convertirFecha(String fecha) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date fechaUtil = formato.parse(fecha);
            return new Date(fechaUtil.getTime());
        } catch (ParseException e) {
            Log.e("DateConversionError", "Error al convertir fecha", e);
            return null;
        }
    }

    // AsyncTask para registrar mantenimiento
    private static class RegistrarMantenimientoTask extends AsyncTask<String, Void, Boolean> {
        private final Connection conexion;
        private final DB_Mantenimiento actividad;
        private int codigoMantenimiento; // Variable para almacenar el código generado

        public RegistrarMantenimientoTask(Connection conexion, DB_Mantenimiento actividad) {
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
                Date fechaInicioDate = DB_Mantenimiento.convertirFecha(fechaInicio);
                Date fechaFinDate = DB_Mantenimiento.convertirFecha(fechaFin);

                if (fechaInicioDate == null || fechaFinDate == null) {
                    return false; // Si alguna conversión falla, el registro también falla
                }

                String query = "INSERT INTO mantenimiento (codigo_cliente, fecha_inicio, fecha_fin) VALUES (?, ?, ?)";
                PreparedStatement stmt = conexion.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.setString(1, codigoCliente);
                stmt.setDate(2, fechaInicioDate);
                stmt.setDate(3, fechaFinDate);
                stmt.executeUpdate();

                // Obtener el código de mantenimiento generado
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    codigoMantenimiento = generatedKeys.getInt(1); // Asigna el código generado
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                Log.e("DatabaseError", "Error al registrar mantenimiento", e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(actividad, "Mantenimiento registrado exitosamente", Toast.LENGTH_SHORT).show();

                // Intent para iniciar la actividad Tarea y pasar el código de mantenimiento
                Intent intent = new Intent(actividad, DB_Tarea.class);
                intent.putExtra("codigo_mantenimiento", codigoMantenimiento); // Pasar el código de mantenimiento
                intent.putExtra("codCli", codigoCliente);
                actividad.startActivity(intent);
                actividad.finish(); // Opcionalmente, finalizar la actividad actual
            } else {
                Toast.makeText(actividad, "Error al registrar mantenimiento", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
