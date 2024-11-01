package com.example.jmg_ascensores;

import java.util.Date;

public class Ent_Cliente {

    private String codigo;
    private String nombre_empresa;
    private Integer codTrab, codMant;
    private String password;
    private String ubicacion;
    private Date Fec;
    private double latitud; // Nueva propiedad
    private double longitud; // Nueva propiedad

    public String getCodigo() {
        return codigo;
    }

    public Integer getCodMant() {
        return codMant;
    }

    public void setCodMant(Integer codMant) {
        this.codMant = codMant;
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

    // Nuevos métodos para latitud
    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public Integer getCodTrab() {
        return codTrab;
    }

    public void setCodTrab(Integer codTrab) {
        this.codTrab = codTrab;
    }

    // Nuevos métodos para longitud
    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
