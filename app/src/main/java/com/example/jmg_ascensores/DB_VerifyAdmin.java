package com.example.jmg_ascensores;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_VerifyAdmin extends AsyncTask<String, Void, String[]> {
    private Connection connection;
    private Context context; // Agregar contexto

    // Constructor que recibe la conexión y el contexto de MainActivity
    public DB_VerifyAdmin(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }

    @Override
    protected String[] doInBackground(String... params) {
        String code = params[0];
        String contrasena = params[1];
        String dtCli[] = new String[2];

        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                // Verificar en la tabla de clientes
                ResultSet resultSet = statement.executeQuery("SELECT *  FROM clientes WHERE codigo = '" + code + "' AND contrasena = '" + contrasena + "'");
                if (resultSet.next()) {
                    dtCli[0] = "cliente";
                    dtCli[1] = resultSet.getString("codigo");
                } else {
                    // Verificar en la tabla de trabajadores
                    resultSet = statement.executeQuery("SELECT *  FROM trabajadores WHERE codigo = '" + code + "' AND contrasena = '" + contrasena + "'");
                    if (resultSet.next()) {
                        dtCli[0] = "trabajador";
                        dtCli[1] = String.valueOf(resultSet.getInt("id"));
                    } else {
                        // Verificar en la tabla de administradores
                        resultSet = statement.executeQuery("SELECT * FROM administrador WHERE codigo = '" + code + "' AND contrasena = '" + contrasena + "'");
                        if (resultSet.next()) {
                            dtCli[0] = "admin";
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
    return dtCli;
    }

    @Override
    protected void onPostExecute(String[] dtCli) {
        if (dtCli != null) {
            Log.i("Database", "Inicio de sesión exitoso como: " );

            Intent intent;
            switch (dtCli[0]) {
                case "cliente":
                    intent = new Intent(context, Home_Cliente.class);
                    intent.putExtra("codCli", dtCli[1]);
                    break;
                case "trabajador":
                    intent = new Intent(context, Home_Trab.class);
                    intent.putExtra("codTrab", dtCli[1]);
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
