package com.fypkevin03.ar_restaurantmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_ShoppingCart extends AppCompatActivity {

    public int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //TextView tv = (TextView) findViewById(R.id.output);
        ListView lv = (ListView) findViewById(R.id.cartListview);

        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        ArrayList<CartObject> cartObjects = new ArrayList<CartObject>();
        CartAdapter adapter = new CartAdapter(this, cartObjects);

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

                cartObjects.add(new CartObject(
                        resultSet.getInt(0),
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5)
                ));

                totalPrice += resultSet.getInt(4);
            } while (resultSet.moveToNext());
        }

        //tv.setText(tv.getText() + "\n" + totalPrice);
    }

    public void goToCheckout(View v){
        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        Cursor resultSet = cart.rawQuery("Select * from cart",null);
        resultSet.moveToFirst();
        if (resultSet.getCount() != 0){
            Intent i = new Intent(this, CheckoutActivity.class);
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


}