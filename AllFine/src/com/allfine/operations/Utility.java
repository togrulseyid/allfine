package com.allfine.operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.allfine.R;
import com.allfine.activities.MainActivity;
import com.allfine.activities.RootActivity;
import com.allfine.constants.BusinessConstants;
import com.allfine.models.core.ContactNumbersModel;
import com.allfine.models.core.FriendModel;
import com.allfine.models.core.UserModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class Utility {

	private static final String jpeg = ".jpg";
	private static final String adsDirectory = "ads";
	private static final String userProfileDirectory = "userProfile";
	private ArrayList<File> recycledFiles;

	public static final boolean isEmptyOrNull(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		}

		return false;
	}

	/*
	 * This method used for EditText
	 */
	public static final String nullStringToEmpty(String string) {
		if (string == null) {
			return "";
		}
		return string;
	}

	/*
	 * This method used for Date type EditText
	 */
	public static final String nullStringToDate(String string) {
		if (string == null) {
			return "--/--/----";
		}
		return string;
	}

	public static String getMD5EncodedString(String string) {

		byte[] data = string.getBytes();
		String result = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = new BigInteger(1, md.digest(data)).toString(16);
		} catch (NoSuchAlgorithmException e) {
		}

		return result;
	}

	public static final String getAppSignature(Context context) {

		String appSignature = new BigInteger(1, getApkMd5(context))
				.toString(16);

		if (appSignature != null && appSignature.length() < 32) {
			appSignature = "0" + appSignature;
		}

		return "0f4f84bb35392c8370f343af2895e50c";
	}

	private static final byte[] getApkMd5(Context context) {

		try {
			Signature[] signatures = context.getPackageManager()
					.getPackageInfo(context.getPackageName(),
							PackageManager.GET_SIGNATURES).signatures;

			Signature signature = signatures[0];
			byte[] hexBytes = signature.toByteArray();
			MessageDigest digest = MessageDigest.getInstance("MD5");
			return digest.digest(hexBytes);
		} catch (NameNotFoundException ex) {

		} catch (Exception e) {

		}
		return null;
	}

	/*
	 * Check network connection (it does not depend WiFi or Cell Network)
	 */
	public static final boolean checkNetwork(Context context) {

		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo == null) {
			return false;
		}

		return networkInfo.isConnected();
	}

	/*
	 * 
	 */
	public static final boolean checkInternetConnection() {

		HttpParams httpParameters = new BasicHttpParams();
		// if HTTP connection time is less than
		// BusinessConstants.INTERNET_CHEKING_TIMEOUT seconds, will be
		// IOException
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				BusinessConstants.INTERNET_CHEKING_TIMEOUT);
		// if HTTP downloading time is less than
		// BusinessConstants.INTERNET_CHEKING_TIMEOUT seconds, will be
		// IOException
		HttpConnectionParams.setSoTimeout(httpParameters,
				BusinessConstants.INTERNET_CHEKING_TIMEOUT);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

		try {

			// Developer thought that if Google down, down all servers in the
			// world :)
			HttpGet httpGet = new HttpGet("https://www.google.com");

			HttpResponse response = httpClient.execute(httpGet);

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));
			StringBuilder sb = new StringBuilder();

			String line = null;
			try {
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line);
				}
				if (sb.length() > 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception ex) {
				return false;
			}

		} catch (ClientProtocolException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

	}

	@SuppressLint("TrulyRandom")
	public static String encrypt(String strToEncrypt, String key) {

		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			final SecretKeySpec secretKeySpec = new SecretKeySpec(
					key.getBytes(), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

			final String encryptedString = Base64.encodeToString(
					cipher.doFinal(strToEncrypt.getBytes()), Base64.DEFAULT);

			return encryptedString;
		} catch (Exception e) {
		}
		return null;

	}

	public static String decrypt(String strToDecrypt, String key) {
		/*
		 * try { Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		 * final SecretKeySpec secretKeySpec = new SecretKeySpec(
		 * key.getBytes(), "AES"); cipher.init(Cipher.DECRYPT_MODE,
		 * secretKeySpec); final String decryptedString = new
		 * String(cipher.doFinal(Base64 .decode(strToDecrypt.getBytes(),
		 * Base64.DEFAULT))); return decryptedString; } catch (Exception e) { }
		 * return null;
		 */
		return strToDecrypt;
	}

	public static final File getOuputAdsContentFile(Context context, int adsId) {

		File file = new File(
				context.getExternalFilesDir(Environment.DIRECTORY_PICTURES
						+ File.separator + adsDirectory + File.separator
						+ Integer.toString(adsId)), Integer.toString(adsId)
						+ jpeg);
		return file;
	}

	public static final File getOutputAdsDir(Context context, int adsId) {

		File file = new File(
				context.getExternalFilesDir(Environment.DIRECTORY_PICTURES
						+ File.separator + adsDirectory),
				Integer.toString(adsId));

		return file;

	}

	public static final File getOuputProfileImageFile(Context context,
			String photoName) {

		if (photoName != null && !photoName.contains(jpeg)) {
			photoName += jpeg;
		}
		File file = new File(
				context.getExternalFilesDir(Environment.DIRECTORY_PICTURES
						+ File.separator + userProfileDirectory), photoName);

		return file;
	}

	public static ArrayList<String> getDateDays() {
		ArrayList<String> days = new ArrayList<String>();
		for (int i = 1; i <= 31; i++) {
			if (i / 10 == 0) {
				days.add("0" + i);
			} else {
				days.add(Integer.toString(i));
			}
		}
		return days;
	}

	public static ArrayList<String> getDateMonths() {
		ArrayList<String> months = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {
			if (i / 10 == 0) {
				months.add("0" + i);
			} else {
				months.add(Integer.toString(i));
			}
		}
		return months;
	}

	public static ArrayList<String> getDateYears() {

		ArrayList<String> years = new ArrayList<String>();

		for (int i = 1930; i <= 1996; i++) {
			years.add(Integer.toString(i));
		}

		return years;
	}

	private void deleteDirectory(File rootFile) {
		if (rootFile.exists()) {
			if (rootFile.isDirectory()) {
				recycledFiles.add(rootFile);
				File files[] = rootFile.listFiles();
				for (File subFile : files) {
					if (subFile.isDirectory()) {
						deleteDirectory(subFile);
					} else {
						subFile.delete();
					}
				}
			} else {
				rootFile.delete();
			}
		}
	}

	public static int getDateAsInt() {
		Calendar calendar = Calendar.getInstance();

		int date = 1;
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		date = (((date * year * 100) + month) * 100) + day;

		return date;
	}

	public static int getPreviousDateAsInt() {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);

		int date = 1;
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		date = (((date * year * 100) + month) * 100) + day;

		return date;

	}

	public static long getDateWithHour() {

		Calendar calendar = Calendar.getInstance();

		long date = 1;
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);

		// Log.d("testB", Integer.toString(year));
		// Log.d("testB", Integer.toString(month));
		// Log.d("testB", Integer.toString(day));
		// Log.d("testB", Integer.toString(hour));
		// Log.d("testB", Integer.toString(minute));
		// Log.d("testB", Integer.toString(second));

		date = (date * year * 100) + month;
		date = date * 100 + day;
		date = date * 100 + hour;
		date = date * 100 + minute;
		date = date * 100 + second;

		// Log.d("testB", Long.toString(date));

		return date;
	}

	// Togrul
	public static String numberFormatter(String currency, String numberString,
			Integer maximumFractionDigits, String format) {

		return currency
				+ manadsFormat(new BigDecimal(numberString),
						maximumFractionDigits, format);
	}

	public static String numberFormatter(String currency, BigDecimal number,
			int maximumFractionDigits, String format) {
		return currency + manadsFormat(number, maximumFractionDigits, format);
	}

	private static String manadsFormat(BigDecimal value,
			int maximumFractionDigits, String format) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		DecimalFormat df = new DecimalFormat(format, symbols); // ###,###.00
		// df.setRoundingMode(RoundingMode.DOWN);
		df.setMaximumFractionDigits(maximumFractionDigits);
		String output = df.format(value.setScale(maximumFractionDigits,
				BigDecimal.ROUND_FLOOR));
		return output;
	}

	public static String fullNameMaker(String firstName, String lastName) {

		if (!isEmptyOrNull(firstName)) {

			if (!isEmptyOrNull(lastName)) {
				return firstName + " " + lastName;
			}

			return firstName;

		} else if (!isEmptyOrNull(lastName)) {

			return lastName;
		}

		return null;
	}

	public static void hideKeyBoard(EditText editText, Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	/**
	 * Hides the soft keyboard
	 */
	public static void hideSoftKeyboard(Activity activity) {
		if (activity.getCurrentFocus() != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(activity
					.getCurrentFocus().getWindowToken(), 0);
		}
	}

	public static boolean checkCashOutSubmit(String number) {

		if (!isEmptyOrNull(number)) {

			if (!number.contains(".")) {
				return true;
			}

			return false;
		}
		return false;
	}

	public static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else if (target.equals("")) {
			return true;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}

	/**
	 * Validate password with regular expression Valid example:
	 * <b>Use4sas!@#$%^&*()+=</b>
	 * 
	 * @param password
	 *            password for validation
	 * @return true valid password, false invalid password
	 */
	public static boolean isValidPassword(CharSequence password) {
		String PASSWORD_PATTERN = "^[a-zA-Z0-9!@#$%^&*+()=(?=\\S+$)_-]{6,50}$";
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	/**
	 * Validate username with regular expression Valid example <b>tog9</b> Just
	 * lower case.
	 * 
	 * @param username
	 *            username for validation
	 * @return true valid username, false invalid username
	 */
	public static boolean isValidUsername(String username) {
		username = username.toLowerCase(Locale.ENGLISH);
		String USERNAME_PATTERN = "^[a-z0-9_-]{4,50}$";
		Pattern pattern = Pattern.compile(USERNAME_PATTERN);
		Matcher matcher = pattern.matcher(username);
		return matcher.matches();
	}

	@SuppressLint("DefaultLocale")
	public static String correctNewsFeedTitle(String title, int start, int end) {
		// Log.d("testD", "Title: " + title);
		StringBuilder stringBuilder = new StringBuilder(title);
		stringBuilder
				.replace(0, 1, stringBuilder.substring(0, 1).toUpperCase());
		if (stringBuilder.toString().length() >= end) {
			return stringBuilder.toString().substring(start, end) + "...";
		} else {
			return stringBuilder.toString();
		}
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static void setRelativeLayoutBackground(
			RelativeLayout relativeLayout, Drawable drawable) {
		if (Build.VERSION.SDK_INT >= 16) {
			relativeLayout.setBackground(drawable);
		} else {
			relativeLayout.setBackgroundDrawable(drawable);
		}
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static void setViewBackground(View view, Drawable drawable) {
		if (Build.VERSION.SDK_INT >= 16) {
			view.setBackground(drawable);
		} else {
			view.setBackgroundDrawable(drawable);
		}
	}

	public static void finishForToken(Activity activity) {

		SharedPreferences sharedPreferences = activity.getSharedPreferences(
				activity.getResources().getString(R.string._SP_ALL_FINE),
				Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(
				activity.getResources()
						.getString(R.string._SP_ROOTING_ACTIVITY),
				activity.getResources().getString(
						R.string._ROOTING_START_ACTIVITY)).commit();
		Intent intentActivity = new Intent(activity, RootActivity.class);
		activity.startActivity(intentActivity);

		// Intent intentService = new Intent(activity, SignoutService.class);
		// activity.startService(intentService);

		activity.finish();
	}

	public static void sendGoogleAnalytics(String actionName, Activity activity) {

		Bundle bundle = new Bundle();
		bundle.putString(
				activity.getResources().getString(R.string._B_ACTION_NAME),
				actionName);
		// Intent intent = new Intent(activity,
		// GoogleAnalyticsSenderService.class);
		// intent.putExtras(bundle);
		// activity.startService(intent);
	}

	public static String parseURL(String actionMethodData) {
		if (actionMethodData.startsWith("http://")
				|| actionMethodData.startsWith("https://")) {
			return actionMethodData;
		}
		return "http://" + actionMethodData;
	}

	/*
	 * Load Resource String by Id
	 */
	public static int loadStringResourcesByString(Context context, String title) {
		return context.getResources().getIdentifier(title, "string",
				context.getPackageName());
	}

	/*
	 * Load Resource String by Id
	 */
	public static int loadColorResourcesByString(Context context, String title) {
		return context.getResources().getIdentifier(title, "color",
				context.getPackageName());
	}

	/*
	 * @return boolean true if string is number
	 */
	public static boolean isNumeric(String text) {
		return text.matches("-?\\d+(\\.\\d+)?");
	}

	public static void writeUserInfo(Context context, UserModel result) {

		SharedPreferences preference = context.getSharedPreferences(
				context.getString(R.string._SP_ALL_FINE), Context.MODE_PRIVATE);
		Editor edit = preference.edit();

		// Put Entire Model to Preference
		ObjectConvertor<UserModel> userModelConvertor = new ObjectConvertor<UserModel>();
		try {
			edit.putString(context.getString(R.string._SP_USER_MODEL),
					userModelConvertor.getClassString(result)).commit();
		} catch (IOException e) {
			e.printStackTrace();
		}

		edit.putInt(context.getString(R.string._SP_USER_ID), result.getUserId())
				.commit();

		edit.putString(context.getString(R.string._SP_USER_TOKEN),
				result.getToken()).commit();

		// edit.commit();
		ObjectConvertor<ArrayList<FriendModel>> objectConvertor = new ObjectConvertor<ArrayList<FriendModel>>();
		try {
			edit.putString(context.getString(R.string._SP_USER_FRIENDS),
					objectConvertor.getClassString(result.getFriends()))
					.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}

		edit.putString(context.getString(R.string._SP_USER_DATE_CREATED),
				result.getDateCreated()).commit();

		edit.putString(context.getString(R.string._SP_USER_EMAIL),
				result.getEmail()).commit();

		edit.putInt(context.getString(R.string._SP_USER_EVENT_ID),
				result.getEventId()).commit();

		edit.putString(context.getString(R.string._SP_USER_USERNAME),
				result.getUserName()).commit();

		edit.putString(context.getString(R.string._SP_USER_FIRTSNAME),
				result.getFirtsName()).commit();

		edit.putString(context.getString(R.string._SP_USER_LASTNAME),
				result.getLastName()).commit();

		edit.putString(context.getString(R.string._SP_USER_PHONE_NUMBER),
				result.getPhoneNumber()).commit();

	}

	public static UserModel getUserInfo(Context context) {
		UserModel result = new UserModel();
		SharedPreferences preference = context.getSharedPreferences(
				context.getString(R.string._SP_ALL_FINE), Context.MODE_PRIVATE);

		ObjectConvertor<UserModel> objectConvertor = new ObjectConvertor<UserModel>();
		try {
			return objectConvertor.getClassObject(
					preference.getString(
							context.getString(R.string._SP_USER_MODEL), ""),
					UserModel.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	// GCMREG ID

	/**
	 * Gets the current registration ID for application on GCM service, if there
	 * is one.
	 * <p>
	 * If result is empty, the app needs to register. @return registration ID,
	 * or empty string if there is no existing registration ID.
	 * </p>
	 * */
	public static String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGcmPreferences(context);
		String registrationId = prefs.getString(
				context.getString(R.string._P_APP_REG_ID), "");
		if (registrationId.isEmpty()) {
			// Log.d(TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.

		int registeredVersion = prefs.getInt(
				context.getString(R.string._P_APP_VERSION), Integer.MIN_VALUE);
		int currentVersion = Utility.getAppVersion(context);
		if (registeredVersion != currentVersion) {
			// Log.d(TAG, "App version changed.");
			// textViewAppTitle.append("\nApp version changed.");
			return "";
		}
		return registrationId;
	}

	public static String getDeviceId(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	public static SharedPreferences getGcmPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but how you store the regID in your app is up to you.
		return context.getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	public static String getGCM(Context context) {
		return getGcmPreferences(context).getString(
				context.getString(R.string._SP_VIDEO_APP_GCM), null);
	}

	public static void writeGCMToSharedPreferences(Context context, String gcmID) {
		final SharedPreferences prefs = Utility.getGcmPreferences(context);
		prefs.edit()
				.putBoolean(
						context.getString(R.string._SP_VIDEO_APP_IS_GCM_SEND),
						true).commit();
	}

	public static boolean isSendGCMToServer(Context context) {
		final SharedPreferences prefs = Utility.getGcmPreferences(context);
		return prefs.getBoolean(
				context.getString(R.string._SP_VIDEO_APP_IS_GCM_SEND), false);
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If it
	 * doesn't, display a dialog that allows users to download the APK from the
	 * Google Play Store or enable it in the device's system settings.
	 */
	public static boolean checkPlayServices(Activity activity) {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(activity);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
						BusinessConstants.PLAY_SERVICES_RESOLUTION_REQUEST)
						.show();
			} else {
				// Log.d(TAG, "This device is not supported.");
				activity.finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * Stores the registration ID and the app versionCode in the application's
	 * {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */
	public static void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = Utility.getGcmPreferences(context);

		int appVersion = Utility.getAppVersion(context);
		// Log.d(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(context.getString(R.string._P_APP_REG_ID), regId);
		editor.putInt(context.getString(R.string._P_APP_VERSION), appVersion);
		editor.commit();

	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	public static ArrayList<ContactNumbersModel> getContacts(Activity activity) {
		ArrayList<ContactNumbersModel> contactNumbersModels = null;

		ContentResolver cr = activity.getContentResolver(); // Activity/Application
		// android.content.Context
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);

		if (cursor.moveToFirst()) {
			contactNumbersModels = new ArrayList<ContactNumbersModel>();
			do {
				String id = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts._ID));

				if (Integer
						.parseInt(cursor.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					
					//public final Cursor query (Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) 
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?",
							new String[] { id },
							null);
					
					while (pCur.moveToNext()) {
						String contactNumber = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

						String contactDisplayName = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
						
						
						
						
						String contactName = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA2));
						
						String contactLastName=
										 pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA3));
						
						ContactNumbersModel number = new ContactNumbersModel();
						number.setDisplayName(contactDisplayName);
						number.setFirstName(contactName);
						number.setLastName(contactLastName);
							ArrayList<String> numbers = new ArrayList<String>();
							numbers.add(contactNumber);
						number.setNumbers(numbers);
						
						contactNumbersModels.add(number);
						break;
					}
					pCur.close();
				}

			} while (cursor.moveToNext());
		}

		return contactNumbersModels;
	}

}
