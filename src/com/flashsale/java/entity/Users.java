package com.flashsale.java.entity;

public class Users {
    private int id;
    private String email;
    private String username;

    public Users() {
    }

    public Users(int id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return String.format("| %-5d | %-30s | %-20s |", id, email, username);
    }

    public static String getHeader() {
        return String.format("| %-5s | %-30s | %-20s |\n", "ID", "Email Address", "Username") +
                "-------------------------------------------------------------";
    }
}
