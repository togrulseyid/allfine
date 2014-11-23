package com.allfine.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allfine.models.core.ContactNumbersModel;

public class ContactsAdapter extends BaseAdapter {
	Activity activity;
	ArrayList<ContactNumbersModel> alContacts;

	public ContactsAdapter(Activity activity,
			ArrayList<ContactNumbersModel> alContacts) {
		this.alContacts = alContacts;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return alContacts.size();
	}

	@Override
	public ContactNumbersModel getItem(int position) {
		return alContacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (convertView == null) {
			holder = new ViewHolder();
			
			LinearLayout linearLayout = new LinearLayout(activity);
			
			LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			linearLayout.setOrientation(LinearLayout.VERTICAL);
			linearLayout.setLayoutParams(params);

			TextView fullName = new TextView(activity);
			TextView number = new TextView(activity);

			holder.number = number;
			holder.fullName = fullName;

			linearLayout.addView(holder.number);
			linearLayout.addView(holder.fullName);
			holder.linearLayout = linearLayout;
			view = holder.linearLayout;

			view.setTag(holder);
		} else {

			holder = (ViewHolder) view.getTag();
		}
		return view;
	}

	ViewHolder holder;

	class ViewHolder {
		private LinearLayout linearLayout;
		private TextView number;
		private TextView fullName;
	}
}
