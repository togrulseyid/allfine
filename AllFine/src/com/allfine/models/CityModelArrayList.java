/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allfine.models;

import java.util.ArrayList;

import com.allfine.models.core.CoreModel;

/**
 *
 * @author eypirimov
 */
public class CityModelArrayList extends CoreModel{
    
    private ArrayList<CityModel> cityList;

    public ArrayList<CityModel> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<CityModel> cityList) {
        this.cityList = cityList;
    }
    
}
