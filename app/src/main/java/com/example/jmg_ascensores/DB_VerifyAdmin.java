package com.example.jmg_ascensores;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_VerifyAdmin extends AsyncTask<String, Void, String[]> {
    private Connection connection;
    private Context context;

    // Constructor que recibe la conexión y el contexto de MainActivity
    public DB_VerifyAdmin(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }

    @Override
    protected String[] doInBackground(String... params) {
        String code = params[0];
        String contrasena = params[1];
        String[] dtCli = new String[2];

        if (connection != null) {
            try {
                // Preparar consulta para la tabla de clientes
                String queryCliente = "SELECT codigo FROM clientes WHERE codigo = ? AND contrasena = ?";
                PreparedStatement statement = connection.prepareStatement(queryCliente);
                statement.setString(1, code);
                statement.setString(2, contrasena);
                ResultSet resultSet = statement.executeQuery();

                // Verificar en la tabla de clientes
                if (resultSet.next()) {
                    dtCli[0] = "cliente";
                    dtCli[1] = resultSet.getString("codigo");
                } else {
                    // Preparar y ejecutar consulta para la tabla de trabajadores
                    String queryTrabajador = "SELECT id FROM trabajadores WHERE codigo = ? AND contrasena = ?";
                    statement = connection.prepareStatement(queryTrabajador);
                    statement.setString(1, code);
                    statement.setString(2, contrasena);
                    resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        dtCli[0] = "trabajador";
                        dtCli[1] = String.valueOf(resultSet.getInt("id"));
                    } else {
                        // Preparar y ejecutar consulta para la tabla de administradores
                        String queryAdmin = "SELECT codigo FROM administrador WHERE codigo = ? AND contrasena = ?";
                        statement = connection.prepareStatement(queryAdmin);
                        statement.setString(1, code);
                        statement.setString(2, contrasena);
                        resultSet = statement.executeQuery();

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
        if (dtCli != null && dtCli[0] != null) {
            Log.i("Database", "Inicio de sesión exitoso como: " + dtCli[0]);

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
                    Toast.makeText(context, "Tipo de usuario desconocido.", Toast.LENGTH_SHORT).show();
                    return;
            }

            context.startActivity(intent);
        } else {
            Log.e("Database", "Código o contraseña incorrectos");
            Toast.makeText(context, "Código o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
        }
    }
}
