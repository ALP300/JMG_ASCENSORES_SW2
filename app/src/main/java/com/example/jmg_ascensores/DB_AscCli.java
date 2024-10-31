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
import java.util.List;

public class DB_AscCli extends AsyncTask<String, Void, ArrayList<Ent_Ascensor>> {
    private static final String TAG = "DB_AscCli"; // Para los logs
    private Connection connection;
    private Context context;

    public DB_AscCli(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }

    @Override
    protected ArrayList<Ent_Ascensor> doInBackground(String... params) {
        String idCliente = params[0]; // El ID del cliente que deseas obtener
        ArrayList<Ent_Ascensor> ascensores = new ArrayList<>();
        Log.i(TAG, "Iniciando la consulta para el cliente: " + idCliente);

        try {
            // Realiza la consulta
            String query = "SELECT marca, modelo, codigo_ascensor FROM ascensores WHERE codigo_cliente = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idCliente);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Procesa el resultado
            while (resultSet.next()) {
                Ent_Ascensor ascensor = new Ent_Ascensor();
                ascensor.setMarca(resultSet.getString("marca"));
                ascensor.setModelo(resultSet.getString("modelo"));
                ascensor.setCodAsc(resultSet.getInt("codigo_ascensor"));
                ascensores.add(ascensor);
                Log.d("Database", "Ascensor encontrado: " + ascensor.getMarca() + ", " + ascensor.getModel());
            }

            // Cierra recursos
            resultSet.close();
            preparedStatement.close();
            Log.i(TAG, "Consulta finalizada, total de ascensores encontrados: " + ascensores.size());

        } catch (SQLException e) {
            Log.e(TAG, "Error en la consulta: " + e.getMessage());
            e.printStackTrace();
        }
        return ascensores; // Retornar la lista de ascensores
    }

    @Override
    protected void onPostExecute(ArrayList<Ent_Ascensor> resultado) {
        super.onPostExecute(resultado);
        if (resultado != null && !resultado.isEmpty()) {
            Log.i(TAG, "Ascensores recibidos en onPostExecute: " + resultado.size());
        } else {
            Toast.makeText(context, "Ascensores no encontrados", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "No se encontraron ascensores.");
        }
    }
}
