package com.example.jmg_ascensores;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Tarea extends RecyclerView.Adapter<Adapter_Tarea.TareaViewHolder> {
    private List<Ent_TareaItem> tareasList;
    private List<Integer> listk = new ArrayList<>();
    private List<Integer> listPos = new ArrayList<>();
    private Integer selectedId;
    // Constructor to initialize the list of tareas
    public Adapter_Tarea(List<Ent_TareaItem> tareasList) {
        this.tareasList = tareasList != null ? tareasList : new ArrayList<>(); // Evita NullPointerException
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        // Get the current task item
        Ent_TareaItem tarea = tareasList.get(position);

        // Set the task name and description to the respective TextViews
        holder.nombreTextView.setText(tarea.getNombre());

        // Set text color to black
        holder.nombreTextView.setTextColor(Color.BLACK);


        holder.cbTar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                listk.add(tarea.getCodTar());
            } else {
                listk.remove(tarea.getCodTar()); // Asegúrate de eliminar el objeto correctamente
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the total number of tasks
        return tareasList.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView;
        private static CheckBox cbTar;
        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the TextViews from the layout
            nombreTextView = itemView.findViewById(R.id.txtTar); // Cambia el ID aquí
            cbTar = itemView.findViewById(R.id.cbTar);

        }
    }
    public List<Integer> getListk(){
        return listk;
    }

}
