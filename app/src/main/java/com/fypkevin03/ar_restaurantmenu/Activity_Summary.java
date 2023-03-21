package com.fypkevin03.ar_restaurantmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Activity_Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        int totalprice = getIntent().getExtras().getInt("totalPrice");

        TextView credit = (TextView) findViewById(R.id.credit);
        credit.setText("Credit you have earn: " + (totalprice * 10));

        ListView summary = (ListView) findViewById(R.id.lv_summary);

        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        ArrayList<Object_Cart> cartObjects = new ArrayList<Object_Cart>();
        Adapter_Summary adapter = new Adapter_Summary(this, cartObjects);
        summary.setAdapter(adapter);

        Cursor resultSet = cart.rawQuery("Select * from cart",null);
        resultSet.moveToFirst();

        if (resultSet.getCount() != 0){
            do{
                cartObjects.add(new Object_Cart(
                        resultSet.getInt(0),
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5)
                ));
            } while (resultSet.moveToNext());
        }
    }

    public void goToMainPage(View v){
        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        cart.execSQL("DROP TABLE IF EXISTS cart;");
        cart.execSQL("CREATE TABLE IF NOT EXISTS cart(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "product_id INTEGER NOT NULL, " +
                "product_name VARCHAR NOT NULL, " +
                "count INTEGER NOT NULL," +
                "price INTEGER NOT NULL," +
                "image INTEGER NOT NULL" +
                ");");
        finish();
    }
}