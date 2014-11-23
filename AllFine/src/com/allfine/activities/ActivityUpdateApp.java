package com.allfine.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.allfine.R;
import com.allfine.models.core.ContactNumbersModel;
import com.allfine.operations.Utility;

public class ActivityUpdateApp extends ActionBarActivity {

	private ArrayList<ContactNumbersModel> alContacts;
	private TextView textView;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_update_app);

		
		startActivity(new Intent(this, ContactsSynchronizationActivity.class));
		finish();
		
//		long time = System.currentTimeMillis();
//		alContacts = Utility.getContacts(this);
//		if (alContacts != null)
//			textView = (TextView) findViewById(R.id.textViewXXXX);
//		textView.setText(alContacts.toString());
//		Log.d("testTime","time: " +( time - System.currentTimeMillis()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_update_app, menu);
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
}
