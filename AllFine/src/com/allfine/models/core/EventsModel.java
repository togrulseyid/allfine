package com.allfine.models.core;

public class EventsModel extends CoreModel {

	private String timeStmp;
	private Integer toUserId;
	private Integer eventId;

	public String getTimeStmp() {
		return timeStmp;
	}

	public void setTimeStmp(String timeStmp) {
		this.timeStmp = timeStmp;
	}

	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	@Override
	public String toString() {
		return "EventsModel [timeStmp=" + timeStmp + ", toUserId=" + toUserId
				+ ", eventId=" + eventId + "]";
	}

}