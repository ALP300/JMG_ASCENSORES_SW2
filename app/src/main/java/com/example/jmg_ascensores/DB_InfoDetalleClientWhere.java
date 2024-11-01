package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DB_InfoDetalleClientWhere extends AsyncTask<String, Void, List<Ent_Cliente>> {
    private Connection connection;
    private Context context;
    private static final String URL = "jdbc:postgresql://dpg-csfb3ue8ii6s739e581g-a.oregon-postgres.render.com:5432/db_jmg_tcnw";
    private static final String USER = "db_jmg_user";
    private static final String PASSWORD = "zALyb2rS9hQ49tB5ijVpYgiMvJajoiL1";

    public DB_InfoDetalleClientWhere( Context context) {
        this.context = context;

    }

    @Override
    protected List<Ent_Cliente> doInBackground(String... params) {// El ID del clientex que deseas obtener
        List<Ent_Cliente>  clients = new ArrayList<>();
        int code = Integer.parseInt(params[0]);
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String query = "SELECT codigo, nombre_empresa, ubicacion,id_trab, codigo_mantenimiento FROM clientes  JOIN mantenimiento ON clientes.codigo = mantenimiento.codigo_cliente WHERE mantenimiento.estado = ? AND id_trab = ?";
            // Cambia a executeQuery para obtener resultados
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "pendiente");
            statement.setInt(2, code);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Crear un nuevo objeto Ent_Trab con los datos obtenidos
                Ent_Cliente client = new Ent_Cliente();
                client.setCodigo(resultSet.getString("codigo"));
                client.setNombre_empresa(resultSet.getString("nombre_empresa"));
                client.setUbicacion(resultSet.getString("ubicacion"));
                client.setCodTrab(resultSet.getInt("id_trab"));
                client.setCodMant(resultSet.getInt("codigo_mantenimiento"));
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
