package com.allfine.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.allfine.R;
import com.allfine.db.DBOperations;
import com.allfine.enums.MessagesEnum;
import com.allfine.exceptions.DataBaseException;
import com.allfine.models.core.ContactsModelList;
import com.allfine.models.core.ExistingContactsModelList;
import com.allfine.operations.NetworkOperations;
import com.allfine.operations.Utility;

public class ContactsSynchronizationActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_contacts_to_server);
		// TODO: First get contacts by AsyncTask @DONE
		// TODO: Send it To Server By AsyncTask @DONE
		// TODO: get Data from AsyncTask @DONE
		// TODO: write it to preference
		// TODO: goto next step @DONE

		new ContactRetrieverAsyncTask(this).execute();
	}

	private class ContactRetrieverAsyncTask extends
			AsyncTask<Void, Void, ExistingContactsModelList> {

		private Activity activity;

		public ContactRetrieverAsyncTask(Activity activity) {
			this.activity = activity;
		}

		@Override
		protected ExistingContactsModelList doInBackground(Void... models) {

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
		protected void onPostExecute(ExistingContactsModelList result) {
			super.onPostExecute(result);

			if (result != null
					&& result.getMessageId() != null
					&& result.getMessageId() == MessagesEnum.MI_SUCCESSFUL
							.getId()) {

				Log.d("contacts", "Loaded Data");
				Log.d("contacts", "SY result: " + result.toString());
				
//				SQLITE operations goes here :) 
				DBOperations dbOperations = DBOperations.instance(activity);
				
				try {
					dbOperations.clearDatabase();
				} catch (DataBaseException e) {
					e.printStackTrace();
				}
				
				try {
					dbOperations.insertIntoActiveContacts(result);
				} catch (DataBaseException e) {
					Log.d("cursor","error3: " + e.getMessage());
					e.printStackTrace();
				} finally {
					dbOperations.closeDataSource();
				}

				SharedPreferences sharedPreferences = getSharedPreferences(
						getString(R.string._SP_ALL_FINE), Context.MODE_PRIVATE);

				sharedPreferences
						.edit()
						.putString(
								getResources().getString(
										R.string._SP_ROOTING_ACTIVITY),
								getResources().getString(
										R.string._ROOTING_MAIN_ACTIVITY))
						.commit();

				// TODO: write to LocalDatabase here

				Intent intent = new Intent(activity, MainActivity.class);
				startActivity(intent);
				activity.finish();

			}

		}
	}

}
