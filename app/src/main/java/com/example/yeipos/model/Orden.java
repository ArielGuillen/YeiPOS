package com.example.yeipos.model;

import java.util.ArrayList;

public class Orden {
    private String numMesa, total, date, time;
    private Boolean status;
    private ArrayList<Producto> ordenItems;
    private String keyID;

    public Orden(){ }

    public Orden(String numMesa, String total, String date, String time, Boolean status, ArrayList<Producto> ordenItems) {
        this.numMesa = numMesa;
        this.total = total;
        this.date = date;
        this.time = time;
        this.status = status;
        this.ordenItems = ordenItems;
    }

    public String getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(String numMesa) {
        this.numMesa = numMesa;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public ArrayList<Producto> getOrdenItems() {
        return ordenItems;
    }

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public void setOrdenItems(ArrayList<Producto> ordenItems) {
        this.ordenItems = ordenItems;
    }


}