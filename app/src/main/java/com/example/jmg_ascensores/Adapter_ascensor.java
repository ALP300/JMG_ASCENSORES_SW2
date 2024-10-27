package com.example.jmg_ascensores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter_ascensor extends BaseAdapter {

    private Context context;
    private List<Ent_Ascensor> items;

    public Adapter_ascensor(Context context, List<Ent_Ascensor> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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

        TextView txtCod = convertView.findViewById(R.id.txtCodAsc);
        TextView txtMar = convertView.findViewById(R.id.txtMarc);
        TextView txtMod = convertView.findViewById(R.id.txtMod);
        txtCod.setText(items.get(position).getCodAsc()+"");
        txtMar.setText(items.get(position).getMarca());
        txtMod.setText(items.get(position).getModel());

        return convertView;
    }
}

