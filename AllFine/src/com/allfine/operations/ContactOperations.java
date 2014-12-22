package com.allfine.operations;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;

import com.allfine.db.DBOperations;
import com.allfine.exceptions.DataBaseException;
import com.allfine.models.core.ExistingContactNumbersModel;
import com.allfine.models.core.ExistingContactsModelList;

public class ContactOperations {
	
	private Activity activity;

	public ContactOperations(Activity activity) {
		this.activity = activity;
	}

	public ExistingContactsModelList getActiveContacts() throws DataBaseException {
		
		DBOperations dbOperations = DBOperations.instance(activity);
		Log.d("testA","dbOperations: " + dbOperations.isOpen());
		Cursor cursor = dbOperations.getActiveContacts();
		
		cursor.moveToFirst();
		
		ExistingContactsModelList modelList = new ExistingContactsModelList();
		ArrayList<ExistingContactNumbersModel> numbersModels = new ArrayList<ExistingContactNumbersModel>();

		while (cursor.moveToNext()) {
			ExistingContactNumbersModel model = new ExistingContactNumbersModel();

			model.setFriendId(cursor.getInt(cursor.getColumnIndex("USER_ID")));

			model.setFriendUserName(cursor.getString(cursor
					.getColumnIndex("USER_NAME")));

			model.setFirstName(cursor.getString(cursor
					.getColumnIndex("FIRST_NAME")));

			model.setLastName(cursor.getString(cursor
					.getColumnIndex("LAST_NAME")));

			model.setDisplayName(cursor.getString(cursor
					.getColumnIndex("DISPLAY_NAME")));

			model.setNumber(cursor.getString(cursor.getColumnIndex("NUMBER")));

			model.setCountryCode(cursor.getString(cursor
					.getColumnIndex("COUNTRY_CODE")));

			model.setUserPhoto(cursor.getString(cursor
					.getColumnIndex("USER_PHOTO")));

			model.setFriendUserCover(cursor.getString(cursor
					.getColumnIndex("USER_COVER")));

			model.setStatus(cursor.getInt(cursor.getColumnIndex("STATUS")));

			numbersModels.add(model);
		}
		
		modelList.setNumbersModels(numbersModels);
		cursor.close();
		dbOperations.closeDataSource();
		return modelList;
	}

}
