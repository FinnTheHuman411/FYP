package com.fypkevin03.ar_restaurantmenu;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_FoodPage extends AppCompatActivity {

    int food_image, product_id;
    String price, product_name, product_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodpage);

        final int food_id = getIntent().getIntExtra("product_id",0);   // Pass from Food Page
        product_id = food_id;
        SQLiteDatabase foods = openOrCreateDatabase("Foods.db",MODE_PRIVATE,null);
        Cursor resultSet = foods.rawQuery("Select * from foods WHERE product_id = ?",new String[] {Integer.toString(product_id)});

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ImageView image = findViewById(R.id.foodImage);
        TextView foodName = findViewById(R.id.foodName);
        TextView basicInfo = findViewById(R.id.foodInfo);

        if (product_id != 0) {
            resultSet.moveToFirst();
            food_image = resultSet.getInt(6);
            price = resultSet.getString(5);
            product_name = resultSet.getString(2);
            product_model = resultSet.getString(7);

            image.setImageResource(food_image);
            foodName.setText(product_name);
            basicInfo.setText("Price: $" + price);
        }


        RatingBar bar = (RatingBar)findViewById(R.id.ratingBar);
        bar.setRating(3);
    }

    public void btn_add(View v){
        add_to_shopping_cart(product_id, product_name, Double.parseDouble(price), food_image);
    }

    public void add_to_shopping_cart(int product_id, String product_name, double price, int image){
        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        Cursor cursor = cart.rawQuery("Select * from cart where product_id=?", new String [] {Integer.toString(product_id)});

        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            ContentValues cv = new ContentValues();
            cv.put("count", cursor.getInt(3) + 1);
            cart.update("cart", cv, "product_id = ?", new String [] {Integer.toString(product_id)});
        } else {
            cart.execSQL("INSERT INTO cart (product_id, count, product_name, price, image) VALUES(" + product_id + ", " + 1 + ", '" + product_name + "', " + price + ", " + image +" );");
        }

        Toast.makeText(getApplicationContext(), product_name + " has been added to cart.",Toast.LENGTH_SHORT).show();
    }

    public void to_preview_mode(View v){
        Intent i = new Intent(this, Activity_Preview_Mode.class);
        i.putExtra("product_model",product_model);
        startActivity(i);
    }

    public void view_comment(View v){
        final String key_username = getIntent().getStringExtra("username");

        Intent i = new Intent(this, Activity_CommentSection.class);
        i.putExtra("username",key_username);
        i.putExtra("product_id",product_id);
        startActivity(i);
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