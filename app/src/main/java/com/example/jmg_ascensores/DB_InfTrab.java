package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DB_InfTrab extends AsyncTask<String, Void, List<Ent_Trab>> {
    private Connection connection;
    private Context context;

    public DB_InfTrab(Connection connection, Context context) {
        this.context = context;
        this.connection = connection;
    }

    @Override
    protected List<Ent_Trab> doInBackground(String... params) {// El ID del clientex que deseas obtener
        List<Ent_Trab>  trabs = new ArrayList<>();

        try {
            String query = "SELECT codigo, nombre, apellido  FROM trabajadores";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(); // Cambia a executeQuery para obtener resultados

            while (resultSet.next()) {
                // Crear un nuevo objeto Ent_Trab con los datos obtenidos
                Ent_Trab trabajador = new Ent_Trab();
                trabajador.setCodigo(resultSet.getString("codigo"));
                trabajador.setNombre(resultSet.getString("nombre"));
                trabajador.setApellido(resultSet.getString("apellido"));
                trabs.add(trabajador);
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

        return trabs;
    }

    @Override
    protected void onPostExecute(List<Ent_Trab> resultado) {
        super.onPostExecute(resultado);
        if (resultado != null) {
            // Maneja el resultado (actualiza UI, etc.)
        } else {
            Toast.makeText(context, "Datos no encontrados", Toast.LENGTH_SHORT).show();
        }
    }

}
