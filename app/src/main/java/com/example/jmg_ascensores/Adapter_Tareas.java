package com.example.jmg_ascensores;

import android.graphics.Color; // Import Color class
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Adapter_Tareas extends RecyclerView.Adapter<Adapter_Tareas.TareaViewHolder> {
    private ArrayList<Ent_TareaItem> tareasList;

    // Constructor to initialize the list of tareas
    public Adapter_Tareas(ArrayList<Ent_TareaItem> tareasList) {
        this.tareasList = tareasList != null ? tareasList : new ArrayList<>(); // Evita NullPointerException
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_tareas, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        // Get the current task item
        Ent_TareaItem tarea = tareasList.get(position);

        // Set the task name and description to the respective TextViews
        holder.nombreTextView.setText(tarea.getNombre());
        holder.descripcionTextView.setText(tarea.getDescripcion());

        // Set text color to black
        holder.nombreTextView.setTextColor(Color.BLACK);
        holder.descripcionTextView.setTextColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        // Return the total number of tasks
        return tareasList.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView, descripcionTextView;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the TextViews from the layout
            nombreTextView = itemView.findViewById(R.id.marcAscAda); // Cambia el ID aquí
            descripcionTextView = itemView.findViewById(R.id.modAscAda); // Cambia el ID aquí
        }
    }
}
