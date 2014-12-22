package com.allfine.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allfine.R;
import com.allfine.constants.BusinessConstants;
import com.allfine.enums.MessagesEnum;
import com.allfine.models.core.EventsModel;
import com.allfine.models.core.FriendModel;
import com.allfine.operations.NetworkOperations;
import com.allfine.operations.Utility;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.togrulseyid.quickaction.library.QuickAction;

public class SendEventAsynTask extends
		AsyncTask<EventsModel, Void, EventsModel> {
	private QuickAction customQuickAction;

	private Activity activity;
	private View view;
	private FriendModel user;
//	private int userId;

	// public SendEventAsynTask(Activity activity, View view) {
	// this.activity = activity;
	// this.view = view;
	// }

	public SendEventAsynTask(Activity activity, FriendModel user, View view) {
		this.activity = activity;
		this.view = view;
		this.user = user;
	}

	@Override
	protected EventsModel doInBackground(EventsModel... params) {
//		userId = params[0].getToUserId();
		NetworkOperations networkOperations = new NetworkOperations(activity);
		return networkOperations.sendEvent(params[0]);
	}

	@Override
	protected void onPostExecute(EventsModel result) {
		super.onPostExecute(result);
		if (!isCancelled()) {
			// Log.d("testA", result.toString());

			if (!isCancelled() && result != null
					&& result.getMessageId() != null) {
				if (result.getMessageId() == MessagesEnum.MI_SUCCESSFUL.getId()) {

					// TODO: Make User Friend Image Border Color Different
					CircularImageView firendsPhoto = (CircularImageView) view
							.findViewById(R.id.circularImageViewLISlidingMenuFriend);

					firendsPhoto.setBorderColor(activity.getResources()
							.getColor(R.color.color_all_fine_main_color));

					// Log.d("testA", "firendsPhoto color change");

					RelativeLayout customLayout = (RelativeLayout) activity
							.getLayoutInflater().inflate(
									R.layout.popup_reached_layout, null);

					TextView textViewReachedFriendName = (TextView) customLayout
							.findViewById(R.id.textViewPopupReachedLayoutFriendName);

					textViewReachedFriendName
							.setText(String.format(
									activity.getString(R.string._PATTERN_TEXTVIEW_REACHED_FRIEND_NAME),
									Utility.maxStringLength(
											Utility.getFullName(
													user.getFirstName(),
													user.getLastName(),
													user.getDisplayName()),
											BusinessConstants.MAX_LENGTH_NAME_OF_FRIEND_REACHED)));

					customQuickAction = new QuickAction(activity,
							R.style.PopupAnimation,
							R.drawable.popup_background, customLayout);

//					customQuickAction.show(view);
					customQuickAction.show(view, (view.getMeasuredWidth()*1)/4, view.getMeasuredHeight());
				} else if (result.getMessageId() == MessagesEnum.MI_TOKEN_ERROR
						.getId()) {

					Utility.finishForToken(activity);
				}
			}
		}

	}

}
