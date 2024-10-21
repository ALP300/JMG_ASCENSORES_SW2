package com.example.jmg_ascensores;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RegistrarMantenimientoTask extends AsyncTask<String, Void, Boolean> {
    private Connection connection;
    private Context context;

    public RegistrarMantenimientoTask(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }

     // Si hubo un error, retornar false


    @Override
    protected Boolean doInBackground(String... params) {
        if (params.length < 3) return false; // Validar que hay suficientes parámetros

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

            // Imprimir para depuración
            System.out.println("Código Cliente: " + codigoCliente);
            System.out.println("Fecha Inicio String: " + fechaInicioStr);
            System.out.println("Fecha Fin String: " + fechaFinStr);
            System.out.println("fechaInicio (java.sql.Date): " + fechaInicio);
            System.out.println("fechaFin (java.sql.Date): " + fechaFin);

            String queryMantenimiento = "INSERT INTO mantenimiento (codigo_cliente, fecha_inicio, fecha_fin) VALUES (?, ?, ?)";
            try (PreparedStatement stmtMantenimiento = connection.prepareStatement(queryMantenimiento)) {
                stmtMantenimiento.setString(1, codigoCliente);
                stmtMantenimiento.setDate(2, fechaInicio); // Debe ser java.sql.Date
                stmtMantenimiento.setDate(3, fechaFin);    // Debe ser java.sql.Date

                stmtMantenimiento.executeUpdate();
                return true; // Inserción exitosa
            }
        } catch (ParseException e) {
            e.printStackTrace(); // Manejo de error en formato de fecha
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de error en SQL
        }

        return false; // Si hubo un error, retornar false
    }


    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Toast.makeText(context, "Mantenimiento registrado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al registrar el mantenimiento", Toast.LENGTH_SHORT).show();
        }
    }

    // Método opcional para mostrar un Toast en caso de errores
    private void showToast(String message) {
        ((Activity) context).runOnUiThread(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }
}
