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
        setContentView(R.layout.adm_registro_trabajador); // Asegúrate de que coincida con el nombre del layout
        logoutButton = findViewById(R.id.logout_button);
        registrarButton = findViewById(R.id.registrar_button);
        nombreInput = findViewById(R.id.nombre_input);
        apellidoInput = findViewById(R.id.apellido_input);
        edadInput = findViewById(R.id.edad_input);
        fechaContactoInput = findViewById(R.id.contacto_input);
        codeInput = findViewById(R.id.code_input);
        passwordInput = findViewById(R.id.password_input);

        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn;
                if (connection == null) {
                    Toast.makeText(View_Adm_RegTrab.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();

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
                if (validarCampos()) {
                    // Llamamos a la tarea para registrar el trabajador
                    if (connection != null) {
                        new DB_RegistrarTrab(connection, View_Adm_RegTrab.this)
                                .execute(
                                        codeInput.getText().toString().trim(),
                                        nombreInput.getText().toString().trim(),
                                        apellidoInput.getText().toString().trim(),
                                        edadInput.getText().toString().trim(),
                                        fechaContactoInput.getText().toString().trim(),
                                        passwordInput.getText().toString().trim()
                                );

                        limpiarCampos();
                        finish();
                    } else {
                        Toast.makeText(View_Adm_RegTrab.this, "Conexión a la base de datos no disponible.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void cerrarSesion() {
        Intent intent = new Intent(View_Adm_RegTrab.this, Home_Main.class);
        startActivity(intent);
        finish();
    }

    private void mostrarDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String fecha = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    fechaContactoInput.setText(fecha);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void limpiarCampos() {
        nombreInput.setText("");
        apellidoInput.setText("");
        edadInput.setText("");
        fechaContactoInput.setText("");
        codeInput.setText("");
        passwordInput.setText("");
    }

    // Método para validar los campos
    private boolean validarCampos() {
        String nombre = nombreInput.getText().toString().trim();
        String apellido = apellidoInput.getText().toString().trim();
        String edadStr = edadInput.getText().toString().trim();
        String dni = codeInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Validar Nombre y Apellido
        if (!nombre.matches("([a-zA-Z]{1,15})( [a-zA-Z]{1,15})?")) {
            nombreInput.setError("Debe contener uno o dos nombres, solo letras y máximo 30 caracteres");
            return false;
        }

        if (!apellido.matches("([a-zA-Z]{1,15})( [a-zA-Z]{1,15})?")) {
            apellidoInput.setError("Debe contener uno o dos apellidos, solo letras y máximo 30 caracteres");
            return false;
        }
        // Validar Edad
        if (!edadStr.matches("\\d{1,2}")) {
            edadInput.setError("Edad debe contener solo números y hasta 2 dígitos");
            return false;
        }

        // Validar DNI
        if (!dni.matches("\\d{8}")) {
            codeInput.setError("DNI debe contener exactamente 8 dígitos");
            return false;
        }

        // Validar Contraseña
        if (!password.matches("[a-zA-Z0-9]{6,15}")) {
            passwordInput.setError("Contraseña debe contener solo letras y números, mínimo 6 caracteres, sin caracteres especiales");
            return false;
        }

        return true;
    }
}
