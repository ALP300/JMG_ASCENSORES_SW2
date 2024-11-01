package com.example.jmg_ascensores;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter_BtnAscensores extends BaseAdapter {

    private Context context;
    private List<Ent_Ascensor> ascensor;
    private String codMant,codTrab;

    public Adapter_BtnAscensores(Context context, List<Ent_Ascensor> entA, String codMant, String codTrab) {
        this.context = context;
        this.ascensor = entA;
        this.codMant = codMant;
        this.codTrab = codTrab;
    }


    @Override
    public int getCount() {
        return ascensor.size();
    }

    @Override
    public Object getItem(int position) {
        return ascensor.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_ascensor, parent, false);
        }

        // Obtener el nombre de la ascensorr y darle formato
        String Marc = ascensor.get(position).getMarca();
        String Mod = ascensor.get(position).getModel();
        Integer codAsc = ascensor.get(position).getCodAsc();

        // Configurar el TextView con el nombre de la ascensorr
        TextView txt5 = convertView.findViewById(R.id.textView5);
        TextView txt7 = convertView.findViewById(R.id.textView7);
        txt5.setText(Marc);
        txt7.setText(Mod);

        // Configurar el click en toda la tarjeta para abrir la actividad

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, View_Trab_ListaTareas.class);
            intent.putExtra("codAsc",codAsc+"" );
            intent.putExtra("codMant",codMant);
            intent.putExtra("codTrab",codTrab);
            context.startActivity(intent);
        });

        return convertView;
    }
}