package com.example.mehdi.androidcontacts.model;

public class ModelContacts {

	private String contactName;
	private String contactImageUri;
	private String contactID;
    private boolean registeredUser;

	/*********************************************************************************/
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/*********************************************************************************/
	public String getContactImageUri() {
		return contactImageUri;
	}

	public void setContactImageUri(String contactImageUri) {
		this.contactImageUri = contactImageUri;
	}

	/*********************************************************************************/
	public String getContactID() {
		return contactID;
	}

	public void setContactID(String contactID) {
		this.contactID = contactID;
	}

    /*********************************************************************************/
    public boolean isRegisteredUser() { return registeredUser; }

    public void setRegisteredUser(boolean registeredUser) { this.registeredUser = registeredUser; }
}
