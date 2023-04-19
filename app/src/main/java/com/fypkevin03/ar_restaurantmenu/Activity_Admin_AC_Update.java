package com.fypkevin03.ar_restaurantmenu;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Activity_Admin_AC_Update extends AppCompatActivity {

    DatabaseHelper_Account db;
    EditText e1, e2, e3, e4, e5;
    Button b1;
    TextView t1;
    String s1, s2, s3, s4, s5, userId;
    Boolean chkusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ac_update);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new DatabaseHelper_Account(this);
        t1 = (TextView)findViewById(R.id.signuptitle);
        e1 = (EditText)findViewById(R.id.username);
        e2 = (EditText)findViewById(R.id.email);
        e3 = (EditText)findViewById(R.id.age);
        e4 = (EditText)findViewById(R.id.pass);
        e5 = (EditText)findViewById(R.id.cpass);
        b1 = (Button) findViewById(R.id.register);

        final String appMode = getIntent().getStringExtra("mode");
        final String key_userID = getIntent().getStringExtra("userID");
        final String key_username = getIntent().getStringExtra("username");
        final String key_email = getIntent().getStringExtra("email");
        final String key_age = getIntent().getStringExtra("age");
        final String key_password = getIntent().getStringExtra("password");
        userId = key_userID;
        s1 = key_username;
        s2 = key_email;
        s3 = key_age;
        s4 = key_password;
        s5 = key_password;

        t1.setText("Update Account #" + key_userID);
        e1.setHint("Username: " + key_username);
        e4.setHint("Password: " + key_password);
        if (appMode.equals("admin")) {
            e2.setVisibility(View.GONE);
            e3.setVisibility(View.GONE);
        } else {
            e2.setHint("Email: " + key_email);
            e3.setHint("Age: " + key_age);
        }

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

                chkusername = db.chkusername(s1);

                String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(s2);

                if(s4.equals(s5)){
                    if(!e1.getText().toString().equals("") && chkusername == false){
                        Toast.makeText(getApplicationContext(), "User Name Already exists", Toast.LENGTH_SHORT).show();
                    }
                    else if (!appMode.equals("admin") && matcher.matches() == false){
                        Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
                    } else {
                        new UpdateAccTask().execute();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class UpdateAccTask extends AsyncTask<String, Void, String > {
        ProgressDialog progressDialog;
        Boolean insert;

        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(Activity_Admin_AC_Update.this,
                    "Updating account",
                    "Loading, please wait...");
        }

        @Override
        protected String doInBackground(String... strings){
            insert = db.update(Integer.parseInt(userId), s1, s2, s3, s4);
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s){
            progressDialog.dismiss();
            if (insert ==true){
                Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
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
}