package com.example.jmg_ascensores;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VerifyClienteTask extends AsyncTask<String, Void, String> { // Cambiar tipo de retorno a String
    private Connection connection;
    private Context context;

    // Constructor que recibe la conexión y el contexto de MainActivity
    public VerifyClienteTask(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String code = params[0];
        String password = params[1];
        String nombreEmpresa = null;
        String codCli = null; // Añadir codCli

        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT codigo, nombre_empresa FROM clientes WHERE codigo = '" + code + "' AND password = '" + password + "'"
                );

                if (resultSet.next()) {
                    nombreEmpresa = resultSet.getString("nombre_empresa"); // Obtener nombre_empresa
                    codCli = resultSet.getString("codigo"); // Obtener codCli
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

        // Retornar codCli y nombre_empresa
        return codCli + ";" + nombreEmpresa; // Retornar ambos valores separados por un delimitador
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            String[] values = result.split(";"); // Dividir el resultado
            String codCli = values[0]; // Primer valor es codCli
            String nombreEmpresa = values[1]; // Segundo valor es nombre_empresa

            Log.i("Database", "Inicio de sesión exitoso");

            Intent intent = new Intent(context, VistaCliente.class);
            intent.putExtra("codCli", codCli); // Pasar codCli a VistaCliente
            intent.putExtra("nombre_empresa", nombreEmpresa); // Pasar nombre de la empresa a VistaCliente
            context.startActivity(intent);
        } else {
            Log.e("Database", "Código o contraseña incorrectos");
        }
    }
}
