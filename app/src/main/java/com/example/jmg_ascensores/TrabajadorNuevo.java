package com.example.jmg_ascensores;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class TrabajadorNuevo extends AppCompatActivity {
    private Button registrarButton;
    private EditText nombreInput;
    private EditText apellidoInput;
    private EditText edadInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_trabajador); // Asegúrate de que coincida con el nombre del layout

        registrarButton = findViewById(R.id.registrar_button); // ID del botón
        nombreInput = findViewById(R.id.nombre_input); // Campo de nombre
        apellidoInput = findViewById(R.id.apellido_input); // Campo de apellido
        edadInput = findViewById(R.id.edad_input); // Campo de edad

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreInput.getText().toString().trim();
                String apellido = apellidoInput.getText().toString().trim();
                String edad = edadInput.getText().toString().trim();

                // Verificar que los campos no estén vacíos
                if (nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty()) {
                    Toast.makeText(TrabajadorNuevo.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Aquí puedes agregar la lógica para guardar los datos en la base de datos
                // Por ejemplo, llamar a una función que realice el registro

                // Simulación de éxito en el registro
                Log.d("TrabajadorNuevo", "Registro exitoso: " + nombre + " " + apellido + ", Edad: " + edad);
                Toast.makeText(TrabajadorNuevo.this, "Trabajador registrado exitosamente", Toast.LENGTH_SHORT).show();

                // Opcional: Redirigir a otra actividad si es necesario
                Intent intent = new Intent(TrabajadorNuevo.this, RegistroClienteTrabajador.class);
                startActivity(intent);
                finish(); // Terminar esta actividad si no deseas volver a ella
            }
        });
    }
}

