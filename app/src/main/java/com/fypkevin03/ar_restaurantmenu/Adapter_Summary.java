package com.fypkevin03.ar_restaurantmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Adapter_Summary extends ArrayAdapter<Object_Cart> {
    public Context mContext;

    public Adapter_Summary(@NonNull Context context, ArrayList<Object_Cart> cartObjects) {
        super(context, 0, cartObjects);
        mContext = context;

    }

    @Override

    public View getView(int position, View convertView, final ViewGroup parent) {

        // Get the data item for this position
        final Object_Cart obj = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_summary, parent, false);
        }

        // Lookup view for data population
        TextView food_product_name = (TextView) convertView.findViewById(R.id.summary_product_name);
        final TextView food_price = (TextView) convertView.findViewById(R.id.summary_price);
        final TextView food_count = (TextView) convertView.findViewById(R.id.summary_count);
        final TextView food_note = (TextView) convertView.findViewById(R.id.summary_note);

        // Populate the data into the template view using the data object
        food_product_name.setText(obj.product_name);
        food_price.setText("Price: $" + obj.price);
        food_count.setText("Count: " + obj.count);
        food_note.setText("Note: " + obj.note);


        // Return the completed view to render on screen
        return convertView;

    }
}
