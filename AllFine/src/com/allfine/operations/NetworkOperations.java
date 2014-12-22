package com.allfine.operations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.allfine.constants.BusinessConstants;
import com.allfine.constants.UrlConstants;
import com.allfine.enums.MessagesEnum;
import com.allfine.models.GCMInfoModel;
import com.allfine.models.core.ContactsModelList;
import com.allfine.models.core.CoreModel;
import com.allfine.models.core.DeleteFriendModel;
import com.allfine.models.core.EventsModel;
import com.allfine.models.core.ExistingContactsModelList;
import com.allfine.models.core.FriendRequestModel;
import com.allfine.models.core.UserModel;

public class NetworkOperations {

	private Context context;

	public NetworkOperations(Context context) {

		this.context = context;
	}

	private String postAndResponseString(String convertedModel, String url,
			int connectionTimeout, int businessDataTimeout)
			throws ClientProtocolException, IOException {

		Log.d("testA", "input : " + convertedModel);

		HttpPost httpPost = new HttpPost(url);

		StringEntity entity = new StringEntity(convertedModel, HTTP.UTF_8);
		httpPost.setEntity(entity);
		HttpClient httpClient = getClient(connectionTimeout,
				businessDataTimeout);
		HttpResponse response = httpClient.execute(httpPost);

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
		StringBuilder result = new StringBuilder();

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			result.append(line);
		}

		Log.d("testA", "output : " + result.toString());

		return result.toString();
	}

	private HttpClient getClient(int connectionTimeOut, int businessDataTimeout) {

		HttpClient httpClient = null;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				connectionTimeOut);
		HttpConnectionParams.setSoTimeout(httpParameters, businessDataTimeout);

		httpClient = new DefaultHttpClient(httpParameters);

		return httpClient;
	}

	private String convertHttps(String url) {
		return url;
	}

	public byte[] downloadsImgContent(String newsFeedContent)
			throws ClientProtocolException, IOException {

		byte[] data = null;

		HttpUriRequest request = new HttpGet(newsFeedContent);
		HttpClient httpClient = getClient(BusinessConstants.CONNECTION_TIMEOUT,
				BusinessConstants.BUSINESS_DATA_TIMEOUT);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();

		data = EntityUtils.toByteArray(entity);

		if (data != null && data.length > 0) {
			return data;
		} else {
			data = null;
		}

		return data;
	}

	// public UserModel activation(LoginAndRegistrationModel model) {
	//
	// model = (LoginAndRegistrationModel) SPProvider.initializeObject(model,
	// context);
	//
	// UserModel userModel = new UserModel();
	//
	// if (!Utility.checkNetwork(context)) {
	// userModel.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// userModel.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<LoginAndRegistrationModel> objectConvertorModel = new
	// ObjectConvertor<LoginAndRegistrationModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(model),
	// Utility.decrypt(UrlConstants.URL_ACTIVATION,
	// Utility.getAppSignature(context)),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<UserModel> objectConvertorUserModel = new
	// ObjectConvertor<UserModel>();
	// userModel = objectConvertorUserModel.getClassObject(result,
	// UserModel.class);
	//
	// return userModel;
	//
	// } catch (ClientProtocolException ex) {
	// userModel.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// } catch (IOException ex) {
	// userModel.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// }
	// }
	//
	// return userModel;
	// }

	// public UserModel login(LoginAndRegistrationModel model) {
	//
	// model = (LoginAndRegistrationModel) SPProvider.initializeObject(model,
	// context);
	//
	// UserModel userModel = new UserModel();
	//
	// if (!Utility.checkNetwork(context)) {
	// userModel.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// userModel.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<LoginAndRegistrationModel> objectConvertor = new
	// ObjectConvertor<LoginAndRegistrationModel>();
	//
	// String result = postAndResponseString(
	// objectConvertor.getClassString(model),
	// Utility.decrypt(UrlConstants.URL_LOGIN,
	// Utility.getAppSignature(context)),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<UserModel> objectConvertorUserModel = new
	// ObjectConvertor<UserModel>();
	// userModel = objectConvertorUserModel.getClassObject(result,
	// UserModel.class);
	//
	// /*
	// * Server common error converted Local Message for Login Process
	// */
	// if (userModel != null
	// && userModel.getMessageId() == MessageConstants.NOT_FOUND_DATA_ERROR) {
	// userModel
	// .setMessageId(MessageConstants.WRONG_PHONE_NUMBER_PASSWORD);
	// }
	//
	// return userModel;
	//
	// } catch (ClientProtocolException ex) {
	// userModel.setMessageId(MessageConstants.SERVER_ERROR_1);
	// } catch (IOException ex) {
	// userModel.setMessageId(MessageConstants.SERVER_ERROR_1);
	// }
	//
	// }
	//
	// return userModel;
	// }

	// public LoginAndRegistrationModel
	// resendActivation(LoginAndRegistrationModel model) {
	//
	// model = (LoginAndRegistrationModel) SPProvider.initializeObject(model,
	// context);
	//
	// if (!Utility.checkNetwork(context)) {
	// model.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// model.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<LoginAndRegistrationModel> objectConvertor = new
	// ObjectConvertor<LoginAndRegistrationModel>();
	//
	// String result = postAndResponseString(
	// objectConvertor.getClassString(model),
	// convertHttps(Utility.decrypt(
	// UrlConstants.URL_RESEND_ACTIVATION,
	// Utility.getAppSignature(context))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// model = objectConvertor.getClassObject(result,
	// LoginAndRegistrationModel.class);
	//
	// return model;
	//
	// } catch (ClientProtocolException ex) {
	// model.setMessageId(MessagesEnum.MI_SERVER_CONNECTION_PROBLEM.getId());
	// } catch (IOException ex) {
	// model.setMessageId(MessagesEnum.MI_SERVER_CONNECTION_PROBLEM.getId());
	// }
	// }
	//
	// return model;
	// }

	// public LoginAndRegistrationModel resendSms(LoginAndRegistrationModel
	// model) {
	//
	// model = (LoginAndRegistrationModel) SPProvider.initializeObject(model,
	// context);
	//
	// if (!Utility.checkNetwork(context)) {
	// model.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// model.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<LoginAndRegistrationModel> objectConvertor = new
	// ObjectConvertor<LoginAndRegistrationModel>();
	//
	// String result = postAndResponseString(
	// objectConvertor.getClassString(model),
	// Utility.decrypt(UrlConstants.URL_RESEND_SMS,
	// Utility.getAppSignature(context)),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// model = objectConvertor.getClassObject(result,
	// LoginAndRegistrationModel.class);
	//
	// return model;
	//
	// } catch (ClientProtocolException ex) {
	// model.setMessageId(MessagesEnum.MI_SERVER_CONNECTION_PROBLEM.getId());
	// } catch (IOException ex) {
	// model.setMessageId(MessagesEnum.MI_SERVER_CONNECTION_PROBLEM.getId());
	// }
	// }
	//
	// return model;
	// }

	// public UserModel forgotPassword(LoginAndRegistrationModel model) {
	//
	// model = (LoginAndRegistrationModel) SPProvider.initializeObject(model,
	// context);
	//
	// UserModel userModel = new UserModel();
	//
	// if (!Utility.checkNetwork(context)) {
	// userModel.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// userModel.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<LoginAndRegistrationModel> objectConvertorModel = new
	// ObjectConvertor<LoginAndRegistrationModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(model),
	// Utility.decrypt(UrlConstants.URL_FORGOT_PASSWORD,
	// Utility.getAppSignature(context)),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<UserModel> objectConvertorUserModel = new
	// ObjectConvertor<UserModel>();
	// userModel = objectConvertorUserModel.getClassObject(result,
	// UserModel.class);
	//
	// return userModel;
	//
	// } catch (ClientProtocolException ex) {
	// userModel.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// } catch (IOException ex) {
	// userModel.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// }
	// }
	//
	// return userModel;
	// }

	// public UserModel saveUserProfileData(UserModel model) {
	//
	// model = (UserModel) SPProvider.initializeObject(model, context);
	//
	// UserModel userModel = new UserModel();
	//
	// if (!Utility.checkNetwork(context)) {
	//
	// userModel.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// userModel.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<UserModel> objectConvertorModel = new
	// ObjectConvertor<UserModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(model),
	// convertHttps(Utility.decrypt(
	// UrlConstants.URL_SAVE_PROFILE_DATA,
	// Utility.getAppSignature(context))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<UserModel> objectConvertorUserModel = new
	// ObjectConvertor<UserModel>();
	// userModel = objectConvertorUserModel.getClassObject(result,
	// UserModel.class);
	//
	// return userModel;
	//
	// } catch (ClientProtocolException ex) {
	//
	// userModel.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// } catch (IOException ex) {
	// userModel.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// }
	// }
	//
	// return userModel;
	// }

	// public UserModel updateUserProfileData(CoreModel model) {
	//
	// model = (CoreModel) SPProvider.initializeObject(model, context);
	//
	// UserModel userModel = new UserModel();
	//
	// if (!Utility.checkNetwork(context)) {
	// userModel.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// userModel.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<CoreModel> objectConvertorModel = new
	// ObjectConvertor<CoreModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(model),
	// Utility.decrypt(UrlConstants.URL_PROFILE_BY_TOKEN,
	// Utility.getAppSignature(context)),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// Log.d("testTTT", "" + result.toString());
	// ObjectConvertor<UserModel> objectConvertorUserModel = new
	// ObjectConvertor<UserModel>();
	// userModel = objectConvertorUserModel.getClassObject(result,
	// UserModel.class);
	//
	// userModel.setMessageId(MessagesEnum.MI_SUCCESSFUL.getId());
	//
	// return userModel;
	//
	// } catch (ClientProtocolException ex) {
	// userModel.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// } catch (IOException ex) {
	// userModel.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// }
	// }
	//
	// return userModel;
	// }

	public CoreModel checkAppVersion(CoreModel model) {

		model = (CoreModel) SPProvider.initializeObject(model, context);

		if (!Utility.checkNetwork(context)) {
			model.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
		} else if (!Utility.checkInternetConnection()) {
			model.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
		} else {
			try {

				ObjectConvertor<CoreModel> objectConvertorModel = new ObjectConvertor<CoreModel>();

				String result = postAndResponseString(
						objectConvertorModel.getClassString(model),
						convertHttps(Utility.decrypt(
								UrlConstants.URL_CHECK_APP_VERSION,
								Utility.getAppSignature(context))), 4000, 4000);

				model = objectConvertorModel.getClassObject(result,
						CoreModel.class);

				return model;

			} catch (ClientProtocolException ex) {
				model.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
			} catch (IOException ex) {
				model.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
			}
		}

		return model;
	}

	// Friend User Profile Activity Model
	// public UserModel getFriendUserProfileActivityModel(CoreModel model) {
	//
	// model = (CoreModel) SPProvider.initializeObject(model, context);
	//
	// UserModel userModel = new UserModel();
	//
	// if (!Utility.checkNetwork(context)) {
	// userModel.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// userModel.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<CoreModel> objectConvertorModel = new
	// ObjectConvertor<CoreModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(model),
	// convertHttps(Utility.decrypt(
	// UrlConstants.URL_ACTIVITY_FRIEND_USER_PROFILE,
	// Utility.getAppSignature(context))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<UserModel> objectConvertorUserModel = new
	// ObjectConvertor<UserModel>();
	// userModel = objectConvertorUserModel.getClassObject(result,
	// UserModel.class);
	//
	// } catch (ClientProtocolException ex) {
	// userModel.setMessageId(MessageConstants.UN_SUCCESSFUL);
	// } catch (IOException ex) {
	// userModel.setMessageId(MessageConstants.UN_SUCCESSFUL);
	// }
	// }
	//
	// return userModel;
	// }

	// FRIENDS USER PROFILE ACTIVITY LIST
	// public ActivityModelArrayList getFriendUserProfileActivityList(CoreModel
	// model) {
	//
	// model = (CoreModel) SPProvider.initializeObject(model, context);
	//
	// ActivityModelArrayList activitymodelarraylist = new
	// ActivityModelArrayList();
	//
	// if (!Utility.checkNetwork(context)) {
	// activitymodelarraylist
	// .setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// activitymodelarraylist
	// .setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<CoreModel> objectConvertorModel = new
	// ObjectConvertor<CoreModel>();
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(model),
	// convertHttps(Utility
	// .decrypt(
	// UrlConstants.URL_ACTIVITY_FRIEND_USER_PROFILE_ACTIVITY,
	// Utility.getAppSignature(context))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<ActivityModelArrayList> objectConvertorUserModel = new
	// ObjectConvertor<ActivityModelArrayList>();
	// activitymodelarraylist = objectConvertorUserModel
	// .getClassObject(result, ActivityModelArrayList.class);
	//
	// } catch (ClientProtocolException ex) {
	// activitymodelarraylist
	// .setMessageId(MessageConstants.UN_SUCCESSFUL);
	// } catch (IOException ex) {
	// activitymodelarraylist
	// .setMessageId(MessageConstants.UN_SUCCESSFUL);
	// }
	// }
	//
	// return activitymodelarraylist;
	// }

	// public ActivityModelArrayList
	// getFriendsUserProfileActivityListPagination(PagingModel model) {
	//
	// model = (PagingModel) SPProvider.initializeObject(model, context);
	// ActivityModelArrayList activitymodelarraylist = new
	// ActivityModelArrayList();
	//
	// if (!Utility.checkNetwork(context)) {
	// activitymodelarraylist
	// .setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// activitymodelarraylist
	// .setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<CoreModel> objectConvertorModel = new
	// ObjectConvertor<CoreModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(model),
	// convertHttps(Utility
	// .decrypt(
	// UrlConstants.URL_ACTIVITY_FRIEND_USER_PROFILE_ACTIVITY_PAGINATION,
	// Utility.getAppSignature(context))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<ActivityModelArrayList> objectConvertorUserModel = new
	// ObjectConvertor<ActivityModelArrayList>();
	// activitymodelarraylist = objectConvertorUserModel
	// .getClassObject(result, ActivityModelArrayList.class);
	//
	// return activitymodelarraylist;
	//
	// } catch (ClientProtocolException ex) {
	// model.setMessageId(MessageConstants.UN_SUCCESSFUL);
	// } catch (IOException ex) {
	// model.setMessageId(MessageConstants.UN_SUCCESSFUL);
	// }
	// }
	//
	// return activitymodelarraylist;
	// }

	// public ActivityModelArrayList getUserProfileActivityList(CoreModel model)
	// {
	//
	// model = (CoreModel) SPProvider.initializeObject(model, context);
	//
	// ActivityModelArrayList activitymodelarraylist = new
	// ActivityModelArrayList();
	//
	// if (!Utility.checkNetwork(context)) {
	// activitymodelarraylist
	// .setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// activitymodelarraylist
	// .setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<CoreModel> objectConvertorModel = new
	// ObjectConvertor<CoreModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(model),
	// convertHttps(Utility.decrypt(
	// UrlConstants.URL_ACTIVITY_USER_PROFILE,
	// Utility.getAppSignature(context))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<ActivityModelArrayList> objectConvertorUserModel = new
	// ObjectConvertor<ActivityModelArrayList>();
	// activitymodelarraylist = objectConvertorUserModel
	// .getClassObject(result, ActivityModelArrayList.class);
	//
	// } catch (ClientProtocolException ex) {
	// activitymodelarraylist
	// .setMessageId(MessageConstants.UN_SUCCESSFUL);
	// } catch (IOException ex) {
	// activitymodelarraylist
	// .setMessageId(MessageConstants.UN_SUCCESSFUL);
	// }
	// }
	//
	// return activitymodelarraylist;
	// }

	// public ActivityModelArrayList
	// getUserProfileActivityListPagination(PagingModel model) {
	//
	// model = (PagingModel) SPProvider.initializeObject(model, context);
	// ActivityModelArrayList activitymodelarraylist = new
	// ActivityModelArrayList();
	//
	// if (!Utility.checkNetwork(context)) {
	// activitymodelarraylist
	// .setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// activitymodelarraylist
	// .setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<CoreModel> objectConvertorModel = new
	// ObjectConvertor<CoreModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(model),
	// convertHttps(Utility
	// .decrypt(
	// UrlConstants.URL_ACTIVITY_USER_PROFILE_PAGINATION,
	// Utility.getAppSignature(context))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<ActivityModelArrayList> objectConvertorUserModel = new
	// ObjectConvertor<ActivityModelArrayList>();
	// activitymodelarraylist = objectConvertorUserModel
	// .getClassObject(result, ActivityModelArrayList.class);
	//
	// return activitymodelarraylist;
	//
	// } catch (ClientProtocolException ex) {
	// model.setMessageId(MessageConstants.UN_SUCCESSFUL);
	// } catch (IOException ex) {
	// model.setMessageId(MessageConstants.UN_SUCCESSFUL);
	// }
	// }
	//
	// return activitymodelarraylist;
	// }

	// public RatingPointModelArrayList getTopRatings(CoreModel model) {
	//
	// model = (CoreModel) SPProvider.initializeObject(model, context);
	// RatingPointModelArrayList ratingPointModelArrayList = new
	// RatingPointModelArrayList();
	//
	// if (!Utility.checkNetwork(context)) {
	// ratingPointModelArrayList
	// .setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// ratingPointModelArrayList
	// .setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<CoreModel> objectConvertorModel = new
	// ObjectConvertor<CoreModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(model),
	// convertHttps(convertHttps(Utility.decrypt(
	// UrlConstants.URL_TOP_RATING,
	// Utility.getAppSignature(context)))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<RatingPointModelArrayList> objectConvertorUserModel = new
	// ObjectConvertor<RatingPointModelArrayList>();
	// ratingPointModelArrayList = objectConvertorUserModel
	// .getClassObject(result, RatingPointModelArrayList.class);
	//
	// } catch (ClientProtocolException ex) {
	// ratingPointModelArrayList
	// .setMessageId(MessageConstants.UN_SUCCESSFUL);
	// } catch (IOException ex) {
	// ratingPointModelArrayList
	// .setMessageId(MessageConstants.UN_SUCCESSFUL);
	// }
	// }
	//
	// return ratingPointModelArrayList;
	// }

	// public RatingPointModelArrayList getTopRatingsPagination(PagingModel
	// pagingModel) {
	//
	// pagingModel = (PagingModel) SPProvider.initializeObject(pagingModel,
	// context);
	// RatingPointModelArrayList ratingPointModelArrayList = new
	// RatingPointModelArrayList();
	//
	// if (!Utility.checkNetwork(context)) {
	// ratingPointModelArrayList
	// .setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// ratingPointModelArrayList
	// .setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<CoreModel> objectConvertorModel = new
	// ObjectConvertor<CoreModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(pagingModel),
	// convertHttps(Utility.decrypt(
	// UrlConstants.URL_TOP_RATING_PAGING,
	// Utility.getAppSignature(context))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<RatingPointModelArrayList> objectConvertorUserModel = new
	// ObjectConvertor<RatingPointModelArrayList>();
	// ratingPointModelArrayList = objectConvertorUserModel
	// .getClassObject(result, RatingPointModelArrayList.class);
	// } catch (ClientProtocolException ex) {
	// ratingPointModelArrayList
	// .setMessageId(MessageConstants.UN_SUCCESSFUL);
	// } catch (IOException ex) {
	// ratingPointModelArrayList
	// .setMessageId(MessageConstants.UN_SUCCESSFUL);
	// }
	// }
	//
	// return ratingPointModelArrayList;
	// }

	// public RatingPointModelArrayList pagingTopRating(PagingModel model) {
	//
	// model = (PagingModel) SPProvider.initializeObject(model, context);
	//
	// RatingPointModelArrayList ratingPointModelArrayList = new
	// RatingPointModelArrayList();
	//
	// if (!Utility.checkNetwork(context)) {
	// ratingPointModelArrayList
	// .setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// ratingPointModelArrayList
	// .setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	// ObjectConvertor<PagingModel> objectConvertorModel = new
	// ObjectConvertor<PagingModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(model),
	// Utility.decrypt(UrlConstants.URL_PROFILE_BY_TOKEN,
	// Utility.getAppSignature(context)),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<RatingPointModelArrayList> objectConvertorUserModel = new
	// ObjectConvertor<RatingPointModelArrayList>();
	// ratingPointModelArrayList = objectConvertorUserModel
	// .getClassObject(result, RatingPointModelArrayList.class);
	//
	// ratingPointModelArrayList
	// .setMessageId(MessagesEnum.MI_SUCCESSFUL.getId());
	//
	// return ratingPointModelArrayList;
	//
	// } catch (ClientProtocolException ex) {
	// ratingPointModelArrayList
	// .setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// } catch (IOException ex) {
	// ratingPointModelArrayList
	// .setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// }
	// }
	//
	// return ratingPointModelArrayList;
	// }

	// public AdvertismentModelArrayList getActiveAds(CoreModel modell) {
	//
	// modell = (CoreModel) SPProvider.initializeObject(modell, context);
	//
	// AdvertismentModelArrayList model = new AdvertismentModelArrayList();
	//
	// if (Utility.checkNetwork(context) && Utility.checkInternetConnection()) {
	// try {
	//
	// ObjectConvertor<CoreModel> objectConvertorModel = new
	// ObjectConvertor<CoreModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(modell),
	// convertHttps(Utility.decrypt(
	// UrlConstants.URL_ACTIVE_ADS,
	// Utility.getAppSignature(context))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<AdvertismentModelArrayList> objectConvertorUserModel =
	// new ObjectConvertor<AdvertismentModelArrayList>();
	//
	// model = objectConvertorUserModel.getClassObject(result,
	// AdvertismentModelArrayList.class);
	//
	// } catch (ClientProtocolException ex) {
	// model.setMessageId(MessageConstants.SERVER_ERROR_1);
	// } catch (IOException ex) {
	// model.setMessageId(MessageConstants.SERVER_ERROR_1);
	// } catch (Exception e) {
	// model.setMessageId(MessageConstants.SERVER_ERROR_1);
	// }
	// }
	//
	// return model;
	// }

	// public RatingInviteModelArrayList getRatingInvitesModel(CoreModel
	// coreModel) {
	//
	// coreModel = (CoreModel) SPProvider.initializeObject(coreModel, context);
	// RatingInviteModelArrayList ratingPointModelArrayList = new
	// RatingInviteModelArrayList();
	//
	// if (!Utility.checkNetwork(context)) {
	// ratingPointModelArrayList
	// .setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// ratingPointModelArrayList
	// .setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<CoreModel> objectConvertorModel = new
	// ObjectConvertor<CoreModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(coreModel),
	// Utility.decrypt(UrlConstants.URL_TOP_INVITER,
	// Utility.getAppSignature(context)),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<RatingInviteModelArrayList> objectConvertorUserModel = new ObjectConvertor<RatingInviteModelArrayList>();
	// ratingPointModelArrayList = objectConvertorUserModel.getClassObject(result, RatingInviteModelArrayList.class);
	//
	// } catch (ClientProtocolException ex) {
	// ratingPointModelArrayList.setMessageId(MessageConstants.UN_SUCCESSFUL);
	// } catch (IOException ex) {
	// ratingPointModelArrayList.setMessageId(MessageConstants.UN_SUCCESSFUL);
	// }
	// }
	//
	// return ratingPointModelArrayList;
	// }

	// public RatingInviteModelArrayList getRatingInvitesModelPaging(
	// PagingModel pagingModel) {
	//
	// pagingModel = (PagingModel) SPProvider.initializeObject(pagingModel,
	// context);
	//
	// RatingInviteModelArrayList ratingInviteModelArrayList = new
	// RatingInviteModelArrayList();
	//
	// if (!Utility.checkNetwork(context)) {
	// ratingInviteModelArrayList
	// .setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// ratingInviteModelArrayList
	// .setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	// ObjectConvertor<PagingModel> objectConvertorModel = new
	// ObjectConvertor<PagingModel>();
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(pagingModel),
	// convertHttps(Utility.decrypt(
	// UrlConstants.URL_TOP_INVITER_PAGING,
	// Utility.getAppSignature(context))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	// ObjectConvertor<RatingInviteModelArrayList> objectConvertorUserModel =
	// new ObjectConvertor<RatingInviteModelArrayList>();
	// ratingInviteModelArrayList = objectConvertorUserModel
	// .getClassObject(result,
	// RatingInviteModelArrayList.class);
	// } catch (ClientProtocolException ex) {
	// ratingInviteModelArrayList
	// .setMessageId(MessageConstants.UN_SUCCESSFUL);
	// } catch (IOException ex) {
	// ratingInviteModelArrayList
	// .setMessageId(MessageConstants.UN_SUCCESSFUL);
	// }
	// }
	//
	// return ratingInviteModelArrayList;
	// }

	// public CityModelArrayList getCities(CoreModel model) {
	//
	// model = (CoreModel) SPProvider.initializeObject(model, context);
	// CityModelArrayList cityModelArrayList = new CityModelArrayList();
	//
	// if (!Utility.checkNetwork(context)) {
	// cityModelArrayList
	// .setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// cityModelArrayList
	// .setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<CoreModel> objectConvertorModel = new
	// ObjectConvertor<CoreModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(model),
	// convertHttps(Utility.decrypt(
	// UrlConstants.URL_LOAD_CITIES,
	// Utility.getAppSignature(context))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<CityModelArrayList> objectConvertorUserModel = new
	// ObjectConvertor<CityModelArrayList>();
	// cityModelArrayList = objectConvertorUserModel.getClassObject(
	// result, CityModelArrayList.class);
	//
	// return cityModelArrayList;
	//
	// } catch (ClientProtocolException ex) {
	// model.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// } catch (IOException ex) {
	// model.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// }
	// }
	//
	// return cityModelArrayList;
	// }

	// public NewsFeedModelArrayList getNewsFeedPaging(PagingModel pagingModel)
	// {
	//
	// pagingModel = (PagingModel) SPProvider.initializeObject(pagingModel,
	// context);
	//
	// NewsFeedModelArrayList model = new NewsFeedModelArrayList();
	//
	// if (!Utility.checkNetwork(context)) {
	// model.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// model.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<PagingModel> objectConvertorModel = new
	// ObjectConvertor<PagingModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(pagingModel),
	// convertHttps(Utility.decrypt(
	// UrlConstants.URL_NEWS_FEED_PAGING,
	// Utility.getAppSignature(context))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<NewsFeedModelArrayList> objectConvertorUserModel = new
	// ObjectConvertor<NewsFeedModelArrayList>();
	// model = objectConvertorUserModel.getClassObject(result,
	// NewsFeedModelArrayList.class);
	//
	// Log.d("orxan", model.toString());
	// return model;
	//
	// } catch (ClientProtocolException ex) {
	// model.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// } catch (IOException ex) {
	// model.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// }
	// }
	//
	// return model;
	// }

	// public NewsFeedModelArrayList getNewsFeed(CoreModel coreModel) {
	//
	// coreModel = (CoreModel) SPProvider.initializeObject(coreModel, context);
	// NewsFeedModelArrayList model = new NewsFeedModelArrayList();
	//
	// if (!Utility.checkNetwork(context)) {
	// model.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// model.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<CoreModel> objectConvertorModel = new
	// ObjectConvertor<CoreModel>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(coreModel),
	// convertHttps(Utility.decrypt(
	// UrlConstants.URL_NEWS_FEED,
	// Utility.getAppSignature(context))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<NewsFeedModelArrayList> objectConvertorUserModel = new
	// ObjectConvertor<NewsFeedModelArrayList>();
	// model = objectConvertorUserModel.getClassObject(result,
	// NewsFeedModelArrayList.class);
	//
	// return model;
	//
	// } catch (ClientProtocolException ex) {
	// model.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// } catch (IOException ex) {
	// model.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// }
	// }
	//
	// return model;
	// }

	// public CoreModel uploadActionInfoModelArrayList(ActionInfoModelArrayList
	// modelList) {
	//
	// modelList = (ActionInfoModelArrayList) SPProvider.initializeObject(
	// modelList, context);
	//
	// CoreModel coreModel = new CoreModel();
	//
	// if (!Utility.checkNetwork(context)) {
	// coreModel.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
	// } else if (!Utility.checkInternetConnection()) {
	// coreModel.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
	// } else {
	// try {
	//
	// ObjectConvertor<ActionInfoModelArrayList> objectConvertorModel = new
	// ObjectConvertor<ActionInfoModelArrayList>();
	//
	// String result = postAndResponseString(
	// objectConvertorModel.getClassString(modelList),
	// convertHttps(Utility.decrypt(
	// UrlConstants.URL_UPLOAD_ACTION_INFO,
	// Utility.getAppSignature(context))),
	// BusinessConstants.CONNECTION_TIMEOUT,
	// BusinessConstants.BUSINESS_DATA_TIMEOUT);
	//
	// ObjectConvertor<CoreModel> objectConvertorUserModel = new
	// ObjectConvertor<CoreModel>();
	// coreModel = objectConvertorUserModel.getClassObject(result,
	// CoreModel.class);
	//
	// return coreModel;
	//
	// } catch (ClientProtocolException ex) {
	// coreModel.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// } catch (IOException ex) {
	// coreModel.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
	// }
	// }
	//
	// return coreModel;
	//
	// }

	public UserModel getUser(UserModel model) {

		model = (UserModel) SPProvider.initializeObject(model, context);

		if (!Utility.checkNetwork(context)) {
			model.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
		} else if (!Utility.checkInternetConnection()) {
			model.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
		} else {

			try {
				ObjectConvertor<UserModel> objectConvertor = new ObjectConvertor<UserModel>();
				String result = postAndResponseString(
						objectConvertor.getClassString(model),
						Utility.decrypt(UrlConstants.URL_LOGIN,
								Utility.getAppSignature(context)),
						BusinessConstants.CONNECTION_TIMEOUT,
						BusinessConstants.BUSINESS_DATA_TIMEOUT);

				model = objectConvertor.getClassObject(result, UserModel.class);
				return model;
			} catch (ClientProtocolException ex) {
				model.setMessageId(MessagesEnum.MI_SERVER_CONNECTION_PROBLEM
						.getId());
			} catch (IOException ex) {
				model.setMessageId(MessagesEnum.MI_SERVER_CONNECTION_PROBLEM
						.getId());
			}

		}
		return model;
	}

	public EventsModel sendEvent(EventsModel model) {

		model = (EventsModel) SPProvider.initializeObject(model, context);

		if (!Utility.checkNetwork(context)) {
			model.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
		} else if (!Utility.checkInternetConnection()) {
			model.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
		} else {

			try {
				ObjectConvertor<EventsModel> objectConvertor = new ObjectConvertor<EventsModel>();
				String result = postAndResponseString(
						objectConvertor.getClassString(model),
						Utility.decrypt(UrlConstants.URL_SEND_EVENT,
								Utility.getAppSignature(context)),
						BusinessConstants.CONNECTION_TIMEOUT,
						BusinessConstants.BUSINESS_DATA_TIMEOUT);

				// Log.d("result",result);
				model = objectConvertor.getClassObject(result,
						EventsModel.class);
				return model;
			} catch (ClientProtocolException ex) {
				model.setMessageId(MessagesEnum.MI_SERVER_CONNECTION_PROBLEM
						.getId());
			} catch (IOException ex) {
				model.setMessageId(MessagesEnum.MI_SERVER_CONNECTION_PROBLEM
						.getId());
			}

		}
		return model;
	}

	public GCMInfoModel sendGCMToServer(GCMInfoModel gcmInfoModel) {
		// TODO Auto-generated method stub
		return null;
	}

	public ExistingContactsModelList sendContactsAndGetUsers(Activity context,
			ContactsModelList model) {
		
		model = (ContactsModelList) SPProvider.initializeObject(model, context);
		
		ExistingContactsModelList existingModels = new ExistingContactsModelList();
		
		if (!Utility.checkNetwork(context)) {
			existingModels.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
		} else if (!Utility.checkInternetConnection()) {
			existingModels.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
		} else {
			try {

				ObjectConvertor<ContactsModelList> objectConvertorModel = new ObjectConvertor<ContactsModelList>();

				String result = postAndResponseString(
						objectConvertorModel.getClassString(model),
						convertHttps(Utility.decrypt(
								UrlConstants.URL_SEND_GET_CONTACT_LIST,
								Utility.getAppSignature(context))),
						BusinessConstants.CONNECTION_TIMEOUT,
						BusinessConstants.BUSINESS_DATA_TIMEOUT);

				ObjectConvertor<ExistingContactsModelList> objectConvertorUserModel = new ObjectConvertor<ExistingContactsModelList>();
				existingModels = objectConvertorUserModel.getClassObject(result,
						ExistingContactsModelList.class);

			} catch (ClientProtocolException ex) {
				existingModels.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
			} catch (IOException ex) {
				existingModels.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
			}
		}

		return existingModels;

	}

	public DeleteFriendModel deleteFriend(DeleteFriendModel model) {
		model = (DeleteFriendModel) SPProvider.initializeObject(model, context);

		if (!Utility.checkNetwork(context)) {
			model.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
		} else if (!Utility.checkInternetConnection()) {
			model.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
		} else {
			try {

				ObjectConvertor<DeleteFriendModel> objectConvertorModel = new ObjectConvertor<DeleteFriendModel>();

				String result = postAndResponseString(
						objectConvertorModel.getClassString(model),
						convertHttps(Utility.decrypt(
								UrlConstants.URL_DELETE_FRIEND,
								Utility.getAppSignature(context))), 4000, 4000);

				model = objectConvertorModel.getClassObject(result,
						DeleteFriendModel.class);

				return model;

			} catch (ClientProtocolException ex) {
				model.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
			} catch (IOException ex) {
				model.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
			}
		}

		return model;

	}

	public FriendRequestModel sendFriendRequest(FriendRequestModel model) {
		model = (FriendRequestModel) SPProvider.initializeObject(model, context);

		if (!Utility.checkNetwork(context)) {
			model.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
		} else if (!Utility.checkInternetConnection()) {
			model.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
		} else {
			try {

				ObjectConvertor<FriendRequestModel> objectConvertorModel = new ObjectConvertor<FriendRequestModel>();

				String result = postAndResponseString(
						objectConvertorModel.getClassString(model),
						convertHttps(Utility.decrypt(
								UrlConstants.URL_ADD_FRIEND,
								Utility.getAppSignature(context))), 4000, 4000);

				model = objectConvertorModel.getClassObject(result,
						FriendRequestModel.class);

				return model;

			} catch (ClientProtocolException ex) {
				model.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
			} catch (IOException ex) {
				model.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
			}
		}

		return model;
	}

	public CoreModel sendFriendRequestAnswer(FriendRequestModel model) {
		model = (FriendRequestModel) SPProvider
				.initializeObject(model, context);

		if (!Utility.checkNetwork(context)) {
			model.setMessageId(MessagesEnum.MI_NO_NETWORK_CONNECTION.getId());
		} else if (!Utility.checkInternetConnection()) {
			model.setMessageId(MessagesEnum.MI_NO_INTERNET_CONNECTION.getId());
		} else {
			try {

				ObjectConvertor<FriendRequestModel> objectConvertorModel = new ObjectConvertor<FriendRequestModel>();

				String result = postAndResponseString(
						objectConvertorModel.getClassString(model),
						convertHttps(Utility.decrypt(
								UrlConstants.URL_FRIEND_REQ_ANSWER,
								Utility.getAppSignature(context))), 4000, 4000);

				model = objectConvertorModel.getClassObject(result,
						FriendRequestModel.class);

				return model;

			} catch (ClientProtocolException ex) {
				model.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
			} catch (IOException ex) {
				model.setMessageId(MessagesEnum.MI_EXCEPTION_ERROR.getId());
			}
		}

		return model;
	}

}