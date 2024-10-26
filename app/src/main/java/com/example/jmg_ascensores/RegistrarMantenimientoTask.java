package com.example.jmg_ascensores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RegistrarMantenimientoTask extends AsyncTask<String, Void, Integer> { // Cambiar el tipo de retorno a Integer
    private Connection connection;
    private Context context;

    public RegistrarMantenimientoTask(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }

    @Override
    protected Integer doInBackground(String... params) {
        if (params.length < 3) return null; // Validar que hay suficientes parámetros

        String codigoCliente = params[0];
        String fechaInicioStr = params[1];
        String fechaFinStr = params[2];

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Convertir cadenas de fecha a Date
            java.util.Date fechaInicioUtil = sdf.parse(fechaInicioStr);
            java.util.Date fechaFinUtil = sdf.parse(fechaFinStr);

            // Convertir a java.sql.Date
            java.sql.Date fechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
            java.sql.Date fechaFin = new java.sql.Date(fechaFinUtil.getTime());

            String queryMantenimiento = "INSERT INTO mantenimiento (codigo_cliente, fecha_inicio, fecha_fin) VALUES (?, ?, ?)";
            try (PreparedStatement stmtMantenimiento = connection.prepareStatement(queryMantenimiento, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtMantenimiento.setString(1, codigoCliente);
                stmtMantenimiento.setDate(2, fechaInicio);
                stmtMantenimiento.setDate(3, fechaFin);

                int affectedRows = stmtMantenimiento.executeUpdate();
                if (affectedRows == 0) {
                    return null; // Inserción fallida, no se generó un ID
                }

                // Obtener el código de mantenimiento generado
                java.sql.ResultSet generatedKeys = stmtMantenimiento.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Retorna el codigo_mantenimiento generado
                } else {
                    return null; // No se generó un ID
                }
            }
        } catch (ParseException e) {
            e.printStackTrace(); // Manejo de error en formato de fecha
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de error en SQL
        }

        return null; // Si hubo un error, retornar null
    }

    @Override
    protected void onPostExecute(Integer codigoMantenimiento) {
        if (codigoMantenimiento != null) {
            Toast.makeText(context, "Mantenimiento registrado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al registrar el mantenimiento", Toast.LENGTH_SHORT).show();
        }
    }

}
