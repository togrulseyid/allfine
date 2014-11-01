package com.allfine.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.allfine.R;

public class StartActivity extends Activity {

	private Button buttonSignIn;
	private Button buttonSignUp;
	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		activity = this;
		setContentView(R.layout.activity_start);

		buttonSignIn = (Button) findViewById(R.id.buttonStartActivitySignIn);
		buttonSignUp = (Button) findViewById(R.id.buttonStartActivitySignUp);

		buttonSignIn.setOnClickListener(new MyOnClickListener());
		buttonSignUp.setOnClickListener(new MyOnClickListener());

	}

	private class MyOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = null;
			switch (v.getId()) {

			case R.id.buttonStartActivitySignIn:

				intent = new Intent(activity, SignInActivity.class);
				break;

			case R.id.buttonStartActivitySignUp:

				intent = new Intent(activity, SignUpActivity.class);
				break;

			default:
				break;
			}

			startActivity(intent);
		}
	}
}
