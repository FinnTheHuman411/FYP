package com.fypkevin03.ar_restaurantmenu;

public class Object_Food {
    int index;
    int foodID;
    String image;
    String foodName;
    String foodPrice;
    String foodType;

    public Object_Food(int foodID, String image, String foodName, String foodPrice, String foodType) {
        this.foodID = foodID;
        this.image = image;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodType = foodType;
    }

    public int getFoodID(){
        return foodID;
    }

    public void setFoodID(int foodID){
        this.foodID = foodID;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getFoodName(){
        return foodName;
    }

    public void setFoodName(String foodName){
        this.foodName = foodName;
    }

    public String getFoodPrice(){
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice){
        this.foodName = foodName;
    }

    public String getFoodType(){
        return foodType;
    }

    public void setFoodType(String foodType){
        this.foodType = foodType;
    }

}
