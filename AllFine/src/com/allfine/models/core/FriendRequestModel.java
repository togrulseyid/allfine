package com.allfine.models.core;

public class FriendRequestModel extends CoreModel {

	private String reqNumber;
	private boolean confirmed;

	public String getFriendNumber() {
		return reqNumber;
	}

	public void setFriendNumber(String friendNumber) {
		this.reqNumber = friendNumber;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	@Override
	public String toString() {
		return "FriendRequestModel [friendNumber=" + reqNumber
				+ ", confirmed=" + confirmed + "]";
	}

}