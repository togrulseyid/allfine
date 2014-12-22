package com.allfine.activities;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.allfine.R;
import com.allfine.db.DBOperations;
import com.allfine.db.SQLiteHelper;
import com.allfine.exceptions.DataBaseException;
import com.allfine.models.UserEventsHistoryModel;

public class SettingsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		try {
			inserdd();
		} catch (DataBaseException e) {
			Log.d("cursor", "error2: " + e.getMessage());
			e.printStackTrace();
		}

		SQLiteHelper.exportDB();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void inserdd() throws DataBaseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

		// insertIntoEventsHistory
		DBOperations dbOperations = DBOperations.instance(this);
		Log.d("cursor", "inserdd: " + dbOperations.isOpen());
		UserEventsHistoryModel model = new UserEventsHistoryModel();
		model.setDate(dateFormat.format(new Date()));

		model.setSenderFirstName("Togrul");
		model.setSenderLastName("Seyidov");
		model.setSenderId(1);
		model.setStatus(1);
		long cursor = dbOperations.insertIntoEventsHistory(this, model);
		Log.d("cursor", "id: " + cursor);

		dbOperations = DBOperations.instance(this);
		model = new UserEventsHistoryModel();
		model.setDate(dateFormat.format(new Date()));
		model.setSenderFirstName("Hasan");
		model.setSenderLastName("Seyidov");
		model.setSenderId(2);
		model.setStatus(1);
		cursor = dbOperations.insertIntoEventsHistory(this, model);
		Log.d("cursor", "id: " + cursor);

		dbOperations = DBOperations.instance(this);
		model = new UserEventsHistoryModel();
		model.setDate(dateFormat.format(new Date()));
		model.setSenderFirstName("Umar");
		model.setSenderLastName("Seyidov");
		model.setSenderId(3);
		model.setStatus(1);
		cursor = dbOperations.insertIntoEventsHistory(this, model);
		Log.d("cursor", "id: " + cursor);
	}
}
