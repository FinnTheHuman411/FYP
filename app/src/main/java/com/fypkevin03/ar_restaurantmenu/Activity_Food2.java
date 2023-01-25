package com.fypkevin03.ar_restaurantmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

public class Activity_Food2 extends AppCompatActivity {

    int product_id = 1002;
    String product_name = "Bacon and Parmesan Risotto";
    int price = 72;
    int image = R.drawable.fd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        RatingBar bar = (RatingBar)findViewById(R.id.bar);
        bar.setRating(5);
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