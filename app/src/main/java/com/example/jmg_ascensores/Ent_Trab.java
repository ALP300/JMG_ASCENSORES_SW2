package com.example.jmg_ascensores;

import java.util.Date;

public class Ent_Trab {

    private String codigo;
    private String nombre_empresa;
    private String password;
    private String ubicacion;
    private Date Fec;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFec() {
        return Fec;
    }

    public void setFec(Date fec) {
        Fec = fec;
    }
}

