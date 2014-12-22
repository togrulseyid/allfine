package com.allfine.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.allfine.R;
import com.allfine.adapters.AddFriendActivityBaseAdapter;
import com.allfine.asynctask.AddFriendAsyncTask;
import com.allfine.constants.BusinessConstants;
import com.allfine.models.core.ExistingContactNumbersModel;
import com.allfine.models.core.UserModel;
import com.allfine.operations.Utility;

public class ActiveUsersListActivity extends ActionBarActivity {

	private ListView listView;
	private AddFriendActivityBaseAdapter adapter;
	private ArrayList<ExistingContactNumbersModel> numbersModels;
	private UserModel userModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friend);

		listView = (ListView) findViewById(R.id.listViewAddFriendActivity);
		numbersModels = new ArrayList<ExistingContactNumbersModel>();
		adapter = new AddFriendActivityBaseAdapter(this, numbersModels);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new MyOnItemClickListener());

		userModel = Utility.getUserInfo(this);
		// TODO: start Async Task here
		new AddFriendAsyncTask(this, adapter, userModel, numbersModels)
				.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_friend, menu);
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

	// private class AddFriendAsyncTask extends
	// AsyncTask<Void, Void, ExistingContactsModelList> {
	//
	// private Activity activity;
	//
	// public AddFriendAsyncTask(Activity activity) {
	// this.activity = activity;
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// // TODO: show ProgressBar here
	// }
	//
	// @Override
	// protected ExistingContactsModelList doInBackground(Void... arg0) {
	//
	// ContactOperations contactOperations = new ContactOperations(
	// activity);
	// ExistingContactsModelList contactsModelList = null;
	// try {
	// contactsModelList = contactOperations.getActiveContacts();
	// contactsModelList.setMessageId(MessagesEnum.MI_SUCCESSFUL
	// .getId());
	// } catch (DataBaseException e) {
	// e.printStackTrace();
	// }
	//
	// return contactsModelList;
	// }
	//
	// @Override
	// protected void onPostExecute(ExistingContactsModelList result) {
	// super.onPostExecute(result);
	//
	// if (result != null && result.getMessageId() != null) {
	// if (result.getMessageId() == MessagesEnum.MI_SUCCESSFUL.getId()) {
	//
	// for (ExistingContactNumbersModel numbersModel : result
	// .getNumbersModels()) {
	//
	// if (userModel.existUserById(numbersModel.getUserId())) {
	// numbersModels.add(numbersModel);
	// }
	// }
	//
	// adapter.notifyDataSetChanged();
	//
	// } else if (result.getMessageId() ==
	// MessagesEnum.MI_NO_INTERNET_CONNECTION
	// .getId()
	// || result.getMessageId() == MessagesEnum.MI_NO_NETWORK_CONNECTION
	// .getId()) {
	// // TODO: Toast No internet connection
	// } else if (result.getMessageId() == MessagesEnum.MI_TOKEN_ERROR
	// .getId()) {
	//
	// Utility.finishForToken(activity);
	// }
	// }
	//
	// }
	// }

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view,
				int position, long id) {

			Bundle bundle = new Bundle();
			Intent intent = new Intent(getBaseContext(),
					ActiveUserProfileActivity.class);

			bundle.putSerializable(
					getString(R.string._B_EXISTING_CONTACT_NUMBERS_MODEL),
					adapter.getItem(position));

			intent.putExtras(bundle);
			// startActivity(intent);
			startActivityForResult(intent, BusinessConstants.RESULT_ADD_FRIEND);

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to

		if (requestCode == BusinessConstants.RESULT_ADD_FRIEND) {

			// Make sure the request was successful
			if (resultCode == RESULT_OK) {

				if (data.getExtras()
						.getBoolean(
								getString(R.string.INTENT_REQ_IS_FRIEND_REQUEST_CHANGED))) {

					Bundle bundle = new Bundle();
					bundle.putBoolean(
							getString(R.string.INTENT_REQ_IS_FRIEND_REQUEST_CHANGED),
							true);
					data = getIntent();
					data.putExtras(bundle);
					setResult(Activity.RESULT_OK, data);

				}

			}
		}
	}
}
