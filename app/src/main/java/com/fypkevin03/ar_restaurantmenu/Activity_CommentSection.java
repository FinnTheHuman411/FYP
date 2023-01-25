package com.fypkevin03.ar_restaurantmenu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Activity_CommentSection extends AppCompatActivity {

    EditText cm;
    String cmstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentwall);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        cm = (EditText)findViewById(R.id.comment_bar);
        LinearLayout comment_frame = (LinearLayout)findViewById(R.id.comment_bar_frame);

        if (getIntent().getStringExtra("username") == null){
            comment_frame.setVisibility(View.GONE);
        }

        final int product_id = getIntent().getIntExtra("product_id",0);   // Pass from Food Page
        ListView cmlv = (ListView) findViewById(R.id.cmListview);
        SQLiteDatabase comments = openOrCreateDatabase("comments",MODE_PRIVATE,null);
        ArrayList<CommentObject> commentObjects = new ArrayList<CommentObject>();
        CommentAdapter adapter = new CommentAdapter(this, commentObjects);

        cmlv.setAdapter(adapter);

        Cursor resultSet = comments.rawQuery("Select * from comments WHERE product_id = ?",new String[] {Integer.toString(product_id)});
        resultSet.moveToFirst();

        if (resultSet.getCount() != 0){
            do{
                commentObjects.add(new CommentObject(
                        resultSet.getInt(0),
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            } while (resultSet.moveToNext());
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

    public void comment_add(View v){
        final String key_username = getIntent().getStringExtra("username");   // Pass from LoginActivity and HomePageActivity
        final int product_id = getIntent().getIntExtra("product_id",0);   // Pass from Food Page
        cmstr = cm.getText().toString();
        if (cmstr != null) {
            add_to_comment_database(product_id, key_username, cmstr);
        }
    }

    public void add_to_comment_database(int product_id, String username, String comment){
        SQLiteDatabase comments = openOrCreateDatabase("comments",MODE_PRIVATE,null);
        comments.execSQL("INSERT INTO comments (product_id, username, comment) VALUES(" + product_id + ", '" + username + "', '" + comment + "');");
        Toast.makeText(getApplicationContext(), "Comment created.",Toast.LENGTH_SHORT).show();
        this.recreate();
    }
}
