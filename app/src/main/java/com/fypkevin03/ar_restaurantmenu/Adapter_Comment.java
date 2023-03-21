package com.fypkevin03.ar_restaurantmenu;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Adapter_Comment extends ArrayAdapter<Object_Comment> {

    public Context mContext;

    public Adapter_Comment(@NonNull Context context, ArrayList<Object_Comment> commentObjects) {
        super(context, 0, commentObjects);
        mContext = context;
    }

    @Override

    public View getView(int position, View convertView, final ViewGroup parent) {

        // Get the data item for this position
        final Object_Comment obj = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_cardview, parent, false);
        }

        // Lookup view for data population
        TextView username = (TextView) convertView.findViewById(R.id.comment_username);
        final TextView commentContent = (TextView) convertView.findViewById(R.id.content);

        // Populate the data into the template view using the data object
        username.setText(obj.username);
        commentContent.setText(obj.comment);

        // Return the completed view to render on screen
        return convertView;

    }


}