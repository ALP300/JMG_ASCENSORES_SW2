package com.example.jmg_ascensores;

public class Ent_AscensorItem {
    private String marca;
    private String modelo;

    public Ent_AscensorItem(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }
}
