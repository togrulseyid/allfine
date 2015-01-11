package com.allfine.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allfine.R;
import com.allfine.asynctask.SignUpAsyncTask;
import com.allfine.models.core.SignUpModel;
import com.allfine.operations.Utility;
import com.allfine.views.InfoDialog;
import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;

public class SignUpActivity extends ActionBarActivity {

	private TextView editTextSignUpCountryCode;
	private EditText editTextSignUpPassword, editTextSignUpPassword2;
	private EditText editTextSignUpFirstName, editTextSignUpLastName;
	private EditText editTextSignUpNumber;
	private EditText editTextSignUpUserName;
	private Button buttonSignUpCreateNewAccount;

	private String name;
	private String code;
	private String numberCode;
	private CountryPicker picker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		editTextSignUpCountryCode = (TextView) findViewById(R.id.textViewSignUpCountryCode);
		editTextSignUpPassword = (EditText) findViewById(R.id.editTextSignUpPassword);
		editTextSignUpPassword2 = (EditText) findViewById(R.id.editTextSignUpPassword2);
		editTextSignUpFirstName = (EditText) findViewById(R.id.editTextSignUpFirstName);
		editTextSignUpLastName = (EditText) findViewById(R.id.editTextSignUpLastName);
		editTextSignUpNumber = (EditText) findViewById(R.id.editTextSignUpNumber);
		editTextSignUpUserName = (EditText) findViewById(R.id.editTextSignUpUserName);
		buttonSignUpCreateNewAccount = (Button) findViewById(R.id.buttonSignUpCreateNewAccount);

		editTextSignUpCountryCode
				.setOnClickListener(new MyOnClickListener(this));
		buttonSignUpCreateNewAccount.setOnClickListener(new MyOnClickListener(
				this));
		// readAndWrite();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sign_up, menu);
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

	private void createNewAccount(Activity activity) {
		InfoDialog infoDialog = new InfoDialog(activity,
				R.drawable.ic_actionbar_home,
				R.string.message_info_dialog_title,
				R.string.message_info_dialog_title);

		/*if (Utility.isEmptyOrNull(editTextSignUpFirstName.getText().toString())) {
			// TODO: show error dialog
			infoDialog.setMessage(R.string.message_info_dialog_null_first_name);
			infoDialog.show();
		} else if (Utility.isEmptyOrNull(editTextSignUpLastName.getText()
				.toString())) {
			infoDialog.setMessage(R.string.message_info_dialog_null_last_name);
			infoDialog.show();
		} else */
		if (Utility.isEmptyOrNull(editTextSignUpCountryCode.getText()
				.toString())) {
			infoDialog
					.setMessage(R.string.message_info_dialog_null_country_code);
			infoDialog.show();
		} else if (Utility.isEmptyOrNull(editTextSignUpNumber.getText()
				.toString())) {
			infoDialog.setMessage(R.string.message_info_dialog_null_number);
			infoDialog.show();

		}/* else if (Utility.isEmptyOrNull(editTextSignUpUserName.getText()
				.toString())) {
			infoDialog.setMessage(R.string.message_info_dialog_null_username);
			infoDialog.show();

		} else if (Utility.isEmptyOrNull(editTextSignUpPassword.getText()
				.toString())
				|| Utility.isEmptyOrNull(editTextSignUpPassword2.getText()
						.toString())
				|| !editTextSignUpPassword.getText().toString()
						.equals(editTextSignUpPassword2.getText().toString())
				|| editTextSignUpPassword.getText().toString().length() < BusinessConstants.PASSWORD_MIN_LENGTH) {

			infoDialog
					.setMessage(R.string.message_info_dialog_null_or_different_password);
			infoDialog.show();

		} */
		else {

			SignUpModel model = new SignUpModel();
			model.setFirstName(editTextSignUpFirstName.getText().toString());
			model.setLastName(editTextSignUpLastName.getText().toString());
			model.setUserName(editTextSignUpUserName.getText().toString());
			// model.setGcm(gcm);
			// model.setImei(imei);
			model.setPhoneNumber(editTextSignUpNumber.getText().toString());
			model.setCountryCode(editTextSignUpCountryCode.getText().toString());
			model.setPassword(editTextSignUpPassword.getText().toString());

			Log.d("testA", "" + model.toString());
			new SignUpAsyncTask(activity).execute(model);
		}

	}

	private class MyOnClickListener implements OnClickListener {
		private Activity activity;

		public MyOnClickListener(Activity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.textViewSignUpCountryCode) {

				picker = CountryPicker.newInstance("Select Country");
				picker.show(getSupportFragmentManager(), "COUNTRIES");
				picker.setListener(new CountryPickerListener() {

					@Override
					public void onSelectCountry(String name, String code,
							String numberCode) {
						// Invoke your function here
						Log.d("testA", name + " " + code + " " + numberCode);
						SignUpActivity.this.name = name;
						SignUpActivity.this.code = code;
						SignUpActivity.this.numberCode = numberCode;
						editTextSignUpCountryCode.setText(numberCode);
						picker.dismiss();
					}
				});

			} else if (v.getId() == R.id.buttonSignUpCreateNewAccount) {
				createNewAccount(activity);
			}
		}
	}

//	private void readAndWrite() {
//		try {
//			// Read from local file
//			String allCountriesString = readFileAsString();
//
//			String name = readFileAsString(this, allCountriesString);
//
//			Log.d("countrypicker", "country: " + name);
//			writeToFile(name, "base64");
//
//			ObjectConvertor<CountryList> convertor = new ObjectConvertor<CountryList>();
//			CountryList countryList = convertor.getClassObject(
//					allCountriesString, CountryList.class);
//
//			Log.d("countryList", "countryList; " + countryList);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}

//	private static String readFileAsString(Context context, String string) throws java.io.IOException {
//		// byte[] data = Base64.decode(base64, Base64.DEFAULT);
//		// return new String(data, "UTF-8");
//		byte[] base64 = string.getBytes();
//		return new String(Base64.encode(base64, Base64.DEFAULT), "UTF-8");
//	}

//	private void writeToFile(String string, String fileName) {
//		Log.d("countrypicker", "string: " + string);
//		File sd = Environment.getExternalStorageDirectory();
//		String filename = File.separator + "BackupFolder" + File.separator
//				+ fileName;
//
//		File file = new File(sd, filename);
//		try {
//			file.createNewFile();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		FileOutputStream outputStream;
//		try {
//			outputStream = new FileOutputStream(file);
//			outputStream.write(string.getBytes());
//			outputStream.flush();
//			outputStream.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	private String readFileAsString() {
//		// Read text from file
//		StringBuilder text = new StringBuilder();
//
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(new File(
//					Environment.getExternalStorageDirectory(), File.separator
//							+ "BackupFolder" + File.separator + "file.txt")));
//			String line;
//
//			while ((line = br.readLine()) != null) {
//				text.append(line);
//				text.append('\n');
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			// You'll need to add proper error handling here
//		}
//
//		return text.toString();
//	}
}