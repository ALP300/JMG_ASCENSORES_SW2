package com.example.jmg_ascensores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter_BtnEmpresas extends BaseAdapter {

    private Context context;
    private List<Ent_Cliente> cliente;
    private String codTrab;
    public Adapter_BtnEmpresas(Context context, List<Ent_Cliente> cliente, String codTrab) {
        this.context = context;
        this.cliente = cliente;
        this.codTrab = codTrab;
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

        // Obtener el nombre de la empresa y darle formato
        String empresa = cliente.get(position).getNombre_empresa();
        empresa = "Empresa " + empresa.substring(0, 1).toUpperCase() + empresa.substring(1);

        // Configurar el TextView con el nombre de la empresa
        TextView empresaTextView = convertView.findViewById(R.id.empresaTextView);
        empresaTextView.setText(empresa);

        // Configurar el click en toda la tarjeta para abrir la actividad
        String finalEmpresa = empresa;
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, View_Trab_DetalleEmp.class);
            intent.putExtra("empresa", finalEmpresa);
            intent.putExtra("codMant", cliente.get(position).getCodMant()+"");
            intent.putExtra("codTrab", codTrab);
            intent.putExtra("ubicacion", cliente.get(position).getUbicacion());
            intent.putExtra("codCli", cliente.get(position).getCodigo());
            context.startActivity(intent);
        });

        return convertView;
    }
}