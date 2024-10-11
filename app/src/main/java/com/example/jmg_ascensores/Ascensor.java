package com.example.jmg_ascensores;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;

public class Ascensor extends AppCompatActivity {

    private EditText marcaInput, modeloInput;
    private Button guardarButton;
    private Connection connection; // La conexión a la base de datos
    private String clienteCodigo; // Almacenará el código del cliente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ascensor); // Asegúrate de que este sea el layout correcto

        // Enlazamos los campos del layout
        marcaInput = findViewById(R.id.marca_input);
        modeloInput = findViewById(R.id.modelo_input);
        guardarButton = findViewById(R.id.guardar_button);

        // Recibir el código del cliente de la actividad anterior
        clienteCodigo = getIntent().getStringExtra("codigo_cliente"); // Obtener el código del cliente

        // Asignamos el listener al botón de guardar ascensor
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String marca = marcaInput.getText().toString().trim();
                String modelo = modeloInput.getText().toString().trim();

                if (marca.isEmpty() || modelo.isEmpty()) {
                    Toast.makeText(Ascensor.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Aquí deberías realizar la conexión con la base de datos y registrar el ascensor
                if (connection != null) {
                    new RegistrarAscensorTask(connection, Ascensor.this) {
                        @Override
                        protected void onPostExecute(Boolean result) {
                            super.onPostExecute(result);
                            if (result) {
                                Toast.makeText(Ascensor.this, "Ascensor registrado con éxito.", Toast.LENGTH_SHORT).show();
                                // Aquí podrías cerrar la actividad o limpiar los campos
                            } else {
                                Toast.makeText(Ascensor.this, "Error al registrar ascensor.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute(clienteCodigo, marca, modelo); // Pasar el código del cliente al registrar ascensor
                } else {
                    Toast.makeText(Ascensor.this, "Error: No se puede conectar a la base de datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Conexión a la base de datos
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn;
                if (connection == null) {
                    Toast.makeText(Ascensor.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }
}

