package com.example.jmg_ascensores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AscensorAdapter extends RecyclerView.Adapter<AscensorAdapter.ViewHolder> {

    private ArrayList<AscensorItem> ascensoresList;

    public AscensorAdapter(ArrayList<AscensorItem> ascensoresList) {
        this.ascensoresList = ascensoresList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ascensor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AscensorItem ascensor = ascensoresList.get(position);
        holder.marcaTextView.setText(ascensor.getMarca());
        holder.modeloTextView.setText(ascensor.getModelo());
    }

    @Override
    public int getItemCount() {
        return ascensoresList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView marcaTextView, modeloTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            marcaTextView = itemView.findViewById(R.id.marcaTextView);
            modeloTextView = itemView.findViewById(R.id.modeloTextView);
        }
    }
}
