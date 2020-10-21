package com.example.yeipos.modelos;

public class Usuario {
    String name;
    String correo;
    String passw;


    public Usuario() {
    }

    public Usuario(String name, String correo, String passw) {
        this.name = name;
        this.correo = correo;
        this.passw = passw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

}
