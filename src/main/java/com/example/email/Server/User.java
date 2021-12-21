package com.example.email.Server;

public class User {
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private int idSend;
    private int idReceive;


    public int getIdSend() {
        return idSend;
    }

    public void setIdSend(Integer idSend) {
        this.idSend = idSend;
    }

    public int getIdReceive() {
        return idReceive;
    }

    public void setIdReceive(Integer idReceive) {
        this.idReceive = idReceive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
