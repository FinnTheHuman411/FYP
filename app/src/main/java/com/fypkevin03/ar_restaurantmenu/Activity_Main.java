package com.fypkevin03.ar_restaurantmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

public class Activity_Main extends AppCompatActivity {

    DatabaseHelper_Account usdb;
    DatabaseHelper_Foods fdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usdb = new DatabaseHelper_Account(this);
        fdb = new DatabaseHelper_Foods(this);

        if (usdb.chkusername("admin") == true){
            usdb.insert("admin", "admin@example.com", "20", "admin", "admin");
            usdb.insert("testuser", "testuser@example.com", "20", "testuser", "user");
            fdb.insert(3001, "Biscuit St Esteve", "Dessert", "Biscuit St Estève, ganache & gel abricot, chocolat blanc & sorbet abricot.", 30.0, "https://i.imgur.com/04pV3fn.jpg", "biscuit_st_esteve_from_grands_boulevards_hotel.glb", 0.75, 1.25);
            fdb.insert(4001, "Cherry Soda Float", "Drink", "Lemon lime soda with ice cream and cherry syrup.", 22.0, "https://i.imgur.com/IW5QpnZ.jpg", "cherry_soda_float.glb", 0.75, 1.25);
            fdb.insert(4002, "Green Soda", "Drink", "Green soft drink with green apple taste.", 9.0, "https://i.imgur.com/t8QmRUx.jpg", "fizzy_drink.glb", 0.75, 1.25);
            fdb.insert(2001, "Goat cheese tartine from La Mandragore", "Main", "Toast with goat-milk made cheese with salad.", 72.0, "https://i.imgur.com/tHs4815.jpg", "goat_cheese_tartine_from_la_mandragore.glb", 0.75, 1.25);
            fdb.insert(2002, "'Cheez'Burger", "Main", "Hamburger with lettuce, tomato, beef and cheese.", 45.0, "https://i.imgur.com/SfpynDb.jpg", "home_made_burger_by_t__q.glb", 0.75, 1.25);
            fdb.insert(2003, "Roast Beef Sandwich", "Main", "Sandwich with roast beef, cornichons, chevre, salad, mayonnaise on a baguette tradition.", 63.0, "https://i.imgur.com/wm1lmy5.jpg", "home-made_sandwich.glb", 0.75, 1.25);
            fdb.insert(3002, "Miage Pie", "Dessert", "Raspberry Pie with cream.", 29.0, "https://i.imgur.com/3XleQRc.jpg", "day_229_miage_pie.glb", 0.75, 1.25);
            fdb.insert(2004, "Burrata Pizza", "Main", "Homemade pizza with arugula, tomato sauce and creamy burrata cheese.", 56.0, "https://i.imgur.com/f43Fdv2.jpg", "pizza_ballerina.glb", 0.75, 1.5);
            fdb.insert(2005, "Prime Rib with Pommes Dauphines", "Main", "Prime rib (beef) with Pommes dauphine which is crisp potato puffs made by mixing mashed potatoes with savoury choux pastry.", 106.0, "https://i.imgur.com/ulR9yEa.jpg", "prime_rib_with_pommes_dauphines.glb", 0.75, 1.25);
            fdb.insert(2006, "Barbecued Rib", "Main", "Rib (beef) with barbecue sauce and pea sprout.", 99.0, "https://i.imgur.com/kwtbAwm.jpg", "ribs_from_joia.glb", 0.75, 1.25);
            fdb.insert(1001, "Salad", "Appetizer", "Biscuit St Estève, ganache & gel abricot, chocolat blanc & sorbet abricot.", 32.0, "https://i.imgur.com/30DpBqe.jpg", "salad_plate.glb", 0.75, 1.25);
            fdb.insert(4003, "Viennese Coffee with Chantilly", "Dessert", "Espresso topped with abundant whipped cream and sprinkled with cocoa powder.", 16.0, "https://i.imgur.com/bKp9dgg.jpg", "vienna_coffee_with_chantilly.glb", 0.75, 1.25);

        }


        SQLiteDatabase cart = openOrCreateDatabase("cart",MODE_PRIVATE,null);
        cart.execSQL("DROP TABLE IF EXISTS cart;");
        cart.execSQL("CREATE TABLE IF NOT EXISTS cart(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "product_id INTEGER NOT NULL, " +
                "product_id_size VARCHAR NOT NULL, " +
                "product_name VARCHAR NOT NULL, " +
                "product_model VARCHAR, " +
                "count INTEGER NOT NULL," +
                "price INTEGER NOT NULL," +
                "image VARCHAR NOT NULL," +
                "scale DOUBLE NOT NULL," +
                "note VARCHAR" +
                ");");
        SQLiteDatabase comments = openOrCreateDatabase("comments",MODE_PRIVATE,null);
        //comments.execSQL("DROP TABLE IF EXISTS comments;");
        comments.execSQL("CREATE TABLE IF NOT EXISTS comments(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "product_id INTEGER NOT NULL, " +
                "username VARCHAR NOT NULL, " +
                "comment VARCHAR NOT NULL" +
                ");");
        SQLiteDatabase ratings = openOrCreateDatabase("ratings",MODE_PRIVATE,null);
        //comments.execSQL("DROP TABLE IF EXISTS comments;");
        ratings.execSQL("CREATE TABLE IF NOT EXISTS ratings(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "product_id INTEGER NOT NULL, " +
                "username VARCHAR NOT NULL, " +
                "rating DOUBLE NOT NULL" +
                ");");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Activity_Main.this, Activity_Login.class);
                startActivity(i);
                finish();
            }
        },2000);

    }
}