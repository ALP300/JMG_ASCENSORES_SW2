package com.example.jmg_ascensores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHelper {

    private static final String URL = "jdbc:postgresql://dpg-csfb3ue8ii6s739e581g-a.oregon-postgres.render.com:5432/db_jmg_tcnw";
    private static final String USER = "db_jmg_user";
    private static final String PASSWORD = "zALyb2rS9hQ49tB5ijVpYgiMvJajoiL1";

    public void actualizarRegistro(int id, String codigo) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establecer la conexión
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // Crear la consulta SQL
            String sql = "UPDATE clientes SET id_trab = ? WHERE codigo = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, codigo);

            // Ejecutar la actualización
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}