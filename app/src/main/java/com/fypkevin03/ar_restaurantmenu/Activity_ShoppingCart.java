package com.fypkevin03.ar_restaurantmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Arrays.*;

public class Activity_ShoppingCart extends AppCompatActivity {

    DatabaseHelper_Foods db;
    public double totalPrice = 0;
    RecyclerView rv;
    Double[] listScore;
    String[] listGenre;
    int key_foodID;
    Adapter_Recommender recommenderAdapter;
    String[] FoodTypeList;
    Double[] FoodTypeListScore ;

    //20230418 Collaborative Filtering
    Map<String, Double> FoodIDScore;
    //<UserID, <FoodID, Rating> >
//    Map<Integer, HashMap<Integer, Double>> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //TextView tv = (TextView) findViewById(R.id.output);
        ListView lv = (ListView) findViewById(R.id.cartListview);
        Button btn_checkout = (Button) findViewById(R.id.btn_checkout);

        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        ArrayList<Object_Cart> cartObjects = new ArrayList<Object_Cart>();
        Adapter_Cart adapter = new Adapter_Cart(this, cartObjects);

        lv.setAdapter(adapter);

        Cursor resultSet = cart.rawQuery("Select * from cart",null);
        resultSet.moveToFirst();

        if (resultSet.getCount() != 0){
            do{
//                tv.setText(tv.getText() + "\n" +
//                        resultSet.getString(1) + " " +
//                        resultSet.getString(2) + " " +
//                        resultSet.getString(3) + " " +
//                        resultSet.getString(4)
//                        );

                cartObjects.add(new Object_Cart(
                        resultSet.getInt(0),
                        resultSet.getInt(1),
                        resultSet.getString(3),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getString(9)
                ));

                totalPrice += resultSet.getInt(5) * resultSet.getInt(6);
            } while (resultSet.moveToNext());
        }

        //20230418 rating
        SQLiteDatabase ratings = openOrCreateDatabase("ratings",MODE_PRIVATE,null);
        Bundle extras = getIntent().getExtras();
        SQLiteDatabase foodTypes = openOrCreateDatabase("foods",MODE_PRIVATE,null);
//        FoodTypeScore

        final String key_username = getIntent().getStringExtra("username");   //Pass from HomePageActivity

        if (key_username != null) {
            Cursor ratingCursor = ratings.rawQuery("Select genre, rating from ratings where username = ? group by genre", new String[] {key_username});
            //0  : _id,
            //1 : product_id
            //2 : username
            //3 : rating

            //GetFoodType
            db = new DatabaseHelper_Foods(this);


            ratingCursor.moveToFirst();
            if (ratingCursor.getCount() != 0) {
                listScore = new Double[ratingCursor.getCount()];
                listGenre = new String[ratingCursor.getCount()];

                int i = 0;
                do {
                    listScore[i] =  ratingCursor.getDouble(1);
                    listGenre[i] = ratingCursor.getString(0);
                    i++;

//                    listItems.add(new Object_Food(
//                            resultSetRecommender.getInt(1),
//                            resultSetRecommender.getString(6),
//                            resultSetRecommender.getString(2),
//                            "$" + resultSetRecommender.getString(5),
//                            resultSetRecommender.getString(3)

                } while (ratingCursor.moveToNext());
            } else {
                rollbackRecommender();
                return;
            }

            //FoodTypeCursor = <product_id, foodType>
            //
//            FoodTypeList = new String[db.getFoodTypeCount()];
//            FoodTypeListScore = new Integer[db.getFoodTypeCount()];

            FoodTypeListScore = new Double[db.getFoodTypeCount()];
            for(int i = 0; i < FoodTypeListScore.length; i++) {
                FoodTypeListScore[i] = 0.0;
            }

            Cursor FoodTypeCursor = ratings.rawQuery("Select rating, genre from ratings where username = ? group by genre", new String[] {key_username});
            FoodTypeList = db.getFoodTypeList();
            FoodTypeCursor.moveToFirst();
            if (FoodTypeCursor.getCount() != 0) {
                do {
                    for (int i = 0; i < FoodTypeList.length;i++){
                        if (FoodTypeList[i].equals(FoodTypeCursor.getString(1))) {
                            FoodTypeListScore[i] = FoodTypeListScore[i] + 3.0 + (FoodTypeCursor.getDouble(0) - 3.0);
                        } else {
                            FoodTypeListScore[i] = FoodTypeListScore[i] + 3.0;
                        }
                    }
                } while (FoodTypeCursor.moveToNext());
            }

            //Queue based on FoodTypeListScore[]
            int maxCounter;
            String[] SortedFoodType = new String[FoodTypeList.length];

            for (int k = 0; k < FoodTypeListScore.length; k++){
                maxCounter = 0;
                for (int i = 0; i < FoodTypeListScore.length; i++){
                    if (FoodTypeListScore[i] >= FoodTypeListScore[maxCounter]){
                        maxCounter = i;
                    }
                }
                SortedFoodType[k] = FoodTypeList[maxCounter];
                FoodTypeListScore[maxCounter] = -1.0;
            }

            List<Object_Food> listItems = new ArrayList<>();
            rv = (RecyclerView) findViewById(R.id.recommenderView);
            for(int i = 0; i < FoodTypeList.length; i++) {
                SQLiteDatabase foods = openOrCreateDatabase("Foods.db", MODE_PRIVATE, null);
                Cursor resultSetRecommender = foods.rawQuery("Select * from foods WHERE genre = ?", new String[] {SortedFoodType[i]});
                resultSetRecommender.moveToFirst();
                if (resultSetRecommender.getCount() != 0){
                    do{
                        listItems.add(new Object_Food(
                                resultSetRecommender.getInt(1),
                                resultSetRecommender.getString(6),
                                resultSetRecommender.getString(2),
                                "$" + resultSetRecommender.getString(5),
                                resultSetRecommender.getString(3)
                        ));
                    } while (resultSetRecommender.moveToNext());
                }
            }
            recommenderAdapter = new Adapter_Recommender(listItems);
            rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
            rv.setAdapter(recommenderAdapter);
        }
        else {
            rollbackRecommender();
        }
        btn_checkout.setText("Checkout: $" + totalPrice);
    }

    //20230417 recommender RecyclerView
    private void rollbackRecommender() {
        List<Object_Food> listItems = new ArrayList<>();
        rv = (RecyclerView) findViewById(R.id.recommenderView);
        SQLiteDatabase foods = openOrCreateDatabase("Foods.db", MODE_PRIVATE, null);
        Cursor resultSetRecommender = foods.rawQuery("Select * from foods", null);
        resultSetRecommender.moveToFirst();

        if (resultSetRecommender.getCount() != 0) {
            do {
                listItems.add(new Object_Food(
                        resultSetRecommender.getInt(1),
                        resultSetRecommender.getString(6),
                        resultSetRecommender.getString(2),
                        "$" + resultSetRecommender.getString(5),
                        resultSetRecommender.getString(3)
                ));
            } while (resultSetRecommender.moveToNext());
        }

        recommenderAdapter = new Adapter_Recommender(listItems);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(recommenderAdapter);
    }

    public void goToCheckout(View v){
        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        Cursor resultSet = cart.rawQuery("Select * from cart",null);
        resultSet.moveToFirst();
        if (resultSet.getCount() != 0){
            Intent i = new Intent(this, Activity_Checkout.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "You must add product before checkout.", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToPreview(View v){
        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        Cursor resultSet = cart.rawQuery("Select * from cart",null);
        resultSet.moveToFirst();
        if (resultSet.getCount() != 0){
            Intent i = new Intent(this, Activity_Preview_Cart.class);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "You must add product before checkout.", Toast.LENGTH_SHORT).show();
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
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double price) {
        totalPrice = price;
        Button btn_checkout = (Button) findViewById(R.id.btn_checkout);
        btn_checkout.setText("Checkout: $" + totalPrice);
    }

    class Adapter_Recommender extends RecyclerView.Adapter<Adapter_Recommender.MyHolder> {
        private List<Object_Food> foodModelList;
        private View view;

        public Adapter_Recommender(List<Object_Food> foodModelList){
            this.foodModelList = foodModelList;
        }

        public class CollaborativeFiltering {
            private String itemName;


        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            view = LayoutInflater.from(Activity_ShoppingCart.this).inflate(R.layout.food_preview_recommender,parent,false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            Glide.with(view).load(foodModelList.get(holder.getAdapterPosition()).getImage()).into(holder.icon);
//            holder.icon.setImage(foodModelList.get(pos).getImage());
            holder.foodName.setText(foodModelList.get(holder.getAdapterPosition()).getFoodName());
            holder.foodPrice.setText(foodModelList.get(holder.getAdapterPosition()).getFoodPrice());
            holder.foodType.setText(foodModelList.get(holder.getAdapterPosition()).getFoodType());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    key_foodID = foodModelList.get(holder.getAdapterPosition()).getFoodID();
                    final String key_username = getIntent().getStringExtra("username");   //Pass from LoginActivity
                    Intent intent;
                    // Access the row position here to get the correct data item
                    intent = new Intent(view.getContext(), Activity_FoodPage.class);
                    intent.putExtra("username",key_username);
                    intent.putExtra("product_id",key_foodID);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return foodModelList.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{
            ImageView icon;
            TextView foodName;
            TextView foodPrice;
            TextView foodType;

            public MyHolder(@NonNull View itemView){
                super(itemView);
                icon = itemView.findViewById(R.id.icon);
                foodName = itemView.findViewById(R.id.FoodName);
                foodPrice = itemView.findViewById(R.id.FoodPrice);
                foodType = itemView.findViewById(R.id.FoodType);
            }
        }
    }

}