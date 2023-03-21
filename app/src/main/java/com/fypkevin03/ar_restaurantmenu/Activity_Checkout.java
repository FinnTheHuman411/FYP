package com.fypkevin03.ar_restaurantmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Activity_Checkout extends AppCompatActivity {


    public int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        Cursor resultSet = cart.rawQuery("Select * from cart",null);
        resultSet.moveToFirst();
        if (resultSet.getCount() != 0){
            do{
                totalPrice += resultSet.getInt(4);
            } while (resultSet.moveToNext());
        }

        TextView tv_totalPrice = (TextView) findViewById(R.id.totalPrice);
        tv_totalPrice.setText("Total Price: $" + totalPrice);
    }

    public void btn_checkout(View v){
        new CheckoutTask().execute();
    }

    class CheckoutTask extends AsyncTask<String, Void, String > {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(Activity_Checkout.this,
                    "Checkout in Process",
                    "Don't close the app!");
        }

        @Override
        protected String doInBackground(String... strings){
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s){
            Intent i = new Intent(Activity_Checkout.this, Activity_Summary.class);
            i.putExtra("totalPrice", totalPrice);
            startActivity(i);
            finish();
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