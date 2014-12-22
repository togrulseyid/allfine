package com.allfine.enums;

public enum ObjectTypeEnum {
	FRIEND_REQUEST_MODEL(1), NOTIFICATION_MODEL(2);

	private ObjectTypeEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	private int id;

}
