package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;

public class MainActivityTrabajador extends AppCompatActivity {
    private EditText codeInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button loginButton2;
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion_trabajador); // Cargar el layout correspondiente

        codeInput = findViewById(R.id.code_input_text);
        passwordInput = findViewById(R.id.password_input_text);
        loginButton = findViewById(R.id.login_button);

        // Conectar a la base de datos y obtener la conexión
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection == null) {
                    Toast.makeText(MainActivityTrabajador.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = codeInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Validar campos
                if (code.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivityTrabajador.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (connection != null) {
                    new VerifyTrabajadorTask(connection, MainActivityTrabajador.this).execute(code, password);
                } else {
                    Toast.makeText(MainActivityTrabajador.this, "Conexión a la base de datos no disponible.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
