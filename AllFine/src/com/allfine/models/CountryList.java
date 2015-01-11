package com.allfine.models;

import java.util.ArrayList;

public class CountryList {

	private ArrayList<Country> countries;

	public ArrayList<Country> getCountries() {
		return countries;
	}

	public void setCountries(ArrayList<Country> countries) {
		this.countries = countries;
	}

	@Override
	public String toString() {
		return "CountryList [countries=" + countries + "]";
	}

}
