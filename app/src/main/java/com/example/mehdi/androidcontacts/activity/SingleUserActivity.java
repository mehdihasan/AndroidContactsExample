package com.example.mehdi.androidcontacts.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mehdi.androidcontacts.R;
import com.example.mehdi.androidcontacts.adapter.AdapterContactDetails;
import com.example.mehdi.androidcontacts.helper.ContactsHelper;
import com.example.mehdi.androidcontacts.model.ModelContactDetails;

import java.util.ArrayList;

public class SingleUserActivity extends ActionBarActivity {
	
	private final Context mContext = SingleUserActivity.this;
	private String contactId, contactAvatarUri;
    private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_user);

        Bundle bundle = getIntent().getExtras();
        contactId = bundle.getString("contactId");
        contactAvatarUri = bundle.getString("contactAvatar");

        setTitle(bundle.getString("contactName"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		init();
	}

    /*********************************************************************************/
	/*********************************************************************************/
	// MENU
	/*********************************************************************************/
	/*********************************************************************************/
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
            Toast.makeText(SingleUserActivity.this, "settings", Toast.LENGTH_LONG).show();
			return true;
		}
        else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
	
	/*********************************************************************************/
	/*********************************************************************************/
	// CUSTOM METHODS
	/*********************************************************************************/
	/*********************************************************************************/
	
	private void init() 
	{
		ImageView img = (ImageView) findViewById(R.id.imageView1);
		if (null != contactAvatarUri) {
			try 
			{ 
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.parse(contactAvatarUri));
				if (null != bitmap) {
					img.setImageBitmap(bitmap);
				}
			} 
			catch (Exception e) {e.printStackTrace();}
		}

        listView = (ListView) findViewById(R.id.listViewDetails);
		new GetContactDetails().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	
	/*********************************************************************************/
	/*********************************************************************************/
	// GET CONTACT DETAILS
	/*********************************************************************************/
	/*********************************************************************************/
	
	public class GetContactDetails extends AsyncTask<String, Void, String>
	{
		ArrayList<String> emailList;
		ArrayList<String> phoneNumberList;
        ArrayList<String> rawContactIdList;
		
		@Override
		protected String doInBackground(String... params)
		{	
			emailList = ContactsHelper.getInstance().getEmailAddresses(mContext.getContentResolver(), contactId);
			phoneNumberList = ContactsHelper.getInstance().getPhoneNumbers(mContext.getContentResolver(), contactId);
            rawContactIdList = ContactsHelper.getInstance().getRawContactId(mContext.getContentResolver(), contactId);
			
			return null;
		}
		
		@Override
	    protected void onPostExecute(String result)
		{
            ArrayList<ModelContactDetails> list = new ArrayList<>();

			// PHONE
			//TextView phoneNumberView = (TextView) findViewById(R.id.phoneNumbers);
			if (null != phoneNumberList) {
				//StringBuffer sbp = new StringBuffer();
				for(String object: phoneNumberList){
                    ModelContactDetails mcd = new ModelContactDetails();
                    mcd.setItemType(0);
                    mcd.setItemValue(object);
                    list.add(mcd);
					//sbp.append("\n"+object);
				}
				//phoneNumberView.setText(sbp);
			}
						
			// EMAIL
			//TextView emailView = (TextView) findViewById(R.id.emailAddresses);
			if (null != emailList) {
				//StringBuffer sbe = new StringBuffer();
				for(String object: emailList){
                    ModelContactDetails mcd = new ModelContactDetails();
                    mcd.setItemType(1);
                    mcd.setItemValue(object);
                    list.add(mcd);
					//sbe.append("\n"+object);
				}
				//emailView.setText(sbe);
			}

            // RAW ID
            if (null != rawContactIdList) {
                //StringBuffer sbe = new StringBuffer();
                for(String object: rawContactIdList){
                    ModelContactDetails mcd = new ModelContactDetails();
                    mcd.setItemType(3);
                    mcd.setItemValue("RAW ID : "+object);
                    list.add(mcd);
                    //sbe.append("\n"+object);
                }
                //emailView.setText(sbe);
            }

            listView.setAdapter(new AdapterContactDetails(mContext, list));
	    }
	}
}
