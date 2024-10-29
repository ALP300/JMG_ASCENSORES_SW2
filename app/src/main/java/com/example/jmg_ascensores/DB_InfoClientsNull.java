package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DB_InfoClientsNull extends AsyncTask<String, Void, List<Ent_Cliente>> {
    private Connection connection;
    private Context context;

    public DB_InfoClientsNull(Connection connection, Context context) {
        this.context = context;
        this.connection = connection;
    }

    @Override
    protected List<Ent_Cliente> doInBackground(String... params) {// El ID del clientex que deseas obtener
        List<Ent_Cliente>  clients = new ArrayList<>();

        try {
            String query = "SELECT codigo, nombre_empresa FROM clientes WHERE id_trab IS NULL";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(); // Cambia a executeQuery para obtener resultados

            while (resultSet.next()) {
                // Crear un nuevo objeto Ent_Trab con los datos obtenidos
                Ent_Cliente client = new Ent_Cliente();
                client.setCodigo(resultSet.getString("codigo"));
                client.setNombre_empresa(resultSet.getString("nombre_empresa"));
                clients.add(client);
            }
            // Retorna la lista de trabajadores
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Retorna null en caso de error
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close(); // Cierra la conexión si es necesario
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clients;
    }

    @Override
    protected void onPostExecute(List<Ent_Cliente> resultado) {
        super.onPostExecute(resultado);
        if (resultado != null) {
            // Maneja el resultado (actualiza UI, etc.)
        } else {
            Toast.makeText(context, "Datos no encontrados", Toast.LENGTH_SHORT).show();
        }
    }

}
