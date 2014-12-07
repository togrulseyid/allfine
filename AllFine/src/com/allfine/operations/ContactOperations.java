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
			model.setFirstName(cursor.getString(cursor
					.getColumnIndex("FIRST_NAME")));
			model.setLastName(cursor.getString(cursor
					.getColumnIndex("LAST_NAME")));
			model.setDisplayName(cursor.getString(cursor
					.getColumnIndex("DISPLAY_NAME")));
			model.setNumber(cursor.getString(cursor.getColumnIndex("NUMBER")));
			// cursor.getString(cursor.getColumnIndex("COUNTRY_CODE"));
			model.setUserPhoto(cursor.getString(cursor
					.getColumnIndex("USER_PHOTO")));
			model.setUserId(cursor.getInt(cursor.getColumnIndex("USER_ID")));
			numbersModels.add(model);
			// cursor.getInt(cursor.getColumnIndex("STATUS"));
		}
		
		modelList.setNumbersModels(numbersModels);
		cursor.close();
		dbOperations.closeDataSource();
		return modelList;
	}

}
