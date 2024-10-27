package com.example.jmg_ascensores;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_VerifyAdmin extends AsyncTask<String, Void, String> {
    private Connection connection;
    private Context context; // Agregar contexto

    // Constructor que recibe la conexión y el contexto de MainActivity
    public DB_VerifyAdmin(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String code = params[0];
        String contrasena = params[1];
        String userType = null;

        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                // Verificar en la tabla de clientes
                ResultSet resultSet = statement.executeQuery("SELECT *  FROM clientes WHERE codigo = '" + code + "' AND password = '" + contrasena + "'");
                if (resultSet.next()) {
                    userType = "cliente";
                } else {
                    // Verificar en la tabla de trabajadores
                    resultSet = statement.executeQuery("SELECT *  FROM registro_trabajadores WHERE codigo = '" + code + "' AND contrasena = '" + contrasena + "'");
                    if (resultSet.next()) {
                        userType = "trabajador";
                    } else {
                        // Verificar en la tabla de administradores
                        resultSet = statement.executeQuery("SELECT * FROM administrador WHERE codigo = '" + code + "' AND contrasena = '" + contrasena + "'");
                        if (resultSet.next()) {
                            userType = "admin";
                        }
                    }
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
    return userType;
    }

    @Override
    protected void onPostExecute(String userType) {
        if (userType != null) {
            Log.i("Database", "Inicio de sesión exitoso como: " + userType);

            Intent intent;
            switch (userType) {
                case "cliente":
                    intent = new Intent(context, Home_Cliente.class);
                    break;
                case "trabajador":
                    intent = new Intent(context, Home_Trab.class);
                    break;
                case "admin":
                    intent = new Intent(context, Home_Admin.class);
                    break;
                default:
                    Log.e("Database", "Tipo de usuario desconocido");
                    return;
            }

            context.startActivity(intent);
        } else {
            Log.e("Database", "Código o contraseña incorrectos");
        }
    }
}
