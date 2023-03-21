package com.fypkevin03.ar_restaurantmenu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder_Homepage {

    ImageView itemImage;
    TextView FoodName;
    TextView FoodPrice;
    TextView FoodType;
    ViewHolder_Homepage(View v){
        itemImage = v.findViewById(R.id.icon);
        FoodName = v.findViewById(R.id.FoodName);
        FoodPrice = v.findViewById(R.id.FoodPrice);
        FoodType = v.findViewById(R.id.FoodType);
    }
}
