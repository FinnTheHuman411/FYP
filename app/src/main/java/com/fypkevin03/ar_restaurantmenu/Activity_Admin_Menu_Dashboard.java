package com.fypkevin03.ar_restaurantmenu;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Activity_Admin_Menu_Dashboard extends AppCompatActivity {

    DatabaseHelper_Foods db;
    int product_id;
    double product_size_s, product_size_l;
    String price, product_name, product_model, food_image, product_type, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_dashboard);

        db = new DatabaseHelper_Foods(this);

        final int food_id = getIntent().getIntExtra("product_id",0);   // Pass from Food Page
        product_id = food_id;
        Cursor resultSet = db.getInfo(food_id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ImageView image = findViewById(R.id.foodImage);
        TextView foodName = findViewById(R.id.foodName);
        TextView basicInfo = findViewById(R.id.foodInfo);

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
            foodName.setText(product_name + " (#" + Integer.toString(product_id) + ")");
            basicInfo.setText("Price: $" + price + "\n\n" + "Type: " + product_type + "\n\n" + "Info: " + description
                    + "\n\n" + "Scale: S-" + product_size_s + " M-1 L-" + product_size_l + "\n\n" + "Image Link: " +
                    food_image + "\n\n" + "Model Name: " + product_model);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void openActivity_update_FD(View v) {
        Intent intent = new Intent(this, Activity_Admin_Menu_Update.class);
        intent.putExtra("product_id",Integer.toString(product_id));
        intent.putExtra("product_name",product_name);
        intent.putExtra("product_type",product_type);
        intent.putExtra("description",description);
        intent.putExtra("price",price);
        intent.putExtra("food_image",food_image);
        intent.putExtra("product_model",product_model);
        intent.putExtra("product_size_s",Double.toString(product_size_s));
        intent.putExtra("product_size_l",Double.toString(product_size_l));
        startActivity(intent);
        finish();
    }

    public void delete_FD(View v) {

        Boolean remove = db.remove(product_id);
        if (remove == true) {
            Toast.makeText(getApplicationContext(), "Removed Successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
        }

    }
}