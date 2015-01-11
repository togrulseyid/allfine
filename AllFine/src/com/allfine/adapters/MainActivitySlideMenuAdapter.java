package com.allfine.adapters;

import java.util.ArrayList;

import android.app.Activity;
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
	private ViewHolder viewHolder;

	public MainActivitySlideMenuAdapter(Activity activity,
			ArrayList<FriendModel> friendModels) {
		
		this.activity = activity;
		
		if (friendModels != null) {
			this.friendModels = friendModels;
		} else {
			this.friendModels = new ArrayList<FriendModel>();
		}
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

	// @Override
	// public int getViewTypeCount() {
	// return MainMenuRowType.values().length;
	// }
	//
	// @Override
	// public int getItemViewType(int position) {
	// FriendModel item = getItem(position);
	// if (item.getConfirmed() == 1) {
	// return MainMenuRowType.FRIEND.getId();
	// } else {
	// return MainMenuRowType.NON_FRIEND.getId();
	// }
	// }

	@Override
	public View getView(int position, View viewConvert, ViewGroup viewGroup) {

		// ViewHolder.FriendViewHolder friendViewHolder = null;
		// ViewHolder.NoFriendViewHolder noFriendViewHolder = null;
		//
		// int type = getItemViewType(position);
		//
		// Log.d("testA","getConfirmed: " + type);
		//
		// FriendModel friendModel = getItem(position);
		//
		// if (type == MainMenuRowType.FRIEND.getId()) {
		// if (viewConvert == null) {
		// viewConvert = activity.getLayoutInflater().inflate(
		// R.layout.list_item_sliding_menu_friend, viewGroup,
		// false);
		// friendViewHolder = new ViewHolder().new FriendViewHolder();
		//
		// friendViewHolder.imageViewFriendProfile = (ImageView) viewConvert
		// .findViewById(R.id.circularImageViewLISlidingMenuFriend);
		//
		// viewConvert.setTag(friendViewHolder);
		//
		// noFriendViewHolder = null;
		//
		// } else {
		// friendViewHolder = (FriendViewHolder) viewConvert.getTag();
		// noFriendViewHolder = null;
		// }
		//
		// if (friendModel.getPhoneNumber() != null) {
		// try {
		// Picasso.with(activity).load(friendModel.getPhoto())
		// .error(R.drawable.avatar_default)
		// .placeholder(R.drawable.avatar_default)
		// .into(friendViewHolder.imageViewFriendProfile);
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// } else {
		// viewConvert.setVisibility(View.GONE);
		// }
		//
		// } else {
		//
		// if (viewConvert == null) {
		// viewConvert = activity.getLayoutInflater().inflate(
		// R.layout.list_item_sliding_menu_non_friend, viewGroup,
		// false);
		// noFriendViewHolder = new ViewHolder().new NoFriendViewHolder();
		//
		// noFriendViewHolder.imageViewFriendProfile = (ImageView) viewConvert
		// .findViewById(R.id.circularImageViewLISlidingMenuNoFriend);
		//
		// noFriendViewHolder.imageViewBlur = (ImageView) viewConvert
		// .findViewById(R.id.imageViewLISlidingMenuNoFriend);
		//
		// viewConvert.setTag(noFriendViewHolder);
		//
		// friendViewHolder = null;
		//
		// } else {
		// noFriendViewHolder = (NoFriendViewHolder) viewConvert.getTag();
		// friendViewHolder = null;
		// }
		//
		// if (friendModel.getPhoneNumber() != null) {
		// try {
		// Picasso.with(activity).load(friendModel.getPhoto())
		// .error(R.drawable.avatar_default)
		// .placeholder(R.drawable.avatar_default)
		// .into(noFriendViewHolder.imageViewFriendProfile);
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// } else {
		// viewConvert.setVisibility(View.GONE);
		// }
		//
		// }
		//
		// return viewConvert;

		View view = viewConvert;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = activity.getLayoutInflater().inflate(
					R.layout.list_item_sliding_menu_friend, viewGroup, false);
			viewHolder.imageViewFriendProfile = (ImageView) view
					.findViewById(R.id.circularImageViewLISlidingMenuFriend);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		FriendModel friendModel = getItem(position);

		if (friendModel.getPhoneNumber() != null) {
			try {

				// if (friendModel.getConfirmed() != null
				// && friendModel.getConfirmed().compareTo(1) > 0) {
				// viewHolder.imageViewFriendProfile
				// .setBackgroundColor(activity
				// .getResources()
				// .getColor(
				// R.color.color_all_fine_main_blur_color));
				// }

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

	class ViewHolder {
		public ImageView imageViewFriendProfile;

		// public class FriendViewHolder extends ViewHolder {
		// }
		//
		// public class NoFriendViewHolder extends ViewHolder {
		// ImageView imageViewBlur;
		// }

	}

}
