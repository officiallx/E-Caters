package com.obnoxious.ecatering.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.Event;
import com.obnoxious.ecatering.models.User;
import com.obnoxious.ecatering.view.EventTimeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Bleeding Rain on 4/17/2019.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{

    List<Event> events;
    Event event = new Event();
    Context c,d;
    String photoPath, userId;
    private static RecyclerView.RecyclerListener itemListener;

    /*private OnItemClickListener onClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){

        onClickListener = listener;

    }*/

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mImageView;
        public TextView mName, toolbar_title;
        RelativeLayout rl_main;
        CardView cv_home;

        public HomeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            c = itemView.getContext();
            mImageView = itemView.findViewById(R.id.personal_list_icon);
            mName = itemView.findViewById(R.id.menuName);

            Typeface face = Typeface.createFromAsset(c.getAssets(), "font/CaviarDreams.ttf");
            mName.setTypeface(face);

            SharedPreferences prefs = c.getSharedPreferences("USER_ID", MODE_PRIVATE);
            userId = prefs.getString("USER_ID",null);
            Log.d("username", "home adapter ko user id woo hoo: "+userId);

/*            cv_home = itemView.findViewById(R.id.cv_home);
            cv_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener!=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onClickListener.onItemClick(position);
                        }
                    }
                }
            });*/

        }

        public void bindMenu(Event menu){

            String menuName = menu.getEventName();
            mName.setText(menuName);

            photoPath = (menu.getProfilePath());

            //load photo from server
            Picasso.get().load(photoPath)
                    .fit()
                    .into(mImageView);
        }

        @Override
        public void onClick(View v) {

            cv_home = itemView.findViewById(R.id.cv_home);

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
                    String pos = Integer.toString(position);
                    i.putExtra("POSITION",pos);
                    c.startActivity(i);
                }
                if(position == 1){
                    Intent i = new Intent(c, EventTimeActivity.class);
                    i.putExtra("EXTRA_MESSAGE","Birthday");
                    String pos = Integer.toString(position);
                    i.putExtra("POSITION",pos);
                    c.startActivity(i);
                }
                if(position == 2){
                    Intent i = new Intent(c, EventTimeActivity.class);
                    i.putExtra("EXTRA_MESSAGE","Party");
                    String pos = Integer.toString(position);
                    i.putExtra("POSITION",pos);
                    c.startActivity(i);
                }
                if(position == 3){
                    Intent i = new Intent(c, EventTimeActivity.class);
                    i.putExtra("EXTRA_MESSAGE","Funeral");
                    String pos = Integer.toString(position);
                    i.putExtra("POSITION",pos);
                    c.startActivity(i);
                }
                if(position == 4){
                    Intent i = new Intent(c, EventTimeActivity.class);
                    i.putExtra("EXTRA_MESSAGE","Baby Shower");
                    String pos = Integer.toString(position);
                    i.putExtra("POSITION",pos);
                    c.startActivity(i);
                }
                if(position == 5){
                    Intent i = new Intent(c, EventTimeActivity.class);
                    i.putExtra("EXTRA_MESSAGE","Gathering");
                    String pos = Integer.toString(position);
                    i.putExtra("POSITION",pos);
                    c.startActivity(i);
                }
                if(position == 6){
                    Intent i = new Intent(c, EventTimeActivity.class);
                    i.putExtra("EXTRA_MESSAGE","Festival");
                    String pos = Integer.toString(position);
                    i.putExtra("POSITION",pos);
                    c.startActivity(i);
                }
                if(position == 7){
                    Intent i = new Intent(c, EventTimeActivity.class);
                    i.putExtra("EXTRA_MESSAGE","Business");
                    String pos = Integer.toString(position);
                    i.putExtra("POSITION",pos);
                    c.startActivity(i);
                }
                if(position == 8){
                    Intent i = new Intent(c, EventTimeActivity.class);
                    i.putExtra("EXTRA_MESSAGE","Other");
                    String pos = Integer.toString(position);
                    i.putExtra("POSITION",pos);
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