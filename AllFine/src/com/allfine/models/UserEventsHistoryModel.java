package com.allfine.models;

public class UserEventsHistoryModel {

	private Integer senderId;
	private String senderFirstName;
	private String senderLastName;
	private String displayName;
	private String date;
	private String historyId;
	private Integer status;

	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public String getSenderFirstName() {
		return senderFirstName;
	}

	public void setSenderFirstName(String senderFirstName) {
		this.senderFirstName = senderFirstName;
	}

	public String getSenderLastName() {
		return senderLastName;
	}

	public void setSenderLastName(String senderLastName) {
		this.senderLastName = senderLastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHistoryId() {
		return historyId;
	}

	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserEventsHistoryModel [senderId=" + senderId
				+ ", senderFirtName=" + senderFirstName + ", senderLastName="
				+ senderLastName + ", displayName=" + displayName + ", date="
				+ date + ", historyId=" + historyId + ", status=" + status
				+ "]";
	}

}