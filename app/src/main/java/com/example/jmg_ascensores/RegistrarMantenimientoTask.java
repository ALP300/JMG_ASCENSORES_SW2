package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        String fechaInicioStr = params[1];
        String fechaFinStr = params[2];
        int idTareaSeleccionada = Integer.parseInt(params[3]);  // Recibir el ID de la tarea seleccionada

        // Convertir las fechas de String a java.sql.Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date fechaInicioUtil = sdf.parse(fechaInicioStr);
            java.util.Date fechaFinUtil = sdf.parse(fechaFinStr);
            Date fechaInicio = new Date(fechaInicioUtil.getTime());
            Date fechaFin = new Date(fechaFinUtil.getTime());

            connection.setAutoCommit(false);  // Iniciar una transacción

            // Consulta SQL para insertar en la tabla Mantenimiento
            String queryMantenimiento = "INSERT INTO mantenimiento (codigo_cliente, fecha_inicio, fecha_fin) VALUES (?, ?, ?) RETURNING id";
            try (PreparedStatement stmtMantenimiento = connection.prepareStatement(queryMantenimiento)) {
                stmtMantenimiento.setString(1, codigoCliente);
                stmtMantenimiento.setDate(2, fechaInicio);
                stmtMantenimiento.setDate(3, fechaFin);

                ResultSet rs = stmtMantenimiento.executeQuery();
                int idMantenimiento = -1;
                if (rs.next()) {
                    idMantenimiento = rs.getInt(1);  // Obtener el ID generado
                }
                rs.close();

                // Insertar en la tabla intermedia mantenimiento_tareas
                if (idMantenimiento != -1) {
                    String queryMantenimientoTareas = "INSERT INTO mantenimiento_tareas (id_mantenimiento, id_tarea) VALUES (?, ?)";
                    try (PreparedStatement stmtMantenimientoTareas = connection.prepareStatement(queryMantenimientoTareas)) {
                        stmtMantenimientoTareas.setInt(1, idMantenimiento);
                        stmtMantenimientoTareas.setInt(2, idTareaSeleccionada);  // Aquí insertar la tarea seleccionada
                        stmtMantenimientoTareas.executeUpdate();
                    }
                }

                connection.commit();  // Confirmar la transacción
                return true;
            } catch (SQLException e) {
                connection.rollback();  // Revertir si ocurre un error
                e.printStackTrace();
                return false;
            }
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
            return false;
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
