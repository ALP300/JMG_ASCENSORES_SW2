package com.example.jmg_ascensores;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDatabaseTask extends AsyncTask<Void, Void, Connection> {
    // Configura tus constantes de conexión
    private static final String DB_URL = "jdbc:postgresql://dpg-cs391pjv2p9s738vcbi0-a.oregon-postgres.render.com:5432/jmg_bd";
    private static final String USERNAME = "jmg_bd_user";
    private static final String PASSWORD = "Z73pxTACjrn4uzswVY0I4msxc7yMzha8";

    @Override
    protected Connection doInBackground(Void... voids) {
        Connection connection = null;
        try {
            // Cargar el controlador JDBC para PostgreSQL
            Class.forName("org.postgresql.Driver");
            // Intentar establecer la conexión
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            Log.e("Database", "Error de SQL: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("Database", "Controlador no encontrado: " + e.getMessage());
        }
        return connection;
    }

    @Override
    protected void onPostExecute(Connection connection) {
        if (connection == null) {
            Log.e("Database", "Conexión fallida");
        } else {
            Log.i("Database", "Conexión exitosa");
            // Aquí puedes cerrar la conexión si no la necesitas más
            // try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
