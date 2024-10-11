package com.example.jmg_ascensores;

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

    @Override
    protected Boolean doInBackground(String... params) {
        String codigoCliente = params[0];
        String fechaInicioStr = params[1]; // Fecha de inicio en formato String
        String fechaFinStr = params[2];    // Fecha de fin en formato String
        String descripcion = params[3];

        // Convertir las fechas de String a java.sql.Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date fechaInicioUtil = sdf.parse(fechaInicioStr);
            java.util.Date fechaFinUtil = sdf.parse(fechaFinStr);
            Date fechaInicio = new Date(fechaInicioUtil.getTime());
            Date fechaFin = new Date(fechaFinUtil.getTime());

            // Consulta SQL para insertar los datos en la tabla Mantenimientos
            String query = "INSERT INTO mantenimiento (codigo_cliente, fecha_inicio, fecha_fin, descripcion) VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                // Establecer los parámetros de la consulta
                stmt.setString(1, codigoCliente);  // Código del cliente
                stmt.setDate(2, fechaInicio);       // Fecha de inicio como java.sql.Date
                stmt.setDate(3, fechaFin);          // Fecha de fin como java.sql.Date
                stmt.setString(4, descripcion);     // Descripción

                // Ejecutar la consulta
                stmt.executeUpdate();
                return true; // Si el registro fue exitoso
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Si ocurrió un error en la conversión de fecha
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Si ocurrió un error al ejecutar la consulta
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Toast.makeText(context, "Mantenimiento registrado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al registrar el mantenimiento", Toast.LENGTH_SHORT).show();
        }
    }
}
