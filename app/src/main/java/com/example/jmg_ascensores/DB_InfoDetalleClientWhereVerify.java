package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_InfoDetalleClientWhereVerify extends AsyncTask<String, Void, Boolean> {
    private Connection connection;
    private Context context;
    private static final String URL = "jdbc:postgresql://dpg-csfb3ue8ii6s739e581g-a.oregon-postgres.render.com:5432/db_jmg_tcnw";
    private static final String USER = "db_jmg_user";
    private static final String PASSWORD = "zALyb2rS9hQ49tB5ijVpYgiMvJajoiL1";
    private Boolean v=false;
    public DB_InfoDetalleClientWhereVerify(Context context) {
        this.context = context;

    }

    @Override
    protected Boolean doInBackground(String... params) {// El ID del clientex que deseas obtener
        int code = Integer.parseInt(params[0]);
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String query = "SELECT * FROM tareas WHERE estado = ? AND codigo_mantenimiento = ?";
            // Cambia a executeQuery para obtener resultados
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "falta");
            statement.setInt(2, code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                v=true;
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

        return v;
    }

    @Override
    protected void onPostExecute(Boolean resultado) {
        super.onPostExecute(resultado);
        if (resultado != null) {
            // Maneja el resultado (actualiza UI, etc.)
        } else {
            Toast.makeText(context, "Datos no encontrados", Toast.LENGTH_SHORT).show();
        }
    }

}
