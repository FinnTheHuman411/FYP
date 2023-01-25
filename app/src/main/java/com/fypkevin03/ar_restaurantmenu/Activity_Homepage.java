package com.fypkevin03.ar_restaurantmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Activity_Homepage extends AppCompatActivity {

    GridView gridView;

    String FoodName[] = new String[]{
            "Gyudon (Japanese Beef Bowl)",
            "Bacon and Parmesan Risotto",
            "Nikujaga (Beef and Potatoes)",
            "Banana Cake",
            "Peppermint Mocha",
            "Coke",
    };

    String FoodType[] = new String[]{
            "Rice",
            "Rice",
            "Miscellaneous",
            "Dessert",
            "Beverage",
            "Beverage",
    };
    String FoodPrice[] = new String[]{
            "$50",
            "$75",
            "$45",
            "$26",
            "$12",
            "$7",
    };
    int FoodImages[] = new int[]{
            R.drawable.fd1,
            R.drawable.fd2,
            R.drawable.ms1,
            R.drawable.ds1,
            R.drawable.bv1,
            R.drawable.bv2,
    };

    List<FoodModel> listItems = new ArrayList<>();
    HomePageAdapter homePageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        if(getIntent().getExtras() != null){
            showInfo();
        }

        gridView = findViewById(R.id.gridview);

        for (int i = 0; i < FoodName.length; i++){
            FoodModel movieModel = new FoodModel(FoodImages[i], FoodName[i], FoodPrice[i], FoodType[i]);
            listItems.add(movieModel);
        }

        homePageAdapter = new HomePageAdapter(listItems,this);
        gridView.setAdapter(homePageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = listItems.indexOf(homePageAdapter.getItem(position));
                final String key_username = getIntent().getStringExtra("username");   //Pass from LoginActivity
                Intent intent;
                if (pos == 0) {
                    intent = new Intent(view.getContext(), Activity_Food1.class);
                    intent.putExtra("username",key_username);
                    startActivity(intent);
                } else if (pos == 1) {
                    intent = new Intent(view.getContext(), Activity_Food2.class);
                    intent.putExtra("username",key_username);
                    startActivity(intent);
                } else if (pos == 2) {
                    intent = new Intent(view.getContext(), Activity_Misc1.class);
                    intent.putExtra("username",key_username);
                    startActivity(intent);
                } else if (pos == 3) {
                    intent = new Intent(view.getContext(), Activity_Dessert1.class);
                    intent.putExtra("username",key_username);
                    startActivity(intent);
                } else if (pos == 4) {
                    intent = new Intent(view.getContext(), Activity_Beve_Soup1.class);
                    intent.putExtra("username",key_username);
                    startActivity(intent);
                } else if (pos == 5) {
                    intent = new Intent(view.getContext(), Activity_Beve_Soup2.class);
                    intent.putExtra("username",key_username);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.homepagemenu, menu);
        MenuItem searchBar = menu.findItem(R.id.search_bar);

        SearchView searchView = (SearchView) searchBar.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                homePageAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.search_bar){
            return true;
        } else if (id == R.id.logout){
            Intent i = new Intent(Activity_Homepage.this, Activity_Login.class);
            startActivity(i);
            finish();
            return true;
        } else if (id == R.id.user_info){
            final String key_username = getIntent().getStringExtra("username");   //Pass from LoginActivity
            Intent i = new Intent(Activity_Homepage.this, Activity_UserInfo.class);
            i.putExtra("username",key_username);
            startActivity(i);
            return true;
        } else if (id == R.id.about_app){
            Intent i = new Intent(Activity_Homepage.this, Activity_Aboutus.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class HomePageAdapter extends BaseAdapter implements Filterable {

        private List<FoodModel> foodModelList;
        private List<FoodModel> foodModelListFiltered;
        private Context context;

        public HomePageAdapter(List<FoodModel> movieModelList, Context context){
            this.foodModelList = movieModelList;
            this.foodModelListFiltered = movieModelList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return foodModelListFiltered.size();
        }

        @Override
        public Object getItem(int i) {
            return foodModelListFiltered.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View conview, ViewGroup viewGroup) {
            View view = getLayoutInflater().inflate(R.layout.food_preview_grid, null);

            ImageView imageView = view.findViewById(R.id.icon);
            TextView movieName = view.findViewById(R.id.FoodName);
            TextView moviePrice = view.findViewById(R.id.FoodPrice);
            TextView movieGenre = view.findViewById(R.id.FoodType);

            imageView.setImageResource(foodModelListFiltered.get(i).getImage());
            movieName.setText(foodModelListFiltered.get(i).getFoodName());
            moviePrice.setText(foodModelListFiltered.get(i).getFoodPrice());
            movieGenre.setText(foodModelListFiltered.get(i).getFoodType());

            return view;
        }


        @Override
        public Filter getFilter(){
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults filterResults = new FilterResults();
                    if(charSequence == null || charSequence.length() == 0){
                        filterResults.count = foodModelList.size();
                        filterResults.values = foodModelList;
                    } else {
                        String searchStr = charSequence.toString().toLowerCase();
                        List<FoodModel> resultData = new ArrayList<>();
                        for(FoodModel foodModel:foodModelList){
                            if(foodModel.getFoodName().toLowerCase().contains(searchStr)||foodModel.getFoodType().toLowerCase().contains(searchStr)){
                                resultData.add(foodModel);
                            }
                            filterResults.count = resultData.size();
                            filterResults.values = resultData;
                        }
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    foodModelListFiltered = (List<FoodModel>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
    }

    public void showInfo(){
        DatabaseHelper db = new DatabaseHelper(this);
        final String key_username = getIntent().getStringExtra("username");   //Pass from LoginActivity
        Cursor res = db.getInfo(key_username);
        if (res.getCount() == 0){
            Toast.makeText(Activity_Homepage.this, "NO INFO", Toast.LENGTH_SHORT).show();
            return ;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("ID: " + res.getString(0) + "\n");
            buffer.append("Name: " + res.getString(1) + "\n");
            buffer.append("Email: " + res.getString(2) + "\n");
            buffer.append("Password: " + res.getString(3) + "\n");
            buffer.append("Age: " + res.getString(4) + "\n");
            buffer.append("Credit: " + res.getString(5) + "\n");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Homepage.this);
        builder.setCancelable(true);
        builder.setTitle("User Entries");
        builder.setMessage(buffer.toString());
        builder.show();
    }

    public void goToShoppingCart(View v){
        Intent i = new Intent(this, Activity_ShoppingCart.class);
        startActivity(i);
    }
}