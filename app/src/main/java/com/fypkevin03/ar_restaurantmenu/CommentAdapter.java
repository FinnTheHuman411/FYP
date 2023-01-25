package com.fypkevin03.ar_restaurantmenu;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class CommentAdapter extends ArrayAdapter<CommentObject> {

    public Context mContext;

    public CommentAdapter(@NonNull Context context, ArrayList<CommentObject> commentObjects) {
        super(context, 0, commentObjects);
        mContext = context;
    }

    @Override

    public View getView(int position, View convertView, final ViewGroup parent) {

        // Get the data item for this position
        final CommentObject obj = getItem(position);

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