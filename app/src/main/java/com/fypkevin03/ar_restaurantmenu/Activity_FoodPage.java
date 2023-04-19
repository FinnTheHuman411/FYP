package com.fypkevin03.ar_restaurantmenu;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Activity_FoodPage extends AppCompatActivity {

    DatabaseHelper_Foods db;
    int product_id;
    double product_size_s, product_size_l;
    double modelScale = 1;
    String price, product_name, product_model, food_image, product_type, description;
    private String foodSize = "M";
    EditText noteEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodpage);

        db = new DatabaseHelper_Foods(this);

        final int food_id = getIntent().getIntExtra("product_id",0);   // Pass from Food Page
        final String key_username = getIntent().getStringExtra("username");
        product_id = food_id;
        Cursor resultSet = db.getInfo(product_id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ImageView image = findViewById(R.id.foodImage);
        TextView foodName = findViewById(R.id.foodName);
        TextView basicInfo = findViewById(R.id.foodInfo);
        RadioGroup radioGroup = findViewById(R.id.sizeSelect);
        noteEdit = (EditText)findViewById(R.id.editTextNote);

        if (product_id != 0) {
            resultSet.moveToFirst();
            food_image = resultSet.getString(6);
            price = resultSet.getString(5);
            product_name = resultSet.getString(2);
            product_type = resultSet.getString(3);
            description = resultSet.getString(4);
            product_model = resultSet.getString(7);
            product_size_s = resultSet.getDouble(8);
            product_size_l = resultSet.getDouble(9);

            Glide.with(this).load(food_image).into(image);
            foodName.setText(product_name);
            basicInfo.setText("Price: $" + price + "\n" + "Type: " + product_type + "\n" + "Info: " + description);
        }


        SQLiteDatabase ratings = openOrCreateDatabase("ratings",MODE_PRIVATE,null);
        Cursor ratingCursor = ratings.rawQuery("Select * from ratings WHERE product_id = ?",new String[] {Integer.toString(product_id)});
        Double rating = 0.0;
        int totalRated = 0;
        ratingCursor.moveToFirst();

        if (ratingCursor.getCount() != 0){
            do{
                rating = rating + ratingCursor.getDouble(3);
                totalRated++;
            } while (ratingCursor.moveToNext());
            rating = rating / totalRated;
        }

        RatingBar ratingStars = (RatingBar)findViewById(R.id.ratingBar);
        ratingStars.setRating(rating.floatValue());

        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (getIntent().getStringExtra("username") != null){
                    double ratingValue = (double)v;
                    Cursor cursor = ratings.rawQuery("Select * from ratings WHERE username = ? AND product_id = ?",new String[] {key_username, Integer.toString(product_id)});

                    if (cursor.getCount()>0) {
                        cursor.moveToFirst();
                        ContentValues cv = new ContentValues();
                        cv.put("rating", ratingValue);
                        ratings.update("ratings", cv, "username = ? AND product_id = ?", new String [] {key_username, Integer.toString(product_id)});
                    } else {
                        ratings.execSQL("INSERT INTO ratings (product_id, username, rating) VALUES(" + product_id + ", '" + key_username + "', " + ratingValue + ");");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "You can only rate after logging in!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if (checkedId == R.id.radioButton) {
                    modelScale = (float)product_size_s;
                    foodSize = "S";
                } else if (checkedId == R.id.radioButton2) {
                    modelScale = 1f;
                    foodSize = "M";
                } else if (checkedId == R.id.radioButton3) {
                    modelScale = (float)product_size_l;
                    foodSize = "L";
                }
            }
        });
    }

    public void btn_add(View v){
        String note = noteEdit.getText().toString();
        String product_name_size = product_name;
        Double modelScaleDouble = (double)modelScale;

        if (foodSize == "S") {
            product_name_size = product_name + " Small";
        } else if (foodSize == "M") {
            product_name_size = product_name + " Medium";
        } else if (foodSize == "L") {
            product_name_size = product_name + " Large";
        }

        add_to_shopping_cart(product_id, product_name_size, Double.parseDouble(price), food_image, modelScaleDouble, note);
    }

    public void add_to_shopping_cart(int product_id, String product_name, double price, String image, double scale, String note){
        String productid_size = product_id + foodSize;

        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        //Cursor cursor = cart.rawQuery("Select * from cart where product_id_size=?", new String [] {productid_size});

        /*if (cursor.getCount()>0) {
            cursor.moveToFirst();
            ContentValues cv = new ContentValues();
            cv.put("count", cursor.getInt(4) + 1);
            cart.update("cart", cv, "product_id_size = ?", new String [] {productid_size});
        } else {*/
        cart.execSQL("INSERT INTO cart (product_id, product_id_size, count, product_name, product_model, price, image, scale, note) VALUES(" + product_id + ", '" + productid_size + "', " + 1 + ", '" + product_name + "', '"+ product_model +"', " + price + ", '" + image + "' , "+ scale +" , '" + note + "');");

        //}

        Toast.makeText(getApplicationContext(), product_name + " has been added to cart.",Toast.LENGTH_SHORT).show();
    }

    public void to_preview_mode(View v){
        Intent i = new Intent(this, Activity_Preview_Mode.class);
        i.putExtra("product_id",product_id);
        i.putExtra("product_name",product_name);
        i.putExtra("price",price);
        i.putExtra("food_image",food_image);
        i.putExtra("product_model",product_model);
        i.putExtra("product_size_s",product_size_s);
        i.putExtra("product_size_l",product_size_l);
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