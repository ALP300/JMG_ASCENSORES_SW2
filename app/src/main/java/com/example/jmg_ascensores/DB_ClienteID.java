package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_ClienteID extends AsyncTask<String, Void, Ent_Cliente> {
    private Connection connection;
    private Context context;

    public DB_ClienteID(Connection connection, Context context) {
        this.context = context;
        this.connection = connection;
    }

    @Override
    protected Ent_Cliente doInBackground(String... params) {
        String idCliente = params[0]; // El ID del cliente que deseas obtener
        Ent_Cliente clientex = null;

        try {
            // Realiza la consulta
            String query = "SELECT codigo, nombre_empresa, ubicacion, m.fecha_fin FROM clientes c INNER JOIN mantenimiento m ON m.codigo_cliente = c.codigo WHERE codigo = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idCliente);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Procesa el resultado
            if (resultSet.next()) {
                clientex = new Ent_Cliente();
                clientex.setCodigo(resultSet.getString("codigo"));
                clientex.setNombre_empresa(resultSet.getString("nombre_empresa"));
                clientex.setUbicacion(resultSet.getString("ubicacion"));

                // Procesar la ubicación
                String[] coords = resultSet.getString("ubicacion").split(","); // Divide la cadena
                if (coords.length == 2) {
                    try {
                        clientex.setLatitud(Double.parseDouble(coords[0].trim())); // Asigna la latitud
                        clientex.setLongitud(Double.parseDouble(coords[1].trim())); // Asigna la longitud
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        // Manejo de errores en caso de que la conversión falle
                    }
                } else {
                    // Manejo de caso en que la ubicación no tiene el formato esperado
                    clientex.setLatitud(0.0); // Valor por defecto
                    clientex.setLongitud(0.0); // Valor por defecto
                }

                clientex.setFec(resultSet.getDate("fecha_fin"));
            }

            // Cierra recursos
            resultSet.close();
            preparedStatement.close();
            // No cerramos la conexión aquí, esto se debe manejar fuera de esta clase.
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientex;
    }

    @Override
    protected void onPostExecute(Ent_Cliente resultado) {
        super.onPostExecute(resultado);
        if (resultado != null) {
            // Actualiza la UI o maneja el resultado aquí
            if (context instanceof View_ProxManten) {
                ((View_ProxManten) context).mostrarCliente(resultado);
            }
        } else {
            Toast.makeText(context, "Datos no encontrados", Toast.LENGTH_SHORT).show();
        }
    }
}
