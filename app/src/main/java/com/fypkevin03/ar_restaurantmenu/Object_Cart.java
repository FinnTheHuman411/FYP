package com.fypkevin03.ar_restaurantmenu;


import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class Object_Cart {
    public int cartID, product_id;
    public String product_name, note, image;
    public int count, price;

    public Object_Cart(int cartID, int product_id, String product_name, int count, int price, String image, String note){
        this.cartID = cartID;
        this.product_id = product_id;
        this.product_name = product_name;
        this.count = count;
        this.price = price;
        this.image = image;
        this.note = note;
    }

}
