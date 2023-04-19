package com.fypkevin03.ar_restaurantmenu;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_Admin_AC_Viewer extends AppCompatActivity {

    ListView listView;
    DatabaseHelper_Account db;
    int currentUser;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_admin_ac_viewer);

        final int key_userId = getIntent().getIntExtra("currentUser",0);
        currentUser = key_userId;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new DatabaseHelper_Account(this);

        List<HashMap<String , String>> displayList = new ArrayList<>();
        ArrayList<String> username = new ArrayList<String>();
        ArrayList<String> role = new ArrayList<String>();
        Cursor resultSet = db.getAllUsers();
        resultSet.moveToFirst();
        listView = findViewById(R.id.acListview);

        if (resultSet.getCount() != 0){
            do{
                // Add data
                username.add(resultSet.getString(1));
                role.add(resultSet.getString(6));

                // Create hashmap for simple list item 2
                HashMap<String , String> hashMap = new HashMap<>();
                hashMap.put("title" , resultSet.getString(1));
                hashMap.put("text" , resultSet.getString(6));
                displayList.add(hashMap);

            } while (resultSet.moveToNext());
        }

        ListAdapter listAdapter = new SimpleAdapter(
                this,
                displayList,
                android.R.layout.simple_list_item_2 ,
                new String[]{"title" , "text"} ,
                new int[]{android.R.id.text1 , android.R.id.text2});

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Activity_Admin_AC_Viewer.this, Activity_Admin_AC_Dashboard.class);
                i.putExtra("currentUser",currentUser);
                i.putExtra("username",username.get(position));
                startActivity(i);
                finish();
            }
        });


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