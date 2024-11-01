package com.example.jmg_ascensores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_Ascensor_Update {

    private static final String URL = "jdbc:postgresql://dpg-csfb3ue8ii6s739e581g-a.oregon-postgres.render.com:5432/db_jmg_tcnw";
    private static final String USER = "db_jmg_user";
    private static final String PASSWORD = "zALyb2rS9hQ49tB5ijVpYgiMvJajoiL1";

    public void actualizarAscen(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establecer la conexión
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // Crear la consulta SQL
            String sql = "UPDATE ascensores SET estado = ? WHERE codigo_ascensor = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "terminado");
            preparedStatement.setInt(2, id);

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