package com.example.jmg_ascensores;

import android.content.Context;
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

        TextView txtCod = convertView.findViewById(R.id.txtCodT);
        TextView txtMar = convertView.findViewById(R.id.txtNom);
        TextView txtMod = convertView.findViewById(R.id.txtApe);
        txtCod.setText(trab.get(position).getCodigo());
        txtMar.setText(trab.get(position).getNombre());
        txtMod.setText(trab.get(position).getApellido());


        return convertView;
    }
}