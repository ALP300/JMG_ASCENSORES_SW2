package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DB_Cliente extends AsyncTask<String, Void, Ent_Cliente> {
    private Connection connection;
    private Context context;
    private TextView txtNom;

    public DB_Cliente(Connection connection, Context context) {
        this.context = context;
        this.connection = connection;
    }

    @Override
    protected Ent_Cliente doInBackground(String... params) {
        String idCliente = params[0]; // El ID del clientex que deseas obtener
        Ent_Cliente clientex = null;

        try {
            // Establece la conexión a la base de datos

            // Realiza la consulta
            String query = "SELECT codigo,nombre_empresa,ubicacion,m.fecha_fin FROM clientes c INNER JOIN mantenimiento m ON m.codigo_cliente = c.codigo WHERE codigo = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idCliente);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Procesa el resultado
            if (resultSet.next()) {
                clientex = new Ent_Cliente();
                clientex.setCodigo(resultSet.getString("codigo"));
                clientex.setNombre_empresa(resultSet.getString("nombre_empresa"));
                clientex.setUbicacion(resultSet.getString("ubicacion"));
                clientex.setFec(resultSet.getDate("fecha_fin"));
            }

            // Cierra recursos
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientex;
    }

    @Override
    protected void onPostExecute(Ent_Cliente resultado) {
        super.onPostExecute(resultado);
            if (resultado != null) {
                // Maneja el resultado (actualiza UI, etc.)
            } else {
                Toast.makeText(context, "Datos no encontrados", Toast.LENGTH_SHORT).show();
            }
        }

    }
