package com.example.jmg_ascensores;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.DatePicker;
import java.sql.Connection;
import java.util.Calendar;

public class Mantenimiento extends AppCompatActivity {
    private EditText editTextFechaInicio;
    private EditText editTextFechaFin;
    private EditText editTextDescripcion;
    private Button buttonRegistrar;
    private Connection conexion;
    private String codigoCliente; // Almacena el código del cliente seleccionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_mantenimiento); // Asegúrate de que el nombre del layout es correcto

        // Inicializar los EditTexts y el botón
        editTextFechaInicio = findViewById(R.id.editTextFechaInicio);
        editTextFechaFin = findViewById(R.id.editTextFechaFin);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);

        // Recibir el código del cliente
        codigoCliente = getIntent().getStringExtra("codigo_cliente");

        // Obtener la conexión a la base de datos
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection connection) {
                conexion = connection; // Almacenar la conexión en la variable
                if (conexion == null) {
                    Toast.makeText(Mantenimiento.this, "Error al conectar a la base de datos", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Database", "Conexión establecida en MantenimientoActivity");
                    // Aquí puedes cargar los datos necesarios, si es necesario
                }
            }
        }.execute(); // Ejecutar la tarea de conexión

        // Configurar los DatePickers para las fechas
        editTextFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker(editTextFechaInicio);
            }
        });

        editTextFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker(editTextFechaFin);
            }
        });

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarMantenimiento(); // Llamar al método para registrar mantenimiento
            }
        });
    }

    // Método para mostrar el DatePicker
    private void mostrarDatePicker(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        String fecha = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear; // Formato DD/MM/YYYY
                        editText.setText(fecha);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    // Método para registrar mantenimiento
    private void registrarMantenimiento() {
        String fechaInicio = editTextFechaInicio.getText().toString().trim();
        String fechaFin = editTextFechaFin.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();

        if (codigoCliente == null || fechaInicio.isEmpty() || fechaFin.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Llamar a la tarea de registrar mantenimiento
        new RegistrarMantenimientoTask(conexion, this).execute(codigoCliente, fechaInicio, fechaFin, descripcion);
    }
}
