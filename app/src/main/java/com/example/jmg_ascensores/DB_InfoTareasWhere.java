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

public class DB_InfoTareasWhere extends AsyncTask<String, Void, ArrayList<Ent_TareaItem>> {
    private Connection connection;
    private Context context;

    public DB_InfoTareasWhere(Connection connection, Context context) {
        this.context = context;
        this.connection = connection;
    }

    @Override
    protected ArrayList<Ent_TareaItem> doInBackground(String... params) {// El ID del clientex que deseas obtener
        ArrayList<Ent_TareaItem>  listTareas = new ArrayList<>();
        int code = Integer.parseInt(params[0]);
        try {
            String query = "SELECT descripcion, nombre, codigo_tarea, codigo_mantenimiento FROM tareas WHERE cod_ascensor = ? AND estado = ?";
            // Cambia a executeQuery para obtener resultados
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, code);
            statement.setString(2, "pendiente");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Crear un nuevo objeto Ent_Trab con los datos obtenidos
                Ent_TareaItem tarea = new Ent_TareaItem();
                tarea.setNombre(resultSet.getString("nombre"));
                tarea.setDescripcion(resultSet.getString("descripcion"));
                tarea.setCodTar(resultSet.getInt("codigo_tarea"));
                tarea.setCodMan(resultSet.getInt("codigo_mantenimiento"));
                listTareas.add(tarea);
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

        return listTareas;
    }

    @Override
    protected void onPostExecute(ArrayList<Ent_TareaItem> resultado) {
        super.onPostExecute(resultado);
        if (resultado != null) {
            // Maneja el resultado (actualiza UI, etc.)
        } else {
            Toast.makeText(context, "Datos no encontrados", Toast.LENGTH_SHORT).show();
        }
    }

}
