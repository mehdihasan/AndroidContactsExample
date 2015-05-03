package com.example.mehdi.androidcontacts.helper;

import com.example.mehdi.androidcontacts.model.ModelContacts;

import java.util.Comparator;

public class ComparatorContactName implements Comparator<ModelContacts> {
	
    @Override
    public int compare(ModelContacts o1, ModelContacts o2) 
    {
        return o1.getContactName().compareTo(o2.getContactName());
    }
}