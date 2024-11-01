// TareaItem.java
package com.example.jmg_ascensores;

public class Ent_TareaItem {
    private String nombre;
    private String descripcion;
    private Integer cod_Asc;
    private Integer codTar;
    private Integer codMan;

    public Ent_TareaItem(String nombre, String descripcion, Integer x) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cod_Asc = x;
    }
    public Ent_TareaItem() {

    }

    public Integer getCodTar() {
        return codTar;
    }

    public void setCodTar(Integer codTar) {
        this.codTar = codTar;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCod_Asc() {
        return cod_Asc;
    }

    public void setCod_Asc(Integer cod_Asc) {
        this.cod_Asc = cod_Asc;
    }

    public Integer getCodMan() {
        return codMan;
    }

    public void setCodMan(Integer codMan) {
        this.codMan = codMan;
    }
}
