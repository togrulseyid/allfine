package com.allfine.activities;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.allfine.R;
import com.allfine.constants.BusinessConstants;
import com.allfine.enums.MessagesEnum;
import com.allfine.models.core.CoreModel;
import com.allfine.operations.NetworkOperations;
import com.allfine.operations.Utility;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.squareup.picasso.Picasso;

public class RootActivity extends Activity {
	private Activity activity;

	private GcmGenerateAsynchTask gcmGenerateAsynchTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;

		// Remove title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// sets layout
		setContentView(R.layout.activity_root);

		// Initializes controls and set action and content
		ImageView imageViewSplashScreen = (ImageView) findViewById(R.id.imageViewSplashScreen);

		Picasso.with(activity).load(R.drawable.background_start_activity)
				.into(imageViewSplashScreen);

	}

	@Override
	protected void onDestroy() {
		if (gcmGenerateAsynchTask != null
				&& !gcmGenerateAsynchTask.isCancelled()) {
			gcmGenerateAsynchTask.cancel(true);
		}
		super.onDestroy();
	}

	@Override
	protected void onResume() {

		gcmGenerateAsynchTask = new GcmGenerateAsynchTask(activity);
		gcmGenerateAsynchTask.execute();

		super.onResume();
	}

	private class GcmGenerateAsynchTask extends
			AsyncTask<Void, Void, CoreModel> {

		private Activity activity;

		public GcmGenerateAsynchTask(Activity activity) {
			this.activity = activity;
		}

		@Override
		protected CoreModel doInBackground(Void... params) {

			SharedPreferences sharedPreferences = activity
					.getSharedPreferences(
							getResources().getString(R.string._SP_ALL_FINE),
							Context.MODE_PRIVATE);
			String regid = sharedPreferences.getString(getResources()
					.getString(R.string._SP_GCM_REG_ID), null);

			if (regid == null) {

				try {

					GoogleCloudMessaging gcm = GoogleCloudMessaging
							.getInstance(activity);

					if (gcm != null) {
						regid = gcm.register(Utility.decrypt(
								BusinessConstants.GCM_PROJECT_NUMBER,
								Utility.getAppSignature(activity)));

						Log.d("testA", "" + regid);
						if (regid != null) {
							sharedPreferences
									.edit()
									.putString(
											getResources().getString(
													R.string._SP_GCM_REG_ID),
											regid).commit();
						}
					}

				} catch (IOException ex) {

				}

			}

			CoreModel coreModel = null;
			try {
				// Thread.sleep(BusinessConstants.SLEEP_ROOT_ACTIVITY_TIME_MILLI_SECOND);
				NetworkOperations networkOperations = new NetworkOperations(
						activity);
				coreModel = networkOperations.checkAppVersion(new CoreModel());
			} catch (Exception e) {
				e.printStackTrace();
			}

			return coreModel;

		}

		@Override
		protected void onPostExecute(CoreModel result) {
			// TODO change locale according to SahredPrefrences before starting
			// other activities

			if (!isCancelled()) {

				SharedPreferences sharedPreferences = activity
						.getSharedPreferences(
								getResources().getString(R.string._SP_ALL_FINE),
								Context.MODE_PRIVATE);

				String rootingActivity = sharedPreferences
						.getString(
								getResources().getString(
										R.string._SP_ROOTING_ACTIVITY),
								getResources().getString(
										R.string._ROOTING_START_ACTIVITY));

				Log.d("rootingActivity", "" + rootingActivity);

				if (result != null
						&& result.getMessageId() != null
						&& result.getMessageId() == MessagesEnum.MI_APP_VERSION_INCORRECT
								.getId()) {
					rootingActivity = getResources().getString(
							R.string._ROOTING_FORCE_UPDATE_ACTIVITY);
				}

				Intent intent = null;

				if (rootingActivity.equals(getResources().getString(
						R.string._ROOTING_START_ACTIVITY))) {

					intent = new Intent(activity, StartActivity.class);

				} else if (rootingActivity.equals(getResources().getString(
						R.string._ROOTING_MAIN_ACTIVITY))) {

					Bundle bundle = new Bundle();
					bundle.putInt(
							getResources().getString(R.string._B_REQUEST),
							Integer.parseInt(getResources().getString(
									R.string._REQUEST_FROM_ROOT)));
					intent = new Intent(activity, MainActivity.class);
					intent.putExtras(bundle);

				} else if (rootingActivity.equals(getResources().getString(
						R.string._ROOTING_FORCE_UPDATE_ACTIVITY))) {

					intent = new Intent(activity, ActivityUpdateApp.class);

				} else {
					intent = new Intent(activity, StartActivity.class);
				}

				if (intent != null) {
					startActivity(intent);
					finish();
				}
			}
		}
	}

}
