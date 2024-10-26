package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrarTareasTask extends AsyncTask<String, Void, Boolean> {
    private Connection connection;
    private Context context;

    public RegistrarTareasTask(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String nombre = params[0];
        String descripcion = params[1];
        int codigoMantenimiento = Integer.parseInt(params[2]); // Captura el código del mantenimiento
        String estado = "pendiente"; // Asigna el estado por defecto, podría ser configurable
        boolean isInserted = false;

        if (connection != null) {
            Log.d("Database", "Connection established. Attempting to insert...");
            PreparedStatement stmt = null;
            try {
                // Preparamos la sentencia SQL para insertar los datos en la tabla 'tareas'
                String query = "INSERT INTO tareas (codigo_mantenimiento, nombre, descripcion, estado) VALUES (?, ?, ?, ?)";
                stmt = connection.prepareStatement(query);

                // Asignamos los valores a los parámetros
                stmt.setInt(1, codigoMantenimiento); // Código del mantenimiento
                stmt.setString(2, nombre);           // Nombre de la tarea
                stmt.setString(3, descripcion);      // Descripción de la tarea
                stmt.setString(4, estado);           // Estado de la tarea

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
            Toast.makeText(context, "Tarea registrada exitosamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Error al registrar tarea", Toast.LENGTH_LONG).show();
        }
    }
}
