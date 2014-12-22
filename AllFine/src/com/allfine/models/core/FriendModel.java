package com.allfine.models.core;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FriendModel implements Serializable, Comparable<FriendModel> {

	private Integer userId;
	private String firstName;
	private String lastName;
	private String displayName;
	private Integer confirmed;
	private String userName;
	private String phoneNumber;
	private String photo;
	private String cover;

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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	@Override
	public String toString() {
		return "FriendModel [userId=" + userId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", displayName=" + displayName
				+ ", confirmed=" + confirmed + ", userName=" + userName
				+ ", phoneNumber=" + phoneNumber + ", photo=" + photo
				+ ", cover=" + cover + "]";
	}

	@Override
	public int compareTo(FriendModel friendModel) {
		if (friendModel != null) {
			return friendModel.getUserId().compareTo(getUserId());
		}
		return -1;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof FriendModel) {
			return ((FriendModel) o).getUserId().equals(getUserId());
		}
		return false;
	}

}