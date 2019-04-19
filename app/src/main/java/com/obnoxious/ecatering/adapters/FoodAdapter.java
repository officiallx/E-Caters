package com.obnoxious.ecatering.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.Event;
import com.obnoxious.ecatering.models.FoodItem;
import com.obnoxious.ecatering.view.EventTimeActivity;

import java.util.List;

/**
 * Created by Bleeding Rain on 4/17/2019.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.HomeViewHolder>{

    List<FoodItem> foodItems;
    FoodItem foodItem = new FoodItem();
    Context c;

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mImageView;
        public TextView mName, toolbar_title, food_description, food_package_guests;

        public HomeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            c = itemView.getContext();
            mImageView = itemView.findViewById(R.id.personal_list_icon);
            mName = itemView.findViewById(R.id.menuName);
            food_description = itemView.findViewById(R.id.food_description);
            food_package_guests = itemView.findViewById(R.id.food_package_guests);

            Typeface face = Typeface.createFromAsset(c.getAssets(), "font/CaviarDreams.ttf");
            mName.setTypeface(face);
            food_description.setTypeface(face);
            food_package_guests.setTypeface(face);
        }

        public void bindMenu(FoodItem foodItem){
            String menuName = foodItem.getItemName();
            String description = foodItem.getItemDescription();
            String guests = foodItem.getItemGuests();
            mName.setText(menuName);
            food_description.setText(description);
            food_package_guests.setText(guests + " Guests");
        }

        @Override
        public void onClick(View v) {

        }
    }

    public FoodAdapter(List<FoodItem> foodItems){

        this.foodItems = foodItems;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_menu_row,parent,false);
        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, final int position) {

        holder.bindMenu(foodItems.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    //Intent i = new Intent(c, EventTimeActivity.class);
                    //i.putExtra("EXTRA_MESSAGE","Wedding");
                    //c.startActivity(i);
                }
                Log.d("position", "onClick: "+ foodItems.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }


}