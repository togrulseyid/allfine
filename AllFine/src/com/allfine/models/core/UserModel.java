package com.allfine.models.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.util.Log;

@SuppressWarnings("serial")
public class UserModel extends CoreModel implements Serializable {

	private String userName;
	private String firtsName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String countryCode;
	private ArrayList<FriendModel> friends;
	private Integer eventId;
	private String dateCreated;
	private String gcm;
	private String imei;
	private String phoneModel = "android";
	private String photo;
	private String cover;

	public FriendModel userGetFriendById(Integer userId) {
		FriendModel friendModel;
		Iterator<FriendModel> itr = friends.iterator();
		while (itr.hasNext()) {
			friendModel = (FriendModel) itr.next();
			if (userId.equals(friendModel.getUserId())) {
				return friendModel;
			}
		}
		return null;
	}

	public UserModel removeUserFriendById(Integer userId) {
		FriendModel friendModel;
		Iterator<FriendModel> itr = friends.iterator();
		while (itr.hasNext()) {
			friendModel = (FriendModel) itr.next();
			if (userId.equals(friendModel.getUserId())) {
				itr.remove();
				return this;
			}
		}
		return null;
	}

	public boolean existUserById(Integer userId) {
		if (userId != null) {
			FriendModel friendModel;
			Iterator<FriendModel> itr = friends.iterator();
			while (itr.hasNext()) {
				friendModel = (FriendModel) itr.next();
				if (userId.equals(friendModel.getUserId())) {
					return true;
				}
			}
		}

		return false;
	}
	
	
	@Override
	public boolean equals(Object obj) {

		if (obj instanceof FriendModel) {
			return ((FriendModel) obj).getUserId() == getUserId();
		}

		return false;
	}
	
	public boolean equalsFriend(ExistingContactNumbersModel obj) {

		for (FriendModel friendModel : getFriends()) {
			
			if(friendModel.getUserId() == obj.getFriendId()){
				return true;
			}
		}
		return false;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirtsName() {
		return firtsName;
	}

	public void setFirtsName(String firtsName) {
		this.firtsName = firtsName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public ArrayList<FriendModel> getFriends() {
		if(friends == null) {
			return new ArrayList<FriendModel>();
		}
		return friends;
	}
	

	public void setFriends(ArrayList<FriendModel> friends) {
		this.friends = friends;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getGcm() {
		return gcm;
	}

	public void setGcm(String gcm) {
		this.gcm = gcm;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	@Override
	public String toString() {
		return "UserModel [userName=" + userName + ", firtsName=" + firtsName
				+ ", lastName=" + lastName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", countryCode="
				+ countryCode + ", friends=" + friends + ", eventId=" + eventId
				+ ", dateCreated=" + dateCreated + ", gcm=" + gcm + ", imei="
				+ imei + ", phoneModel=" + phoneModel + ", photo=" + photo
				+ ", cover=" + cover + "]";
	}

	/*
	 * addFriend FriendModel
	 */
	 public void addFriend(FriendModel model) {
		 if(!getFriends().contains(model)) {
			 getFriends().add(model);
		 }
	 }

	/*
	 * addFriends ArrayList<FriendModel>
	 */
	public void addFriends(ArrayList<FriendModel> models) {

		if (getFriends() == null) {
			setFriends(new ArrayList<FriendModel>());
		}
		for (FriendModel friendModel : models) {
			Log.d("FriendRequestModel",
					"nextElement: " + friendModel.getUserId());
			if (!getFriends().contains(friendModel)) {
				Log.d("FriendRequestModel",
						"if nextElement: " + friendModel.getUserId());
				getFriends().add(friendModel);
			}
		}
	}

	public void unDublicateFriens() {
		if (getFriends() != null) {
			ArrayList<FriendModel> friends = new ArrayList<FriendModel>(
					new LinkedHashSet<FriendModel>(getFriends()));

			setFriends(new ArrayList<FriendModel>());

			for (FriendModel friendModel : friends) {
				while (!getFriends().contains(friendModel)) {
					getFriends().add(friendModel);
				}
			}

			setFriends(friends);
			// Set<Customer> depdupeCustomers = new LinkedHashSet<>(customers);
			// customers.clear();
			// customers.addAll(dedupeCustomers);
		}
	}
	
//	public ArrayList<FriendModel> unDublicatedFriens() {
//		Set<FriendModel> friends = new HashSet<FriendModel>();
//		friends.addAll(getFriends());
//		setFriends(new ArrayList<FriendModel>(friends));
//		return new ArrayList<FriendModel>(friends);
//	}
	
	
	@SuppressWarnings("unchecked")
	public <T> void removeDuplicate(List<T> list) {
		Set<T> set = new HashSet<T>();
		List<T> newList = new ArrayList<T>();
		for (Iterator<T> iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add((T) element))
				newList.add((T) element);
		}
		list.clear();
		list.addAll(newList);
	}

	
	
	public void removeDuplicate() {
		Set<FriendModel> set = new HashSet<FriendModel>();
		ArrayList<FriendModel> newList = new ArrayList<FriendModel>();
		for (Iterator<FriendModel> iter = getFriends().iterator(); iter
				.hasNext();) {
			FriendModel element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		getFriends().clear();
		getFriends().addAll(newList);
	}

	/*
	 * addFriends Set<FriendModel>
	 */
	// public void addFriends(Set<FriendModel> friendModels) {
	// // TODO Auto-generated method stub
	//
	// }

}