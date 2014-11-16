package com.allfine.activities;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.allfine.constants.BusinessConstants;
import com.allfine.fragments.MainFragment;
import com.allfine.models.core.EventsModel;
import com.allfine.models.core.UserModel;
import com.allfine.operations.NetworkOperations;
import com.allfine.operations.Utility;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.togrulseyid.quickaction.library.QuickAction;

public class MainActivity extends ActionBarActivity {

	private SlidingMenu slidingMenu;
	private MainActivitySlideMenuAdapter menuAdapter;
	private Activity activity;
	private UserModel user;

	// Menu
	private QuickAction customQuickAction;
	private ListView listViewMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		activity = this;

		user = Utility.getUserInfo(activity);

		makeMenu();

		listViewMenu = (ListView) findViewById(R.id.listViewListSlidingMenuFriendsProfiles);

		MainActivitySlideMenuAdapter menuAdapter = new MainActivitySlideMenuAdapter(
				this, user.getFriends());

		if (user.getFriends().size() < BusinessConstants.MAX_USER_FRIEND) {

			View footerView = getLayoutInflater().inflate(
					R.layout.footer_add_friend_layout, null);

			listViewMenu.addFooterView(footerView, null, true);

			footerView.findViewById(R.id.imageButtonMainActivityAddFriend)
					.setOnClickListener(new MyOnClickListener());

			Log.d("testA", "added footer");
		}

		listViewMenu.setAdapter(menuAdapter);

		listViewMenu.setOnItemClickListener(new MyOnItemClickListener(user));
		listViewMenu
				.setOnItemLongClickListener(new MyOnItemLongClickListener());

		@SuppressLint("InflateParams")
		RelativeLayout customLayout = (RelativeLayout) getLayoutInflater()
				.inflate(R.layout.popup_reached_layout, null);

		customQuickAction = new QuickAction(this, R.style.PopupAnimation,
		// R.drawable.ic_notification_menu_edge,
				R.drawable.popup_background, customLayout);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment(slidingMenu, user))
					.commit();
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

	private class SendEventAsynTask extends
			AsyncTask<EventsModel, Void, EventsModel> {
		private Activity activity;
		private View view;

		public SendEventAsynTask(Activity activity, View view) {
			this.activity = activity;
			this.view = view;
		}

		@Override
		protected EventsModel doInBackground(EventsModel... params) {
			NetworkOperations networkOperations = new NetworkOperations(
					activity);
			return networkOperations.sendEvent(params[0]);
		}

		@Override
		protected void onPostExecute(EventsModel result) {
			super.onPostExecute(result);

			Log.d("testA", result.toString());

			if (result != null) {
				// TODO: Make User Friend Image Border Color Different
				CircularImageView firendsPhoto = (CircularImageView) view
						.findViewById(R.id.circularImageViewLISlidingMenuFriend);

				firendsPhoto.setBorderColor(getResources().getColor(
						R.color.color_all_fine_main_color));
				Log.d("testA", "firendsPhoto color change");

				customQuickAction.show(view);
			}
		}

	}

	private class MyOnItemClickListener implements OnItemClickListener {
		private UserModel user;

		public MyOnItemClickListener(UserModel user) {
			this.user = user;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {

			EventsModel eventsModel = new EventsModel();
			eventsModel.setTimeStmp("" + new Date().getTime());
			eventsModel
					.setToUserId(user.getFriends().get(position).getUserId());
			eventsModel.setEventId(1);
			new SendEventAsynTask(activity, view).execute(eventsModel);
		}

	}

	private class MyOnItemLongClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> adapterView, View view,
				int position, long id) {

			Log.d("testA", "position: " + position);

			RelativeLayout customLayout = (RelativeLayout) getLayoutInflater()
					.inflate(R.layout.popup_friend_settings_layout, null);

			customLayout.findViewById(R.id.imageViewPopUpFrinedSettingsRemove)
					.setOnClickListener(new MyOnClickListener());
			customLayout.findViewById(R.id.imageViewPopUpFrinedSettingsEdit)
					.setOnClickListener(new MyOnClickListener());

			// TODO: show friends settings
			customQuickAction = new QuickAction(activity,
					R.style.PopupAnimation, R.drawable.popup_background,
					customLayout);
			customQuickAction.show(view);

			return false;
		}

	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imageViewPopUpFrinedSettingsRemove:
				Toast.makeText(activity, "remove", Toast.LENGTH_SHORT).show();
				break;

			case R.id.imageViewPopUpFrinedSettingsEdit:
				Toast.makeText(activity, "edit", Toast.LENGTH_SHORT).show();
				break;

			case R.id.imageButtonMainActivityAddFriend:
				
				Toast.makeText(activity, "Add Friend", Toast.LENGTH_SHORT)
						.show();
				Intent intent = new Intent(activity, AddFriendActivity.class);
				startActivity(intent);
				
				break;

			default:
				break;
			}
		}

	}
}
