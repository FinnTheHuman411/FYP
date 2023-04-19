package com.fypkevin03.ar_restaurantmenu;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_Admin_Menu_Viewer extends AppCompatActivity {

    ListView listView;
    DatabaseHelper_Foods db;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_admin_ac_viewer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new DatabaseHelper_Foods(this);

        List<HashMap<String , String>> displayList = new ArrayList<>();
        ArrayList<Integer> foods = new ArrayList<Integer>();
        Cursor resultSet = db.getAllFoods();
        resultSet.moveToFirst();
        listView = findViewById(R.id.acListview);

        if (resultSet.getCount() != 0){
            do{
                // Add data
                foods.add(resultSet.getInt(1));

                // Create hashmap for simple list item 2
                HashMap<String , String> hashMap = new HashMap<>();
                hashMap.put("title" , resultSet.getString(2));
                hashMap.put("text" , "$" + resultSet.getString(5) + ", " + resultSet.getString(3));
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
                Intent i = new Intent(Activity_Admin_Menu_Viewer.this, Activity_Admin_Menu_Dashboard.class);
                i.putExtra("product_id",foods.get(position));
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