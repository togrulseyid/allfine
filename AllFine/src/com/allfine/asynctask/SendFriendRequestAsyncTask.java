package com.allfine.asynctask;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.allfine.R;
import com.allfine.enums.MessagesEnum;
import com.allfine.models.core.ExistingContactNumbersModel;
import com.allfine.models.core.FriendModel;
import com.allfine.models.core.FriendRequestModel;
import com.allfine.models.core.UserModel;
import com.allfine.operations.NetworkOperations;
import com.allfine.operations.Utility;
import com.allfine.views.InfoDialog;

public class SendFriendRequestAsyncTask extends
		AsyncTask<ExistingContactNumbersModel, Void, FriendRequestModel> {

	private Activity activity;
	private InfoDialog infoDialog;
	private ExistingContactNumbersModel contactNumbersModel;

	public SendFriendRequestAsyncTask(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected FriendRequestModel doInBackground(
			ExistingContactNumbersModel... models) {
		
		contactNumbersModel = models[0];
		
		FriendRequestModel friendRequestModel = new FriendRequestModel();
		
		friendRequestModel.setUserName(contactNumbersModel.getFriendUserName());
		
		friendRequestModel.setReqNumber(contactNumbersModel.getNumber());
		
		friendRequestModel.setTimeStmp(new java.sql.Timestamp(new Date()
				.getTime()).toString());

		NetworkOperations networkOperations = new NetworkOperations(activity);
		
		return networkOperations.sendFriendRequest(friendRequestModel);
	}

	@Override
	protected void onPostExecute(FriendRequestModel result) {
		super.onPostExecute(result);

		if (!isCancelled() && result != null && result.getMessageId() != null) {
			if (result.getMessageId() == MessagesEnum.MI_SUCCESSFUL.getId()) {

				Log.d("addFriend", "addFriend ok");
				Toast.makeText(activity, "addFriend ok", 1000).show();

				// TODO: add CONST_FRIEND_REQUESTED to user id
				UserModel userModel = Utility.getUserInfo(activity);

				FriendModel friendModel = new FriendModel();
				friendModel.setUserId(contactNumbersModel.getFriendId());
				friendModel.setConfirmed(result.getConfirmed());
				friendModel
						.setDisplayName(contactNumbersModel.getDisplayName());
				friendModel.setFirstName(contactNumbersModel.getFirstName());
				friendModel.setLastName(contactNumbersModel.getLastName());
				friendModel.setPhoneNumber(result.getReqNumber());
				friendModel.setUserName(result.getUserName());
				friendModel.setUserId(result.getUserId());

				if (userModel.getFriends() == null) {
					userModel.setFriends(new ArrayList<FriendModel>());
				}
				userModel.addFriend(friendModel);
				userModel.removeDuplicate();
				
				Utility.writeUserInfo(activity, userModel);
				
				
				Bundle bundle = new Bundle();
				bundle.putBoolean(activity.getString(R.string.INTENT_REQ_IS_FRIEND_REQUEST_CHANGED), true);
				Intent intent = activity.getIntent();
				intent.putExtras(bundle);
				activity.setResult(Activity.RESULT_OK, intent);
				
				activity.finish();

			} else if (result.getMessageId() == MessagesEnum.MI_NO_INTERNET_CONNECTION
					.getId()) {
				infoDialog = new InfoDialog(activity,
						R.drawable.img_friend_settings,
						R.string.message_info_dialog_title,
						MessagesEnum.MI_NO_INTERNET_CONNECTION.getMessageId());
				infoDialog.show();
			} else if (result.getMessageId() == MessagesEnum.MI_NO_NETWORK_CONNECTION
					.getId()) {
				infoDialog = new InfoDialog(activity,
						R.drawable.img_friend_settings,
						R.string.message_info_dialog_title,
						MessagesEnum.MI_NO_NETWORK_CONNECTION.getMessageId());
				infoDialog.show();

			} else if (result.getMessageId() == MessagesEnum.MI_ALREADY_FRIEND_REQUEST_SEND
					.getId()) {
				infoDialog = new InfoDialog(activity,
						R.drawable.img_friend_settings,
						R.string.message_info_dialog_title,
						MessagesEnum.MI_ALREADY_FRIEND_REQUEST_SEND
								.getMessageId());
				infoDialog.show();
			} else if (result.getMessageId() == MessagesEnum.MI_TOKEN_ERROR
					.getId()) {

				Utility.finishForToken(activity);
			}
		}

	}

}
