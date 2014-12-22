/**
 * 
 */
package com.allfine.enums;

/**
 * @author Seyid
 * 
 */
public enum FriendRequestStatusEnum {

	STATUS_ACCEPT(1), STATUS_REJECT(2), STATUS_BLOCK(3);

	private FriendRequestStatusEnum(int status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	private int status;

}
