package com.example.mehdi.androidcontacts.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mehdi.androidcontacts.R;
import com.example.mehdi.androidcontacts.adapter.AdapterContacts;
import com.example.mehdi.androidcontacts.helper.ContactsHelper;
import com.example.mehdi.androidcontacts.model.ModelContacts;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private static final String SORT_BY_NAME = "name";
    private static final String SORT_BY_ID = "id";
    private String sortType; // name / id

    private AdapterContacts adapter;
    private final Context mContext = MainActivity.this;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        new GetContactList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SORT_BY_NAME);
        //new GetContactList().execute();
    }

    /*********************************************************************************/
    /*********************************************************************************/
    // MENU
    /*********************************************************************************/
    /*********************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_phonebook, menu);

        // Associate searchable configuration with the SearchView
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        //searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        if (null != searchView) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    adapter.getFilter().filter(s);
                    return false;
                }
            });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_sort_id) {
            if (sortType.equalsIgnoreCase(SORT_BY_NAME)) {
                new GetContactList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SORT_BY_ID);
            } else {
                toast("Already Activated");
            }
            return true;
        }
        else if (id == R.id.action_sort_name) {
            if (sortType.equalsIgnoreCase(SORT_BY_ID)) {
                new GetContactList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SORT_BY_NAME);
            } else {
                toast("Already Activated");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*********************************************************************************/
    /*********************************************************************************/
    // PUBLISH CONTACTS
    /*********************************************************************************/
    /*********************************************************************************/

    public class GetContactList extends AsyncTask<String, Void, String>
    {
        ArrayList<ModelContacts> list = new ArrayList<>();

        @Override
        protected String doInBackground(String... params)
        {
            sortType = params[0];
            list = ContactsHelper.getInstance().readContacts(mContext.getContentResolver(), sortType);
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            // need to test ac.add

            if (null == adapter) {
                adapter = new AdapterContacts(mContext, list);
                listView.setAdapter(adapter);
            } else {
                adapter.clear();
                adapter.setNewData(list);
                adapter.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }
    }

    /*********************************************************************************/
    /*********************************************************************************/
    // Custom
    /*********************************************************************************/
    /*********************************************************************************/

    private void toast(String msg)
    {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
}