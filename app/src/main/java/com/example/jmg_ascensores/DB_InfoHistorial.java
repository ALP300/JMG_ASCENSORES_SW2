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

public class DB_InfoHistorial extends AsyncTask<String, Void, List<Ent_Mantenimiento>> {
    private Connection connection;
    private Context context;

    public DB_InfoHistorial(Connection connection, Context context) {
        this.context = context;
        this.connection = connection;
    }

    @Override
    protected List<Ent_Mantenimiento> doInBackground(String... params) {// El ID del clientex que deseas obtener
        List<Ent_Mantenimiento>  listManten = new ArrayList<>();

        try {
            String query = "SELECT codigo_mantenimiento, codigo_cliente, fecha_inicio, fecha_fin  FROM mantenimiento WHERE estado = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "Terminado");
            ResultSet resultSet = statement.executeQuery(); // Cambia a executeQuery para obtener resultados

            while (resultSet.next()) {
                // Crear un nuevo objeto Ent_Trab con los datos obtenidos
                Ent_Mantenimiento mantenimiento = new Ent_Mantenimiento();
                mantenimiento.setCod_mant(resultSet.getInt("codigo_mantenimiento"));
                mantenimiento.setCod_cli(resultSet.getString("codigo_cliente"));
                mantenimiento.setFecha_inic(resultSet.getDate("fecha_inicio"));
                mantenimiento.setFecha_fin(resultSet.getDate("fecha_fin"));
                listManten.add(mantenimiento);
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

        return listManten;
    }

    @Override
    protected void onPostExecute(List<Ent_Mantenimiento> resultado) {
        super.onPostExecute(resultado);
        if (resultado != null) {
            // Maneja el resultado (actualiza UI, etc.)
        } else {
            Toast.makeText(context, "Datos no encontrados", Toast.LENGTH_SHORT).show();
        }
    }

}
