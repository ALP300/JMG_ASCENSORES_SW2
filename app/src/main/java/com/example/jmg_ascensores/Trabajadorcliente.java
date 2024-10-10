package com.example.jmg_ascensores;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Trabajadorcliente extends AppCompatActivity {
    private Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trabajador_cliente); // Asegúrate de que esto coincida con tu archivo de diseño

        myButton = findViewById(R.id.vista2); // Reemplaza con la ID real de tu botón

        if (myButton != null) { // Opcional: Verifica si el botón no es null
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Manejar clic en el botón
                }
            });
        } else {
            Log.e("RegistroCliente", "¡El botón es null! Verifica el archivo de diseño.");
        }
    }
}
