package com.allfine.adapters;

import java.util.ArrayList;

import com.allfine.models.core.CoreModel;

public class ExistingContactsModelList extends CoreModel {
	private ArrayList<ExistingContactNumbersModel> numbersModels;

	public ArrayList<ExistingContactNumbersModel> getNumbersModels() {
		return numbersModels;
	}

	public void setNumbersModels(
			ArrayList<ExistingContactNumbersModel> numbersModels) {
		this.numbersModels = numbersModels;
	}

	@Override
	public String toString() {
		return "ExistingContactsModelList [numbersModels=" + numbersModels
				+ "]";
	}

}
