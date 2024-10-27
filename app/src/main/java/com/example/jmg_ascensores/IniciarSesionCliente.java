package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;

public class IniciarSesionCliente extends AppCompatActivity {

    private EditText codeInput;
    private EditText passwordInput;
    private Button loginButton;
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion_cliente);

        codeInput = findViewById(R.id.code_input_text);
        passwordInput = findViewById(R.id.password_input_text);
        loginButton = findViewById(R.id.login_button);

        // Conectar a la base de datos y obtener la conexión
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection == null) {
                    Toast.makeText(IniciarSesionCliente.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(IniciarSesionCliente.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (connection != null) {
                    new VerifyClienteTask(connection, IniciarSesionCliente.this) {
                        @Override
                        protected void onPostExecute(Boolean isAdmin) {
                            if (isAdmin) {
                                // Si es un admin, llevar a VistaAdministradorActivity
                                Intent intent = new Intent(IniciarSesionCliente.this, VistaCliente.class);
                                intent.putExtra("codCli", code); // Enviamos el código del cliente
                                startActivity(intent);
                                finish(); // Opcional: cierra MainActivity si no quieres volver a ella
                            } else {
                                Toast.makeText(IniciarSesionCliente.this, "Credenciales incorrectas.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute(code, password);
                } else {
                    Toast.makeText(IniciarSesionCliente.this, "Conexión a la base de datos no disponible.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Aquí puedes realizar alguna lógica adicional si es necesario

        // Llamar a la actividad OpcionesActivity
        Intent intent = new Intent(IniciarSesionCliente.this, OpcionesActivity.class);
        startActivity(intent);

        // Cerrar la actividad actual si no quieres volver a ella
        finish();

        // Llamar al método de superclase si no quieres evitar el comportamiento estándar
        super.onBackPressed(); // Opcional, dependiendo de tu lógica
    }

}
