package com.allfine.operations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;

public class ContactInfoProvider {

	private Activity activity;

	public ContactInfoProvider(Activity activity) {
		this.activity = activity;
	}

//	public AndroidContactModel findContactInfoByNumber(String number) {
//
//		AndroidContactModel model = new AndroidContactModel();
//		uploadContactPhoto(activity, number, model);
//		if (model.isHasPhoto()) {
//			model.setBitmap(BitmapFactory.decodeStream(openPhoto(model.getId())));
//		}
//
//		return model;
//	}

	private InputStream openPhoto(long contactId) {
		Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI,
				contactId);
		Uri photoUri = Uri.withAppendedPath(contactUri,
				Contacts.Photo.CONTENT_DIRECTORY);
		Cursor cursor = activity.getContentResolver().query(photoUri,
				new String[] { Contacts.Photo.PHOTO }, null, null, null);
		if (cursor == null) {
			return null;
		}
		try {
			if (cursor.moveToFirst()) {
				byte[] data = cursor.getBlob(0);
				if (data != null) {
					return new ByteArrayInputStream(data);
				}
			}
		} finally {
			cursor.close();
		}
		return null;
	}

//	private void uploadContactPhoto(Activity activity, String number, AndroidContactModel model) {
//		String name = null;
//		String contactId = null;
//		InputStream input = null;
//
//		// define the columns I want the query to return
//		String[] projection = new String[] {
//				ContactsContract.PhoneLookup.DISPLAY_NAME,
//				ContactsContract.PhoneLookup._ID };
//
//		// encode the phone number and build the filter URI
//		Uri contactUri = Uri.withAppendedPath(
//				ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
//				Uri.encode(number));
//
//		// query time
//		Cursor cursor = activity.getContentResolver().query(contactUri,
//				projection, null, null, null);
//
//		if (cursor.moveToFirst()) {
//
//			// Get values from contacts database:
//			contactId = cursor.getString(cursor
//					.getColumnIndex(ContactsContract.PhoneLookup._ID));
//			name = cursor.getString(cursor
//					.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
//
//			// Get photo of contactId as input stream:
//			Uri uri = ContentUris.withAppendedId(
//					ContactsContract.Contacts.CONTENT_URI,
//					Long.parseLong(contactId));
//			input = ContactsContract.Contacts.openContactPhotoInputStream(
//					activity.getContentResolver(), uri);
//
//			model.setNumber(number);
//			model.setName(name);
//			model.setId(Long.parseLong(contactId));
//
//		} else {
//			// Contact Not Found
//			model.setNumber(number);
//			return;
//		}
//
//		// Only continue if we found a valid contact photo:
//		if (input == null) {
//			// No photo found
//			model.setHasPhoto(false);
//			model.setName(name);
//			model.setId(Long.parseLong(contactId));
//			return; // no photo
//		} else {
//			// Photo found
//			model.setHasPhoto(true);
//			model.setName(name);
//			model.setId(Long.parseLong(contactId));
//		}
//	}

}
