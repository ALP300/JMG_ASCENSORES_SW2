package com.example.jmg_ascensores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

public class Adapter_BtnEmpresas extends BaseAdapter {

    private Context context;
    private List<Ent_Cliente> cliente;

    public Adapter_BtnEmpresas(Context context, List<Ent_Cliente> cliente) {
        this.context = context;
        this.cliente = cliente;
    }

    @Override
    public int getCount() {
        return cliente.size();
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
            convertView = inflater.inflate(R.layout.adapter_empresas, parent, false);
        }
        String Empresa = cliente.get(position).getNombre_empresa();
        Empresa = "Empresa "+Empresa.substring(0, 1).toUpperCase() + Empresa.substring(1);
        Button btnEmp = convertView.findViewById(R.id.btnEmpresas);
        btnEmp.setText(Empresa);
        String finalEmpresa = Empresa;
        btnEmp.setOnClickListener(v -> {
            Intent intent = new Intent(context, View_TrabajadorTarea.class);
            intent.putExtra("empresa", finalEmpresa);
            intent.putExtra("ubicacion", cliente.get(position).getUbicacion());
            context.startActivity(intent);
        });

        return convertView;
    }
}