package com.fypkevin03.ar_restaurantmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_ShoppingCart extends AppCompatActivity {

    public double totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //TextView tv = (TextView) findViewById(R.id.output);
        ListView lv = (ListView) findViewById(R.id.cartListview);
        Button btn_checkout = (Button) findViewById(R.id.btn_checkout);

        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        ArrayList<Object_Cart> cartObjects = new ArrayList<Object_Cart>();
        Adapter_Cart adapter = new Adapter_Cart(this, cartObjects);

        lv.setAdapter(adapter);

        Cursor resultSet = cart.rawQuery("Select * from cart",null);
        resultSet.moveToFirst();

        if (resultSet.getCount() != 0){
            do{
//                tv.setText(tv.getText() + "\n" +
//                        resultSet.getString(1) + " " +
//                        resultSet.getString(2) + " " +
//                        resultSet.getString(3) + " " +
//                        resultSet.getString(4)
//                        );

                cartObjects.add(new Object_Cart(
                        resultSet.getInt(0),
                        resultSet.getInt(1),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6)
                ));

                totalPrice += resultSet.getInt(4) * resultSet.getInt(5);
            } while (resultSet.moveToNext());
        }

        btn_checkout.setText("Checkout: $" + totalPrice);
    }

    public void goToCheckout(View v){
        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        Cursor resultSet = cart.rawQuery("Select * from cart",null);
        resultSet.moveToFirst();
        if (resultSet.getCount() != 0){
            Intent i = new Intent(this, Activity_Checkout.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "You must add product before checkout.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double price) {
        totalPrice = price;
        Button btn_checkout = (Button) findViewById(R.id.btn_checkout);
        btn_checkout.setText("Checkout: $" + totalPrice);
    }

}