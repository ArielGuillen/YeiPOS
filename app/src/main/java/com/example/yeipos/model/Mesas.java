package com.example.yeipos.model;

public class Mesas {
    private String numMesa;

    public Mesas(){}
    public Mesas(String numMesa) {
        this.numMesa = numMesa;
    }

    public String getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(String numMesa) {
        this.numMesa = numMesa;
    }

    @Override
    public String toString() {
        return numMesa;
    }
}
