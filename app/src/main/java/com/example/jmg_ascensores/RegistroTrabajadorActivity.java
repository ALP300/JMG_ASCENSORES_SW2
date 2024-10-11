package com.example.jmg_ascensores;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;

public class RegistroTrabajadorActivity extends AppCompatActivity {
    private EditText nombreInput, apellidoInput, edadInput, fechaContactoInput, codeInput, passwordInput;
    private Button registrarButton;
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_trabajador);

        // Enlazamos los elementos del layout
        nombreInput = findViewById(R.id.nombre_input);
        apellidoInput = findViewById(R.id.apellido_input);
        edadInput = findViewById(R.id.edad_input);
        fechaContactoInput = findViewById(R.id.contacto_input);
        codeInput = findViewById(R.id.code_input);
        passwordInput = findViewById(R.id.password_input);
        registrarButton = findViewById(R.id.registrar_button);

        // Aquí se debería establecer la conexión a la base de datos
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection == null) {
                    Toast.makeText(RegistroTrabajadorActivity.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();

        // Asignamos el listener para el botón registrar
        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos los valores de los campos de texto
                String nombre = nombreInput.getText().toString().trim();
                String apellido = apellidoInput.getText().toString().trim();
                String edad = edadInput.getText().toString().trim();
                String fechaContacto = fechaContactoInput.getText().toString().trim();
                String codigo = codeInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Validar campos
                if (nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty() || fechaContacto.isEmpty() || codigo.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegistroTrabajadorActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Llamamos a la tarea para registrar el trabajador
                if (connection != null) {
                    new VerifyTrabajadorRegistroTask(connection, RegistroTrabajadorActivity.this)
                            .execute(codigo, nombre, apellido, edad, fechaContacto, password);
                } else {
                    Toast.makeText(RegistroTrabajadorActivity.this, "Conexión a la base de datos no disponible.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

