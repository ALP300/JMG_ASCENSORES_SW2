package com.example.jmg_ascensores;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class VerifyTrabajadorRegistroTask extends AsyncTask<String, Void, Boolean> {
    private Connection connection;
    private Context context;

    public VerifyTrabajadorRegistroTask(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String codigo = params[0];
        String nombre = params[1];
        String apellido = params[2];
        String edadStr = params[3];
        String fechaContactoStr = params[4]; // Fecha en texto
        String password = params[5];
        boolean isInserted = false;

        if (connection != null) {
            try {
                int edad = Integer.parseInt(edadStr);
                // Convertir fecha de texto a java.sql.Date
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Ajusta el formato si es necesario
                java.util.Date utilDate = sdf.parse(fechaContactoStr); // Conversión a Date
                java.sql.Date fechaContrato = new java.sql.Date(utilDate.getTime()); // Conversión a java.sql.Date

                // Log de los datos a insertar
                Log.d("Database", "Insertando trabajador: " + codigo + ", " + nombre + ", " + apellido + ", " + edad + ", " + fechaContrato + ", " + password);

                // Preparamos la sentencia SQL para insertar los datos
                String query = "INSERT INTO registro_trabajadores (codigo, nombre, apellido, edad, fecha_contrato, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(query);

                // Asignamos los valores
                stmt.setString(1, codigo);
                stmt.setString(2, nombre);
                stmt.setString(3, apellido);
                stmt.setInt(4, edad);
                stmt.setDate(5, fechaContrato); // Guardamos la fecha como tipo DATE
                stmt.setString(6, password);

                // Ejecutamos la sentencia
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    isInserted = true; // Registro exitoso
                }

                // Cerrar recursos
                stmt.close();

            } catch (SQLException e) {
                Log.e("Database", "Error al insertar: " + e.getMessage());
            } catch (ParseException e) {
                Log.e("Database", "Error al parsear fecha: " + e.getMessage());
            }
        } else {
            Log.e("Database", "Conexión es null");
        }

        return isInserted;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Toast.makeText(context, "Trabajador registrado exitosamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Error al registrar trabajador", Toast.LENGTH_LONG).show();
        }
    }
}

