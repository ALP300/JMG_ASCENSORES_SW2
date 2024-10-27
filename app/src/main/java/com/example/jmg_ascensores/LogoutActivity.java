package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LogoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout);

        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes agregar lógica para cerrar sesión si es necesario

                // Redirige al layout de opciones
                Intent intent = new Intent(LogoutActivity.this, MainActivity.class); // Cambia OpcionesActivity por el nombre real de tu Activity
                startActivity(intent);
                finish(); // Termina esta actividad
            }
        });
    }
}
