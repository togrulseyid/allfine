package com.allfine.models.core;

import java.util.ArrayList;

public class ContactNumbersModel {

	private String firstName;
	private String lastName;
	private String displayName;
	private ArrayList<String> numbers;

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

	public ArrayList<String> getNumbers() {
		return numbers;
	}

	public void setNumbers(ArrayList<String> numbers) {
		this.numbers = numbers;
	}

	@Override
	public String toString() {
		return "ContactNumbersModel [firstName=" + firstName + ", lastName="
				+ lastName + ", displayName=" + getDisplayName() + ", numbers="
				+ numbers + "]";
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
