package com.allfine.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allfine.R;
import com.allfine.operations.Utility;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class AddFriendActivityBaseAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<ExistingContactNumbersModel> numbersModels;
	private ExistingContactNumbersModel model;
	private ViewHolder holder;

	public AddFriendActivityBaseAdapter(Activity activity,
			ArrayList<ExistingContactNumbersModel> numbersModels) {
		this.activity = activity;
		this.numbersModels = numbersModels;
	}

	@Override
	public int getCount() {
		return numbersModels.size();
	}

	@Override
	public ExistingContactNumbersModel getItem(int postion) {
		return numbersModels.get(postion);
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
					R.layout.list_item_add_friend, viewGroup, false);

			holder = new ViewHolder();

			holder.userNumber = (TextView) view
					.findViewById(R.id.textViewAddFriendActivityBaseAdapterUserNumber);
			holder.userFullName = (TextView) view
					.findViewById(R.id.textViewAddFriendActivityBaseAdapterUserFullName);
			holder.userPhoto = (CircularImageView) view
					.findViewById(R.id.circularImageViewAddFriendActivityBaseAdapterUserPhoto);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		model = getItem(position);
		if (model != null) {

			if (!Utility.isEmptyOrNull(model.getFirstName())
					|| !Utility.isEmptyOrNull(model.getLastName())) {

				holder.userFullName.setText(Utility.fullNameMaker(
						model.getFirstName(), model.getLastName()));

			}

			if (model.getNumber() != null) {
				holder.userNumber.setText(model.getNumber());
			}
			if (model.getUserPhoto() != null) {
				Picasso.with(activity).load(model.getUserPhoto())
						.into(holder.userPhoto);
			} else {
				holder.userPhoto.setVisibility(View.GONE);
			}

		} else {
			view.setVisibility(View.GONE);
		}

		return view;
	}

	private class ViewHolder {
		private ImageView userPhoto;
		private TextView userFullName;
		private TextView userNumber;
	}

}
