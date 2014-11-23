package com.allfine.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.allfine.R;
import com.allfine.adapters.AddFriendActivityBaseAdapter;
import com.allfine.adapters.ExistingContactNumbersModel;
import com.allfine.adapters.ExistingContactsModelList;
import com.allfine.enums.MessagesEnum;

public class AddFriendActivity extends ActionBarActivity {

	private ListView listView;
	private AddFriendActivityBaseAdapter adapter;
	private ArrayList<ExistingContactNumbersModel> numbersModels;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friend);

		listView = (ListView) findViewById(R.id.listViewAddFriendActivity);
		numbersModels = new ArrayList<ExistingContactNumbersModel>();
		adapter = new AddFriendActivityBaseAdapter(this, numbersModels);
		listView.setAdapter(adapter);

		// TODO: start Async Task here
		new AddFriendAsyncTask(this).execute();
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

	private class AddFriendAsyncTask extends
			AsyncTask<Void, Void, ExistingContactsModelList> {

		private Activity activity;

		public AddFriendAsyncTask(Activity activity) {
			this.activity = activity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// TODO: show ProgressBar here
		}

		@Override
		protected ExistingContactsModelList doInBackground(Void... arg0) {
			
			return makeFakeData();
			
		}

		private ExistingContactsModelList makeFakeData() {
			String photo = "https://scontent-b-vie.xx.fbcdn.net/hphotos-xpf1/v/t1.0-9/10157107_1400525340228351_5442709198150233351_n.jpg?oh=6f8928d7f233730c793d263cdec8e566&oe=54E4C586";
			
			ExistingContactsModelList contactsModelList = new ExistingContactsModelList();			
			ArrayList<ExistingContactNumbersModel> numbersModelss = new ArrayList<ExistingContactNumbersModel>();
			for (int i = 0; i <= 50; i++) {
				ExistingContactNumbersModel object = new ExistingContactNumbersModel();
				object.setUserPhoto(photo);
				object.setFirstName("Togrul");
				object.setLastName("Seydiov" + i);
				object.setNumber(i + "8731053");
				numbersModelss.add(object);
			}
			contactsModelList.setMessageId(MessagesEnum.MI_SUCCESSFUL
							.getId());
			contactsModelList.setNumbersModels(numbersModelss );
			
			
			return contactsModelList;
		}

		@Override
		protected void onPostExecute(ExistingContactsModelList result) {
			super.onPostExecute(result);

			if (result != null && result.getMessageId() != null
					&& result.getMessageId() == MessagesEnum.MI_SUCCESSFUL
							.getId()) {

				for (ExistingContactNumbersModel contactNumbersModel : result
						.getNumbersModels()) {

					numbersModels.add(contactNumbersModel);
				}

				adapter.notifyDataSetChanged();

			}

		}
	}
}
