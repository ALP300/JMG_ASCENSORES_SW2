package com.example.jmg_ascensores;

import android.os.AsyncTask;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class VerifyTrabajadorTask extends AsyncTask<String, Void, Boolean> {
    private Connection connection;
    private AppCompatActivity activity; // Cambiado a AppCompatActivity

    public VerifyTrabajadorTask(Connection connection, AppCompatActivity activity) { // Cambiado a AppCompatActivity
        this.connection = connection;
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String code = params[0];
        String password = params[1];

        String query = "SELECT * FROM registro_trabajadores WHERE codigo = ? AND contrasena = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); // Devuelve true si hay resultados
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            // Redirigir al layout vista_trabajador
            Intent intent = new Intent(activity, VistaTrab.class); // Asegúrate de que este nombre de actividad sea correcto
            activity.startActivity(intent);
            activity.finish(); // Opcional: cerrar la actividad actual
        } else {
            Toast.makeText(activity, "Código o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
        }
    }
}
