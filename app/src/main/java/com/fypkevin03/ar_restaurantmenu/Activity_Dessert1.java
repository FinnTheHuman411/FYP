package com.fypkevin03.ar_restaurantmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class Activity_Dessert1 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        RatingBar bar = (RatingBar)findViewById(R.id.bar);
        bar.setRating(4);
    }

    public void btn_add(View v){
        add_to_shopping_cart(3001, 1, "Banana Cake", 26, R.drawable.ds1);
    }

    public void add_to_shopping_cart(int product_id, int count, String product_name, int price, int image){
        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);

        cart.execSQL("INSERT INTO cart (product_id, count, product_name, price, image) VALUES(" + product_id + ", " + count + ", '" + product_name + "', " + price + ", " + image +" );");

        Toast.makeText(getApplicationContext(), product_name + " has been added to cart.",Toast.LENGTH_SHORT).show();
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