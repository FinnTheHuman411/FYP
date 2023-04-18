package com.fypkevin03.ar_restaurantmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_Admin extends AppCompatActivity {

    Button b1;
    Button b2;
    Button b3;
    Button b4;

    // button 1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        b1 = (Button) findViewById(R.id.btn_create_ac);
        b2 = (Button) findViewById(R.id.btn_create_ac);
        b3 = (Button) findViewById(R.id.btn_create_ac);
        b4 = (Button) findViewById(R.id.btn_create_ac);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_Create_AC();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_Edit_AC();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_Create_Menu();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_Edit_Menu();
            }
        });
    }

        public void openActivity_Create_AC() {
            Intent intent = new Intent(this, Activity_Admin_AC_Add.class);
            startActivity(intent);
        }

        public void openActivity_Edit_AC() {
            Intent intent = new Intent(this, Activity_Admin_AC_Add.class);
            startActivity(intent);
        }

        public void openActivity_Create_Menu() {
            Intent intent = new Intent(this, Activity_Admin_AC_Add.class);
            startActivity(intent);
        }

        public void openActivity_Edit_Menu() {
            Intent intent = new Intent(this, Activity_Admin_AC_Add.class);
            startActivity(intent);
        }
}
