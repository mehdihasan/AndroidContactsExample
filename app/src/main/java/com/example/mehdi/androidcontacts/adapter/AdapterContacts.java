package com.example.mehdi.androidcontacts.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mehdi.androidcontacts.R;
import com.example.mehdi.androidcontacts.activity.SingleUserActivity;
import com.example.mehdi.androidcontacts.model.ModelContacts;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterContacts extends ArrayAdapter<ModelContacts> {

    private ContactsFilter cFilter;
	private final Context mContext;
    private List<ModelContacts> contactList;
    private List<ModelContacts> contactListNew;

	public AdapterContacts(Context nContext, List<ModelContacts> objects)
	{
		super(nContext, 0, objects);
		this.mContext = nContext;
        this.contactList = objects;
        this.contactListNew = new ArrayList<>();
        this.contactListNew.addAll(objects);
	}

    public void setNewData(List<ModelContacts> objects)
    {
        if (null != this.contactListNew) {
            this.contactListNew = null;
        }
        if (null != cFilter) {
            cFilter = null;
        }
        //this.contactList = objects;
        this.contactListNew = new ArrayList<>();
        this.contactListNew.addAll(objects);
    }

    static class ViewHolder {
        TextView cName;
        TextView registerMarker;
        ImageView userAvatar;
    }

    @Override
    public Filter getFilter()
    {
        if (cFilter == null)
            cFilter = new ContactsFilter();

        return cFilter;
    }
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
       
		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_items, parent, false);
            holder = new ViewHolder();
            holder.cName = (TextView) convertView.findViewById(R.id.textView1);
            holder.registerMarker = (TextView) convertView.findViewById(R.id.viewOne);
            holder.userAvatar = (ImageView) convertView.findViewById(R.id.imageView1);

            convertView.setTag(holder);
		} else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        final ModelContacts mc = getItem(position);

        if (null != mc) {
            // CONTACT NAME
            holder.cName.setText(mc.getContactName() + "("+mc.getContactID()+")");

            // CONTACT AVATAR
            final String imageUri = mc.getContactImageUri();
            Picasso.with(mContext)
                    .load(imageUri)
                    .placeholder(R.drawable.avatar) // optional
                    .error(R.drawable.defaulf)         // optional
                    .into(holder.userAvatar);

            // REGISTER MARKER
            holder.registerMarker.setText(String.valueOf(position));
            if (mc.isRegisteredUser()) {
                holder.registerMarker.setBackgroundColor(mContext.getResources().getColor(R.color.reg));
            } else {
                holder.registerMarker.setBackgroundColor(mContext.getResources().getColor(R.color.non_reg));
            }

            // ROW TAPPING ACTION
            convertView.findViewById(R.id.holder).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sua = new Intent(mContext, SingleUserActivity.class);
                    sua.putExtra("contactId", mc.getContactID());
                    sua.putExtra("contactName", mc.getContactName());
                    sua.putExtra("contactAvatar", imageUri);
                    mContext.startActivity(sua);
                }
            });
        }
       
		// Return the completed view to render on screen
		return convertView;
    }


    /**
     * Custom filter for ArrayList<ModelContacts>
     */
    @SuppressWarnings("unchecked")
    private class ContactsFilter extends Filter
    {
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            contactList.clear();
            if (null != results.values) {
                contactList.addAll((List<ModelContacts>) results.values);
                Log.d("RESULTS:", "" + contactList.size());
                AdapterContacts.this.notifyDataSetChanged();
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelContacts> filteredResults = getFilteredResults(constraint);
            FilterResults results = new FilterResults();
            results.values = filteredResults;
            results.count = filteredResults.size();
            return results;
        }

        private List getFilteredResults(CharSequence constraint) {

            List<ModelContacts> temp = new ArrayList<>();

            if (TextUtils.isEmpty(constraint)) {

                if (temp.size() > 0)
                    temp.clear();
                // I have created new List from old myList before search begins
                    /*if(mContext instanceof MainActivity){
                        temp.addAll(((MainActivity) mContext).getListData());
                        Log.d("RESULTS NEW:", "" + temp.size());
                    }*/
                temp.addAll(contactListNew);
            }
            else
            {
                // newList
                for (ModelContacts model : contactListNew)
                {
                    if(model.getContactName().toLowerCase().contains(constraint.toString().toLowerCase()))
                        temp.add(model);
                }
            }

            return temp;
        }
    }
}