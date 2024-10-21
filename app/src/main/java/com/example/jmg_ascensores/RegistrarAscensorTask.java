package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrarAscensorTask extends AsyncTask<String, Void, Boolean> {
    private Connection connection;
    private Context context;

    public RegistrarAscensorTask(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String marca = params[0];
        String modelo = params[1];
        String codigoCliente = params[2]; // Captura el código del cliente
        boolean isInserted = false;

        if (connection != null) {
            Log.d("Database", "Connection established. Attempting to insert...");
            PreparedStatement stmt = null;
            try {
                // Preparamos la sentencia SQL para insertar los datos en la tabla 'ascensores'
                String query = "INSERT INTO ascensores (codigo_cliente, marca, modelo) VALUES (?, ?, ?)";
                stmt = connection.prepareStatement(query);

                // Asignamos los valores a los parámetros
                stmt.setString(1, codigoCliente);  // Código del cliente
                stmt.setString(2, marca);           // Marca del ascensor
                stmt.setString(3, modelo);          // Modelo del ascensor

                // Ejecutamos la sentencia
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    isInserted = true;  // Registro exitoso
                    Log.d("Database", "Insert successful, rows inserted: " + rowsInserted);
                } else {
                    Log.e("Database", "Insert failed, no rows inserted.");
                }

            } catch (SQLException e) {
                Log.e("Database", "Error al insertar: " + e.getMessage());
            } finally {
                // Cerrar el PreparedStatement de manera segura
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException e) {
                    Log.e("Database", "Error al cerrar el PreparedStatement: " + e.getMessage());
                }
            }
        } else {
            Log.e("Database", "Conexión es null");
        }

        return isInserted;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Toast.makeText(context, "Ascensor registrado exitosamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Error al registrar ascensor", Toast.LENGTH_LONG).show();
        }
    }

}

