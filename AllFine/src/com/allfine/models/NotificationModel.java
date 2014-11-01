package com.allfine.models;

import java.io.Serializable;

import com.allfine.models.core.CoreModel;

@SuppressWarnings("serial")
public class NotificationModel extends CoreModel implements Serializable {

	Integer eventId;
	String timeStmp;

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getTimeStmp() {
		return timeStmp;
	}

	public void setTimeStmp(String timeStmp) {
		this.timeStmp = timeStmp;
	}

	@Override
	public String toString() {
		return "NotificationModel [eventId=" + eventId + ", timeStmp="
				+ timeStmp + "]";
	}

}
