package com.example.mehdi.androidcontacts.helper;

import com.example.mehdi.androidcontacts.model.ModelContacts;

import java.util.Comparator;

/**
 * Created by Mehdi on 3/17/2015.
 */
public class ComparatorContactId implements Comparator<ModelContacts> {

    @Override
    public int compare(ModelContacts o1, ModelContacts o2)
    {
        return Integer.parseInt(o1.getContactID()) - Integer.parseInt(o2.getContactID());
    }
}