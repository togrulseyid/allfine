package com.allfine.models.core;

import java.util.ArrayList;

public class ContactsModelList extends CoreModel {
	ArrayList<ContactNumbersModel> contactNumbersModels;

	public ArrayList<ContactNumbersModel> getContactNumbersModels() {
		return contactNumbersModels;
	}

	public void setContactNumbersModels(
			ArrayList<ContactNumbersModel> contactNumbersModels) {
		this.contactNumbersModels = contactNumbersModels;
	}

	@Override
	public String toString() {
		return "ContactsModelList [contactNumbersModels="
				+ contactNumbersModels + "]";
	}

}
