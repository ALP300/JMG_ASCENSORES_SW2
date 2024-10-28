package com.example.jmg_ascensores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter_Historial extends BaseAdapter {

    private Context context;
    private List<Ent_Mantenimiento> mant;

    public Adapter_Historial(Context context, List<Ent_Mantenimiento> mant) {
        this.context = context;
        this.mant = mant;
    }

    @Override
    public int getCount() {
        return mant.size();
    }

    @Override
    public Object getItem(int position) {
        return mant.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_historial, parent, false);
        }

        TextView txtCodM = convertView.findViewById(R.id.txtCM);
        TextView txtCodC = convertView.findViewById(R.id.txtCC);
        TextView txtFecI = convertView.findViewById(R.id.txtFI);
        TextView txtFecF = convertView.findViewById(R.id.txtFF);
        txtCodM.setText(mant.get(position).getCod_mant()+"");
        txtCodC.setText(mant.get(position).getCod_cli());
        txtFecI.setText(mant.get(position).getFecha_inic()+"");
        txtFecF.setText(mant.get(position).getFecha_fin()+"");

        return convertView;
    }
}