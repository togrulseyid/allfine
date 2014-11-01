package com.allfine.models.core;

public class FriendModel {

	private Integer userId;
	private String firstName;
	private String lastName;
	private Integer confirmed;
	private String userName;
	private String phoneNumber;
	private String photo;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Integer getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Integer confirmed) {
		this.confirmed = confirmed;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "FriendModel [userId=" + userId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", confirmed=" + confirmed
				+ ", userName=" + userName + ", phoneNumber=" + phoneNumber
				+ ", photo=" + photo + "]";
	}

}
