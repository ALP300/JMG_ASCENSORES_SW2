package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

        // Primero verificamos si el código ya existe
        if (codigoExistente(codigo)) {
            return false; // Si el código existe, retornamos false
        }

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
                stmt.setObject(5, null); // Puedes ajustar este valor según tu lógica
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

    // Método para verificar si el código ya existe en la base de datos
    private boolean codigoExistente(String codigo) {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM clientes WHERE codigo = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0; // Si hay más de 0, el código ya existe
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            Log.e("Database", "Error al verificar código: " + e.getMessage());
        }
        return exists;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Toast.makeText(context, "Cliente registrado exitosamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "El código ya existe en la base de datos", Toast.LENGTH_LONG).show();
        }
    }
}
