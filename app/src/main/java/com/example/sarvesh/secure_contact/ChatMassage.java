package com.example.sarvesh.secure_contact;

/**
 * Created by sarvesh on 13/1/18.
 */

public class ChatMassage {

    private String name,password;


    public ChatMassage(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {return name; }

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
