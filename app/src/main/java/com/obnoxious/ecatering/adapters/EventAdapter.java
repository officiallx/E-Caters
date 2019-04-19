package com.obnoxious.ecatering.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.obnoxious.ecatering.Interface.ClickListener;
import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.EventItem;

import java.util.List;

/**
 * Created by Bleeding Rain on 4/15/2019.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private List<EventItem> itemList;
    private ClickListener clicklistener = null;
    Context c;

    public EventAdapter(Context c, List<EventItem> itemList){
        this.c = c;
        this.itemList = itemList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView title, subtitle;
        public ImageView icon;
        private RelativeLayout main;

        public MyViewHolder(final View parent) {
            super(parent);

            title = (TextView) parent.findViewById(R.id.title);
            subtitle = (TextView) parent.findViewById(R.id.subtitle);
            icon = (ImageView) parent.findViewById(R.id.icon);

            Typeface face = Typeface.createFromAsset(c.getAssets(), "font/CaviarDreams.ttf");
            title.setTypeface(face);
            subtitle.setTypeface(face);

            main = parent.findViewById(R.id.rl_main);
            main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clicklistener !=null){
                        clicklistener.itemClicked(v,getAdapterPosition());
                    }
                }
            });
        }
    }
    public void setClickListener(ClickListener clickListener){
        this.clicklistener = clickListener;
    }
    @Override
    public EventAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_dashboard_row,parent,false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventItem row = itemList.get(position);
        holder.title.setText(row.getTitle());
        holder.subtitle.setText(row.getSubtitle());
        holder.icon.setImageResource(row.getImageId());

        Animation animation = AnimationUtils.loadAnimation(c, android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
