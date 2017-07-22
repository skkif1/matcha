package com.matcha.entity;

import java.util.List;

public class SearchRequest {

    private Integer minAge;
    private Integer maxAge;
    private Integer rate;
    private List<String> interests;

    public SearchRequest(Integer minAge, Integer maxAge, Integer rate, List<String> interests) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.rate = rate;
        this.interests = interests;
    }


    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", rate=" + rate +
                ", interests=" + interests +
                '}';
    }
}
