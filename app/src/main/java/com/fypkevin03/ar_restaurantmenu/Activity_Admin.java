package com.fypkevin03.ar_restaurantmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_Admin extends AppCompatActivity {

    Button b1,b2,b3,b4,b5;

    // button 1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
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

/*
        public void openActivity_Edit_AC(View v) {
            Intent intent = new Intent(this, Activity_Admin_AC_Add.class);
            startActivity(intent);
        }

        public void openActivity_Create_Menu(View v) {
            Intent intent = new Intent(this, Activity_Admin_AC_Add.class);
            startActivity(intent);
        }

        public void openActivity_Edit_Menu(View v) {
            Intent intent = new Intent(this, Activity_Admin_AC_Add.class);
            startActivity(intent);
        }
 */
}
