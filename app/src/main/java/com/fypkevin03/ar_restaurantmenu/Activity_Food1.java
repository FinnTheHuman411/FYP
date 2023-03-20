package com.fypkevin03.ar_restaurantmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_Food1 extends AppCompatActivity {

    int product_id = 1001;
    String product_name = "Pepperoni Pizza";
    String product_model = "1001.glb";
    int price = 75;
    int image = R.drawable.fd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        RatingBar bar = (RatingBar)findViewById(R.id.bar);
        bar.setRating(4);
    }

    public void btn_add(View v){
        add_to_shopping_cart(product_id, 1, product_name, price, image);
    }

    public void add_to_shopping_cart(int product_id, int count, String product_name, int price, int image){
        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);

        cart.execSQL("INSERT INTO cart (product_id, count, product_name, price, image) VALUES(" + product_id + ", " + count + ", '" + product_name + "', " + price + ", " + image +" );");

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