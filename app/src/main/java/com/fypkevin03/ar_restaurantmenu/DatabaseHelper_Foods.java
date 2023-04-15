package com.fypkevin03.ar_restaurantmenu;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Random;


public class DatabaseHelper_Foods extends SQLiteOpenHelper {
    Context mContext;
    public DatabaseHelper_Foods(@Nullable Context context) {
        super(context, "Foods.db", null, 4);
        mContext = context;
    }

    final int id = new Random().nextInt((9999 - 1000) + 1) + 1000;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table foods(_id INTEGER, product_id INTEGER primary key, product_name VARCHAR, genre VARCHAR, product_info VARCHAR, price DOUBLE, image INTEGER, product_model VARCHAR, size_s DOUBLE, size_l DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("drop table if exists foods");
        onCreate(db);
    }

    //inserting in database
    public boolean insert(Integer product_id, String product_name, String genre, String product_info, Double price, Integer image, String product_model, Double size_s, Double size_l){
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
        contentValues.put("size_s", size_s);
        contentValues.put("size_l", size_l);
        long ins =db.insert("foods", null, contentValues);
        if (ins == -1) return false;
        else return true;
    }

}
