package com.allfine.models.core;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GoogleCloudMessagingModel implements Serializable {

	private Object object;
	private int objectType;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	@Override
	public String toString() {
		return "GoogleCloudMessagingModel [object=" + object + ", objectType="
				+ objectType + "]";
	}

}
