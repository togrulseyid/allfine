package com.allfine.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.allfine.R;
import com.allfine.enums.MessagesEnum;
import com.allfine.models.core.UserModel;
import com.allfine.operations.NetworkOperations;
import com.allfine.operations.Utility;

public class SignInActivity extends ActionBarActivity {

	private Button signIn;
	private EditText phoneNumber;
	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		activity = this;

		signIn = (Button) findViewById(R.id.buttonSignInActivitySignIn);
		phoneNumber = (EditText) findViewById(R.id.editTextignInActivityUserNameOrPhone);
		signIn.setOnClickListener(new MyOnClickListener());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sign_in, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class MyOnClickListener implements OnClickListener {

		private UserModel userModel;

		public MyOnClickListener() {
			
		}

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.buttonSignInActivitySignIn:

				userModel = new UserModel();

				String text = phoneNumber.getText().toString().trim();

				if (Utility.isNumeric(text)) {
					userModel.setPhoneNumber(text);
				} else {
					userModel.setUserName(text);
				}

				// check();
				if (phoneNumber.getText().toString() != ""
						&& phoneNumber.getText().toString() != null) {

					SharedPreferences sharedPreferences = activity
							.getSharedPreferences(
									getResources().getString(
											R.string._SP_ALL_FINE),
									Context.MODE_PRIVATE);
					String regid = sharedPreferences.getString(getResources()
							.getString(R.string._SP_GCM_REG_ID), null);

					userModel.setGcm(regid);
					userModel.setImei(Utility
							.getDeviceId(getApplicationContext()));

					Log.d("testB", "" + userModel);

					new SignInAsynTask(activity).execute(userModel);

				}

				break;

			default:
				break;
			}
		}
	}

	private class SignInAsynTask extends
			AsyncTask<UserModel, Integer, UserModel> {

		private Activity activity;
		private DialogFragment dialog;

		public SignInAsynTask(Activity activity) {
			this.activity = activity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (dialog == null) {
				// dialog = new LoadingDialog();
				// dialog.show(getSupportFragmentManager(), "");
			}
		}

		@Override
		protected UserModel doInBackground(UserModel... userModels) {
			NetworkOperations networkOperations = new NetworkOperations(
					activity);
			return networkOperations.getUser(userModels[0]);
		}

		@Override
		protected void onPostExecute(UserModel result) {
			super.onPostExecute(result);

			if (!isCancelled()) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (result != null && result.getMessageId() != null) {

					if (result.getMessageId() == MessagesEnum.MI_SUCCESSFUL.getId()) {
						Utility.writeUserInfo(activity, result);

						SharedPreferences sharedPreferences = getSharedPreferences(
								getString(R.string._SP_ALL_FINE),
								Context.MODE_PRIVATE);

						sharedPreferences
								.edit()
								.putString(
										getResources().getString(
												R.string._SP_ROOTING_ACTIVITY),
										getResources()
												.getString(
														R.string._ROOTING_MAIN_ACTIVITY))
								.commit();

						Intent intent = new Intent(activity, MainActivity.class);
						startActivity(intent);
						SignInActivity.this.activity.finish();
						finish();
					}

				} else {
					// dialog = new InformativeDialog(result.getMessageId());
					// dialog.show(getFragmentManager(), "");
				}

			} else if (result.getMessageId() == MessagesEnum.MI_TOKEN_ERROR.getId() ) {

				Utility.finishForToken(activity);

			} else {
				// dialog = new
				// InformativeDialog(MessageConstants.SERVER_ERROR_1);
				// dialog.show(getSupportFragmentManager(), "");
			}

		}

	}

	// /**
	// * Registers the application with GCM servers asynchronously.
	// * <p>
	// * Stores the registration ID and the app versionCode in the application's
	// * shared preferences.
	// */
	//
	// class RegisterInBackgroundAsyncTask extends AsyncTask<Void, Void, Void> {
	//
	// @Override
	// protected Void doInBackground(Void... params) {
	// String msg = "";
	//
	// try {
	//
	// // if (gcm == null) {
	// // gcm = GoogleCloudMessaging.getInstance(activity);
	// // }
	//
	// regid = gcm.register(SENDER_ID);
	// Log.d("testA", "" + regid);
	// msg = "Device registered, registration ID=" + regid;
	//
	// // Send the registration ID to your server over HTTP, so it can
	// // use GCM/HTTP or CCS to send messages to your app.
	//
	// // get Telephone IMEI address
	// TelephonyManager telephonyManager = (TelephonyManager)
	// getSystemService(Context.TELEPHONY_SERVICE);
	// String imei = telephonyManager.getDeviceId();
	// GCMInfoModel gcmModel = new GCMInfoModel();
	// gcmModel.setGcm(regid);
	// gcmModel.setImei(imei);
	//
	// new StartAsynTask().execute(gcmModel);
	//
	// // Persist the regID - no need to register again.
	// Utility.storeRegistrationId(activity, regid);
	//
	// } catch (IOException ex) {
	// msg = "Error :" + ex.getMessage();
	// // If there is an error, don't just keep trying to register.
	// // Require the user to click a button again, or perform
	// // exponential back-off.
	// }
	//
	// Log.d(TAG, "" + msg);
	// return null;
	// }
	//
	// }

	// private class StartAsynTask extends
	// AsyncTask<GCMInfoModel, Integer, GCMInfoModel> {
	//
	// @Override
	// protected GCMInfoModel doInBackground(GCMInfoModel... params) {
	// NetworkOperations networkOperations = new NetworkOperations(
	// getApplicationContext());
	// return networkOperations.sendGCMToServer(params[0]);
	// }
	//
	// @Override
	// protected void onPostExecute(GCMInfoModel result) {
	// super.onPostExecute(result);
	//
	// Log.d("iemi", "" + result);
	//
	// if (result != null && result.getMessageId() != null) {
	//
	// if (result.getMessageId() == MessageConstants.SUCCESSFUL) {
	//
	// // TODO: Write to pref that GCM has been send to server
	// Utility.writeGCMToSharedPreferences(
	// getApplicationContext(), result.getGcm());
	//
	// // new CheckAppVersionAsynTask().execute();
	//
	// } else {
	// // TODO: Error Occurred
	// }
	//
	// } else {
	// // TODO: No Internet Connection
	// }
	// }
	//
	// }

	// private void check() {
	// if (Utility.isSendGCMToServer(getApplicationContext())) {
	//
	// } else {
	// // Check device for Play Services APK. If check succeeds, proceed
	// // with GCM registration.
	//
	// if (Utility.checkPlayServices(this)) {
	//
	// // TODO: check if send to server
	// gcm = GoogleCloudMessaging.getInstance(this);
	//
	// regid = Utility.getRegistrationId(activity);
	// Log.d("testA", "" + regid);
	//
	// if (regid.isEmpty()) {
	//
	// new RegisterInBackgroundAsyncTask().execute();
	//
	// } else {
	// // new CheckAppVersionAsynTask().execute();
	// }
	//
	// } else {
	// }
	// }
	// }
}
