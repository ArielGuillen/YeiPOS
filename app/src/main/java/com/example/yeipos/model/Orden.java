package com.example.yeipos.model;

import java.util.List;

public class Orden {
    private String numMesa, cuentaAcumulada, date, time;
    private Boolean status;
    private List<Producto> productos;

    public Orden(){ }

    public Orden(String numMesa, String cuentaAcumulada, String date, String time, Boolean status){
        this.numMesa = numMesa;
        this.cuentaAcumulada = cuentaAcumulada;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(String numMesa) {
        this.numMesa = numMesa;
    }

    public String getCuentaAcumulada() {
        return cuentaAcumulada;
    }

    public void setCuentaAcumulada(String cuentaAcumulada) {
        this.cuentaAcumulada = cuentaAcumulada;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}