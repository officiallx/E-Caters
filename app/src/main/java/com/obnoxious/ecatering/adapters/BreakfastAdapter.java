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
import com.squareup.picasso.Picasso;

import java.util.List;

public class BreakfastAdapter extends RecyclerView.Adapter<BreakfastAdapter.BreakfastViewHolder> {

    List<Breakfast> breakfasts;
    Context c;

    public class BreakfastViewHolder extends RecyclerView.ViewHolder{

        public TextView mName;
        public BreakfastViewHolder(@NonNull View itemView) {
            super(itemView);
            c = itemView.getContext();

            mName = itemView.findViewById(R.id.package_list_name);

            Typeface face = Typeface.createFromAsset(c.getAssets(), "font/CaviarDreams.ttf");
            mName.setTypeface(face);
        }

        public void bindMenu(Breakfast breakfast){

            String packageFoodName = breakfast.getBreakfastName();
            mName.setText(packageFoodName);

        }
    }

    public BreakfastAdapter(List<Breakfast> breakfastItem){

        this.breakfasts = breakfastItem;
    }

    @NonNull
    @Override
    public BreakfastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.package_list_row,viewGroup,false);
        return new BreakfastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BreakfastViewHolder breakfastViewHolder, int i) {

        breakfastViewHolder.bindMenu(breakfasts.get(i));
    }

    @Override
    public int getItemCount() {
        return breakfasts.size();
    }
}
