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

import java.util.ArrayList;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class Adapter_Cart extends ArrayAdapter<Object_Cart> {

    public Context mContext;

    public Adapter_Cart(@NonNull Context context, ArrayList<Object_Cart> cartObjects) {
        super(context, 0, cartObjects);
        mContext = context;
    }

    @Override

    public View getView(int position, View convertView, final ViewGroup parent) {

        // Get the data item for this position
        final Object_Cart obj = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cardview, parent, false);
        }

        // Lookup view for data population
        ImageView food_product_image = (ImageView) convertView.findViewById(R.id.product_image);
        TextView food_product_name = (TextView) convertView.findViewById(R.id.comment_username);
        final TextView food_price = (TextView) convertView.findViewById(R.id.content);
        final TextView food_count = (TextView) convertView.findViewById(R.id.count);

        // Populate the data into the template view using the data object
        food_product_image.setImageResource(obj.image);
        food_product_name.setText(obj.product_name);
        food_price.setText("Price: $" + obj.price);
        food_count.setText("Count: " + obj.count);

        //button
        com.google.android.material.button.MaterialButton btn_remove = (com.google.android.material.button.MaterialButton) convertView.findViewById(R.id.btn_remove);

        // Cache row position inside the button using `setTag`
        btn_remove.setTag(position);

        // Attach the click event handler
        btn_remove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                Object_Cart object = getItem(position);
                remove(object);

                SQLiteDatabase cart = mContext.openOrCreateDatabase("cart",mContext.MODE_PRIVATE,null);
                cart.delete("cart", "_id = " + object.cartID, null);
            }
        });

        // Return the completed view to render on screen
        return convertView;

    }


}