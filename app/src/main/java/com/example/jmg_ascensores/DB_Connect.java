package com.example.jmg_ascensores;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connect extends AsyncTask<Void, Void, Connection> {
    // Configura tus constantes de conexión
    private static final String DB_URL = "jdbc:postgresql://dpg-csfb3ue8ii6s739e581g-a.oregon-postgres.render.com:5432/db_jmg_tcnw";
    private static final String USERNAME = "db_jmg_user";
    private static final String PASSWORD = "zALyb2rS9hQ49tB5ijVpYgiMvJajoiL1";

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
