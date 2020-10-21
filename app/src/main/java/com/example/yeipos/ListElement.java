package com.example.yeipos;

public class ListElement {
    public String name;
    public String email;


    //--------------------------------Constructor-----------------------------
    public ListElement(String name, String email) {
        this.name = name;
        this.email = email;
    }
    //-----------------------------Getters and Setters-----------------------

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
