package com.example.jmg_ascensores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://jmg_bd_user:Z73pxTACjrn4uzswVY0I4msxc7yMzha8@dpg-cs391pjv2p9s738vcbi0-a.oregon-postgres.render.com:5432/jmg_bd"; // Asegúrate de que la URL sea correcta
    private static final String USER = "jmg_bd_user"; // Usuario de la base de datos
    private static final String PASSWORD = "Z73pxTACjrn4uzswVY0I4msxc7yMzha8"; // Contraseña de la base de datos

    // Método para obtener la conexión
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver"); // Carga el controlador de PostgreSQL
            return DriverManager.getConnection(URL, USER, PASSWORD); // Devuelve la conexión
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Imprime cualquier excepción que ocurra
            return null;
        }
    }
}
