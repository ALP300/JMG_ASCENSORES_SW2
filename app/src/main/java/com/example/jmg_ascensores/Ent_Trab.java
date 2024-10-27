package com.example.jmg_ascensores;

import java.util.Date;

public class Ent_Trab {

    private String codigo;
    private String nombre;
    private String apellido;
    private int edad;
    private Date fecha_contrato;
    private Date contrasena;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Date getFecha_contrato() {
        return fecha_contrato;
    }

    public void setFecha_contrato(Date fecha_contrato) {
        this.fecha_contrato = fecha_contrato;
    }

    public Date getContrasena() {
        return contrasena;
    }

    public void setContrasena(Date contrasena) {
        this.contrasena = contrasena;
    }
}

