package com.allfine.models.core;

@SuppressWarnings("serial")
public class FriendRequestModel extends CoreModel {

	private String reqNumber;
	private String userName;
	private Integer confirmed;
	// private Timestamp timeStmp;
	private String timeStmp;
	private UserDataModel userData;

	public String getReqNumber() {
		return reqNumber;
	}

	public void setReqNumber(String reqNumber) {
		this.reqNumber = reqNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Integer confirmed) {
		this.confirmed = confirmed;
	}

	public String getTimeStmp() {
		return timeStmp;
	}

	public void setTimeStmp(String timeStmp) {
		this.timeStmp = timeStmp;
	}

	public UserDataModel getUserData() {
		return userData;
	}

	public void setUserData(UserDataModel userData) {
		this.userData = userData;
	}

	@Override
	public String toString() {
		return "FriendRequestModel [reqNumber=" + reqNumber + ", userName="
				+ userName + ", confirmed=" + confirmed + ", timeStmp="
				+ timeStmp + ", userData=" + userData + "]";
	}

	// public Timestamp getTimeStmp() {
	// return timeStmp;
	// }
	//
	// public void setTimeStmp(Timestamp timeStmp) {
	// this.timeStmp = timeStmp;
	// }

}