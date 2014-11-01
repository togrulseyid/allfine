/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allfine.models;

/**
 *
 * @author eypirimov
 */
public class CityModel {
    
    private Integer id;
    private String cityName;
    private String language;
    private String countryCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
