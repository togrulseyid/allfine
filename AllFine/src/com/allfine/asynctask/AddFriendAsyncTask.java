package com.allfine.asynctask;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.allfine.adapters.AddFriendActivityBaseAdapter;
import com.allfine.enums.MessagesEnum;
import com.allfine.exceptions.DataBaseException;
import com.allfine.models.core.ExistingContactNumbersModel;
import com.allfine.models.core.ExistingContactsModelList;
import com.allfine.models.core.UserModel;
import com.allfine.operations.ContactOperations;
import com.allfine.operations.Utility;

public class AddFriendAsyncTask extends
		AsyncTask<Void, Void, ExistingContactsModelList> {

	private Activity activity;

	public AddFriendAsyncTask(Activity activity) {
		this.activity = activity;
	}

	private AddFriendActivityBaseAdapter adapter;
	private UserModel userModel;
	private ArrayList<ExistingContactNumbersModel> numbersModels;

	public AddFriendAsyncTask(Activity activity,
			AddFriendActivityBaseAdapter adapter, UserModel userModel,
			ArrayList<ExistingContactNumbersModel> numbersModels) {
		this.activity = activity;

		this.adapter = adapter;
		this.userModel = userModel;
		this.numbersModels = numbersModels;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// TODO: show ProgressBar here
	}

	@Override
	protected ExistingContactsModelList doInBackground(Void... arg0) {

		ContactOperations contactOperations = new ContactOperations(activity);
		ExistingContactsModelList contactsModelList = null;
		try {
			contactsModelList = contactOperations.getActiveContacts();
			contactsModelList.setMessageId(MessagesEnum.MI_SUCCESSFUL.getId());
		} catch (DataBaseException e) {
			e.printStackTrace();
		}

		return contactsModelList;
	}

	@Override
	protected void onPostExecute(ExistingContactsModelList result) {
		super.onPostExecute(result);

		if (result != null && result.getMessageId() != null) {
			if (result.getMessageId() == MessagesEnum.MI_SUCCESSFUL.getId()) {

				Log.d("numbersModel","userModel: " + userModel);
				for (ExistingContactNumbersModel numbersModel : result
						.getNumbersModels()) {

					if (userModel == null || userModel.getFriends() == null
							|| !userModel.equalsFriend(numbersModel)) {
						numbersModels.add(numbersModel);

						Log.d("testA", "numbersModel: " + numbersModel);
					}
				}

				adapter.notifyDataSetChanged();

			} else if (result.getMessageId() == MessagesEnum.MI_NO_INTERNET_CONNECTION
					.getId()
					|| result.getMessageId() == MessagesEnum.MI_NO_NETWORK_CONNECTION
							.getId()) {
				// TODO: Toast No internet connection
			} else if (result.getMessageId() == MessagesEnum.MI_TOKEN_ERROR
					.getId()) {

				Utility.finishForToken(activity);
			}
		}

	}

}
