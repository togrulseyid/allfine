package com.allfine.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.allfine.R;
import com.allfine.models.core.FriendModel;
import com.squareup.picasso.Picasso;

public class MainActivitySlideMenuAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<FriendModel> friendModels;

	public MainActivitySlideMenuAdapter(Activity activity,
			ArrayList<FriendModel> friendModels) {
		this.activity = activity;
		this.friendModels = friendModels;
	}

	@Override
	public int getCount() {
		if (friendModels != null)
			return friendModels.size();
		return 0;
	}

	@Override
	public FriendModel getItem(int itemID) {
		return friendModels.get(itemID);
	}

	@Override
	public long getItemId(int itemID) {
		return itemID;
	}

	ViewHolder viewHolder;

	@Override
	public View getView(int position, View viewConvert, ViewGroup viewGroup) {

		View view = viewConvert;

		if (view == null) {
			viewHolder = new ViewHolder();
			view = activity.getLayoutInflater().inflate(
					R.layout.list_item_sliding_menu, viewGroup, false);

			viewHolder.imageViewFriendProfile = (ImageView) view
					.findViewById(R.id.circularImageViewLISlidingMenuFriend);

			view.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		FriendModel friendModel = getItem(position);
		Log.d("friendModel", "" + friendModel.toString());
		if (friendModel.getPhoneNumber() != null) {
			try {
				Picasso.with(activity).load(friendModel.getPhoto())
						.error(R.drawable.avatar_default)
						.placeholder(R.drawable.avatar_default)
						.into(viewHolder.imageViewFriendProfile);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			view.setVisibility(View.GONE);
		}

		return view;
	}

	private static class ViewHolder {
		public ImageView imageViewFriendProfile;
	}
}
