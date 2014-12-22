package com.allfine.models.core;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExistingContactNumbersModel extends CoreModel implements
		Serializable {

	private Integer friendId;
	private String firstName;
	private String lastName;
	private String displayName;
	private String friendUserName;
	private String number;
	private String countryCode;
	private String userPhoto;
	private String friendUserCover;
	private Integer status;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Integer getFriendId() {
		return friendId;
	}

	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
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

	public String getFriendUserName() {
		return friendUserName;
	}

	public void setFriendUserName(String friendUserName) {
		this.friendUserName = friendUserName;
	}

	public String getFriendUserCover() {
		return friendUserCover;
	}

	public void setFriendUserCover(String friendUserCover) {
		this.friendUserCover = friendUserCover;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ExistingContactNumbersModel [friendId=" + friendId
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", displayName=" + displayName + ", friendUserName="
				+ friendUserName + ", number=" + number + ", countryCode="
				+ countryCode + ", userPhoto=" + userPhoto
				+ ", friendUserCover=" + friendUserCover + ", status=" + status
				+ "]";
	}

}