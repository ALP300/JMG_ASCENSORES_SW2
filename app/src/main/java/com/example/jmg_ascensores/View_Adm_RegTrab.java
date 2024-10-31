package com.example.jmg_ascensores;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.util.Calendar;

public class View_Adm_RegTrab extends AppCompatActivity {
    private Button registrarButton, logoutButton;
    private EditText nombreInput, apellidoInput, edadInput, fechaContactoInput, codeInput, passwordInput;
    private Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_trabajador); // Asegúrate de que coincida con el nombre del layout
        logoutButton = findViewById(R.id.logout_button);
        registrarButton = findViewById(R.id.registrar_button); // ID del botón
        nombreInput = findViewById(R.id.nombre_input); // Campo de nombre
        apellidoInput = findViewById(R.id.apellido_input); // Campo de apellido
        edadInput = findViewById(R.id.edad_input); // Campo de edad
        fechaContactoInput = findViewById(R.id.contacto_input); // Campo para la fecha
        codeInput = findViewById(R.id.code_input); // Campo para el código
        passwordInput = findViewById(R.id.password_input); // Campo para la contraseña

        // Aquí se debería establecer la conexión a la base de datos
        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection == null) {
                    Toast.makeText(View_Adm_RegTrab.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();

        // Configurar el DatePicker para fecha de contacto
        fechaContactoInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreInput.getText().toString().trim();
                String apellido = apellidoInput.getText().toString().trim();
                String edadStr = edadInput.getText().toString().trim();
                String fechaContacto = fechaContactoInput.getText().toString().trim();
                String codigo = codeInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Verificar que los campos no estén vacíos
                if (nombre.isEmpty() || apellido.isEmpty() || edadStr.isEmpty() || fechaContacto.isEmpty() || codigo.isEmpty() || password.isEmpty()) {
                    Toast.makeText(View_Adm_RegTrab.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar que la edad sea un número
                int edad;
                try {
                    edad = Integer.parseInt(edadStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(View_Adm_RegTrab.this, "La edad debe ser un número.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Llamamos a la tarea para registrar el trabajador
                if (connection != null) {
                    new DB_RegistrarTrab(connection, View_Adm_RegTrab.this)
                            .execute(codigo, nombre, apellido, edadStr, fechaContacto, password);

                    // Limpiar todos los campos
                    limpiarCampos();

                    // Redirigir a la actividad anterior
                    finish(); // Termina la actividad actual
                } else {
                    Toast.makeText(View_Adm_RegTrab.this, "Conexión a la base de datos no disponible.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void cerrarSesion() {
        Intent intent = new Intent(View_Adm_RegTrab.this, Home_Main.class); // Cambia a tu actividad de login
        startActivity(intent);
        finish(); // Finaliza esta actividad
    }

    // Método para mostrar el DatePicker
    private void mostrarDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String fecha = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear; // Formato DD/MM/YYYY
                    fechaContactoInput.setText(fecha);
                }, year, month, day);
        datePickerDialog.show();
    }

    // Método para limpiar los campos
    private void limpiarCampos() {
        nombreInput.setText("");
        apellidoInput.setText("");
        edadInput.setText("");
        fechaContactoInput.setText("");
        codeInput.setText("");
        passwordInput.setText("");
    }




}


