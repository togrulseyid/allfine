//package com.allfine.asynctask;
//
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import android.app.Activity;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.allfine.adapters.MainActivitySlideMenuAdapter;
//import com.allfine.db.DBOperations;
//import com.allfine.exceptions.DataBaseException;
//import com.allfine.models.UserEventsHistoryModel;
//import com.allfine.models.core.EventsModel;
//import com.allfine.models.core.FriendModel;
//import com.allfine.operations.Utility;
//
//public class WriteToDB extends AsyncTask<Object, Void, Long> {
//	private Activity activity;
//	private MainActivitySlideMenuAdapter menuAdapter;
//
//	public WriteToDB(Activity activity) {
//		this.activity = activity;
//	}
//
//	public WriteToDB(Activity activity, MainActivitySlideMenuAdapter menuAdapter) {
//		this.menuAdapter = menuAdapter;
//		this.activity = activity;
//	}
//
//	@Override
//	protected Long doInBackground(Object... params) {
//		FriendModel friendModel = (FriendModel) params[0];
//		EventsModel eventModel = (EventsModel) params[1];
//
//		if (friendModel != null && eventModel != null) {
//			DBOperations dbOperations = DBOperations.instance(activity);
//			UserEventsHistoryModel model = new UserEventsHistoryModel();
//
//			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
//
//			model.setSenderId(friendModel.getUserId());
//			model.setSenderFirstName(friendModel.getFirstName());
//			model.setSenderLastName(friendModel.getLastName());
//			model.setDisplayName(friendModel.getDisplayName());
//			model.setDate(dateFormat.format(new Date(new Timestamp(Long
//					.parseLong(eventModel.getTimeStmp())).getTime())));
//			model.setHistoryId(eventModel.getTimeStmp()
//					+ Utility.getUserId(activity) + friendModel.getUserId());
//			model.setStatus(1);
//
//			try {
//				return dbOperations.insertIntoEventsHistory(activity, model);
//			} catch (DataBaseException e) {
//				e.printStackTrace();
//			}
//
//		}
//		return (long) -1;
//	}
//
//	@Override
//	protected void onPostExecute(Long result) {
//		super.onPostExecute(result);
//
//		if (!isCancelled() && !result.equals(-1)) {
//			Log.d("menuAdapter", "result: " + result);
//			menuAdapter.notifyDataSetChanged();
//		}
//	}
//
//}
