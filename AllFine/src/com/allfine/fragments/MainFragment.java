package com.allfine.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allfine.R;
import com.allfine.models.core.UserModel;
import com.allfine.operations.Utility;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

	private SlidingMenu slidingMenu;
	private ImageButton imageButtonMenuOpener;
	private UserModel user;
	private TextView textViewUserProfileUserFullName;
	private TextView textViewUserProfileUserPhoneNumber;
	private TextView textViewUserProfileUserFriendsCount;
	private CircularImageView circularImageViewFragmentMainProfilePhoto;
	private RelativeLayout complexViewUserCover;
	private ListView listView;

	public MainFragment(SlidingMenu slidingMenu, UserModel user) {
		this.slidingMenu = slidingMenu;
		this.user = user;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		Log.d("userD", "" + user);

		textViewUserProfileUserFullName = (TextView) rootView
				.findViewById(R.id.textViewUserProfileUserFullName);
		textViewUserProfileUserPhoneNumber = (TextView) rootView
				.findViewById(R.id.textViewUserProfileUserPhoneNumber);
		textViewUserProfileUserFriendsCount = (TextView) rootView
				.findViewById(R.id.textViewUserProfileUserFriendsCount);
		circularImageViewFragmentMainProfilePhoto = (CircularImageView) rootView
				.findViewById(R.id.circularImageViewFragmentMainProfilePhoto);
		imageButtonMenuOpener = (ImageButton) rootView
				.findViewById(R.id.imageButtonFragmentMainMenuOpener);
		complexViewUserCover = (RelativeLayout) rootView
				.findViewById(R.id.complexViewUserCover);
		listView = (ListView) rootView
				.findViewById(R.id.listViewMainActivityCallList);

		imageButtonMenuOpener.setOnClickListener(new MyOnClickListener());

		
		fillUserInfo(user);
		
		
		return rootView;
	}

	private void fillUserInfo(UserModel user) {
		String fullName = Utility.fullNameMaker(user.getFirtsName(),
				user.getLastName());
		if (fullName != null) {
			textViewUserProfileUserFullName.setText(fullName);
		}

		if (user.getPhoneNumber() != null) {
			textViewUserProfileUserPhoneNumber.setText(user.getPhoneNumber());
		}

		if (user.getFriends() != null) {
			textViewUserProfileUserFriendsCount.setText(String.format(
					getString(R.string._PATTERN_USER_PROFILE_FRIENDS_COUNT),
					user.getFriends().size()));
		}
		if (user.getPhoto() != null) {
			Picasso.with(getActivity()).load(user.getPhoto())
					.error(R.drawable.avatar_default)
					.placeholder(R.drawable.avatar_default)
					.into(circularImageViewFragmentMainProfilePhoto);
		}
		if (user.getCover() != null) {
			Log.d("testA","getCover" + user.getCover());
			Picasso.with(getActivity()).load(user.getCover())
					.error(R.drawable.profile_cover_image1)
					.placeholder(R.drawable.profile_cover_image1)
					.into(new Target() {

						@Override
						public void onPrepareLoad(Drawable arg0) {
							Utility.setRelativeLayoutBackground(
									complexViewUserCover,
									getActivity().getResources().getDrawable(
											R.drawable.profile_cover_image1));
						}

						@Override
						public void onBitmapLoaded(Bitmap bit, LoadedFrom arg1) {
							Utility.setRelativeLayoutBackground(
									complexViewUserCover, new BitmapDrawable(
											getActivity().getResources(), bit));
						}

						@Override
						public void onBitmapFailed(Drawable arg0) {
							Utility.setRelativeLayoutBackground(
									complexViewUserCover,
									getActivity().getResources().getDrawable(
											R.drawable.profile_cover_image1));
						}
					});
		}

	}

	private class MyOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {

			switch (view.getId()) {
			case R.id.imageButtonFragmentMainMenuOpener:
				slidingMenu.toggle(true);
				break;

			default:
				break;
			}

		}

	}

	
}