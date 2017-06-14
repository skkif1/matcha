package com.mvc.Entity;


public class User {

    private Integer id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String salt;
    private Boolean confirm;
    private UserInformation information;

    public User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public User(Integer id, String login, String firstName, String lastName, String email, String password, String salt, Boolean confirm, UserInformation information) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.confirm = confirm;
        this.information = information;
    }

    public UserInformation getInformation() {
        return information;
    }

    public void setInformation(UserInformation information) {
        this.information = information;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return "User" + "\n" +
                "login='" + login + "\n" +
                ", email='" + email + "\n" +
                ", password='" + password + "\n" +
                ", salt='" + salt + "\n" +
                ", confirm=" + confirm + "\n" +
                ", confirm=" + information.toString() + "\n";
    }
}
