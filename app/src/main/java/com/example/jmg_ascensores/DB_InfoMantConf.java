package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DB_InfoMantConf extends AsyncTask<String, Void, ArrayList<Ent_Mantenimiento>> {
    private static final String TAG = "DB_AscCli"; // Para los logs
    private Connection connection;
    private Context context;

    public DB_InfoMantConf(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }

    @Override
    protected ArrayList<Ent_Mantenimiento> doInBackground(String... params) {
        String idCliente = params[0]; // El ID del cliente que deseas obtener
        ArrayList<Ent_Mantenimiento> mantenimiento = new ArrayList<>();
        Log.i(TAG, "Iniciando la consulta para el cliente: " + idCliente);

        try {
            // Realiza la consulta
            String query = "SELECT codigo_mantenimiento, fecha_inicio, fecha_fin FROM mantenimiento WHERE codigo_cliente = ? AND estado = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idCliente);
            preparedStatement.setString(2, "confirmar");
            ResultSet resultSet = preparedStatement.executeQuery();

            // Procesa el resultado
            while (resultSet.next()) {
                Ent_Mantenimiento mantConf = new Ent_Mantenimiento();
                mantConf.setCod_mant(resultSet.getInt("codigo_mantenimiento"));
                mantConf.setFecha_inic(resultSet.getDate("fecha_inicio"));
                mantConf.setFecha_fin(resultSet.getDate("fecha_fin"));
                mantenimiento.add(mantConf);
                Log.d("Database", "Ascensor encontrado: " + mantConf.getCod_mant());
            }

            // Cierra recursos
            resultSet.close();
            preparedStatement.close();
            Log.i(TAG, "Consulta finalizada, total de mantenimiento encontrados: " + mantenimiento.size());

        } catch (SQLException e) {
            Log.e(TAG, "Error en la consulta: " + e.getMessage());
            e.printStackTrace();
        }
        return mantenimiento; // Retornar la lista de mantenimiento
    }

    @Override
    protected void onPostExecute(ArrayList<Ent_Mantenimiento> resultado) {
        super.onPostExecute(resultado);
        if (resultado != null && !resultado.isEmpty()) {
            Log.i(TAG, "Ascensores recibidos en onPostExecute: " + resultado.size());
        } else {
            Toast.makeText(context, "Ascensores no encontrados", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "No se encontraron ascensores.");
        }
    }
}
