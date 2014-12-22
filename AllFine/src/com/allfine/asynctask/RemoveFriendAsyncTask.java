package com.allfine.asynctask;

import android.app.Activity;
import android.os.AsyncTask;

import com.allfine.adapters.MainActivitySlideMenuAdapter;
import com.allfine.enums.MessagesEnum;
import com.allfine.models.core.DeleteFriendModel;
import com.allfine.models.core.FriendModel;
import com.allfine.models.core.UserModel;
import com.allfine.operations.NetworkOperations;
import com.allfine.operations.Utility;

public class RemoveFriendAsyncTask extends
		AsyncTask<FriendModel, Void, DeleteFriendModel> {

	private Activity activity;
	private UserModel userModel;
	private FriendModel friendModel;
	private MainActivitySlideMenuAdapter menuAdapter;

	public RemoveFriendAsyncTask(Activity activity,
			MainActivitySlideMenuAdapter menuAdapter, UserModel userModel) {
		this.activity = activity;
		this.menuAdapter = menuAdapter;
		this.userModel = userModel;
	}

	@Override
	protected DeleteFriendModel doInBackground(FriendModel... friendModels) {
		// TODO: network operation send it to Server
		friendModel = friendModels[0];
		NetworkOperations networkOperations = new NetworkOperations(activity);
		DeleteFriendModel deleteFriendModel = new DeleteFriendModel(friendModel);
		return networkOperations.deleteFriend(deleteFriendModel);
	}

	@Override
	protected void onPostExecute(DeleteFriendModel result) {
		super.onPostExecute(result);

		if (result != null && result.getMessageId() != null) {
			if (result.getMessageId() == MessagesEnum.MI_SUCCESSFUL.getId()) {

				userModel = userModel.removeUserFriendById(friendModel.getUserId());

				Utility.writeUserInfo(activity, userModel);
				menuAdapter.notifyDataSetChanged();

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
