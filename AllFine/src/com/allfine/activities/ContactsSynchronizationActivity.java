package com.allfine.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.allfine.R;
import com.allfine.enums.MessagesEnum;
import com.allfine.models.core.ContactsModelList;
import com.allfine.operations.NetworkOperations;
import com.allfine.operations.Utility;

public class ContactsSynchronizationActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		if (BuildConfig.DEBUG) {
//			Utils.enableStrictMode();
//		}
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_contacts_to_server);
		// TODO: First get contacts by AsyncTask
		// TODO: Send it To Server By AsyncTask
		// TODO: get Data from AsyncTask write it to preference
		// TODO: goto next step

		new ContactRetrieverAsyncTask(this).execute();
	}

	private class ContactRetrieverAsyncTask extends
			AsyncTask<Void, Void, ContactsModelList> {

		private Activity activity;

		public ContactRetrieverAsyncTask(Activity activity) {
			this.activity = activity;
		}

		@Override
		protected ContactsModelList doInBackground(Void... models) {

			ContactsModelList contactsModelList = new ContactsModelList();
			contactsModelList.setNumbersModels(Utility.getContacts(activity));

			NetworkOperations networkOperations = new NetworkOperations(
					activity);

			return networkOperations.sendContactsAndGetUsers(activity,
					contactsModelList);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(ContactsModelList result) {
			super.onPostExecute(result);

			if (result != null
					&& result.getMessageId() != null
					&& result.getMessageId() == MessagesEnum.MI_SUCCESSFUL
							.getId()) {

				Log.d("testA", "Loaded Data");
				Log.d("testA", " " + result.toString());

			}

		}
	}

}
