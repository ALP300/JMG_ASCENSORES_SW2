package com.example.jmg_ascensores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Ascensor extends RecyclerView.Adapter<Adapter_Ascensor.ViewHolder> {

    private ArrayList<Ent_Ascensor> ascensoresList;

    public Adapter_Ascensor(ArrayList<Ent_Ascensor> ascensoresList) {
        this.ascensoresList = ascensoresList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ascensor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ent_Ascensor ascensor = ascensoresList.get(position);
        holder.txtMarc.setText(ascensor.getMarca());
        holder.txtMod.setText(ascensor.getModel());
    }

    @Override
    public int getItemCount() {
        return ascensoresList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         TextView txtMarc,txtMod;

        public ViewHolder(View itemView) {
            super(itemView);
            txtMarc = itemView.findViewById(R.id.txtMarc);
            txtMod = itemView.findViewById(R.id.txtMod);
        }
    }
}
