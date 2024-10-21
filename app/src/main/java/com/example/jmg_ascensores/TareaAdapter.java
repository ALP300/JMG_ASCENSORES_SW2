// TareaAdapter.java
package com.example.jmg_ascensores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {
    private ArrayList<TareaItem> tareasList;

    public TareaAdapter(ArrayList<TareaItem> tareasList) {
        this.tareasList = tareasList;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        TareaItem tarea = tareasList.get(position);
        holder.nombreTextView.setText(tarea.getNombre());
        holder.descripcionTextView.setText(tarea.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return tareasList.size();
    }

    static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView;
        TextView descripcionTextView;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(android.R.id.text1);
            descripcionTextView = itemView.findViewById(android.R.id.text2);
        }
    }
}
