package com.example.yeipos.model;

public class ItemInventario {

    private String nombre;
    private String cantidad;
    private String precio;
    private String guardarFecha;
    private String guardarHora;
    private String categoria;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItemInventario(){}

    public ItemInventario(String nombre, String cantidad, String precio, String guardarFecha, String guardarHora, String categoria) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.guardarFecha = guardarFecha;
        this.guardarHora = guardarHora;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "ItemInventario{" +
                "nombre='" + nombre + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", precio='" + precio + '\'' +
                ", guardarFecha='" + guardarFecha + '\'' +
                ", guardarHora='" + guardarHora + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
