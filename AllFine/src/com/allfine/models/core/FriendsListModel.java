package com.allfine.models.core;

import java.util.ArrayList;

public class FriendsListModel extends CoreModel {

	private ArrayList<FriendModel> friends;

	public ArrayList<FriendModel> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<FriendModel> friends) {
		this.friends = friends;
	}

	@Override
	public String toString() {
		return "FriendsList [friends=" + friends + "]";
	}

}
