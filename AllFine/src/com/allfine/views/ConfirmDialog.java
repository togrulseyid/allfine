package com.allfine.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.allfine.R;
import com.allfine.interfaces.DialogConfirmOnCancelListener;

public class ConfirmDialog extends Dialog implements
		DialogConfirmOnCancelListener {

	private String title;
	private String message;
	private TextView textViewMesage;
	private TextView textViewMessageTitle;
	private Button buttonOK;
	private Button buttonCancel;
	private Context context;

	public ConfirmDialog(Context context, String title, String message) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		this.title = title;
		this.message = message;
		this.context = context;
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setGravity(Gravity.CENTER);
		setContentView(R.layout.dialog_confirm);
		// android.R.style.Theme_Translucent_NoTitleBar
		textViewMesage = (TextView) findViewById(R.id.textViewDialogConfirmMessageBody);
		textViewMessageTitle = (TextView) findViewById(R.id.textViewDialogConfirmMessageTitle);
		buttonOK = (Button) findViewById(R.id.buttonDialogConfirmOK);
		buttonCancel = (Button) findViewById(R.id.buttonDialogConfirmCancel);

		buttonCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});

		textViewMesage.setText(message);
		textViewMessageTitle.setText(title);
	}

	DialogConfirmOnCancelListener cancelListener;
//	View.OnClickListener onClickListener;
//	public void setOnCancelListener(DialogConfirmOnCancelListener cancelListener) {
//		this.cancelListener = cancelListener;
//		
//		buttonOK.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View view) {
//
//			}
//
//		});
//	}
	
	public void setOnClickListener(View.OnClickListener onClickListener) {
//		this.onClickListener = onClickListener;
		buttonOK.setOnClickListener(onClickListener);
//		buttonCancel.setOnClickListener(onClickListener);
	}

	@Override
	public void onCancelled() {

	}

	@Override
	public void onApproved() {

	}
}
