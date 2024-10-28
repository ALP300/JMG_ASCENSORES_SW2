package com.example.jmg_ascensores;

import java.util.Date;

public class Ent_Mantenimiento {

    private Integer cod_mant;
    private String cod_cli;
    private Date fecha_inic;
    private Date fecha_fin;
    private String estado;

    public Integer getCod_mant() {
        return cod_mant;
    }

    public void setCod_mant(Integer cod_mant) {
        this.cod_mant = cod_mant;
    }

    public String getCod_cli() {
        return cod_cli;
    }

    public void setCod_cli(String cod_cli) {
        this.cod_cli = cod_cli;
    }

    public Date getFecha_inic() {
        return fecha_inic;
    }

    public void setFecha_inic(Date fecha_inic) {
        this.fecha_inic = fecha_inic;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

