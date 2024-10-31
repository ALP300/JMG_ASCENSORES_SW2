package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout; // Cambiado de Button a LinearLayout
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Home_Cliente extends AppCompatActivity {

    private LinearLayout btnManProx; // Cambiar a LinearLayout
    private LinearLayout btnInfo;     // Cambiar a LinearLayout
    private LinearLayout btnCerrarSesion; // Cambiar a LinearLayout
    private TextView textViewBienvenida; // TextView para mostrar la bienvenida personalizada
    private String codCli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_cliente); // Asegúrate de que el layout corresponde a esta actividad

        // Obtén el código del cliente y el nombre de la empresa desde el Intent
        codCli = getIntent().getStringExtra("codCli");
        Log.i("Database", "Código del cliente recibido: " + codCli);
        String nombreEmpresa = getIntent().getStringExtra("nombre_empresa");

        // Configuración del TextView de bienvenida
        textViewBienvenida = findViewById(R.id.textView9); // Verifica que el ID coincida con el layout
        if (nombreEmpresa != null) {
            // Muestra el nombre de la empresa en el saludo de bienvenida
            textViewBienvenida.setText("BIENVENIDO CLIENTE: " + nombreEmpresa);
        }

        // Inicialización de los LinearLayouts
        btnManProx = findViewById(R.id.btnManProx);
        btnInfo = findViewById(R.id.btnInfAsc);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Configuración del LinearLayout para ver mantenimientos próximos
        btnManProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VistaAdmin", "Botón de Mantenimientos Próximos clicado");
                Intent intent = new Intent(Home_Cliente.this, View_Cli_ProxManten.class);
                intent.putExtra("codCli", codCli); // Envía el código del cliente
                startActivity(intent);
            }
        });

        // Configuración del LinearLayout para ver información de ascensores
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Cliente.this, View_Cli_InfoAscensor.class);
                intent.putExtra("codCli", codCli); // Envía el código del cliente
                Log.i("VistaCliente", "Código del cliente que se enviará: " + codCli);
                startActivity(intent);
            }
        });

        // Configuración del LinearLayout para cerrar sesión
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirige a la actividad de inicio de sesión y limpia la pila de actividades
                Intent intent = new Intent(Home_Cliente.this, Home_Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpiar pila de actividades
                startActivity(intent);
                finish(); // Cierra la actividad actual
            }
        });
    }
}
