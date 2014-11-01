package com.allfine.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.allfine.R;

public class CustomNetworkReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				context.getResources().getString(R.string._SP_ALL_FINE),
				Context.MODE_PRIVATE);
		int networkType = sharedPreferences.getInt(context.getResources()
				.getString(R.string._SP_NETWORK_TYPE),
				ConnectivityManager.TYPE_MOBILE);

		Intent intentService = null;

		if (networkType == ConnectivityManager.TYPE_MOBILE) {

//			intentService = new Intent(context, SynchService.class);

		} else if (networkInfo.getType() != ConnectivityManager.TYPE_WIFI) {

//			intentService = new Intent(context, SynchService.class);

		}

		if (intentService != null) {
			context.startService(intentService);
		}

	}
}
