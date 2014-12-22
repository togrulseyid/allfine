package com.allfine.services;

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
import com.allfine.activities.ActiveUserProfileActivity;
import com.allfine.activities.MainActivity;
import com.allfine.enums.ObjectTypeEnum;
import com.allfine.models.NotificationModel;
import com.allfine.models.core.FriendRequestModel;
import com.allfine.models.core.GoogleCloudMessagingModel;
import com.allfine.models.core.SerializableObject;
import com.allfine.operations.ObjectConvertor;
import com.allfine.operations.Utility;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
	// private NotificationModel model = null;
	GoogleCloudMessaging gcm;
	private int objectType = -1;

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.

		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */

			ObjectConvertor<GoogleCloudMessagingModel> googleCloudMessagingModelConvertor = new ObjectConvertor<GoogleCloudMessagingModel>();
			String dataX = Utility.decrypt(extras.get("data").toString(),
					Utility.getAppSignature(getApplicationContext()));

			try {
				GoogleCloudMessagingModel googleCloudMessagingModel = googleCloudMessagingModelConvertor
						.getClassObject(dataX, GoogleCloudMessagingModel.class);
				objectType = googleCloudMessagingModel.getObjectType();

				ObjectNode objectNode = googleCloudMessagingModelConvertor
						.getObjectNode();
				
				if (googleCloudMessagingModel.getObjectType() == ObjectTypeEnum.FRIEND_REQUEST_MODEL
						.getId()) {

					ObjectConvertor<FriendRequestModel> objectConvertor = new ObjectConvertor<FriendRequestModel>();
					
					FriendRequestModel model = objectConvertor.getClassObject(
							objectNode.get("object").toString(),
							FriendRequestModel.class);
					
					Log.d("testA", "data1: " + model.toString());
					
					friendRequestReceiver(model, intent);
				} else if (googleCloudMessagingModel.getObjectType() == ObjectTypeEnum.NOTIFICATION_MODEL
						.getId()) {

					ObjectConvertor<NotificationModel> objectConvertor = new ObjectConvertor<NotificationModel>();
					
					NotificationModel model = objectConvertor.getClassObject(
							objectNode.get("object").toString(),
							NotificationModel.class);
					
					Log.d("testA", "data1: " + model.toString());
					
					notificationRequestReceiver(model, intent);
					
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void notificationRequestReceiver(
			NotificationModel notificationModel, Intent intent) {
		String messageType = gcm.getMessageType(intent);

		String message = notificationModel.getUserId() + " sent "
				+ notificationModel.getEventId();
		notificationModel.setMessage(message);

		if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
			notificationModel.setMessage("Send error");
			sendNotification(notificationModel);
			// sendNotification("Send error: " + extras.toString());
		} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
				.equals(messageType)) {

			notificationModel.setMessage("Deleted messages on server: "
					+ notificationModel.getMessage());
			sendNotification(notificationModel);
			// sendNotification("Deleted messages on server: " +
			// extras.toString());
			// If it's a regular GCM message, do some work.
		} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
				.equals(messageType)) {

			// This loop represents the service doing some work.

			sendNotification(notificationModel); // Received e message
		}

	}

	private void friendRequestReceiver(FriendRequestModel model, Intent intent) {
		String messageType = gcm.getMessageType(intent);

		// String message = model.getUserId() + " sent friend request";
		String message = model.getUserName() + " sent friend request";

		if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
			 model.setMessage("Send error");
			 sendNotification( R.drawable.ic_launcher, "Send error: " , message, null);
		} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
				.equals(messageType)) {

			 model.setMessage("Deleted messages on server: "  + model.getMessage());
			 sendNotification(R.drawable.ic_launcher, "Deleted messages on server", message, null);
			 // sendNotification("Deleted messages on server: " +
			 // extras.toString());
			 // If it's a regular GCM message, do some work.
		} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
				.equals(messageType)) {

			// This loop represents the service doing some work.

			// sendNotification(model); // Received e message

			SerializableObject<FriendRequestModel> obj = new SerializableObject<FriendRequestModel>();
			obj.setObj(model);
			sendNotification(R.drawable.ic_launcher, "Friend Request", message, obj);
		}

	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private void sendNotification(NotificationModel model) {

		// LocalSettingModel localModel = Utility
		// .getLocalSettingModel(getApplicationContext());
		// Log.i("Utility", localModel.toString());

//		long[] vibrationPattern = { 0, 100, 1000, 1000, 2000 };

		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent intent = new Intent(this, MainActivity.class);
		
		if(objectType == ObjectTypeEnum.FRIEND_REQUEST_MODEL.getId()) {
			intent = new Intent(this, ActiveUserProfileActivity.class);

		} else if(objectType == ObjectTypeEnum.NOTIFICATION_MODEL.getId()) {
			intent = new Intent(this, MainActivity.class);
		}
		
		
		
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
//		mBuilder.setVibrate(vibrationPattern);
		// if (localModel.isNotification() && localModel.isVibrate()) {
		// mBuilder.setVibrate(vibrationPattern);
		// }

		mBuilder.setContentIntent(pendingIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private void sendNotification(int icon, String title, String message, SerializableObject obj) {//, int type

		// LocalSettingModel localModel = Utility
		// .getLocalSettingModel(getApplicationContext());
		// Log.i("Utility", localModel.toString());

//		long[] vibrationPattern = { 0, 100, 1000, 1000, 2000 };

		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent notificationIntent = new Intent(this, ActiveUserProfileActivity.class);
		
		Bundle bundle = new Bundle();
		
		bundle.putSerializable(
				getResources().getString(R.string._B_NOTIFICATION_OBJECT), obj);
		
		bundle.putInt(getResources().getString(R.string._B_TYPE_OF_REQUEST),
				ObjectTypeEnum.FRIEND_REQUEST_MODEL.getId());
		
		
		notificationIntent.putExtras(bundle);
		
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this)
				.setSmallIcon(icon)
				.setContentTitle(title)
				.setContentText(message)
				.setAutoCancel(true)
				.setWhen(System.currentTimeMillis())
				.setSound(
						RingtoneManager
								.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
				.setStyle(
						new NotificationCompat.InboxStyle()
								.setBigContentTitle(message));
//		mBuilder.setVibrate(vibrationPattern);
		// if (localModel.isNotification() && localModel.isVibrate()) {
		// mBuilder.setVibrate(vibrationPattern);
		// }

		mBuilder.setContentIntent(pendingIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

	}

}
