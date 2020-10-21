package com.example.yeipos.model;

public class OrderCardViewHome {
    String mesa, time;

    public OrderCardViewHome(String mesa, String time) {
        this.mesa = mesa;
        this.time = time;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
