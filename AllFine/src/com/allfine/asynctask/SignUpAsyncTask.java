package com.allfine.asynctask;

import android.app.Activity;
import android.os.AsyncTask;

import com.allfine.models.core.SignUpModel;
import com.allfine.models.core.UserModel;
import com.allfine.operations.NetworkOperations;

public class SignUpAsyncTask extends AsyncTask<SignUpModel, Integer, UserModel> {
	private Activity activity;

	public SignUpAsyncTask(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected UserModel doInBackground(SignUpModel... models) {

		NetworkOperations networkOperations = new NetworkOperations(activity);
		return networkOperations.createNewUser(new UserModel());

	}

}
