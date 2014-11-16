package com.allfine.models;

public class UserEventsHistoryModel {
	private String senderFirtName;
	private String senderLastName;
	private String date;
	private Integer status;

	public String getSenderFirtName() {
		return senderFirtName;
	}

	public void setSenderFirtName(String senderFirtName) {
		this.senderFirtName = senderFirtName;
	}

	public String getSenderLastName() {
		return senderLastName;
	}

	public void setSenderLastName(String senderLastName) {
		this.senderLastName = senderLastName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserEventsHistoryModel [senderFirtName=" + senderFirtName
				+ ", senderLastName=" + senderLastName + ", date=" + date
				+ ", status=" + status + "]";
	}

}
