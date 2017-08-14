package com.matcha.entity;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.matcha.entity.jsonDeserialize.UserInformationDesializer;

import java.util.ArrayList;

@JsonDeserialize(using = UserInformationDesializer.class)
public class UserInformation {

    private String sex;
    private Integer age;
    private String country;
    private String state;
    private String aboutMe;
    private ArrayList<String> interests;
    private String sexPref;
    private ArrayList<String> photos;
    private String avatar;
    private Double longitude;
    private Double latitude;
    private Integer rate;


    public UserInformation() {
    }

    public UserInformation(String sex, Integer age, String country, String state, String aboutMe, ArrayList<String> interests, String sexPref, Double longitude, Double latitude, Integer likeNumber) {
        this.sex = sex;
        this.age = age;
        this.country = country;
        this.state = state;
        this.aboutMe = aboutMe;
        this.interests = interests;
        this.sexPref = sexPref;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
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

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserInformation{" +
                "sex='" + sex + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", interests=" + interests +
                ", sexPref='" + sexPref + '\'' +
                ", photos=" + photos +
                ", avatar='" + avatar + '\'' +
                ", langitude=" + longitude +
                ", latitude=" + latitude +
                ", rate=" + rate +
                '}';
    }
}
