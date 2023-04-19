package com.fypkevin03.ar_restaurantmenu;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_Admin_Menu_Update extends AppCompatActivity {

    DatabaseHelper_Foods db;
    EditText e1, e2, e3, e4, e5, e6, e7, e8;
    Button b1;
    TextView t1;
    Double s2d,s7d,s8d;
    String s1, s2, s3, s4, s5, s6, s7, s8;
    int product_id_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_update);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new DatabaseHelper_Foods(this);
        t1 = (TextView) findViewById(R.id.updateFoodtitle);
        e1 = (EditText) findViewById(R.id.FoodName);
        e2 = (EditText) findViewById(R.id.FoodPrice);
        e3 = (EditText) findViewById(R.id.FoodType);
        e4 = (EditText) findViewById(R.id.FoodInfo);
        e5 = (EditText) findViewById(R.id.FoodImage);
        e6 = (EditText) findViewById(R.id.FoodModel);
        e7 = (EditText) findViewById(R.id.S_ratio);
        e8 = (EditText) findViewById(R.id.L_ratio);
        b1 = (Button) findViewById(R.id.register);

        final String product_id = getIntent().getStringExtra("product_id");
        final String product_name = getIntent().getStringExtra("product_name");
        final String product_type = getIntent().getStringExtra("product_type");
        final String description = getIntent().getStringExtra("description");
        final String price = getIntent().getStringExtra("price");
        final String food_image = getIntent().getStringExtra("food_image");
        final String product_model = getIntent().getStringExtra("product_model");
        final String product_size_s = getIntent().getStringExtra("product_size_s");
        final String product_size_l = getIntent().getStringExtra("product_size_l");
        product_id_int = Integer.parseInt(product_id);
        s1 = product_name;
        s2 = price;
        s3 = product_type;
        s4 = description;
        s5 = food_image;
        s6 = product_model;
        s7 = product_size_s;
        s8 = product_size_l;

        t1.setText("Update Food #" + product_id);
        e1.setHint("Name: " + product_name);
        e2.setHint("Price: " + price);
        e3.setHint("Type: " + product_type);
        e4.setHint("Info: " + description);
        e5.setHint("Image: " + food_image);
        e6.setHint("Model: " + product_model);
        e7.setHint("Size (S): " + product_size_s);
        e8.setHint("Size (L): " + product_size_l);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (!e1.getText().toString().equals("")) {
                    s1 = e1.getText().toString();
                }
                if (!e2.getText().toString().equals("")) {
                    s2 = e2.getText().toString();
                }
                if (!e3.getText().toString().equals("")) {
                    s3 = e3.getText().toString();
                }
                if (!e4.getText().toString().equals("")) {
                    s4 = e4.getText().toString();
                }
                if (!e5.getText().toString().equals("")) {
                    s5 = e5.getText().toString();
                }
                if (!e6.getText().toString().equals("")) {
                    s5 = e6.getText().toString();
                }
                if (!e7.getText().toString().equals("")) {
                    s5 = e7.getText().toString();
                }
                if (!e8.getText().toString().equals("")) {
                    s8 = e8.getText().toString();
                }

                s2d = Double.parseDouble(s2);
                s7d = Double.parseDouble(s7);
                s8d = Double.parseDouble(s8);

                new FoodAdd().execute();
            }
        });

    }

    class FoodAdd extends AsyncTask<String, Void, String > {
        ProgressDialog progressDialog;
        Boolean insert;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Activity_Admin_Menu_Update.this,
                    "Creating a new food",
                    "Loading, please wait...");
        }

        @Override
        protected String doInBackground(String... strings) {
            insert = db.update(product_id_int, s1, s3, s4, s2d, s5, s6, s7d, s8d);
            try {
                Thread.sleep(2500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (insert ==true) {
                Toast.makeText(getApplicationContext(), "Food created Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


