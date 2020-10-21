package com.example.yeipos.model;

public class CardItem {
    private String nombre, guardarFecha, cantidad;

    public CardItem(){}

    public CardItem(String nombre, String guardarFecha, String cantidad) {
        this.nombre = nombre;
        this.guardarFecha = guardarFecha;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGuardarFecha() {
        return guardarFecha;
    }

    public void setGuardarFecha(String guardarFecha) {
        this.guardarFecha = guardarFecha;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }


}
