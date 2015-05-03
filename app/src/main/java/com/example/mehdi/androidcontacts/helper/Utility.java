package com.example.mehdi.androidcontacts.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by Mehdi on 3/14/2015.
 */
public class Utility
{
    private static Utility instance;

    public synchronized static Utility getInstance()
    {
        if( instance == null ) {
            instance = new Utility();
        }
        return instance;
    }

    /*********************************************************************************/
    /*********************************************************************************/
    // MAKE A PHONE CALL
    /*********************************************************************************/
    /*********************************************************************************/

    public void call(Context nContext, String phone)
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        nContext.startActivity(callIntent);
    }


    /*********************************************************************************/
    /*********************************************************************************/
    // EMAIL
    /*********************************************************************************/
    /*********************************************************************************/

    public void sendEmail(Context nContext, String email)
    {
        String[] TO = {email};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        //emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            nContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(nContext,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
