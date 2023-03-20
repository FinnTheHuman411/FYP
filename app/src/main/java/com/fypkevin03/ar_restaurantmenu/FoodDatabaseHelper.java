package com.fypkevin03.ar_restaurantmenu;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Random;


public class FoodDatabaseHelper extends SQLiteOpenHelper {
    Context mContext;
    public FoodDatabaseHelper(@Nullable Context context) {
        super(context, "Foods.db", null, 3);
        mContext = context;
    }

    final int id = new Random().nextInt((9999 - 1000) + 1) + 1000;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table foods(_id INTEGER, product_id INTEGER primary key, product_name VARCHAR, genre VARCHAR, product_info VARCHAR, price DOUBLE, image INTEGER, product_model VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("drop table if exists foods");
        onCreate(db);
    }

    //inserting in database
    public boolean insert(Integer product_id, String product_name, String genre, String product_info, Double price, Integer image, String product_model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", id);
        contentValues.put("product_id", product_id);
        contentValues.put("product_name", product_name);
        contentValues.put("genre", genre);
        contentValues.put("product_info", product_info);
        contentValues.put("price", price);
        contentValues.put("image", image);
        contentValues.put("product_model", product_model);
        long ins =db.insert("foods", null, contentValues);
        if (ins == -1) return false;
        else return true;
    }
    //checking if username exists
    /*
    public Boolean chkusername(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=?", new String [] {username});
        if (cursor.getCount() > 0) return false;
        else return true;
    }
    //checking the username and password
    public Boolean emailpassword(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username=? and password=?", new String[]{username,password});
        if (cursor.getCount()>0) return true;
        else return false;
    }

    public Cursor getInfo(String key_username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username =?" , new String[]{key_username});
        return cursor;
    }
    */

//    public void add_to_shopping_cart(int product_id, int count, String product_name, int price) {
//        SQLiteDatabase cart = this.getWritableDatabase();
//        cart.execSQL("INSERT INTO cart (product_id, count, product_name, price) VALUES(" + product_id + ", " + count + ", '" + product_name + "', " + price + ");");
////
////        Toast.makeText(mContext, product_name + " has been added to cart.",Toast.LENGTH_SHORT).show();
//    }

}
