package com.example.jmg_ascensores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Mant extends RecyclerView.Adapter<Adapter_Mant.ViewHolder> {

    private List<Ent_Mantenimiento> mantensList;
    private List<Integer> listk = new ArrayList<>();

    public Adapter_Mant(List<Ent_Mantenimiento> mantensList) {
        this.mantensList = mantensList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_manten, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ent_Mantenimiento mantenimi = mantensList.get(position);

        holder.txtCod.setText(mantenimi.getCod_mant()+"");
        holder.txtFecI.setText(mantenimi.getFecha_inic()+"");
        holder.txtFecF.setText(mantenimi.getFecha_fin()+"");

        holder.cbCMan.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                listk.add(mantenimi.getCod_mant());
            } else {
                listk.remove(mantenimi.getCod_mant()); // Asegúrate de eliminar el objeto correctamente
            }
        });
    }

    @Override
    public int getItemCount() {
        return mantensList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         TextView txtCod,txtFecI,txtFecF;
         CheckBox cbCMan;
        public ViewHolder(View itemView) {
            super(itemView);
            txtCod = itemView.findViewById(R.id.txtCodMa);
            txtFecI = itemView.findViewById(R.id.txtFecIn);
            txtFecF = itemView.findViewById(R.id.txtFecFi);
            cbCMan = itemView.findViewById(R.id.cbMants);
        }
    }
    public List<Integer> getListk(){
        return listk;
    }
}
