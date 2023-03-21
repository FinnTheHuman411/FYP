package com.fypkevin03.ar_restaurantmenu;


public class Object_Comment {
    public int comment_id, product_id;
    public String username, comment;

    public Object_Comment(int comment_id, int product_id, String username, String comment){
        this.comment_id = comment_id;
        this.product_id = product_id;
        this.username = username;
        this.comment = comment;
    }

}
