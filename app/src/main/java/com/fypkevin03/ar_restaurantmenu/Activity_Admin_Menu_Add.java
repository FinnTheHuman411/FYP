package com.fypkevin03.ar_restaurantmenu;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_Admin_Menu_Add extends AppCompatActivity {

    DatabaseHelper_Foods db;
    EditText e1, e2, e3, e4, e5, e6, e7, e8;
    Button b1;
    TextView t1;
    Double s2d,s7d,s8d;
    String s1, s2, s3, s4, s5, s6, s7, s8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new DatabaseHelper_Foods(this);
        e1 = (EditText) findViewById(R.id.FoodName);
        e2 = (EditText) findViewById(R.id.FoodPrice);
        e3 = (EditText) findViewById(R.id.FoodType);
        e4 = (EditText) findViewById(R.id.FoodInfo);
        e5 = (EditText) findViewById(R.id.FoodImage);
        e6 = (EditText) findViewById(R.id.FoodModel);
        e7 = (EditText) findViewById(R.id.S_ratio);
        e8 = (EditText) findViewById(R.id.L_ratio);
        b1 = (Button) findViewById(R.id.register);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                s1 = e1.getText().toString();
                s2 = e2.getText().toString();
                s3 = e3.getText().toString();
                s4 = e4.getText().toString();
                s5 = e5.getText().toString();
                s6 = e6.getText().toString();
                s7 = e7.getText().toString();
                s8 = e8.getText().toString();

                s2d = Double.parseDouble(s2);
                s7d = Double.parseDouble(s7);
                s8d = Double.parseDouble(s8);

                if (s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("") || s5.equals("") || s6.equals("") || s7.equals("") || s8.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    new FoodAdd().execute();
                }
            }
        });

    }

    class FoodAdd extends AsyncTask<String, Void, String > {
        ProgressDialog progressDialog;
        Boolean insert;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Activity_Admin_Menu_Add.this,
                    "Creating a new food",
                    "Loading, please wait...");
        }

        @Override
        protected String doInBackground(String... strings) {
            insert = db.insert(null, s1, s3, s4, s2d, s5, s6, s7d, s8d);
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


