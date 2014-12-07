package com.allfine.operations;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;

import com.allfine.db.DBOperations;
import com.allfine.exceptions.DataBaseException;
import com.allfine.models.UserEventsHistoryModel;
import com.allfine.models.UserEventsHistoryModelList;

public class HistoryOperations {
	private Activity activity;

	public HistoryOperations(Activity activity) {
		this.activity = activity;
	}

	public UserEventsHistoryModelList getUserEventsHistory() throws DataBaseException {
		
		UserEventsHistoryModelList eventsList = new UserEventsHistoryModelList();
		
		DBOperations dbOperations = DBOperations.instance(activity);

		Log.d("cursor","getUserEventsHistory start");
		Cursor cursor = dbOperations.getUserEventsHistory();
		Log.d("cursor","getUserEventsHistory start: " + cursor.getColumnIndex("LAST_NAME"));
		Log.d("cursor","getUserEventsHistory start: " + cursor.getCount());
		cursor.moveToFirst();
		
		ArrayList<UserEventsHistoryModel> userEventsHistoryModels = new ArrayList<UserEventsHistoryModel>();
		while (cursor.moveToNext()) {
			UserEventsHistoryModel model = new UserEventsHistoryModel();
			model.setSenderId(cursor.getInt(cursor.getColumnIndex("SENDER_USER_ID")));
			Log.d("cursor","" + cursor.getString(cursor.getColumnIndex("LAST_NAME")));
			model.setSenderFirstName(cursor.getString(cursor.getColumnIndex("LAST_NAME")));
			model.setSenderLastName(cursor.getString(cursor.getColumnIndex("FIRST_NAME")));
			model.setDisplayName(cursor.getString(cursor.getColumnIndex("DISPLAY_NAME")));
			model.setDate(cursor.getString(cursor.getColumnIndex("ACTION_DATE")));
			model.setHistoryId(cursor.getString(cursor.getColumnIndex("HISTORY_ID")));
			model.setStatus(cursor.getInt(cursor.getColumnIndex("STATUS")));
			userEventsHistoryModels.add(model);
		}

		eventsList.setUserEventsHistoryModels(userEventsHistoryModels);
		cursor.close();
		dbOperations.closeDataSource();

		return eventsList;
	}

}
