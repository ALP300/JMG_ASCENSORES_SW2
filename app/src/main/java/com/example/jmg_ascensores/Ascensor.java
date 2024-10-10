package com.example.jmg_ascensores;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;

public class Ascensor extends AppCompatActivity {
    private EditText marcaInput, modeloInput;
    private Button guardarButton;
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ascensor); // Asegúrate de que esto coincida con tu archivo de diseño

        // Enlazamos los elementos del layout
        marcaInput = findViewById(R.id.marca_input);
        modeloInput = findViewById(R.id.modelo_input);
        guardarButton = findViewById(R.id.guardar_button);

        // Aquí se debería establecer la conexión a la base de datos
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn; // Guardar la conexión para su uso posterior
                if (connection == null) {
                    Toast.makeText(Ascensor.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();

        // Asignamos el listener para el botón guardar
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos los valores de los campos de texto
                String marca = marcaInput.getText().toString().trim();
                String modelo = modeloInput.getText().toString().trim();

                // Validar campos
                if (marca.isEmpty() || modelo.isEmpty()) {
                    Toast.makeText(Ascensor.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Llamamos a la tarea para registrar el ascensor
                if (connection != null) {
                    new RegistrarAscensorTask(connection, Ascensor.this)
                            .execute(marca, modelo);
                } else {
                    Toast.makeText(Ascensor.this, "Conexión a la base de datos no disponible.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
