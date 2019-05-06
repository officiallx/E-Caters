package com.obnoxious.ecatering.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.Breakfast;
import com.obnoxious.ecatering.models.Event;
import com.obnoxious.ecatering.models.Lunch;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LunchAdapter extends RecyclerView.Adapter<LunchAdapter.LunchViewHolder> {

    List<Lunch> lunches;
    Context c;

    public class LunchViewHolder extends RecyclerView.ViewHolder{

        public TextView mName;
        public LunchViewHolder(@NonNull View itemView) {
            super(itemView);
            c = itemView.getContext();

            mName = itemView.findViewById(R.id.package_list_name);

            Typeface face = Typeface.createFromAsset(c.getAssets(), "font/CaviarDreams.ttf");
            mName.setTypeface(face);
        }

        public void bindMenu(Lunch lunch){

            String packageFoodName = lunch.getLunchName();
            mName.setText(packageFoodName);

        }
    }

    public LunchAdapter(List<Lunch> lunchItem){

        this.lunches = lunchItem;
    }

    @NonNull
    @Override
    public LunchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.package_list_row,viewGroup,false);
        return new LunchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LunchViewHolder lunchViewHolder, int i) {

        lunchViewHolder.bindMenu(lunches.get(i));
    }

    @Override
    public int getItemCount() {
        return lunches.size();
    }
}
