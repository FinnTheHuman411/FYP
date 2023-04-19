package com.fypkevin03.ar_restaurantmenu;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Random;


public class DatabaseHelper_Foods extends SQLiteOpenHelper {
    Context mContext;
    public DatabaseHelper_Foods(@Nullable Context context) {
        super(context, "Foods.db", null, 5);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table foods(_id INTEGER, product_id INTEGER primary key, product_name VARCHAR, genre VARCHAR, product_info VARCHAR, price DOUBLE, image VARCHAR, product_model VARCHAR, size_s DOUBLE, size_l DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("drop table if exists foods");
        onCreate(db);
    }

    //inserting in database
    public boolean insert(Integer product_id, String product_name, String genre, String product_info, Double price, String image, String product_model, Double size_s, Double size_l){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        final int id = new Random().nextInt((9999 - 1000) + 1) + 1000;
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

    public boolean update(int product_id, String product_name, String genre, String product_info, Double price, String image, String product_model, Double size_s, Double size_l){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("product_name", product_name);
        contentValues.put("genre", genre);
        contentValues.put("product_info", product_info);
        contentValues.put("price", price);
        contentValues.put("image", image);
        contentValues.put("product_model", product_model);
        contentValues.put("size_s", size_s);
        contentValues.put("size_l", size_l);
        long upd = db.update("foods", contentValues, "product_id = ?", new String[] {Integer.toString(product_id)});
        if (upd == -1) return false;
        else return true;
    }

    public boolean remove(int product_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long upd = db.delete("foods", "product_id = ?", new String[] {Integer.toString(product_id)});
        if (upd == -1) return false;
        else return true;
    }

    public Cursor getInfo(int product_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from foods where product_id = ?" , new String[]{Integer.toString(product_id)});
        return cursor;
    }

    public Cursor getAllFoods(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from foods" , null);
        return cursor;
    }
}
