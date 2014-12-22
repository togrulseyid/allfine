package com.allfine.models.core;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserDataModel implements Serializable {

	private Integer udmUserId;
	private String udmUserName;
	private String udmFirstName;
	private String udmLastName;
	private String udmDisplayName;
	private String udmEmail;
	private String udmPhone;
	private String udmCCode;
	private Integer udmEventId;
	private String udmDate;
	private String udmPhoto;
	private String udmCover;

	public Integer getUdmUserId() {
		return udmUserId;
	}

	public void setUdmUserId(Integer udmUserId) {
		this.udmUserId = udmUserId;
	}

	public String getUdmUserName() {
		return udmUserName;
	}

	public void setUdmUserName(String udmUserName) {
		this.udmUserName = udmUserName;
	}

	public String getUdmFirstName() {
		return udmFirstName;
	}

	public void setUdmFirstName(String udmFirstName) {
		this.udmFirstName = udmFirstName;
	}

	public String getUdmLastName() {
		return udmLastName;
	}

	public void setUdmLastName(String udmLastName) {
		this.udmLastName = udmLastName;
	}

	public String getUdmDisplayName() {
		return udmDisplayName;
	}

	public void setUdmDisplayName(String udmDisplayName) {
		this.udmDisplayName = udmDisplayName;
	}

	public String getUdmEmail() {
		return udmEmail;
	}

	public void setUdmEmail(String udmEmail) {
		this.udmEmail = udmEmail;
	}

	public String getUdmPhone() {
		return udmPhone;
	}

	public void setUdmPhone(String udmPhone) {
		this.udmPhone = udmPhone;
	}

	public String getUdmCCode() {
		return udmCCode;
	}

	public void setUdmCCode(String udmCCode) {
		this.udmCCode = udmCCode;
	}

	public Integer getUdmEventId() {
		return udmEventId;
	}

	public void setUdmEventId(Integer udmEventId) {
		this.udmEventId = udmEventId;
	}

	public String getUdmDate() {
		return udmDate;
	}

	public void setUdmDate(String udmDate) {
		this.udmDate = udmDate;
	}

	public String getUdmPhoto() {
		return udmPhoto;
	}

	public void setUdmPhoto(String udmPhoto) {
		this.udmPhoto = udmPhoto;
	}

	public String getUdmCover() {
		return udmCover;
	}

	public void setUdmCover(String udmCover) {
		this.udmCover = udmCover;
	}

	@Override
	public String toString() {
		return "UserDataModel [udmUserId=" + udmUserId + ", udmUserName="
				+ udmUserName + ", udmFirstName=" + udmFirstName
				+ ", udmLastName=" + udmLastName + ", udmDisplayName="
				+ udmDisplayName + ", udmEmail=" + udmEmail + ", udmPhone="
				+ udmPhone + ", udmCCode=" + udmCCode + ", udmEventId="
				+ udmEventId + ", udmDate=" + udmDate + ", udmPhoto="
				+ udmPhoto + ", udmCover=" + udmCover + "]";
	}

}