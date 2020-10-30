package com.example.yeipos.model;

public class Comidas {
    String cantidad, categoria, guardarFecha, guardarHora, id, nombre, precio;

    public Comidas(){}
    public Comidas(String cantidad, String categoria, String guardarFecha, String guardarHora, String id, String nombre, String precio) {
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.guardarFecha = guardarFecha;
        this.guardarHora = guardarHora;
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getGuardarFecha() {
        return guardarFecha;
    }

    public void setGuardarFecha(String guardarFecha) {
        this.guardarFecha = guardarFecha;
    }

    public String getGuardarHora() {
        return guardarHora;
    }

    public void setGuardarHora(String guardarHora) {
        this.guardarHora = guardarHora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
