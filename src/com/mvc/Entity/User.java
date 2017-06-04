package com.mvc.Entity;

public class User {

    private String login;
    private String email;
    private String password;
    private String salt;

    public User() {
    }

    public User(String login, String email, String password, String salt) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "login :" + this.getLogin() + "\n" +
                "email :" + this.getEmail() + "\n" +
                "password :" + this.getPassword() + "\n";
    }
}
