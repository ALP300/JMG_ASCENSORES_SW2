package com.example.jmg_ascensores;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity  extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Tiempo que se mostrará el splash screen
        int SPLASH_DISPLAY_LENGTH = 3000; // 3 segundos

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Iniciar la actividad principal
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish(); // Cierra la actividad de splash
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
