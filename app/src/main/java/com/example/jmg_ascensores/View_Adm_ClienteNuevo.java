package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;

public class View_Adm_ClienteNuevo extends AppCompatActivity {

    private static final int MAP_REQUEST_CODE = 1;
    private EditText nombreEmpresaInput, codigoInput, passwordInput;
    private Button elegirUbicacionButton, registrarClienteButton;
    private TextView ubicacionTextView;
    private Connection connection;
    private String ubicacionSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_nuevo);

        // Enlazamos los campos del layout
        nombreEmpresaInput = findViewById(R.id.nombre_empresa_input);
        codigoInput = findViewById(R.id.codigo_input);
        passwordInput = findViewById(R.id.password_input);
        elegirUbicacionButton = findViewById(R.id.elegir_ubicacion_button);
        registrarClienteButton = findViewById(R.id.registrar_cliente_button);
        ubicacionTextView = findViewById(R.id.ubicacion_textview);

        // Listener para el botón de elegir ubicación
        elegirUbicacionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrimos una nueva actividad para seleccionar la ubicación en Google Maps
                Intent intent = new Intent(View_Adm_ClienteNuevo.this, API_Maps.class);
                startActivityForResult(intent, MAP_REQUEST_CODE);
            }
        });

        // Listener para el botón de registrar cliente
        registrarClienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos los valores de los campos de entrada
                String nombreEmpresa = nombreEmpresaInput.getText().toString().trim();
                String codigo = codigoInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Validación de solo letras y números en código
                if (!codigo.matches("[a-zA-Z0-9]+")) {
                    Toast.makeText(View_Adm_ClienteNuevo.this, "El código solo puede contener letras y números.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validación de la contraseña: debe tener al menos 6 caracteres, un símbolo, una letra minúscula y una letra mayúscula
                if (password.length() < 6 ||
                        !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*") ||
                        !password.matches(".*[a-z].*") ||
                        !password.matches(".*[A-Z].*")) {
                    Toast.makeText(View_Adm_ClienteNuevo.this,
                            "La contraseña debe tener al menos 6 caracteres, contener al menos un símbolo, y tener al menos una letra minúscula y una mayúscula.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validamos que todos los campos estén completos
                if (nombreEmpresa.isEmpty() || codigo.isEmpty() || password.isEmpty() || ubicacionSeleccionada == null) {
                    Toast.makeText(View_Adm_ClienteNuevo.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Aquí deberías realizar la conexión con la base de datos y registrar el cliente
                if (connection != null) {
                    new DB_RegistrarCliente(connection, View_Adm_ClienteNuevo.this) {
                        @Override
                        protected void onPostExecute(Boolean result) {
                            super.onPostExecute(result);
                            if (result) {
                                // Si el registro fue exitoso, navegar a la actividad Mantenimiento y pasar el código del cliente
                                Intent intent = new Intent(View_Adm_ClienteNuevo.this, DB_RegAscensor.class);
                                intent.putExtra("codigo_cliente", codigo); // Pasar el código del cliente a la actividad de mantenimiento
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(View_Adm_ClienteNuevo.this, "Error al registrar cliente.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute(codigo, nombreEmpresa, password, ubicacionSeleccionada);
                } else {
                    Toast.makeText(View_Adm_ClienteNuevo.this, "Error: No se puede conectar a la base de datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Conexión a la base de datos
        new DB_Connect() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn;
                if (connection == null) {
                    Toast.makeText(View_Adm_ClienteNuevo.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    // Este método recibe la ubicación seleccionada en la actividad de Google Maps
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAP_REQUEST_CODE && resultCode == RESULT_OK) {
            // Obtenemos la ubicación desde la actividad de Maps
            ubicacionSeleccionada = data.getStringExtra("ubicacion");
            ubicacionTextView.setText("Ubicación: " + ubicacionSeleccionada);
        }
    }
}
