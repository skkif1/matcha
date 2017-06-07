package com.mvc.Entity;

public class User {

    private String id;
    private String login;
    private String email;
    private String password;
    private String salt;
    private Boolean confirm;

    public User() {
    }

    public User(String login, String email, String password, String salt, Boolean confirm) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.confirm = confirm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
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
        return "User{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", confirm=" + confirm +
                '}';
    }
}
