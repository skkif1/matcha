package com.mvc.Entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mvc.model.jsonDesializer.InformationBoundledUserDesializer;

import java.util.ArrayList;


@JsonDeserialize(using = InformationBoundledUserDesializer.class)
public class InformationBoundledUser extends User{

    private String sex;
    private Integer age;
    private String country;
    private String state;
    private String aboutMe;
    private ArrayList<String> interests;
    private String sexPref;


    public InformationBoundledUser() {
    }

    public InformationBoundledUser(String firstName, String lastName, String sex, Integer age, String country, String state,
                                   String aboutMe, ArrayList<String> interests, String sexPref)
    {
        super(firstName, lastName);
        this.sex = sex;
        this.age = age;
        this.country = country;
        this.state = state;
        this.aboutMe = aboutMe;
        this.interests = interests;
        this.sexPref = sexPref;
    }

    public InformationBoundledUser(String sex, Integer age, String country, String state, String aboutMe, ArrayList<String> interests, String sexPref) {
        this.sex = sex;
        this.age = age;
        this.country = country;
        this.state = state;
        this.aboutMe = aboutMe;
        this.interests = interests;
        this.sexPref = sexPref;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getSexPref() {
        return sexPref;
    }

    public void setSexPref(String sexPref) {
        this.sexPref = sexPref;
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n" +
                "InformationBoundledUser" + "\n" +
                "sex=" + sex + "\n" +
                " age=" + age +"\n" +
                " country=" + country + "\n" +
                " state=" + state + "\n" +
                " aboutMe=" + aboutMe + "\n" +
                " interests=" + interests.toString() + "\n" +
                " sexPref=" + sexPref + "\n";
    }
}
