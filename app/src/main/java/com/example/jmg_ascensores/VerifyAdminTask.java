package com.example.jmg_ascensores;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VerifyAdminTask extends AsyncTask<String, Void, Boolean> {
    private Connection connection;
    private Context context; // Agregar contexto

    // Constructor que recibe la conexión y el contexto de MainActivity
    public VerifyAdminTask(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String code = params[0];
        String password = params[1];
        boolean isValid = false;

        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM administrador WHERE codigo = '" + code + "' AND contrasena = '" + password + "'");

                if (resultSet.next()) {
                    isValid = true;
                }

                // Cerrar recursos
                resultSet.close();
                statement.close();

            } catch (SQLException e) {
                Log.e("Database", "Error en la consulta: " + e.getMessage());
            }
        } else {
            Log.e("Database", "Conexión es null");
        }

        return isValid;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Log.i("Database", "Inicio de sesión exitoso");
            // Redirigir a la vista de administrador si el inicio es exitoso
            Intent intent = new Intent(context, AdminActivity.class); // Cambiar a la nueva actividad
            context.startActivity(intent);
        } else {
            Log.e("Database", "Código o contraseña incorrectos");
        }
    }
}
