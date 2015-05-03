package com.example.mehdi.androidcontacts.helper;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.mehdi.androidcontacts.model.ModelContacts;

import java.util.ArrayList;
import java.util.Collections;

public class ContactsHelper {

    private final int[] userArray = {41, 55, 109, 137, 51, 296, 85, 28, 23, 300, 81, 65, 151, 319, 39, 50, 281, 122, 25, 145, 106, 80, 102, 146, 153, 142, 136, 32, 107, 95};
    private static ContactsHelper instance;

    public synchronized static ContactsHelper getInstance() {
        if( instance == null ) {
            instance = new ContactsHelper();
        }
        return instance;
    }

    private boolean checkIfExists(String contactId)
    {
        int cid = Integer.parseInt(contactId);
		for (int anUserArray : userArray) {
			if (cid == anUserArray) {
				return true;
			}
		}
        return false;
    }

	/*********************************************************************************/
	/*********************************************************************************/
	// READ CONTACTS
	/*********************************************************************************/
	/*********************************************************************************/
	
	public ArrayList<ModelContacts> readContacts(ContentResolver cr, String sortOrder)
	{
		ArrayList<ModelContacts> list = new ArrayList<>();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		String image_uri = "";
		
		if (cur.getCount() > 0) 
		{ 
			while (cur.moveToNext()) 
			{ 
				String id = cur.getString(cur .getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur.getString(cur .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				image_uri = cur.getString(cur .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
				if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
				{
					ModelContacts mc = new ModelContacts();
					mc.setContactID(id);
					
					// NAME
					//System.out.println("name : " + name + ", ID : " + id);
					mc.setContactName(name);
					
					// IMAGE
					if (image_uri != null) 
					{ 
						//System.out.println(Uri.parse(image_uri));
						mc.setContactImageUri(image_uri);
					}

                    mc.setRegisteredUser(checkIfExists(id));
					
					list.add(mc);
				}
				
			} // Cursor while

            if (sortOrder.equalsIgnoreCase("name")) {
                Collections.sort(list, new ComparatorContactName());
            } else {
                Collections.sort(list, new ComparatorContactId());
            }

		} // cursor If
		cur.close();
		
		return list;
	} // readContacts()
	
	/*********************************************************************************/
	/*********************************************************************************/
	// GET RAW CONTACT ID FORM CONTACT ID
	/*********************************************************************************/
	/*********************************************************************************/
	
	public ArrayList<String> getRawContactId(ContentResolver cr, String contactID)
	{
        ArrayList<String> rawID = new ArrayList<>();
        int contactId = Integer.parseInt(contactID);
        int rawContactId = 0;
		String[] projection=new String[]{ContactsContract.RawContacts._ID};
	    String selection= ContactsContract.RawContacts.CONTACT_ID+"=?";
	    String[] selectionArgs=new String[]{String.valueOf(contactId)};
	    Cursor c = cr.query(ContactsContract.RawContacts.CONTENT_URI, projection, selection, selectionArgs, null);

        while (c.moveToNext())
        {
            rawContactId = c.getInt(c.getColumnIndex(ContactsContract.RawContacts._ID));
            rawID.add(String.valueOf(rawContactId));
        } c.close();
	    
	    return rawID;
	}
	
	/*********************************************************************************/
	/*********************************************************************************/
	// READ CONTACT PHONE NUMBERS
	/*********************************************************************************/
	/*********************************************************************************/
	
	public ArrayList<String> getPhoneNumbers(ContentResolver cr, String id)
	{
		ArrayList<String> numbers = new ArrayList<>();
		// PHONE NUMBER
		Cursor pCur = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
				new String[] { id },
				null); 
		while (pCur.moveToNext()) 
		{ 
			//sb.append("\n Phone number:" + phone); 
			numbers.add(pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		} pCur.close(); 
		
		return numbers;
	}
	
	/*********************************************************************************/
	/*********************************************************************************/
	// READ CONTACT EMAIL ADDRESSES
	/*********************************************************************************/
	/*********************************************************************************/
	
	public ArrayList<String> getEmailAddresses(ContentResolver cr, String id)
	{
		ArrayList<String> emails = new ArrayList<>();
		String emailType = null, emailContact = null;
		
		// EMAIL ADDRESS
		Cursor emailCur = cr.query(
				ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
				ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
				new String[] { id },
				null); 
		while (emailCur.moveToNext()) 
		{ 
			emailContact = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
			emailType = emailCur .getString(emailCur .getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
			//sb.append("\nEmail:" + emailContact + "Email type:" + emailType); 
			System.out.println("Email " + emailContact + " Email Type : " + emailType);
			emails.add(emailContact);
		} emailCur.close();
		
		return emails;
	}
}
