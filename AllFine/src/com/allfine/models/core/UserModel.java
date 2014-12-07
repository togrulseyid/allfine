package com.allfine.models.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@SuppressWarnings("serial")
public class UserModel extends CoreModel implements Serializable {

	private String userName;
	private String firtsName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private ArrayList<FriendModel> friends;
	private Integer eventId;
	private String dateCreated;
	private String gcm;
	private String imei;
	private String phoneModel = "android";
	private String photo;
	private String cover;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirtsName() {
		return firtsName;
	}

	public void setFirtsName(String firtsName) {
		this.firtsName = firtsName;
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

	public ArrayList<FriendModel> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<FriendModel> friends) {
		this.friends = friends;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
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

	public FriendModel userGetFriendById(Integer userId) {
		FriendModel friendModel;
		Iterator<FriendModel> itr = friends.iterator();
		while (itr.hasNext()) {
			friendModel = (FriendModel) itr.next();
			if (userId.equals(friendModel.getUserId())) {
				return friendModel;
			}
		}
		return null;
	}
	
	
	@Override
	public String toString() {
		return "UserModel [userName=" + userName + ", firtsName=" + firtsName
				+ ", lastName=" + lastName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", friends=" + friends
				+ ", eventId=" + eventId + ", dateCreated=" + dateCreated
				+ ", gcm=" + gcm + ", imei=" + imei + ", phoneModel="
				+ phoneModel + ", photo=" + photo + ", cover=" + cover + "]";
	}

}