/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allfine.models;

import java.math.BigDecimal;

/**
 * 
 * @author eypirimov
 */
public class ActivityFriendModel {

	private String firstName;
	private String lastName;
	private String userName;
	private String operationDateStr;
	private BigDecimal amount;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperationDateStr() {
		return operationDateStr;
	}

	public void setOperationDateStr(String operationDateStr) {
		this.operationDateStr = operationDateStr;
	}

	@Override
	public String toString() {
		return "ActivityFriendModel [" + "getFirstName " + getFirstName() + " "
				+ "getLastName " + getLastName() + " " + "getOperationDateStr "
				+ getOperationDateStr() + " " + "getUserName " + getUserName()
				+ "]";
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
