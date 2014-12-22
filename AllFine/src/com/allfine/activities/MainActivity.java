package com.allfine.activities;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allfine.R;
import com.allfine.adapters.MainActivitySlideMenuAdapter;
import com.allfine.asynctask.RemoveFriendAsyncTask;
import com.allfine.asynctask.SendEventAsynTask;
import com.allfine.asynctask.WriteToDB;
import com.allfine.constants.BusinessConstants;
import com.allfine.fragments.MainFragment;
import com.allfine.models.core.EventsModel;
import com.allfine.models.core.FriendModel;
import com.allfine.models.core.UserModel;
import com.allfine.operations.Utility;
import com.allfine.views.ConfirmDialog;
import com.allfine.views.InfoDialog;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.togrulseyid.quickaction.library.QuickAction;

public class MainActivity extends ActionBarActivity {

	private SlidingMenu slidingMenu;
	private MainActivitySlideMenuAdapter menuAdapter;
	private Activity activity;
	private UserModel user;
	private Fragment contentFragment;
	// Menu
	private QuickAction customQuickAction;
	private ListView listViewMenu;

	// private ArrayList<FriendModel> friends;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		activity = this;

		user = Utility.getUserInfo(activity);
		
//		Log.d("UserModel","" + user.toString());

		if (user == null) {
			Utility.finishForToken(activity);
		}

		makeMenu();

		listViewMenu = (ListView) findViewById(R.id.listViewListSlidingMenuFriendsProfiles);

		menuAdapter = new MainActivitySlideMenuAdapter(this, user.getFriends());

		if (user.getFriends() == null
				|| user.getFriends().size() < BusinessConstants.MAX_USER_FRIEND) {

			View footerView = getLayoutInflater().inflate(
					R.layout.footer_add_friend_layout, null);

			listViewMenu.addFooterView(footerView, null, true);

			footerView.findViewById(R.id.imageButtonMainActivityAddFriend)
					.setOnClickListener(new MyOnClickListener());

		}

		listViewMenu.setAdapter(menuAdapter);

		listViewMenu.setOnItemClickListener(new MyOnItemClickListener(user));
		listViewMenu
				.setOnItemLongClickListener(new MyOnItemLongClickListener());

		if (savedInstanceState == null) {
			contentFragment = new MainFragment(slidingMenu, user);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, contentFragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Toast.makeText(activity, "Naqaracuqa? :)", Toast.LENGTH_SHORT)
					.show();
			Intent intent = new Intent(activity, SettingsActivity.class);
			startActivity(intent);
			break;

		case android.R.id.home:
			slidingMenu.toggle(true);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * Creates SlidingMenu content
	 */
	private void makeMenu() {

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// configure the SlidingMenu
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.RIGHT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setShadowWidthRes(R.dimen.dimen_shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setBehindOffsetRes(R.dimen.dimen_slidingmenu_offset);
		// slidingMenu.setFadeDegree(0.35f);
		slidingMenu.setFadeDegree(1.0f);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.view_sliding_menu);

		slidingMenu.setOnOpenedListener(new OnOpenedListener() {
			@Override
			public void onOpened() {
				Utility.hideSoftKeyboard(MainActivity.this);
			}
		});

	}

	private class MyOnItemClickListener implements OnItemClickListener {
		private UserModel user;

		public MyOnItemClickListener(UserModel user) {
			this.user = user;
		}

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view,
				int position, long id) {

			FriendModel friendModel = user.getFriends().get(position);
			if (friendModel != null
					&& friendModel.getConfirmed() == BusinessConstants.CONST_FRIEND_ADDED) {
				EventsModel eventsModel = new EventsModel();
				eventsModel.setTimeStmp("" + new Date().getTime());
				eventsModel.setToUserId(friendModel.getUserId());
				eventsModel.setEventId(1);

				// TODO: write to DB
				new WriteToDB(activity,
						((MainFragment) contentFragment).getHistoryModels(),
						((MainFragment) contentFragment).getHistoryList())
						.execute(user.getFriends().get(position), eventsModel);

				// new SendEventAsynTask(activity, friends.get(position),
				// view).execute(eventsModel);

				new SendEventAsynTask(activity,
						user.getFriends().get(position), view)
						.execute(eventsModel);
			} else {
				InfoDialog infoDialog = new InfoDialog(activity, 0,
						R.string.message_info_dialog_title,
						R.string.message_error_not_confirmed_yet);
				infoDialog.show();
			}
		}

	}

	private class MyOnItemLongClickListener implements OnItemLongClickListener {

		@SuppressLint("InflateParams")
		@Override
		public boolean onItemLongClick(AdapterView<?> adapterView, View view,
				int position, long id) throws ArrayIndexOutOfBoundsException {

			RelativeLayout customLayout = (RelativeLayout) getLayoutInflater()
					.inflate(R.layout.popup_friend_settings_layout, null);

			customLayout.findViewById(R.id.imageViewPopUpFrinedSettingsRemove)
					.setOnClickListener(
							new MyOnClickListener(user.getFriends().get(
									position)));
			customLayout.findViewById(R.id.imageViewPopUpFrinedSettingsEdit)
					.setOnClickListener(
							new MyOnClickListener(user.getFriends().get(
									position)));

			// TODO: show friends settings
			customQuickAction = new QuickAction(activity,
					R.style.PopupAnimation, R.drawable.popup_background,
					customLayout);

			// customQuickAction.show(view);
			customQuickAction.show(view, view.getMeasuredWidth(),
					view.getMeasuredHeight());

			return false;
		}

	}

	private class MyOnClickListener implements OnClickListener {
		private FriendModel friendModel;
		private ConfirmDialog dialogFragment;

		public MyOnClickListener() {
		}

		public MyOnClickListener(FriendModel friendModel) {
			this.friendModel = friendModel;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imageViewPopUpFrinedSettingsRemove:
				customQuickAction.dismiss();
				dialogFragment = new ConfirmDialog(
						activity,
						getString(R.string.message_confirm_dialog_title),
						String.format(
								getString(R.string.message_confirm_dialog_body),
								Utility.getFullName(friendModel.getFirstName(),
										friendModel.getLastName(),
										friendModel.getDisplayName())));
				dialogFragment.show();

				dialogFragment.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						if (view.getId() == R.id.buttonDialogConfirmOK) {
							new RemoveFriendAsyncTask(activity, menuAdapter,
									user).execute(friendModel);
							dialogFragment.dismiss();

						}
					}
				});

				break;

			case R.id.imageViewPopUpFrinedSettingsEdit:

				Toast.makeText(activity, "edit", Toast.LENGTH_SHORT).show();
				break;

			case R.id.imageButtonMainActivityAddFriend:

				Toast.makeText(activity, "Add Friend", Toast.LENGTH_SHORT)
						.show();
				Intent intent = new Intent(activity,
						ActiveUsersListActivity.class);
				// startActivity(intent);
				startActivityForResult(intent,
						BusinessConstants.RESULT_ADD_FRIEND);

				break;

			default:
				break;
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to

		if (requestCode == BusinessConstants.RESULT_ADD_FRIEND) {

			// Make sure the request was successful
			if (resultCode == RESULT_OK) {

				if (data.getExtras()
						.getBoolean(
								getString(R.string.INTENT_REQ_IS_FRIEND_REQUEST_CHANGED))) {
					// TODO: Friend Added refresh that

					user.addFriends(Utility.getUserInfo(activity).getFriends());

					menuAdapter.notifyDataSetChanged();

				}
			}
		}
	}
}
