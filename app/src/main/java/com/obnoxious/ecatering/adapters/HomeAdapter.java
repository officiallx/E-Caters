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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.Event;
import com.obnoxious.ecatering.view.EventTimeActivity;

import java.util.List;

/**
 * Created by Bleeding Rain on 4/17/2019.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{

    List<Event> events;
    Event event = new Event();
    Context c;

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mImageView;
        public TextView mName, toolbar_title;

        public HomeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            c = itemView.getContext();
            mImageView = itemView.findViewById(R.id.personal_list_icon);
            mName = itemView.findViewById(R.id.menuName);

            Typeface face = Typeface.createFromAsset(c.getAssets(), "font/CaviarDreams.ttf");
            mName.setTypeface(face);
        }

        public void bindMenu(Event menu){
            String menuName = menu.getEventName();
            mName.setText(menuName);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public HomeAdapter(List<Event> menuItems){

        events = menuItems;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row,parent,false);
        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, final int position) {

        holder.bindMenu(events.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    Intent i = new Intent(c, EventTimeActivity.class);
                    i.putExtra("EXTRA_MESSAGE","Wedding");
                    c.startActivity(i);
                }
                if(position == 1){
                    Intent i = new Intent(c, EventTimeActivity.class);
                    i.putExtra("EXTRA_MESSAGE","Birthday");
                    c.startActivity(i);
                }
                if(position == 2){
                    Intent i = new Intent(c, EventTimeActivity.class);
                    i.putExtra("EXTRA_MESSAGE","Party");
                    c.startActivity(i);
                }
                Log.d("position", "onClick: "+ events.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


}