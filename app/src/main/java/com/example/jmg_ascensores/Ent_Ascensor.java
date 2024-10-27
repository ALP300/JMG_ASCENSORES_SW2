package com.example.jmg_ascensores;

public class Ent_Ascensor {

    private String modelo; // Atributo
    private String marca;
    private Integer codAsc;

    // Getter para 'modelo'
    public String getModel() {
        return modelo; // Asegúrate de que 'modelo' esté correctamente declarado
    }

    // Setter para 'modelo'
    public void setModelo(String modelo) {
        this.modelo = modelo; // Asigna el valor al atributo 'modelo'
    }

    // Getter para 'marca'
    public String getMarca() {
        return marca;
    }

    // Setter para 'marca'
    public void setMarca(String marca) {
        this.marca = marca;
    }

    // Getter para 'codAsc'
    public Integer getCodAsc() {
        return codAsc;
    }

    // Setter para 'codAsc'
    public void setCodAsc(Integer codAsc) {
        this.codAsc = codAsc;
    }
}
