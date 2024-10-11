package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;

public class ClienteNuevo extends AppCompatActivity {

    private EditText nombreEmpresaInput, codigoInput, passwordInput, ubicacionInput;
    private Button registrarClienteButton;
    private Connection connection; // La conexión a la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_nuevo);

        // Enlazamos los campos del layout
        nombreEmpresaInput = findViewById(R.id.nombre_empresa_input);
        codigoInput = findViewById(R.id.codigo_input);
        passwordInput = findViewById(R.id.password_input);
        ubicacionInput = findViewById(R.id.ubicacion_input);
        registrarClienteButton = findViewById(R.id.registrar_cliente_button);

        // Asignamos el listener al botón de registrar
        registrarClienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos los valores de los campos de entrada
                String nombreEmpresa = nombreEmpresaInput.getText().toString().trim();
                String codigo = codigoInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                String ubicacion = ubicacionInput.getText().toString().trim();

                // Validamos que todos los campos estén completos
                if (nombreEmpresa.isEmpty() || codigo.isEmpty() || password.isEmpty() || ubicacion.isEmpty()) {
                    Toast.makeText(ClienteNuevo.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Aquí deberías realizar la conexión con la base de datos y registrar el cliente
                if (connection != null) {
                    new RegistrarClienteTask(connection, ClienteNuevo.this) {
                        @Override
                        protected void onPostExecute(Boolean result) {
                            super.onPostExecute(result);
                            if (result) {
                                // Si el registro fue exitoso, navegar a la actividad Ascensor y pasar el código del cliente
                                Intent intent = new Intent(ClienteNuevo.this, AscensoresMantenimiento.class);
                                intent.putExtra("codigo_cliente", codigo); // Enviamos el código del cliente
                                startActivity(intent);
                                finish(); // Opcional: Cierra esta actividad si ya no la necesitas
                            } else {
                                Toast.makeText(ClienteNuevo.this, "Error al registrar cliente.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute(codigo, nombreEmpresa, password, ubicacion);
                } else {
                    Toast.makeText(ClienteNuevo.this, "Error: No se puede conectar a la base de datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Conexión a la base de datos
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn;
                if (connection == null) {
                    Toast.makeText(ClienteNuevo.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }
}

