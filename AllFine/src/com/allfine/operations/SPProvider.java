package com.allfine.operations;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.allfine.R;
import com.allfine.models.core.CoreModel;

public class SPProvider {

	private Context context;
	private Resources resources;

	public SPProvider(Context context) {
		this.context = context;
		resources = this.context.getResources();
	}

	// public UserModel loadUserModel() {
	//
	// UserModel model = new UserModel();
	//
	// SharedPreferences sharedPreferences = context.getSharedPreferences(
	// resources.getString(R.string._SP_MANADS), Context.MODE_PRIVATE);
	//
	// model.setToken(sharedPreferences.getString(
	// resources.getString(R.string._SP_TOKEN), null));
	//
	// model.setUserId(Integer.parseInt(sharedPreferences.getString(
	// resources.getString(R.string._SP_USERID), "0")));
	//
	// model.setUserName(sharedPreferences.getString(
	// resources.getString(R.string._SP_USERNAME),
	// Integer.toString(MessageConstants.EMPTY_USERNAME)));
	//
	// model.setFirstName(sharedPreferences.getString(
	// resources.getString(R.string._SP_FIRST_NAME), ""));
	//
	// model.setLastName(sharedPreferences.getString(
	// resources.getString(R.string._SP_LAST_NAME), ""));
	//
	// model.setEmail(sharedPreferences.getString(
	// resources.getString(R.string._SP_EMAIL), ""));
	//
	// model.setBirthday(sharedPreferences.getString(
	// resources.getString(R.string._SP_BIRTHDAY), null));
	//
	// model.setUserGender(Integer.parseInt(sharedPreferences.getString(
	// resources.getString(R.string._SP_USER_GENDER_CODE), "1")));
	//
	// model.setVirtualCard(sharedPreferences.getString(
	// resources.getString(R.string._SP_VIRTUAL_CARD), ""));
	//
	// model.setFriendCount(Integer.parseInt(sharedPreferences.getString(
	// resources.getString(R.string._SP_FRIENDS_COUNT), "0")));
	//
	// model.setTotalBalace(new BigDecimal(sharedPreferences.getString(
	// resources.getString(R.string._SP_BALANCE), "0")));
	//
	// model.setTotalPoints(Integer.parseInt(sharedPreferences.getString(
	// resources.getString(R.string._SP_POINTS), "0")));
	//
	// model.setCountry(sharedPreferences.getString(
	// resources.getString(R.string._SP_COUNTRY), ""));
	//
	// model.setCity(sharedPreferences.getString(
	// resources.getString(R.string._SP_CITY_NAME), ""));
	//
	// model.setCityId(Integer.parseInt(sharedPreferences.getString(
	// resources.getString(R.string._SP_CITY_ID), "0")));
	//
	// model.setInviterId(Integer.parseInt(sharedPreferences.getString(
	// resources.getString(R.string._SP_INVITER_ID), "0")));
	//
	// model.setInviterUserName(sharedPreferences.getString(
	// resources.getString(R.string._SP_INVITER_USERNAME), ""));
	//
	// model.setShowPhoneNumber(Integer.parseInt(sharedPreferences.getString(
	// resources.getString(R.string._SP_SHOW_NUMBER_STATUS),
	// Integer.toString(BusinessConstants.SHOW_MY_NUMBER_ON))));
	//
	// model.setSwitchValueCallAds(Integer.parseInt(sharedPreferences
	// .getString(resources
	// .getString(R.string._SP_ADS_SERVICE_CALL_STATUS),
	// Integer.toString(BusinessConstants.CALL_ADS_SWITCH_ON))));
	//
	// model.setCountryCode(sharedPreferences.getString(
	// resources.getString(R.string._SP_COUNTRY_CODE), ""));
	//
	// model.setPhoneNumber(sharedPreferences.getString(
	// resources.getString(R.string._SP_PHONE_NUMBER), ""));
	//
	// model.setProfilePhotoImg(sharedPreferences.getString(
	// resources.getString(R.string._SP_USER_PHOTO), null));
	//
	// model.setProfileCoverImg(sharedPreferences.getString(
	// resources.getString(R.string._SP_USER_COVER), null));
	//
	// model.setImgDirectory(sharedPreferences.getString(
	// resources.getString(R.string._SP_USER_PROFILE_IMG_DIRECTORY),
	// null));
	//
	// TwitterModel twitterModel = new TwitterModel();
	//
	// String token = sharedPreferences.getString(context.getResources()
	// .getString(R.string._SP_TWITT_TOKEN), null);
	// String tokenSecret = sharedPreferences.getString(context.getResources()
	// .getString(R.string._SP_TWITT_TOKEN_SECRET), null);
	//
	// twitterModel.setToken(token);
	// twitterModel.setTokenSecret(tokenSecret);
	// model.setTwitterModel(twitterModel);
	//
	// return model;
	// }

	// public void saveUserModel(UserModel model) {
	//
	// SharedPreferences sharedPreferences = context.getSharedPreferences(
	// resources.getString(R.string._SP_MANADS), Context.MODE_PRIVATE);
	//
	// Editor editor = sharedPreferences.edit();
	//
	// // TOKEN
	// if (model.getToken() != null) {
	// editor.putString(resources.getString(R.string._SP_TOKEN),
	// model.getToken());
	// }
	//
	// // USER_ID
	// if (model.getUserId() != null) {
	// editor.putString(resources.getString(R.string._SP_USERID),
	// Integer.toString(model.getUserId()));
	// }
	//
	// // USERNAME
	// if (model.getUserName() != null) {
	// editor.putString(resources.getString(R.string._SP_USERNAME),
	// model.getUserName());
	// }
	//
	// // FIRST_NAME
	// if (model.getFirstName() != null) {
	// editor.putString(resources.getString(R.string._SP_FIRST_NAME),
	// model.getFirstName());
	// }
	//
	// // LAST_NAME
	// if (model.getLastName() != null) {
	// editor.putString(resources.getString(R.string._SP_LAST_NAME),
	// model.getLastName());
	// }
	//
	// // EMAIL
	// if (model.getEmail() != null) {
	// editor.putString(resources.getString(R.string._SP_EMAIL),
	// model.getEmail());
	// }
	//
	// // BIRTHDAY
	// if (model.getBirthday() != null) {
	// editor.putString(resources.getString(R.string._SP_BIRTHDAY), model
	// .getBirthday().toString());
	// }
	//
	// // USER GENDER CODE
	// if (model.getUserGender() != null) {
	// editor.putString(
	// resources.getString(R.string._SP_USER_GENDER_CODE),
	// Integer.toString(model.getUserGender()));
	// }
	//
	// // VIRTUAL CARD
	// if (model.getVirtualCard() != null) {
	// editor.putString(resources.getString(R.string._SP_VIRTUAL_CARD),
	// model.getVirtualCard());
	// }
	//
	// // TOTAL_BALANCE
	// if (model.getTotalBalace() != null) {
	// editor.putString(resources.getString(R.string._SP_BALANCE), model
	// .getTotalBalace().toString());
	// }
	//
	// // TOTAL_POINTS
	// if (model.getTotalPoints() != null) {
	// editor.putString(resources.getString(R.string._SP_POINTS),
	// Long.toString(model.getTotalPoints()));
	// }
	//
	// // COUNTRY
	// if (model.getCountry() != null) {
	// editor.putString(resources.getString(R.string._SP_COUNTRY),
	// model.getCountry());
	// }
	//
	// // CITY
	// if (model.getCity() != null) {
	// editor.putString(resources.getString(R.string._SP_CITY_NAME),
	// model.getCity());
	// } else {
	// editor.putString(resources.getString(R.string._SP_CITY_NAME), "");
	// }
	//
	// // CITY ID
	// if (model.getCityId() != null) {
	// editor.putString(resources.getString(R.string._SP_CITY_ID),
	// Integer.toString(model.getCityId()));
	// }
	//
	// // INVITER USERNAME
	// if (model.getInviterUserName() != null) {
	// editor.putString(
	// resources.getString(R.string._SP_INVITER_USERNAME),
	// model.getInviterUserName());
	// }
	//
	// // INVITER USER ID
	// if (model.getInviterId() != null) {
	// editor.putString(resources.getString(R.string._SP_INVITER_ID),
	// Integer.toString(model.getInviterId()));
	// }
	//
	// // FRIENDS_COUNT
	// if (model.getFriendCount() != null) {
	// editor.putString(resources.getString(R.string._SP_FRIENDS_COUNT),
	// Integer.toString(model.getFriendCount()));
	// }
	//
	// // PROFILE PERCENTAGE
	// if (model.getProfilePercent() != null) {
	// editor.putString(
	// resources.getString(R.string._SP_PROFILE_PERCENTAGE),
	// Integer.toString(model.getProfilePercent()));
	// }
	//
	// // SHOW MY NUMBER
	// if (model.getShowPhoneNumber() != null) {
	// editor.putString(
	// resources.getString(R.string._SP_SHOW_NUMBER_STATUS),
	// Integer.toString(model.getShowPhoneNumber()));
	// }
	//
	// // SWITCH VALUE OF CALL ADS
	// if (model.getSwitchValueCallAds() != null) {
	// editor.putString(
	// resources.getString(R.string._SP_ADS_SERVICE_CALL_STATUS),
	// Integer.toString(model.getSwitchValueCallAds()));
	// }
	//
	// // COUNTRY CODE
	// if (model.getCountryCode() != null) {
	// editor.putString(resources.getString(R.string._SP_COUNTRY_CODE),
	// model.getCountryCode());
	// }
	//
	// // PHONE NUMBER
	// if (model.getPhoneNumber() != null) {
	// editor.putString(resources.getString(R.string._SP_PHONE_NUMBER),
	// model.getPhoneNumber());
	// }
	//
	// // USER PHOTO
	// if (model.getProfilePhotoImg() != null) {
	// editor.putString(resources.getString(R.string._SP_USER_PHOTO),
	// model.getProfilePhotoImg());
	// editor.putInt(
	// resources.getString(R.string._SP_USER_PHOTO_SYNC_STATUS),
	// BusinessConstants.SYNCH_STATUS_DONE);
	// }
	//
	// // USER COVER
	// if (model.getProfileCoverImg() != null) {
	// editor.putString(resources.getString(R.string._SP_USER_COVER),
	// model.getProfileCoverImg());
	// editor.putInt(
	// resources.getString(R.string._SP_USER_COVER_SYNC_STATUS),
	// BusinessConstants.SYNCH_STATUS_DONE);
	// }
	//
	// // IMAGE DIRECTORY
	// if (model.getImgDirectory() != null) {
	// editor.putString(resources
	// .getString(R.string._SP_USER_PROFILE_IMG_DIRECTORY), model
	// .getImgDirectory());
	// }
	//
	// // TWITTER TOKENS
	// if (model.getTwitterModel() != null) {
	//
	// if (model.getTwitterModel().getToken() != null) {
	// editor.putString(
	// context.getResources().getString(
	// R.string._SP_TWITT_TOKEN), model
	// .getTwitterModel().getToken());
	// }
	//
	// if (model.getTwitterModel().getTokenSecret() != null) {
	// editor.putString(
	// context.getResources().getString(
	// R.string._SP_TWITT_TOKEN_SECRET), model
	// .getTwitterModel().getTokenSecret());
	// }
	//
	// Log.d("testN", "Token : " + model.getTwitterModel().getToken());
	// Log.d("testN", "Secret Token : "
	// + model.getTwitterModel().getTokenSecret());
	//
	// }
	//
	// editor.commit();
	// }

	public static Object initializeObject(Object object, Context context) {

		CoreModel model = null;
		if (object instanceof CoreModel) {
			model = (CoreModel) object;
		}

		if (model != null) {

			SharedPreferences sharedPreferences = context.getSharedPreferences(
					context.getResources().getString(R.string._SP_ALL_FINE),
					Context.MODE_PRIVATE);
			model.setToken(sharedPreferences.getString(context.getResources()
					.getString(R.string._SP_TOKEN), null));
			model.setAppVersion(Integer.parseInt(context.getResources()
					.getString(R.string._APP_VERSION)));
			model.setSysLang(context.getResources().getConfiguration().locale
					.getLanguage());
		}

		if (model != null) {
			return model;
		} else {
			return object;
		}

	}

	public static Object initializePostDescModel(Object object, Context context) {

		CoreModel model = null;
		if (object instanceof CoreModel) {
			model = (CoreModel) object;
		}

		if (model != null) {

			SharedPreferences sharedPreferences = context.getSharedPreferences(
					context.getResources().getString(R.string._SP_ALL_FINE),
					Context.MODE_PRIVATE);
			model.setToken(sharedPreferences.getString(context.getResources()
					.getString(R.string._SP_TOKEN), null));
			model.setAppVersion(Integer.parseInt(context.getResources()
					.getString(R.string._APP_VERSION)));
		}

		if (model != null) {
			return model;
		} else {
			return object;
		}

	}

}
