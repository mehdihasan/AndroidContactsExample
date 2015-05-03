package com.example.mehdi.androidcontacts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mehdi.androidcontacts.helper.Utility;
import com.example.mehdi.androidcontacts.model.ModelContactDetails;
import com.example.mehdi.androidcontacts.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mehdi on 3/14/2015.
 */
public class AdapterContactDetails extends ArrayAdapter<ModelContactDetails>
{
    private final Context mContext;

    public AdapterContactDetails(Context nContext, List<ModelContactDetails> objects)
    {
        super(nContext, 0, objects);
        this.mContext = nContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        ModelContactDetails mcd = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item_details, parent, false);
        }

        // CONTACT NAME
        final String value = mcd.getItemValue();
        TextView cName = (TextView) convertView.findViewById(R.id.textView1);
        cName.setText(value);

        // AVATAR
        ImageView userAvatar = (ImageView) convertView.findViewById(R.id.imageView1);
        final int itemType = mcd.getItemType();
        if (itemType == 0) {
            Picasso.with(mContext)
                    .load(R.drawable.phone)
                    .placeholder(R.drawable.phone) // optional
                    .error(R.drawable.phone)         // optional
                    .into(userAvatar);
        } else if (itemType == 1) {
            Picasso.with(mContext)
                    .load(R.drawable.email)
                    .placeholder(R.drawable.email) // optional
                    .error(R.drawable.email)         // optional
                    .into(userAvatar);
        } else {
            Picasso.with(mContext)
                    .load(R.drawable.defaulf)
                    .placeholder(R.drawable.defaulf) // optional
                    .error(R.drawable.defaulf)         // optional
                    .into(userAvatar);
        }



        // ROW TAPPING ACTION
        convertView.findViewById(R.id.holder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemType == 0) {
                    Utility.getInstance().call(mContext, value);
                } else if (itemType == 1) {
                    Utility.getInstance().sendEmail(mContext, value);
                }
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
