package com.allfine.db;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.allfine.R;
import com.allfine.exceptions.DataBaseException;
import com.allfine.models.UserEventsHistoryModel;
import com.allfine.models.core.ContactNumbersModel;
import com.allfine.models.core.ContactsModelList;
import com.allfine.models.core.ExistingContactNumbersModel;
import com.allfine.models.core.ExistingContactsModelList;

public class DBOperations {

	private Resources resources;
	private static SQLiteDatabase database;
	private static SQLiteHelper helper;

	// private boolean isGreaterVersion;

	/*
	 * Constructor for initialize values
	 */
	private DBOperations(Activity activity) {

		// isGreaterVersion = (android.os.Build.VERSION.SDK_INT >=
		// Build.VERSION_CODES.HONEYCOMB) ? true
		// : false;
		helper = new SQLiteHelper(activity);
		resources = activity.getResources();
	}

	public static DBOperations instance(Activity activity) {
		DBOperations dataOperations = null;

		synchronized (DBOperations.class) {
			// if (!isOpen) {
			if (database == null || !database.isOpen()) {
				Log.d("testA", "DB: is not open");
				dataOperations = new DBOperations(activity);
				dataOperations.openDataSource();
				return dataOperations;
			}
			Log.d("testA", "DB: is open");
		}

		dataOperations = new DBOperations(activity);
		dataOperations.openDataSource();

		return dataOperations;
	}

	/*
	 * Check database is open or not
	 */
	public boolean isOpen() {

		return database.isOpen();// isOpen;
	}

	/*
	 * Get writable access and begin transaction
	 */
	public void openDataSource() throws SQLException {
		synchronized (helper) {
			/*
			 * This IF condition for only guaranty
			 */
			// if (!isOpen) {
			if (database == null || !database.isOpen()) {
				database = helper.getWritableDatabase();
				database.beginTransaction();
			}
		}
	}

	/*
	 * End transaction and close database
	 */
	public void closeDataSource() throws SQLException {
		synchronized (helper) {
			if (database.isOpen()) {
				database.setTransactionSuccessful();
				database.endTransaction();
				helper.close();
			}
		}

	}

	/*
	 * Inserting null value to SQLite occurs Exception
	 */
	private String restrictNullString(String string) {
		if (string == null) {
			string = "";
		} else if (string.trim().length() == 0) {
			string = "";
		}
		return string;
	}

	private Integer restrictNullInteger(Integer integer) {
		if (integer == null) {
			integer = 0;
		}
		return integer;
	}

	private int getInteger(Cursor cursor, String columnName) {
		return cursor.getInt(cursor.getColumnIndex(columnName));
	}

	private String getString(Cursor cursor, String columnName) {
		return cursor.getString(cursor.getColumnIndex(columnName));
	}

	public void clearDatabase() throws DataBaseException {
		try {
			database.delete(resources
					.getString(R.string._DB_TABLE_USER_ACTIVE_CONTACTS_LIST),
					null, null);
			database.delete(resources
					.getString(R.string._DB_TABLE_USER_ALL_CONTACTS_LIST),
					null, null);
		} catch (Exception ex) {
			throw new DataBaseException(ex.getMessage());
		}
	}

	/**
	 * @STATEMENT: GET USER_ACTIVE_CONTACTS_LIST
	 * */
	public Cursor getActiveContacts() throws DataBaseException {
		Log.d("contacts", "DB: getActiveContacts");

		Log.d("testA", "" + isOpen());
		Log.d("testA", "" + database.getVersion() + database.isOpen());
		Cursor cursor = database
				.rawQuery(
						resources
								.getString(R.string._DB_Q_SELECT_TABLE_USER_ACTIVE_CONTACTS_LIST),
						null);
		return cursor;
	}

	/**
	 * @STATEMENT: INSERT USER_ACTIVE_CONTACTS_LIST
	 * */
	public int insertIntoActiveContacts(ExistingContactsModelList models)
			throws DataBaseException {
		SQLiteStatement statement;
		for (ExistingContactNumbersModel model : models.getNumbersModels()) {
			statement = database
					.compileStatement(resources
							.getString(R.string._DB_Q_INSERT_TABLE_USER_ACTIVE_CONTACTS_LIST));
			// USER_ID INTEGER,
			// USER_NAME TEXT,
			// FIRST_NAME TEXT,
			// LAST_NAME TEXT,
			// DISPLAY_NAME TEXT,
			// NUMBER TEXT,
			// COUNTRY_CODE TEXT,
			// USER_PHOTO TEXT,
			// USER_COVER TEXT,
			// STATUS INTEGER)
			Log.d("contacts", "list: " + model.toString());

			if (model.getFriendId() != null) {
				statement.bindLong(2, model.getFriendId());
			}

			if (model.getFriendUserName() != null) {
				statement.bindString(3, model.getFriendUserName());
			}

			if (model.getFirstName() != null) {
				statement.bindString(4, model.getFirstName());
			}
			if (model.getLastName() != null) {
				statement.bindString(5, model.getLastName());
			}
			if (model.getDisplayName() != null) {
				statement.bindString(6, model.getDisplayName());
			}
			if (model.getNumber() != null) {
				statement.bindString(7, model.getNumber());
			}
			if ("+994" != null) {
				statement.bindString(8, "+994");
			}
			if (model.getUserPhoto() != null) {
				statement.bindString(9, model.getUserPhoto());
			}

			if (model.getFriendUserCover() != null) {
				statement.bindString(10, model.getFriendUserCover());
			}

			if (model.getStatus() != null) {
				statement.bindLong(11, model.getStatus());
			}

			Log.d("statement", "statement: " + statement.executeInsert());
			statement.close();
		}

		return -1;
	}

	/**
	 * @STATEMENT: DELETE USER_ACTIVE_CONTACTS_LIST
	 * */
	public int deleteFromActiveContacts(String where, String value)
			throws SQLException {
		return database.delete(resources
				.getString(R.string._DB_TABLE_USER_ACTIVE_CONTACTS_LIST), where
				+ " = ?", new String[] { value });
	}

	/**
	 * @STATEMENT: UPDATE USER_ACTIVE_CONTACTS_LIST
	 * */
	public int updateFromActiveContacts(ExistingContactNumbersModel model,
			String where, String whereArg) throws SQLException {
		if (model == null)
			return -1;
		ContentValues values = new ContentValues();
		// USER_ID INTEGER,
		// COUNTRY_CODE TEXT,
		// STATUS INTEGER
		if (model.getDisplayName() != null) {
			values.put("DISPLAY_NAME", model.getDisplayName());
		}

		if (model.getFirstName() != null) {
			values.put("FIRST_NAME", model.getDisplayName());
		}

		if (model.getLastName() != null) {
			values.put("LAST_NAME", model.getDisplayName());
		}

		if (model.getNumber() != null) {
			values.put("NUMBER", model.getDisplayName());
		}

		if (model.getUserPhoto() != null) {
			values.put("USER_PHOTO", model.getDisplayName());
		}

		database.update(resources
				.getString(R.string._DB_TABLE_USER_ACTIVE_CONTACTS_LIST),
				values, where + " = ?", new String[] { whereArg });

		return -1;
	}

	// Updating single contact
	// public int updateContact(Contact contact) {
	// ContentValues values = new ContentValues();
	// values.put(KEY_NAME, contact.getName());
	// values.put(KEY_PH_NO, contact.getPhoneNumber());
	// return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?", new String[] {
	// String.valueOf(contact.getID()) });
	// }

	/**
	 * @STATEMENT: INSERT USER_ALL_CONTACTS_LIST
	 * */
	public int insertIntoAllContacts(ContactsModelList models)
			throws DataBaseException {
		SQLiteStatement statement = database.compileStatement(resources
				.getString(R.string._DB_Q_INSERT_TABLE_USER_ALL_CONTACTS_LIST));
		for (ContactNumbersModel model : models.getNumbersModels()) {
			for (String number : model.getNumbers()) {
				statement.bindLong(1, 0);
				statement.bindString(2, model.getFirstName());
				statement.bindString(3, model.getLastName());
				statement.bindString(4, model.getDisplayName());
				statement.bindString(5, number);
				statement.bindString(6, "+994");
				statement.bindLong(7, 1);
				statement.execute();
			}
		}

		return -1;
	}

	/**
	 * @STATEMENT: DELETE USER_ALL_CONTACTS_LIST
	 * */
	public int deleteFromAllContacts(String where, String value)
			throws SQLException {
		return database.delete(
				resources.getString(R.string._DB_TABLE_USER_ALL_CONTACTS_LIST),
				where + " = ?", new String[] { value });
	}

	public int deleteStmt(String table, String where, String value)
			throws SQLException {
		return database.delete(table, where + " = ?", new String[] { value });
	}

	/**
	 * @STATEMENT: GET USER_EVENTS_HISTORY_LIST
	 * */
	public Cursor getUserEventsHistory() throws DataBaseException {
		Cursor cursor = database
				.rawQuery(
						resources
								.getString(R.string._DB_Q_SELECT_TABLE_USER_EVENTS_HISTORY_LIST),
						null);

		Log.d("cursor", "count: " + getCount());

		return cursor;
	}

	public long getCount() throws DataBaseException {
		SQLiteStatement s = database
				.compileStatement("select count(*) from USER_EVENTS_HISTORY_LIST");

		return s.simpleQueryForLong();
	}

	/**
	 * @STATEMENT: INSERT USER_EVENTS_HISTORY_LIST
	 * */
	public long insertIntoEventsHistory(Context context,
			UserEventsHistoryModel model) throws DataBaseException {
		SQLiteStatement stmt = database
				.compileStatement(resources
						.getString(R.string._DB_Q_INSERT_TABLE_USER_EVENTS_HISTORY_LIST));
		// @history id = date + (userid + senderId); +1 ID INTEGER PRIMARY KEY
		// AUTOINCREMENT, +2 SENDER_USER_ID INTEGER, +3 FIRST_NAME TEXT, +4
		// LAST_NAME TEXT, +5 DISPLAY_NAME TEXT, +6 ACTION_DATE INTEGER, +7
		// HISTORY_ID TEXT, +8 STATUS INTEGER)
		Log.d("cursor", "list: " + model.toString());

		if (model.getSenderId() != null) {
			stmt.bindLong(2, model.getSenderId());
		}
		if (model.getSenderFirstName() != null) {
			stmt.bindString(3, model.getSenderFirstName());
		}
		if (model.getSenderLastName() != null) {
			stmt.bindString(4, model.getSenderLastName());
		}
		if (model.getDisplayName() != null) {
			stmt.bindString(5, model.getDisplayName());
		}
		if (model.getDate() != null) {
			stmt.bindString(6, model.getDate());
		}
		if (model.getHistoryId() != null) {
			stmt.bindString(7, model.getHistoryId());
		}

		if (model.getStatus() != null) {
			stmt.bindLong(8, model.getStatus());
		}

		long status = stmt.executeInsert();
		stmt.close();
		closeDataSource();
		return status;
	}

	
	/**
	 * @STATEMENT: GET USER_ACTIVE_CONTACTS_LIST BY TYPE
	 * */
	public Cursor getUserActiveProfileByType(String type, String value)
			throws DataBaseException {
		Log.d("contacts", "DB: getActiveContacts");

		Log.d("testA", "" + isOpen());
		Log.d("testA", "" + database.getVersion() + database.isOpen());
		Cursor cursor = database
				.rawQuery(
						String.format(
								resources
										.getString(R.string._DB_Q_SELECT_TABLE_USER_ACTIVE_CONTACTS_LIST_BY_TYPE),
								type), new String[] { value });
		return cursor;
	}
	
}
