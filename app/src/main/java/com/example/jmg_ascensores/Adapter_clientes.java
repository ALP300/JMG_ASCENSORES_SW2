package com.example.jmg_ascensores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Adapter_clientes extends BaseAdapter {

    private Context context;
    private List<Ent_Cliente> cliente;
    private List<String> listID = new ArrayList<>();

    public Adapter_clientes(Context context, List<Ent_Cliente> cliente) {
        this.context = context;
        this.cliente = cliente;
    }

    @Override
    public int getCount() {
        return cliente.size();
    }

    public void setClientes(List<Ent_Cliente> nuevosClientes) {
        this.cliente.clear();
        this.cliente.addAll(nuevosClientes);
        notifyDataSetChanged(); // Notificar que los datos han cambiado
    }

    @Override
    public Object getItem(int position) {
        return cliente.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_clientes, parent, false);
        }

        TextView txtCod = convertView.findViewById(R.id.txtCodC);
        TextView txtEmp = convertView.findViewById(R.id.txtEmp);
        CheckBox cbCliente = convertView.findViewById(R.id.cbCliente);

        String selectedId = cliente.get(position).getCodigo();

        cbCliente.setOnClickListener(v -> {
            if (cbCliente.isChecked()) {
                listID.add(selectedId);
            } else {
                listID.remove(selectedId);
            }
        });

        txtCod.setText(cliente.get(position).getCodigo());
        txtEmp.setText(cliente.get(position).getNombre_empresa());

        return convertView;
    }
    public List<String> getClientId() {
        return listID;
    }

}