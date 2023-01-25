package com.fypkevin03.ar_restaurantmenu;


public class CommentObject {
    public int comment_id, product_id;
    public String username, comment;

    public CommentObject(int comment_id, int product_id, String username, String comment){
        this.comment_id = comment_id;
        this.product_id = product_id;
        this.username = username;
        this.comment = comment;
    }

}
