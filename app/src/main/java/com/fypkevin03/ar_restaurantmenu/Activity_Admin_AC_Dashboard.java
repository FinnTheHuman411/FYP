package com.fypkevin03.ar_restaurantmenu;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_Admin_AC_Dashboard extends AppCompatActivity {

    DatabaseHelper_Account db;
    String userIDString, userTypeString, usernameString, emailString, pwString, ageString;
    int currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ac_dashboard);

        final int key_userId = getIntent().getIntExtra("currentUser",0);
        currentUser = key_userId;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new DatabaseHelper_Account(this);
        final String key_username = getIntent().getStringExtra("username");

        if (key_username != null){
            Cursor res = db.getInfo(key_username);
            if (res.getCount() == 0){
                Toast.makeText(Activity_Admin_AC_Dashboard.this, "NO INFO", Toast.LENGTH_SHORT).show();
            } else {
                TableRow emailRow = findViewById(R.id.accEmail);
                TableRow ageRow = findViewById(R.id.accAge);
                TableRow creditRow = findViewById(R.id.accCredit);

                TextView userID = findViewById(R.id.tv_userinfo_id);
                TextView userName = findViewById(R.id.tv_userinfo_username);
                TextView userEmail = findViewById(R.id.tv_userinfo_em);
                TextView userPW = findViewById(R.id.tv_userinfo_pw);
                TextView userAge = findViewById(R.id.tv_userinfo_age);
                TextView userCredit = findViewById(R.id.tv_userinfo_credit);
                TextView userType = findViewById(R.id.tv_userinfo_role);

                while (res.moveToNext()){
                    userIDString = res.getString(0);
                    userTypeString = res.getString(6);

                    usernameString = res.getString(1);
                    emailString = res.getString(2);
                    pwString = res.getString(3);
                    ageString = res.getString(4);

                    userID.setText(res.getString(0));
                    userName.setText(res.getString(1));
                    userPW.setText(res.getString(3));
                    userType.setText(res.getString(6));

                    if (res.getString(6).equals("admin")) {
                        emailRow.setVisibility(View.GONE);
                        ageRow.setVisibility(View.GONE);
                        creditRow.setVisibility(View.GONE);
                    } else {
                        userEmail.setText(res.getString(2));
                        userAge.setText(res.getString(4));
                        userCredit.setText(res.getString(5));
                    }
                }
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


    public void openActivity_update_AC(View v) {
        Intent intent = new Intent(this, Activity_Admin_AC_Update.class);
        intent.putExtra("userID",userIDString);
        intent.putExtra("mode",userTypeString);
        intent.putExtra("username",usernameString);
        intent.putExtra("email",emailString);
        intent.putExtra("age",ageString);
        intent.putExtra("password",pwString);
        startActivity(intent);
        finish();
    }

    public void delete_AC(View v) {
        if (Integer.toString(currentUser).equals(userIDString)) {
            Toast.makeText(getApplicationContext(), "You can't delete yourself!", Toast.LENGTH_SHORT).show();
        } else {
            Boolean remove = db.remove(Integer.parseInt(userIDString));
            if (remove == true) {
                Toast.makeText(getApplicationContext(), "Removed Successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}