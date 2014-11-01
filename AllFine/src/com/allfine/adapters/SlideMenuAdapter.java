package com.allfine.adapters;
//package com.allfine.adapters;
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class SlideMenuAdapter extends BaseAdapter {
//
//	private ArrayList<String> menuList;
//	private ArrayList<Integer> menuListImage;
//
//	private LayoutInflater inflater;
//	private Resources resources;
//
//	public MainActivitySlideMenuAdapter(Context context) {
//
//		menuList = new ArrayList<String>();
//		menuListImage = new ArrayList<Integer>();
//
//		resources = context.getResources();
//
//		menuList.add(resources.getString(R.string.menu_news_feed_title));
//		menuListImage.add(R.drawable.menu_newsfeed_icon);
//
//		menuList.add(resources.getString(R.string.menu_channels_title));
//		menuListImage.add(R.drawable.menu_newsfeed_icon);
//		
//		menuList.add(resources.getString(R.string.menu_friends_title));
//		menuListImage.add(R.drawable.menu_friends_icon);
//		
//		menuList.add(resources.getString(R.string.menu_ratings_title));
//		menuListImage.add(R.drawable.menu_ratings_icon);
//
//		menuList.add(resources.getString(R.string.menu_manadscard_title));
//		menuListImage.add(R.drawable.menu_wallet_icon);
//		
//		menuList.add(resources.getString(R.string.menu_cashout_title));
//		menuListImage.add(R.drawable.menu_cashout_icon);
//
//		menuList.add(resources.getString(R.string.menu_settings_title));
//		menuListImage.add(R.drawable.menu_settings_icon);
//		
////		menuList.add(resources.getString(R.string.menu_signout_bookmarks));
////		menuListImage.add(R.drawable.menu_settings_icon);
//
////		menuList.add(resources.getString(R.string.menu_signout_title));
////		menuListImage.add(R.drawable.menu_signout_icon);
//
//		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	}
//
//	@Override
//	public int getCount() {
//		if (menuList != null)
//			return menuList.size();
//		return 0;
//	}
//
//	@Override
//	public Object getItem(int itemID) {
//		return menuList.get(itemID);
//	}
//
//	@Override
//	public long getItemId(int itemID) {
//		return itemID;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//
//		ImageViewHolder imageViewHolder = null;
//
//		if (convertView == null) {
//
//			convertView = inflater.inflate(R.layout.slidemenu_adapter_row, null);
//
//			imageViewHolder = new ImageViewHolder();
//
//			imageViewHolder.textViewMenuTitle = (TextView) convertView
//					.findViewById(R.id.textViewMenuTitle);
//			imageViewHolder.imageViewMenuIcon = (ImageView) convertView
//					.findViewById(R.id.imageViewMenuIcon);
//
//			convertView.setTag(imageViewHolder);
//
//		} else {
//
//			imageViewHolder = (ImageViewHolder) convertView.getTag();
//
//		}
//
//		try {
//
//			imageViewHolder.textViewMenuTitle.setText(getItem(position)
//					.toString());
//			imageViewHolder.imageViewMenuIcon.setBackgroundResource(menuListImage.get(position));
//
//		} catch (Exception e) {
//
//		}
//
//		return convertView;
//	}
//
//	private static class ImageViewHolder {
//		public TextView textViewMenuTitle;
//		public ImageView imageViewMenuIcon;
//	}
//}
