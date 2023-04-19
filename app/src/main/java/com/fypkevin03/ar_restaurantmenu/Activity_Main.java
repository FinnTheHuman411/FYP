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

        // Insert Sample data if admin account is not exist
        if (usdb.chkusername("admin") == true){
            // Insert Sample users
            usdb.insert("admin", "admin@example.com", "20", "admin", "admin");
            usdb.insert("testuser", "testuser@example.com", "20", "testuser", "user");

            // Insert Sample foods
            fdb.insert(3001, "Biscuit St Esteve", "Cake", "Biscuit St Estève, ganache & gel abricot, chocolat blanc & sorbet abricot.", 30.0, "https://i.imgur.com/04pV3fn.jpg", "https://github.com/FinnTheHuman411/hkmukevin03fypassets/blob/main/biscuit_st_esteve_from_grands_boulevards_hotel.glb?raw=true", 0.75, 1.25);
            fdb.insert(4001, "Cherry Soda Float", "Cold Drink", "Lemon lime soda with ice cream and cherry syrup.", 22.0, "https://i.imgur.com/IW5QpnZ.jpg", "https://github.com/FinnTheHuman411/hkmukevin03fypassets/blob/main/cherry_soda_float.glb?raw=true", 0.75, 1.25);
            fdb.insert(4002, "Green Soda", "Cold Drink", "Green soft drink with green apple taste.", 9.0, "https://i.imgur.com/t8QmRUx.jpg", "https://github.com/FinnTheHuman411/hkmukevin03fypassets/blob/main/fizzy_drink.glb?raw=true", 0.75, 1.25);
            fdb.insert(2001, "Goat cheese tartine from La Mandragore", "Bread", "Toast with goat-milk made cheese with salad.", 72.0, "https://i.imgur.com/tHs4815.jpg", "https://github.com/FinnTheHuman411/hkmukevin03fypassets/blob/main/goat_cheese_tartine_from_la_mandragore.glb?raw=true", 0.75, 1.25);
            fdb.insert(2002, "'Cheez'Burger", "Bread", "Hamburger with lettuce, tomato, beef and cheese.", 45.0, "https://i.imgur.com/SfpynDb.jpg", "https://github.com/FinnTheHuman411/hkmukevin03fypassets/blob/main/home_made_burger_by_t__q.glb?raw=true", 0.75, 1.25);
            fdb.insert(2003, "Roast Beef Sandwich", "Bread", "Sandwich with roast beef, cornichons, chevre, salad, mayonnaise on a baguette tradition.", 63.0, "https://i.imgur.com/wm1lmy5.jpg", "https://github.com/FinnTheHuman411/hkmukevin03fypassets/blob/main/home-made_sandwich.glb?raw=true", 0.75, 1.25);
            fdb.insert(3002, "Miage Pie", "Pie/Tart", "Raspberry Pie with cream.", 29.0, "https://i.imgur.com/3XleQRc.jpg", "https://github.com/FinnTheHuman411/hkmukevin03fypassets/blob/main/day_229_miage_pie.glb?raw=true", 0.75, 1.25);
            fdb.insert(2004, "Burrata Pizza", "Pizza", "Homemade pizza with arugula, tomato sauce and creamy burrata cheese.", 56.0, "https://i.imgur.com/f43Fdv2.jpg", "https://github.com/FinnTheHuman411/hkmukevin03fypassets/blob/main/pizza_ballerina.glb?raw=true", 0.75, 1.5);
            fdb.insert(2005, "Prime Rib with Pommes Dauphines", "Meat", "Prime rib (beef) with Pommes dauphine which is crisp potato puffs made by mixing mashed potatoes with savoury choux pastry.", 106.0, "https://i.imgur.com/ulR9yEa.jpg", "https://github.com/FinnTheHuman411/hkmukevin03fypassets/blob/main/prime_rib_with_pommes_dauphines.glb?raw=true", 0.75, 1.25);
            fdb.insert(2006, "Barbecued Rib", "Meat", "Rib (beef) with barbecue sauce and pea sprout.", 99.0, "https://i.imgur.com/kwtbAwm.jpg", "https://github.com/FinnTheHuman411/hkmukevin03fypassets/blob/main/ribs_from_joia.glb?raw=true", 0.75, 1.25);
            fdb.insert(1001, "Salad", "Appetizer", "Biscuit St Estève, ganache & gel abricot, chocolat blanc & sorbet abricot.", 32.0, "https://i.imgur.com/30DpBqe.jpg", "https://github.com/FinnTheHuman411/hkmukevin03fypassets/blob/main/salad_plate.glb?raw=true", 0.75, 1.25);
            fdb.insert(4003, "Viennese Coffee with Chantilly", "Hot Drink", "Espresso topped with abundant whipped cream and sprinkled with cocoa powder.", 16.0, "https://i.imgur.com/bKp9dgg.jpg", "https://github.com/FinnTheHuman411/hkmukevin03fypassets/blob/main/vienna_coffee_with_chantilly.glb?raw=true", 0.75, 1.25);
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
                "genre VARCHAR NOT NULL, " +
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