// TareaItem.java
package com.example.jmg_ascensores;

public class TareaItem {
    private String nombre;
    private String descripcion;

    public TareaItem(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
