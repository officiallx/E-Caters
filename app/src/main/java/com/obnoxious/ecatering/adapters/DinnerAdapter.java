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
import com.obnoxious.ecatering.models.Dinner;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DinnerAdapter extends RecyclerView.Adapter<DinnerAdapter.DinnerViewHolder> {

    List<Dinner> dinnerList;
    Context c;

    public class DinnerViewHolder extends RecyclerView.ViewHolder{

        public TextView mName;
        public DinnerViewHolder(@NonNull View itemView) {
            super(itemView);
            c = itemView.getContext();

            mName = itemView.findViewById(R.id.package_list_name);

            Typeface face = Typeface.createFromAsset(c.getAssets(), "font/CaviarDreams.ttf");
            mName.setTypeface(face);
        }

        public void bindMenu(Dinner dinner){

            String packageFoodName = dinner.getDinnerName();
            mName.setText(packageFoodName);

        }
    }

    public DinnerAdapter(List<Dinner> dinners){

        this.dinnerList = dinners;
    }

    @NonNull
    @Override
    public DinnerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.package_list_row,viewGroup,false);
        return new DinnerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DinnerViewHolder dinnerViewHolder, int i) {

        dinnerViewHolder.bindMenu(dinnerList.get(i));
    }

    @Override
    public int getItemCount() {
        return dinnerList.size();
    }
}
