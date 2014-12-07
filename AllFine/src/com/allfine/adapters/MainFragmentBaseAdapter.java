package com.allfine.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allfine.R;
import com.allfine.models.UserEventsHistoryModel;
import com.allfine.models.core.UserModel;
import com.allfine.operations.Utility;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class MainFragmentBaseAdapter extends BaseAdapter {

	private ArrayList<UserEventsHistoryModel> historyList;
	private UserEventsHistoryModel model;
	private Activity activity;
	private ViewHolder holder;
	private UserModel user;

	public MainFragmentBaseAdapter(Activity activity,
			ArrayList<UserEventsHistoryModel> historyList) {
		this.historyList = historyList;
		this.activity = activity;
	}

	public MainFragmentBaseAdapter(Activity activity,
			ArrayList<UserEventsHistoryModel> historyList, UserModel user) {
		this.historyList = historyList;
		this.activity = activity;
		this.user = user;
	}

	@Override
	public int getCount() {
		return historyList.size();
	}

	@Override
	public UserEventsHistoryModel getItem(int position) {
		return historyList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		View view = convertView;
		if (convertView == null) {

			view = activity.getLayoutInflater().inflate(
					R.layout.list_item_events_history, viewGroup, false);

			holder = new ViewHolder();

			holder.eventHistory = (TextView) view
					.findViewById(R.id.textViewMainFragmentBaseAdapterDate);
			holder.userFullName = (TextView) view
					.findViewById(R.id.textViewMainFragmentBaseAdapterUserFullName);
			holder.userPhoto = (CircularImageView) view
					.findViewById(R.id.circularImageViewMainFragmentBaseAdapterUserPhoto);
			holder.imageViewStatus = (ImageView) view
					.findViewById(R.id.imageViewMainFragmentBaseAdapterStatus);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		model = getItem(position);
		Log.d("testA", "model: " + model);
		if (model.getSenderId() != null) {

			if (!Utility.isEmptyOrNull(model.getSenderFirstName())
					|| !Utility.isEmptyOrNull(model.getSenderLastName())) {

				holder.userFullName.setText(Utility.fullNameMaker(
						model.getSenderFirstName(), model.getSenderLastName()));

			} else if (!Utility.isEmptyOrNull(model.getDisplayName())) {
				holder.userFullName.setText(model.getDisplayName());
			}

			Picasso.with(activity)
					.load(user.userGetFriendById(model.getSenderId())
							.getPhoto()).into(holder.userPhoto);

			if (model.getStatus() != null) {
				holder.imageViewStatus.setImageResource(Utility
						.getImageResourceById(activity, model.getStatus()));
			}
			if (model.getDate() != null) {
				holder.eventHistory.setText(model.getDate());
			}

		} else {
			view.setVisibility(View.GONE);
		}

		return view;
	}

	private class ViewHolder {
		private ImageView userPhoto;
		private ImageView imageViewStatus;
		private TextView userFullName;
		private TextView eventHistory;
	}

}
