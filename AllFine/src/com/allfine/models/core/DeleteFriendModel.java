package com.allfine.models.core;

@SuppressWarnings("serial")
public class DeleteFriendModel extends CoreModel {

	private Integer friendUserId;
	private String userName;
	private String phoneNumber;

	public DeleteFriendModel() {

	}

	public DeleteFriendModel(FriendModel friendModel) {
		setFriendUserId(friendModel.getUserId());
		setPhoneNumber(friendModel.getPhoneNumber());
		setUserName(friendModel.getUserName());
	}

	public Integer getFriendUserId() {
		return friendUserId;
	}

	public void setFriendUserId(Integer friendUserId) {
		this.friendUserId = friendUserId;
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

	@Override
	public String toString() {
		return "DeleteFriendModel [friendUserId=" + friendUserId
				+ ", userName=" + userName + ", phoneNumber=" + phoneNumber
				+ "]";
	}

}