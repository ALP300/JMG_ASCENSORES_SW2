package com.example.jmg_ascensores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter_trab extends BaseAdapter {

    private Context context;
    private List<Ent_Trab> trab;

    public Adapter_trab(Context context, List<Ent_Trab> trab) {
        this.context = context;
        this.trab = trab;
    }

    @Override
    public int getCount() {
        return trab.size();
    }

    @Override
    public Object getItem(int position) {
        return trab.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_trab, parent, false);
        }

        // Asigna los TextView de acuerdo con los IDs en el nuevo diseño XML
        TextView txtCod = convertView.findViewById(R.id.txtCodT);
        TextView txtNom = convertView.findViewById(R.id.txtNom);
        TextView txtApe = convertView.findViewById(R.id.txtApe);

        // Configuración del texto de cada TextView
        txtCod.setText(trab.get(position).getCodigo());
        txtNom.setText(trab.get(position).getNombre());
        txtApe.setText(trab.get(position).getApellido());

        // Evento al hacer clic en el elemento para abrir una nueva actividad
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, View_Adm_AsignarCliente.class);
            intent.putExtra("trab_id", trab.get(position).getId() + ""); // Pasar el ID como cadena
            context.startActivity(intent);
        });

        return convertView;
    }
}
