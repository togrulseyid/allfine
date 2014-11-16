package com.allfine.models.core;

import java.util.ArrayList;

public class ContactsModelList extends CoreModel {
	ArrayList<ContactNumbersModel> numbersModels;

	public ArrayList<ContactNumbersModel> getNumbersModels() {
		return numbersModels;
	}

	public void setNumbersModels(ArrayList<ContactNumbersModel> numbersModels) {
		this.numbersModels = numbersModels;
	}

	@Override
	public String toString() {
		return "ContactsModelList [numbersModels=" + numbersModels + "]";
	}

}
