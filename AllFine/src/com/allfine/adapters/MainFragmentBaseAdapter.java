package com.allfine.adapters;

import java.util.ArrayList;

import com.allfine.models.UserEventsHistoryModel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MainFragmentBaseAdapter extends BaseAdapter {

	ArrayList<UserEventsHistoryModel> historyList;

	public MainFragmentBaseAdapter(ArrayList<UserEventsHistoryModel> historyList) {
		this.historyList = historyList;
	}

	@Override
	public int getCount() {
		return historyList.size();
	}

	@Override
	public Object getItem(int position) {
		return historyList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {

		return null;
	}

}
