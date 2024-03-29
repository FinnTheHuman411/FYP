package com.fypkevin03.ar_restaurantmenu;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Random;


public class DatabaseHelper_Account extends SQLiteOpenHelper {
    Context mContext;
    public DatabaseHelper_Account(@Nullable Context context) {
        super(context, "Users.db", null, 2);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(id int, username text primary key, email text, password text, age int, credits int, role text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists user");
    }
    //inserting in database
    public boolean insert(String username, String email, String age,  String password, String role){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        final int id = new Random().nextInt((9999 - 1000) + 1) + 1000;
        final int randomC = new Random().nextInt((5000 - 1000) + 1) + 1000;
        contentValues.put("id", id);
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("age", age);
        contentValues.put("credits", randomC);
        contentValues.put("role", role);
        long ins =db.insert("user", null, contentValues);
        if (ins == -1) return false;
        else return true;
    }
    public boolean update(int id, String username, String email, String age,  String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("age", age);
        long upd = db.update("user", contentValues, "id = ?", new String[] {Integer.toString(id)});
        if (upd == -1) return false;
        else return true;
    }
    public boolean remove(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long upd = db.delete("user", "id = ?", new String[] {Integer.toString(id)});
        if (upd == -1) return false;
        else return true;
    }

    //checking if username exists;
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
    //checking if the user is admin;
    public Boolean chkadminrole(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select role from user where username=?", new String [] {username});
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            if (cursor.getString(0).equals("admin")) {
                return true;
            } else {
                return false;
            }
        }
        else return false;
    }

    public Cursor getInfo(String key_username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username =?" , new String[]{key_username});
        return cursor;
    }

    public int getUserId(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select id from user where username=?", new String [] {username});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        else return 0;
    }

    public Cursor getAllUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user" , null);
        return cursor;
    }

//    public void add_to_shopping_cart(int product_id, int count, String product_name, int price) {
//        SQLiteDatabase cart = this.getWritableDatabase();
//        cart.execSQL("INSERT INTO cart (product_id, count, product_name, price) VALUES(" + product_id + ", " + count + ", '" + product_name + "', " + price + ");");
////
////        Toast.makeText(mContext, product_name + " has been added to cart.",Toast.LENGTH_SHORT).show();
//    }

}
