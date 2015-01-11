package com.allfine.models.core;

import java.util.Date;

@SuppressWarnings("serial")
public class SignUpModel extends CoreModel {

	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String countryCode;
	private String dateCreated = new String(new Date().toString());
	private String gcm;
	private String imei;
	private String phoneModel = "android";
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getGcm() {
		return gcm;
	}

	public void setGcm(String gcm) {
		this.gcm = gcm;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "SignUpModel [userName=" + userName + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", countryCode="
				+ countryCode + ", dateCreated=" + dateCreated + ", gcm=" + gcm
				+ ", imei=" + imei + ", phoneModel=" + phoneModel
				+ ", password=" + password + "]";
	}

}