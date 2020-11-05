package com.example.yeipos.model;

public class OrderCardViewHome {
    String mesa, time, total;

    public OrderCardViewHome(String mesa, String time, String total) {
        this.mesa = mesa;
        this.time = time;
        this.total = total;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
