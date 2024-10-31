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

public class DB_InfoAscenWhere extends AsyncTask<String, Void, List<Ent_Cliente>> {
    private Connection connection;
    private Context context;

    public DB_InfoAscenWhere(Connection connection, Context context) {
        this.context = context;
        this.connection = connection;
    }

    @Override
    protected List<Ent_Cliente> doInBackground(String... params) {// El ID del clientex que deseas obtener
        List<Ent_Cliente>  clients = new ArrayList<>();
        int code = Integer.parseInt(params[0]);
        try {
            String query = "SELECT codigo, nombre_empresa, ubicacion FROM clientes WHERE id_trab = ?";
            // Cambia a executeQuery para obtener resultados
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, code);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Crear un nuevo objeto Ent_Trab con los datos obtenidos
                Ent_Cliente client = new Ent_Cliente();
                client.setCodigo(resultSet.getString("codigo"));
                client.setNombre_empresa(resultSet.getString("nombre_empresa"));
                client.setUbicacion(resultSet.getString("ubicacion"));
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
