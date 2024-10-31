package com.example.jmg_ascensores;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;

public class Home_Main extends AppCompatActivity {

    private EditText codeInput;
    private EditText passwordInput;
    private Button loginButton;
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion);

        codeInput = findViewById(R.id.code_input_text);
        passwordInput = findViewById(R.id.password_input_text);
        loginButton = findViewById(R.id.login_button);

        // Conectar a la base de datos y obtener la conexión
        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection == null) {
                    Toast.makeText(Home_Main.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                String code = codeInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Validar campos
                if (code.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Home_Main.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar que solo se ingresen letras y números
                if (!code.matches("[a-zA-Z0-9]+") || !password.matches("[a-zA-Z0-9]+")) {
                    Toast.makeText(Home_Main.this, "Solo se permiten letras y números.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Deshabilitar el botón mientras se verifica
                loginButton.setEnabled(false);

                if (connection != null) {
                    new DB_VerifyAdmin(connection, Home_Main.this) {
                        @Override
                        protected void onPostExecute(String[] dtCli) {
                            super.onPostExecute(dtCli);
                            // Habilitar el botón de nuevo después de la consulta
                            loginButton.setEnabled(true);
                        }
                    }.execute(code, password);
                } else {
                    Toast.makeText(Home_Main.this, "Conexión a la base de datos no disponible.", Toast.LENGTH_SHORT).show();
                    loginButton.setEnabled(true);  // Rehabilitar en caso de falla de conexión
                }
            }
        });
    }
}
