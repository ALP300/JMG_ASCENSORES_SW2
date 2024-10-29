package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_RegistrarCliente extends AsyncTask<String, Void, Boolean> {
    private Connection connection;
    private Context context;

    public DB_RegistrarCliente(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String codigo = params[0];
        String nombreEmpresa = params[1];
        String password = params[2];
        String ubicacion = params[3];
        boolean isInserted = false;

        if (connection != null) {
            try {
                // Preparamos la sentencia SQL para insertar los datos del cliente
                String query = "INSERT INTO clientes (codigo, nombre_empresa, contrasena, ubicacion, id_trab) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(query);

                // Asignamos los valores a los parámetros
                stmt.setString(1, codigo);
                stmt.setString(2, nombreEmpresa);
                stmt.setString(3, password);
                stmt.setString(4, ubicacion);
                stmt.setObject(5, null);
                // Ejecutamos la sentencia
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    isInserted = true;  // Si se insertaron filas, el registro fue exitoso
                }

                // Cerrar recursos
                stmt.close();

            } catch (SQLException e) {
                Log.e("Database", "Error al insertar: " + e.getMessage());
            }
        } else {
            Log.e("Database", "Conexión es null");
        }

        return isInserted;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Toast.makeText(context, "Cliente registrado exitosamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Error al registrar cliente", Toast.LENGTH_LONG).show();
        }
    }
}
