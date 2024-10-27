package com.example.jmg_ascensores;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class DB_RegAscensor extends AppCompatActivity {

    private EditText marcaInput, modeloInput;
    private Button guardarButton;
    private Button registrarAscensores;
    private RecyclerView recyclerView;
    private Adapter_Ascensor adapter;
    private ArrayList<Ent_AscensorItem> ascensoresList;
    private Connection connection; // La conexión a la base de datos
    private String clienteCodigo; // Almacenará el código del cliente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ascensor); // Asegúrate de que este sea el layout correcto

        // Enlazamos los campos del layout
        marcaInput = findViewById(R.id.marca_input);
        modeloInput = findViewById(R.id.modelo_input);
        guardarButton = findViewById(R.id.guardar_button);
        registrarAscensores = findViewById(R.id.registrar_ascensores);
        recyclerView = findViewById(R.id.ascensores_list);

        // Recibir el código del cliente de la actividad anterior
        clienteCodigo = getIntent().getStringExtra("codigo_cliente");

        // Inicializamos la lista de ascensores
        ascensoresList = new ArrayList<>();
        adapter = new Adapter_Ascensor(ascensoresList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String marca = marcaInput.getText().toString().trim();
                String modelo = modeloInput.getText().toString().trim();

                if (marca.isEmpty() || modelo.isEmpty()) {
                    Toast.makeText(DB_RegAscensor.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Aquí deberías realizar la conexión con la base de datos y registrar el ascensor
                if (connection != null) {
                    // Registra el ascensor en la lista local
                    ascensoresList.add(new Ent_AscensorItem(marca, modelo));

                    // Actualiza el RecyclerView
                    adapter.notifyDataSetChanged();

                    // Limpiar los campos
                    marcaInput.setText("");
                    modeloInput.setText("");

                    Toast.makeText(DB_RegAscensor.this, "Ascensor registrado con éxito.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DB_RegAscensor.this, "Error: No se puede conectar a la base de datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registrarAscensores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connection != null && !ascensoresList.isEmpty()) {
                    // Pasa el código del cliente a la actividad Mantenimiento
                    Intent intent = new Intent(DB_RegAscensor.this, DB_Mantenimiento.class);
                    intent.putExtra("codigo_cliente", clienteCodigo);
                    startActivity(intent);

                    // Ejecuta la tarea para registrar los ascensores en la base de datos
                    new RegisterAscensoresTask().execute();
                } else {
                    Toast.makeText(DB_RegAscensor.this, "No hay ascensores para registrar o no hay conexión a la base de datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        // Conexión a la base de datos
        new ConnectToDatabaseTask() {
            @Override
            protected void onPostExecute(Connection conn) {
                connection = conn;
                if (connection == null) {
                    Toast.makeText(DB_RegAscensor.this, "Error al conectar con la base de datos.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    private class RegisterAscensoresTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                // Inicia una transacción
                connection.setAutoCommit(false);

                // Prepara la consulta de inserción
                String query = "INSERT INTO ascensores (marca, modelo, codigo_cliente) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);

                // Inserta cada ascensor de la lista
                for (Ent_AscensorItem ascensor : ascensoresList) {
                    statement.setString(1, ascensor.getMarca());
                    statement.setString(2, ascensor.getModelo());
                    statement.setString(3, clienteCodigo); // Usa el código del cliente recibido
                    statement.addBatch(); // Añade la operación al batch
                }

                // Ejecuta el batch de inserciones
                statement.executeBatch();

                // Confirma la transacción
                connection.commit();

                return true; // Indica éxito
            } catch (Exception e) {
                try {
                    // En caso de error, revertir los cambios
                    connection.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                e.printStackTrace();
                return false; // Indica fallo
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                // Vaciar la lista local de ascensores después de registrarlos
                ascensoresList.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(DB_RegAscensor.this, "Todos los ascensores han sido registrados.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DB_RegAscensor.this, "Error al registrar los ascensores.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Clase para conectar a la base de datos
    private class ConnectToDatabaseTask extends AsyncTask<Void, Void, Connection> {
        @Override
        protected Connection doInBackground(Void... voids) {
            Connection conn = null;
            try {
                // URL de conexión a PostgreSQL
                String url = "jdbc:postgresql://dpg-csfb3ue8ii6s739e581g-a.oregon-postgres.render.com:5432/db_jmg_tcnw";
                String user = "db_jmg_user";
                String password = "zALyb2rS9hQ49tB5ijVpYgiMvJajoiL1";
                conn = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return conn;
        }
    }
}
