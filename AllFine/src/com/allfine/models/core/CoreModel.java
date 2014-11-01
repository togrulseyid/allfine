package com.allfine.models.core;

public class CoreModel {

	private Integer userId;
	private String message;
	private Integer messageId;
	private String token;
	private Integer appVersion;
	private String sysLang;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(Integer appVersion) {
		this.appVersion = appVersion;
	}

	public String getSysLang() {
		return sysLang;
	}

	public void setSysLang(String sysLang) {
		this.sysLang = sysLang;
	}

	@Override
	public String toString() {
		return "CoreModel [userId=" + userId + ", message=" + message
				+ ", messageId=" + messageId + ", token=" + token
				+ ", appVersion=" + appVersion + ", sysLang=" + sysLang + "]";
	}

}