package com.matcha.entity;

import java.util.List;

public class SearchRequest {

    private Integer minAge;
    private Integer maxAge;
    private Integer rate;
    private Double locationRange;
    private List<String> interests;
    private Double latitude;
    private Double longitude;
    private Integer offset;


    public SearchRequest() {

    }

    public SearchRequest(Integer minAge, Integer maxAge, Integer rate, List<String> interests) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.rate = rate;
        this.interests = interests;
    }


    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge)
    {
        this.minAge = (minAge == null) ? 0 : minAge;
    }

    public Integer getMaxAge() {return maxAge;}

    public void setMaxAge(Integer maxAge)
    {
        this.maxAge = (maxAge == null) ? 128 : maxAge;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = (rate == null) ? 0 : rate;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {

        this.interests = (interests.size() == 0) ? null : interests;
    }

    public Double getLocationRange() {
        return locationRange;
    }

    public void setLocationRange(Double locationRange)
    {
        this.locationRange = (locationRange == null) ? 2000 : locationRange;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", rate=" + rate +
                ", locationRange=" + locationRange +
                ", interests=" + interests +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", offset=" + offset +
                '}';
    }
}
