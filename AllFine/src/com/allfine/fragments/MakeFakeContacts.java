package com.allfine.fragments;

import java.io.IOException;
import java.util.ArrayList;

import android.util.Log;

import com.allfine.models.core.ContactNumbersModel;
import com.allfine.models.core.ContactsModelList;
import com.allfine.operations.ObjectConvertor;

public class MakeFakeContacts {
	static ArrayList<String> numbers;
	static ContactsModelList contactsModelList;
	static ArrayList<ContactNumbersModel> contactNumbersModels;
	static ContactNumbersModel contactNumbersModel;
	
	public static void make() {
		
		contactsModelList = new ContactsModelList();
		
			contactNumbersModels = new ArrayList<ContactNumbersModel>();
			
			
			
				
					contactNumbersModel = new ContactNumbersModel();
					contactNumbersModel.setFirstName("Togrul");
					contactNumbersModel.setLastName("Seyidov");
						numbers = new ArrayList<String>();
							numbers.add("+994518731053");
							numbers.add("051-873-10-54");
							numbers.add("994518731055");
							numbers.add("0518731056");
							numbers.add("00994518731057");
					contactNumbersModel.setNumbers(numbers);
				contactNumbersModels.add(contactNumbersModel);
			
				

//				numbers.clear();
				
				
					contactNumbersModel = new ContactNumbersModel();
					contactNumbersModel.setFirstName("Kamran");
					contactNumbersModel.setLastName("lastKmran");
						numbers = new ArrayList<String>();
							numbers.add("+994511111153");
							numbers.add("051-111-11-54");
							numbers.add("994511111155");
							numbers.add("0511111156");
							numbers.add("00994511111157");
					contactNumbersModel.setNumbers(numbers);
				contactNumbersModels.add(contactNumbersModel);
			
			
//				numbers.clear();
				
					contactNumbersModel = new ContactNumbersModel();
					contactNumbersModel.setFirstName("Qaqas");
					contactNumbersModel.setLastName("Qaqasov");
						numbers = new ArrayList<String>();
							numbers.add("+994512221153");
							numbers.add("051-222-11-54");
							numbers.add("994522211155");
							numbers.add("0522211156");
							numbers.add("00994522211157");
					contactNumbersModel.setNumbers(numbers);
			contactNumbersModels.add(contactNumbersModel);

		
		contactsModelList.setNumbersModels(contactNumbersModels);

		ObjectConvertor<ContactsModelList> objectConvertor = new ObjectConvertor<ContactsModelList>();
		try {
			Log.d("contactsModelList",
					"" + objectConvertor.getClassString(contactsModelList));
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
		

	}

}
