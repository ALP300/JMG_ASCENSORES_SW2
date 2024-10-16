package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DB_AscCli extends AsyncTask<String, Void, List<EntAsc>> {
    private Connection connection;
    private Context context;


    public DB_AscCli(Connection connection, Context context) {
        this.context = context;
        this.connection = connection;
    }

    @Override
    protected List<EntAsc> doInBackground(String... params) {
        String idCliente = params[0]; // El ID del clientex que deseas obtener
        List<EntAsc> ascensores = new ArrayList<>();

        try {
            // Establece la conexión a la base de datos

            // Realiza la consulta
            String query = "SELECT marca,modelo,codigo_ascensor FROM ascensores WHERE codigo_cliente = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idCliente);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Procesa el resultado
            while (resultSet.next()) {
                EntAsc ascensor = new EntAsc();
                ascensor.setMarca(resultSet.getString("marca"));
                ascensor.setModel(resultSet.getString("modelo"));
                ascensor.setCodAsc(resultSet.getInt("codigo_ascensor"));
                ascensores.add(ascensor);
            }

            // Cierra recursos
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ascensores;
    }

    @Override
    protected void onPostExecute(List<EntAsc> resultado) {
        super.onPostExecute(resultado);
            if (resultado != null) {
                // Maneja el resultado (actualiza UI, etc.)
            } else {
                Toast.makeText(context, "ASc_Cli no encontrados", Toast.LENGTH_SHORT).show();
            }
        }

    }
