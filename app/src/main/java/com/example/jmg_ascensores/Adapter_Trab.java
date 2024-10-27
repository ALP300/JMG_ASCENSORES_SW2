package com.example.jmg_ascensores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Trab extends RecyclerView.Adapter<Adapter_Trab.TareaViewHolder> {
    private ArrayList<Ent_TareaItem> tareasList;

    public Adapter_Trab(ArrayList<Ent_TareaItem> tareasList) {
        this.tareasList = tareasList;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actividad_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Ent_TareaItem tarea = tareasList.get(position);
        holder.nombreTextView.setText(tarea.getNombre());
        holder.descripcionTextView.setText(tarea.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return tareasList.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView, descripcionTextView;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombre_tarea_input);
            descripcionTextView = itemView.findViewById(R.id.descripcion_tarea_input);
        }
    }
}
