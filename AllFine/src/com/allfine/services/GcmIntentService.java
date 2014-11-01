package com.allfine.services;

import java.io.IOException;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.allfine.R;
import com.allfine.activities.MainActivity;
import com.allfine.models.NotificationModel;
import com.allfine.operations.ObjectConvertor;
import com.allfine.operations.Utility;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	public static final String TAG = "GCM Demo";
	private NotificationModel model = null;

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */

			ObjectConvertor<NotificationModel> objectConvertor = new ObjectConvertor<NotificationModel>();

			try {
				String data = Utility.decrypt(extras.get("data").toString(),
						Utility.getAppSignature(getApplicationContext()));

				Log.i("extrasX", "data: " + data);

				model = objectConvertor.getClassObject(data,
						NotificationModel.class);

				String message = model.getUserId() + " sent " + model.getEventId();
				model.setMessage(message);

			} catch (IOException e) {
				model.setMessage("Error Occured");
				e.printStackTrace();
			}

			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				model.setMessage("Send error");
				sendNotification(model);
				// sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {

				model.setMessage("Deleted messages on server: "
						+ model.getMessage());
				sendNotification(model);
				// sendNotification("Deleted messages on server: " +
				// extras.toString());
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {

				// This loop represents the service doing some work.

				sendNotification(model); // Received e message
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private void sendNotification(NotificationModel model) {

		// LocalSettingModel localModel = Utility
		// .getLocalSettingModel(getApplicationContext());
		// Log.i("Utility", localModel.toString());

		long[] vibrationPattern = { 0, 100, 1000, 1000, 2000 };

		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent intent = new Intent(this, MainActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(
				getResources().getString(R.string._B_NOTIFICATION_OBJECT),
				model);
		intent.putExtras(bundle);

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Kamran sent ")
				.setContentText(model.getMessage())
				.setAutoCancel(true)
				.setWhen(System.currentTimeMillis())
				.setSound(
						RingtoneManager
								.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
				.setStyle(
						new NotificationCompat.InboxStyle()
								.setBigContentTitle(model.getMessage()));
		mBuilder.setVibrate(vibrationPattern);
		// if (localModel.isNotification() && localModel.isVibrate()) {
		// mBuilder.setVibrate(vibrationPattern);
		// }

		mBuilder.setContentIntent(pendingIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

	}

}
