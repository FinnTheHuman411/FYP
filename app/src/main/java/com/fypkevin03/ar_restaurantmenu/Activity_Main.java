package com.fypkevin03.ar_restaurantmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

public class Activity_Main extends AppCompatActivity {

    DatabaseHelper_Foods fdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fdb = new DatabaseHelper_Foods(this);
        fdb.insert(1001, "Pepperoni Pizza", "Main", "Pizza", 75.0, R.drawable.fd1, "1001.glb", 0.75, 1.25);
        fdb.insert(1002, "Cheezburger", "Main", "Hamburger", 35.0, R.drawable.fd2, "1002.glb", 0.75, 1.25);
        fdb.insert(4002, "Coke", "Drink", "Soft Drink", 7.0, R.drawable.bv2, null, 0.75, 1.25);

        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        cart.execSQL("DROP TABLE IF EXISTS cart;");
        cart.execSQL("CREATE TABLE IF NOT EXISTS cart(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "product_id INTEGER NOT NULL, " +
                "product_id_size VARCHAR NOT NULL, " +
                "product_name VARCHAR NOT NULL, " +
                "product_model VARCHAR, " +
                "count INTEGER NOT NULL," +
                "price INTEGER NOT NULL," +
                "image INTEGER NOT NULL," +
                "scale DOUBLE NOT NULL," +
                "note VARCHAR" +
                ");");
        SQLiteDatabase comments = openOrCreateDatabase("comments",MODE_PRIVATE,null);
        //comments.execSQL("DROP TABLE IF EXISTS comments;");
        comments.execSQL("CREATE TABLE IF NOT EXISTS comments(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "product_id INTEGER NOT NULL, " +
                "username VARCHAR NOT NULL, " +
                "comment VARCHAR NOT NULL" +
                ");");
        SQLiteDatabase ratings = openOrCreateDatabase("ratings",MODE_PRIVATE,null);
        //comments.execSQL("DROP TABLE IF EXISTS comments;");
        ratings.execSQL("CREATE TABLE IF NOT EXISTS ratings(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "product_id INTEGER NOT NULL, " +
                "username VARCHAR NOT NULL, " +
                "rating DOUBLE NOT NULL" +
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