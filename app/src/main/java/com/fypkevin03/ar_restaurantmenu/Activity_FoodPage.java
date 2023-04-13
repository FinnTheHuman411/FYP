package com.fypkevin03.ar_restaurantmenu;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_FoodPage extends AppCompatActivity {

    int food_image, product_id;
    String price, product_name, product_model;
    private String foodSize = "M";

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
        RadioGroup radioGroup = findViewById(R.id.sizeSelect);

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


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if (checkedId == R.id.radioButton) {
                    foodSize = "S";
                } else if (checkedId == R.id.radioButton2) {
                    foodSize = "M";
                } else if (checkedId == R.id.radioButton3) {
                    foodSize = "L";
                }
            }
        });
    }

    public void btn_add(View v){
        String product_name_size = product_name;

        if (foodSize == "S") {
            product_name_size = product_name + " Small";
        } else if (foodSize == "M") {
            product_name_size = product_name + " Medium";
        } else if (foodSize == "L") {
            product_name_size = product_name + " Large";
        }

        add_to_shopping_cart(product_id, product_name_size, Double.parseDouble(price), food_image);
    }

    public void add_to_shopping_cart(int product_id, String product_name, double price, int image){
        String productid_size = product_id + foodSize;

        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        Cursor cursor = cart.rawQuery("Select * from cart where product_id_size=?", new String [] {productid_size});

        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            ContentValues cv = new ContentValues();
            cv.put("count", cursor.getInt(4) + 1);
            cart.update("cart", cv, "product_id_size = ?", new String [] {productid_size});
        } else {
            cart.execSQL("INSERT INTO cart (product_id, product_id_size, count, product_name, price, image) VALUES(" + product_id + ", '" + productid_size + "', " + 1 + ", '" + product_name + "', " + price + ", " + image +" );");

        }

        Toast.makeText(getApplicationContext(), product_name + " has been added to cart.",Toast.LENGTH_SHORT).show();
    }

    public void to_preview_mode(View v){
        Intent i = new Intent(this, Activity_Preview_Mode.class);
        i.putExtra("product_id",product_id);
        i.putExtra("product_name",product_name);
        i.putExtra("price",price);
        i.putExtra("food_image",food_image);
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