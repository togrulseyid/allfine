package com.allfine.activities;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allfine.R;
import com.allfine.adapters.MainActivitySlideMenuAdapter;
import com.allfine.fragments.MainFragment;
import com.allfine.models.core.EventsModel;
import com.allfine.models.core.UserModel;
import com.allfine.operations.NetworkOperations;
import com.allfine.operations.Utility;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.togrulseyid.quickaction.library.QuickAction;

public class MainActivity extends ActionBarActivity {

	private SlidingMenu slidingMenu;
	private MainActivitySlideMenuAdapter menuAdapter;
	private Activity activity;
	private UserModel user;

	// Menu
	private QuickAction customQuickAction;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		activity = this;

		user = Utility.getUserInfo(activity);

		makeMenu();

		listView = (ListView) findViewById(R.id.listViewListSlidingMenuFriendsProfiles);

		MainActivitySlideMenuAdapter menuAdapter = new MainActivitySlideMenuAdapter(
				this, user.getFriends());
		listView.setAdapter(menuAdapter);

		listView.setOnItemClickListener(new MyOnItemClickListener(user));

		@SuppressLint("InflateParams")
		RelativeLayout customLayout = (RelativeLayout) getLayoutInflater()
				.inflate(R.layout.popup_reached_layout, null);
		
		customQuickAction = new QuickAction(this, R.style.PopupAnimation,
				R.drawable.ic_notification_menu_edge, R.drawable.popup_background,
				customLayout);

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
			Toast.makeText(getApplicationContext(), "Naqaracuqa ? :)",
					Toast.LENGTH_SHORT).show();
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

}
