package com.allfine.models.core;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ExistingContactsModelList extends CoreModel implements
		Serializable {

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
