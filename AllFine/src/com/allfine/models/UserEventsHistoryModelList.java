package com.allfine.models;

import java.util.ArrayList;

import com.allfine.models.core.CoreModel;

public class UserEventsHistoryModelList extends CoreModel {
	private ArrayList<UserEventsHistoryModel> userEventsHistoryModels;

	public ArrayList<UserEventsHistoryModel> getUserEventsHistoryModels() {
		return userEventsHistoryModels;
	}

	public void setUserEventsHistoryModels(
			ArrayList<UserEventsHistoryModel> userEventsHistoryModels) {
		this.userEventsHistoryModels = userEventsHistoryModels;
	}

	@Override
	public String toString() {
		return "UserEventsHistoryModelList [userEventsHistoryModels="
				+ userEventsHistoryModels + "]";
	}

}
