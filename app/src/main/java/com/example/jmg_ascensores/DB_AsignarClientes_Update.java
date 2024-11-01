package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_AsignarClientes_Update extends AsyncTask<String, Void, Boolean> {
    private Connection connection;
    private Context context;

    public DB_AsignarClientes_Update(Connection connection, Context context) {
        this.context = context;
        this.connection = connection;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        // El primer parámetro es el ID del trabajador
        Integer idTrabajador = Integer.parseInt(params[0]); // ID del trabajador a insertar
        String idCli = params[1];

        try {
            String updateQuery = "UPDATE clientes SET id_trab = ? WHERE codigo = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setInt(1, idTrabajador); // Establecer el ID del trabajador
                updateStatement.setString(2, idCli); // Establecer el ID del cliente
                updateStatement.executeUpdate(); // Ejecutar la actualización
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close(); // Cierra la conexión si es necesario
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onPostExecute(Boolean x) {
        if (x == true) {
            Toast.makeText(context, "Se han añadido correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Datos no encontrados", Toast.LENGTH_SHORT).show();
        }
    }
}