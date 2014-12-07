package com.allfine.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.UUID;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.allfine.R;
import com.allfine.utils.FileUtils;

public class SQLiteHelper extends SQLiteOpenHelper {

	private Activity context;

	public SQLiteHelper(Activity context) {
		super(context, context.getResources().getString(R.string._DB_NAME),
				null, Integer.parseInt(context.getResources().getString(
						R.string._DB_VERSION)));
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creates tables and indexes
		db.execSQL(context.getResources().getString(
				R.string._DB_CREATE_TABLE_USER_ACTIVE_CONTACTS_LIST));

		db.execSQL(context.getResources().getString(
				R.string._DB_CREATE_TABLE_USER_ALL_CONTACTS_LIST));

		db.execSQL(context.getResources().getString(
				R.string._DB_CREATE_TABLE_USER_EVENTS_HISTORY_LIST));
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL(context.getResources().getString(
				R.string._DB_STATEMENT_DROB_TABLE_IF_EXIST)
				+ context.getResources().getString(
						R.string._DB_CREATE_TABLE_USER_ACTIVE_CONTACTS_LIST));
		db.execSQL(context.getResources().getString(
				R.string._DB_STATEMENT_DROB_TABLE_IF_EXIST)
				+ context.getResources().getString(
						R.string._DB_CREATE_TABLE_USER_ALL_CONTACTS_LIST));

		db.execSQL(context.getResources().getString(
				R.string._DB_STATEMENT_DROB_TABLE_IF_EXIST)
				+ context.getResources().getString(
						R.string._DB_CREATE_TABLE_USER_EVENTS_HISTORY_LIST));

		// Create tables again
		onCreate(db);
	}

	// ////////////////////
	public static String DB_FILEPATH = "/data/data/com.allfine/databases/DB_ALL_FINE";

	/**
	 * Copies the database file at the specified location over the current
	 * internal application database.
	 * */
	public boolean importDatabase(String dbPath) throws IOException {

		// Close the SQLiteOpenHelper so it will commit the created empty
		// database to internal storage.
		close();
		File newDb = new File(dbPath);
		File oldDb = new File(DB_FILEPATH);
		if (newDb.exists()) {
			FileUtils.copyFile(new FileInputStream(newDb),
					new FileOutputStream(oldDb));
			// Access the copied database so SQLiteHelper will cache it and mark
			// it as created.
			getWritableDatabase().close();
			return true;
		}
		return false;
	}

	public static void exportDB() {
		// TODO Auto-generated method stub

		try {
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			if (sd.canWrite()) {
				String currentDBPath = "//data//" + "com.allfine"
						+ "//databases//" + "DB_ALL_FINE";
				String backupDBPath = "/BackupFolder/DatabaseName" + UUID.randomUUID() + ".db";
				File currentDB = new File(data, currentDBPath);
				File backupDB = new File(sd, backupDBPath);

				FileChannel src = new FileInputStream(currentDB).getChannel();
				FileChannel dst = new FileOutputStream(backupDB).getChannel();
				dst.transferFrom(src, 0, src.size());
				src.close();
				dst.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
