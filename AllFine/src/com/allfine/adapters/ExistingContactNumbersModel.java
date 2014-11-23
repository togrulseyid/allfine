package com.allfine.adapters;

public class ExistingContactNumbersModel {
	private String firstName;
	private String lastName;
	private String number;
	private String userPhoto;

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	@Override
	public String toString() {
		return "ExistingContactNumbersModel [firstName=" + firstName
				+ ", lastName=" + lastName + ", number=" + number
				+ ", userPhoto=" + userPhoto + "]";
	}

}