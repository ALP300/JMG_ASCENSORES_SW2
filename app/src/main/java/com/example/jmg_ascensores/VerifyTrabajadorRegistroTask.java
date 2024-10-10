package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VerifyTrabajadorRegistroTask extends AsyncTask<String, Void, Boolean> {
    private Connection connection;
    private Context context;

    public VerifyTrabajadorRegistroTask(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String codigo = params[0];
        String nombre = params[1];
        String apellido = params[2];
        String edadStr = params[3];
        String fechaContactoStr = params[4];
        String password = params[5];
        boolean isInserted = false;

        if (connection != null) {
            try {
                int edad= Integer.parseInt(edadStr);
                java.sql.Date fechaContrato = java.sql.Date.valueOf(fechaContactoStr);
                // Preparamos la sentencia SQL para insertar los datos en la tabla 'registro_trabajadores'
                String query = "INSERT INTO registro_trabajadores (codigo, nombre, apellido, edad, fecha_contrato, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(query);

                // Asignamos los valores a los parámetros
                stmt.setString(1, codigo);           // Código del trabajador
                stmt.setString(2, nombre);           // Nombre del trabajador
                stmt.setString(3, apellido);         // Apellido del trabajador
                stmt.setInt(4, edad);             // Edad del trabajador
                stmt.setDate(5, fechaContrato);    // Fecha de contacto
                stmt.setString(6, password);         // Contraseña

                // Ejecutamos la sentencia
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    isInserted = true;  // Si se insertaron filas, significa que el registro fue exitoso
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
            Toast.makeText(context, "Trabajador registrado exitosamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Error al registrar trabajador", Toast.LENGTH_LONG).show();
        }
    }
}
