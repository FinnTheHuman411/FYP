package com.fypkevin03.ar_restaurantmenu;


import android.content.ContentValues;
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
    double price;
    int count;

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
        price = obj.price;
        count = obj.count;
        food_price.setText("Price: $" + price);
        food_count.setText("Count: " + count);


        //button
        com.google.android.material.button.MaterialButton btn_remove = (com.google.android.material.button.MaterialButton) convertView.findViewById(R.id.btn_remove);
        com.google.android.material.button.MaterialButton btn_add = (com.google.android.material.button.MaterialButton) convertView.findViewById(R.id.btn_add);
        com.google.android.material.button.MaterialButton btn_minus = (com.google.android.material.button.MaterialButton) convertView.findViewById(R.id.btn_minus);

        // Cache row position inside the button using `setTag`
        btn_remove.setTag(position);
        btn_add.setTag(position);
        btn_minus.setTag(position);

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

        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                Object_Cart object = getItem(position);
                count = count+1;
                food_count.setText("Count: " + count);
                ((Activity_ShoppingCart) getContext()).setTotalPrice(((Activity_ShoppingCart) getContext()).getTotalPrice()+obj.price);

                SQLiteDatabase cart = mContext.openOrCreateDatabase("cart",mContext.MODE_PRIVATE,null);
                ContentValues cv = new ContentValues();
                cv.put("count", count);
                cart.update("cart", cv, "_id = ?", new String [] {Integer.toString(object.cartID)});
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                Object_Cart object = getItem(position);
                if (count > 1) {
                    count = count-1;
                    food_count.setText("Count: " + count);
                    ((Activity_ShoppingCart) getContext()).setTotalPrice(((Activity_ShoppingCart) getContext()).getTotalPrice()-obj.price);

                    SQLiteDatabase cart = mContext.openOrCreateDatabase("cart",mContext.MODE_PRIVATE,null);
                    ContentValues cv = new ContentValues();
                    cv.put("count", count);
                    cart.update("cart", cv, "_id = ?", new String [] {Integer.toString(object.cartID)});
                }
            }
        });

        // Return the completed view to render on screen
        return convertView;

    }


}