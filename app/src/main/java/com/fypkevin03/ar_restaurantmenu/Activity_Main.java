package com.fypkevin03.ar_restaurantmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

public class Activity_Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        SQLiteDatabase comments = openOrCreateDatabase("comments",MODE_PRIVATE,null);
        //comments.execSQL("DROP TABLE IF EXISTS comments;");
        comments.execSQL("CREATE TABLE IF NOT EXISTS comments(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "product_id INTEGER NOT NULL, " +
                "username VARCHAR NOT NULL, " +
                "comment VARCHAR NOT NULL" +
                ");");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Activity_Main.this, Activity_Login.class);
                startActivity(i);
                finish();
            }
        },2000);

    }
}