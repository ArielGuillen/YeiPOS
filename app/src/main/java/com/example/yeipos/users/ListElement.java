package com.example.yeipos.users;

public class ListElement {
    public String id;
    public String name;
    public String email;
    public String password;


    //--------------------------------Constructors-----------------------------
    public ListElement(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public ListElement(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public ListElement( String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    //-----------------------------Getters and Setters-----------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
