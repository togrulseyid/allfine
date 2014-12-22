package com.allfine.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.allfine.enums.MessagesEnum;
import com.allfine.models.core.CoreModel;
import com.allfine.models.core.FriendRequestModel;
import com.allfine.operations.NetworkOperations;
import com.allfine.operations.Utility;

public class FriendRequestAnswerAsyncTask extends
		AsyncTask<FriendRequestModel, Void, CoreModel> {
	private Activity activity;
	private FriendRequestModel friendRequestModel;

	public FriendRequestAnswerAsyncTask(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected CoreModel doInBackground(
			FriendRequestModel... friendRequestModels) {
		friendRequestModel = friendRequestModels[0];
		NetworkOperations networkOperations = new NetworkOperations(activity);
		return networkOperations
				.sendFriendRequestAnswer(friendRequestModel);
	}

	@Override
	protected void onPostExecute(CoreModel result) {
		super.onPostExecute(result);

		if (!isCancelled()) {
			Log.d("testA", result.toString());

			if (!isCancelled() && result != null
					&& result.getMessageId() != null) {
				if (result.getMessageId() == MessagesEnum.MI_SUCCESSFUL.getId()) {
					Log.d("testA", "result: " + result);
					
					Utility.updateFriendReq(activity, friendRequestModel);
					
					activity.finish();
				}
			}
		}
	}

}
