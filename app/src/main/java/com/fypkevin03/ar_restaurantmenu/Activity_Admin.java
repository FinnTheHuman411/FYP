package com.fypkevin03.ar_restaurantmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

public class Activity_Admin extends AppCompatActivity {

    Button b1,b2,b3,b4,b5;
    int currentUser;

    // button 1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        final int key_userId = getIntent().getIntExtra("currentUser",0);
        currentUser = key_userId;
    }

    public void openActivity_Create_AC(View v) {
        Intent intent = new Intent(this, Activity_Signup.class);
        intent.putExtra("mode","admin");
        startActivity(intent);
    }
    public void openActivity_Create_Admin(View v) {
        Intent intent = new Intent(this, Activity_Admin_AC_Add.class);
        startActivity(intent);
    }

    public void openActivity_Edit_AC(View v) {
        Intent intent = new Intent(this, Activity_Admin_AC_Viewer.class);
        intent.putExtra("currentUser",currentUser);
        startActivity(intent);
    }

    public void openActivity_Create_Menu(View v) {
        Intent intent = new Intent(this, Activity_Admin_Menu_Add.class);
        startActivity(intent);
    }

    public void openActivity_Edit_Menu(View v) {
        Intent intent = new Intent(this, Activity_Admin_Menu_Viewer.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.adminpagemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.logout){
            Intent i = new Intent(Activity_Admin.this, Activity_Login.class);
            startActivity(i);
            finish();
            return true;
        } else if (id == R.id.about_app){
            Intent i = new Intent(Activity_Admin.this, Activity_Aboutus.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
