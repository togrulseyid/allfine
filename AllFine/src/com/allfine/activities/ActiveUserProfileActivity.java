package com.allfine.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allfine.R;
import com.allfine.asynctask.FriendRequestAnswerAsyncTask;
import com.allfine.asynctask.SendFriendRequestAsyncTask;
import com.allfine.enums.FriendRequestStatusEnum;
import com.allfine.enums.ObjectTypeEnum;
import com.allfine.models.core.ExistingContactNumbersModel;
import com.allfine.models.core.FriendRequestModel;
import com.allfine.models.core.SerializableObject;
import com.allfine.models.core.UserDataModel;
import com.allfine.operations.Utility;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class ActiveUserProfileActivity extends ActionBarActivity {

	private TextView textViewFullName;
	private TextView textViewPhoneNumber;
	private Button buttonSendFriendRequest;
	private Button buttonCancelRequest;
	private CircularImageView userProfilePhoto;
	private RelativeLayout userCoverPhoto;
	private ExistingContactNumbersModel existingContactNumbersModel;
	private int typeOfRequest;
	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_active_user_profile);
		activity = this;

		textViewFullName = (TextView) findViewById(R.id.textViewActiveUserProfileActivityUserFullName);
		textViewPhoneNumber = (TextView) findViewById(R.id.textViewActiveUserProfileActivityUserPhoneNumber);
		buttonSendFriendRequest = (Button) findViewById(R.id.buttonActiveUserProfileActivitySendAcceptRequest);
		buttonCancelRequest = (Button) findViewById(R.id.buttonActiveUserProfileActivityCancelRequest);
		userProfilePhoto = (CircularImageView) findViewById(R.id.circularImageViewActiveUserProfileActivityProfilePhoto);
		userCoverPhoto = (RelativeLayout) findViewById(R.id.complexViewActiveUserProfileActivityUserCover);

		buttonSendFriendRequest.setOnClickListener(new MyOnClickListener());

		Bundle bundle = getIntent().getExtras();
		typeOfRequest = (Integer) bundle.getInt(
				getString(R.string._B_TYPE_OF_REQUEST),
				ObjectTypeEnum.NOTIFICATION_MODEL.getId());

		Log.d("FriendRequestModel", "K started");

		if (typeOfRequest == ObjectTypeEnum.NOTIFICATION_MODEL.getId()) {
			sendFriendRequestConfig(bundle);
		} else if (typeOfRequest == ObjectTypeEnum.FRIEND_REQUEST_MODEL.getId()) {
			acceptRequestConfig(bundle);
		} else {

			finish();
		}

		// fillActiveUserProfile();
	}

	private void acceptRequestConfig(Bundle bundle) {

		@SuppressWarnings("unchecked")
		SerializableObject<FriendRequestModel> obj = (SerializableObject<FriendRequestModel>) bundle
				.getSerializable(getResources().getString(
						R.string._B_NOTIFICATION_OBJECT));

		FriendRequestModel friendRequestModel = obj.getObj();

		if (friendRequestModel != null) {

			// buttonActiveUserProfileActivitySendAcceptRequest
			// buttonActiveUserProfileActivityCancelRequest
			buttonCancelRequest.setVisibility(View.VISIBLE);
			buttonSendFriendRequest
					.setText(R.string.button_text_send_friend_request_accept_req);
			buttonCancelRequest
					.setText(R.string.button_text_cancel_friend_request_remove_req);

			buttonSendFriendRequest
					.setOnClickListener(new AcceptOnClickListener(
							friendRequestModel));
			buttonCancelRequest.setOnClickListener(new AcceptOnClickListener(
					friendRequestModel));

			if (friendRequestModel.getReqNumber() != null) {
				textViewPhoneNumber.setText(friendRequestModel.getReqNumber());
			}

			UserDataModel userDataModel = friendRequestModel.getUserData();

			if (userDataModel.getUdmFirstName() != null
					|| userDataModel.getUdmLastName() != null) {

				textViewFullName.setText(Utility.fullNameMaker(
						userDataModel.getUdmFirstName(),
						userDataModel.getUdmLastName()));
			}

			if (userDataModel.getUdmPhoto() != null) {
				Picasso.with(getApplicationContext())
						.load(userDataModel.getUdmPhoto())
						.into(userProfilePhoto);
			}
			if (userDataModel.getUdmCover() != null) {
				Picasso.with(getApplicationContext())
						.load(userDataModel.getUdmCover()).into(new Target() {

							@Override
							public void onPrepareLoad(Drawable arg0) {
							}

							@Override
							public void onBitmapLoaded(Bitmap bitmap,
									LoadedFrom arg1) {
								Utility.setRelativeLayoutBitmapBackground(
										getApplicationContext(),
										userCoverPhoto, bitmap);
							}

							@Override
							public void onBitmapFailed(Drawable arg0) {
							}
						});
			}

		} else {
			finish();
		}

	}

	private void sendFriendRequestConfig(Bundle bundle) {

		existingContactNumbersModel = (ExistingContactNumbersModel) bundle
				.getSerializable(getString(R.string._B_EXISTING_CONTACT_NUMBERS_MODEL));

		if (existingContactNumbersModel != null) {

			if (existingContactNumbersModel.getNumber() != null) {
				textViewPhoneNumber.setText(existingContactNumbersModel
						.getNumber());
			}

			if (existingContactNumbersModel.getFirstName() != null
					|| existingContactNumbersModel.getLastName() != null
					|| existingContactNumbersModel.getDisplayName() != null) {

				textViewFullName.setText(Utility.getFullName(
						existingContactNumbersModel.getFirstName(),
						existingContactNumbersModel.getLastName(),
						existingContactNumbersModel.getDisplayName()));
			}

			if (existingContactNumbersModel.getUserPhoto() != null) {
				Picasso.with(getApplicationContext())
						.load(existingContactNumbersModel.getUserPhoto())
						.into(userProfilePhoto);
			}
			if (existingContactNumbersModel.getFriendUserCover() != null) {
				Picasso.with(getApplicationContext())
						.load(existingContactNumbersModel.getFriendUserCover())
						.into(new Target() {

							@Override
							public void onPrepareLoad(Drawable arg0) {
							}

							@Override
							public void onBitmapLoaded(Bitmap bitmap,
									LoadedFrom arg1) {
								Utility.setRelativeLayoutBitmapBackground(
										getApplicationContext(),
										userCoverPhoto, bitmap);
							}

							@Override
							public void onBitmapFailed(Drawable arg0) {
							}
						});
			}

		} else {
			finish();
		}

	}

	private class AcceptOnClickListener implements OnClickListener {
		private FriendRequestModel friendRequestModel;

		public AcceptOnClickListener(FriendRequestModel friendRequestModel) {
			this.friendRequestModel = friendRequestModel;
		}

		@Override
		public void onClick(View view) {
			switch (view.getId()) {

			case R.id.buttonActiveUserProfileActivitySendAcceptRequest:
				friendRequestModel.setConfirmed(FriendRequestStatusEnum.STATUS_ACCEPT.getStatus());
				new FriendRequestAnswerAsyncTask(activity).execute(friendRequestModel);
				break;

			case R.id.buttonActiveUserProfileActivityCancelRequest:
				friendRequestModel.setConfirmed(FriendRequestStatusEnum.STATUS_REJECT.getStatus());
				new FriendRequestAnswerAsyncTask(activity).execute(friendRequestModel);
				break;

			default:
				break;
			}
		}

	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.buttonActiveUserProfileActivitySendAcceptRequest:
				new SendFriendRequestAsyncTask(activity)
						.execute(existingContactNumbersModel);
				break;

			default:
				break;
			}
		}

	}
}
