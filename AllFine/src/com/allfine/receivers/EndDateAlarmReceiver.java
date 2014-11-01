package com.allfine.receivers;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.allfine.R;

public class EndDateAlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Bundle bundle = intent.getExtras();

		int hour = bundle.getInt(context.getResources().getString(
				R.string._B_EXPIRE_HOUR));
		int minute = bundle.getInt(context.getResources().getString(
				R.string._B_EXPIRE_MINUTE));

		Calendar calendar = Calendar.getInstance();

		int hourSystem = calendar.get(Calendar.HOUR_OF_DAY);
		int mininuteSystem = calendar.get(Calendar.MINUTE);

		if (hourSystem == hour && mininuteSystem <= (minute + 5)) {
//			Intent intentService = new Intent(context,
//					ClearExpireAdsService.class);
//			context.startService(intentService);
		}

	}

}
