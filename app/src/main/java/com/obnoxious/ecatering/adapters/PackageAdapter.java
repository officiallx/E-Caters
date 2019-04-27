package com.obnoxious.ecatering.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.Package;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Bleeding Rain on 4/17/2019.
 */

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.HomeViewHolder>{

    List<Package> aPackages;
    Package aPackage = new Package();
    Context c;
    String photoPath, menuName;
    private OnItemClickListener onClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){

        onClickListener = listener;

    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mImageView;
        public TextView mName, toolbar_title, food_description, food_package_guests;
        RelativeLayout rl_main;

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

            rl_main = itemView.findViewById(R.id.rl_main);
            rl_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener!=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bindMenu(Package aPackage){
            menuName = aPackage.getPackageType();
            String description = aPackage.getPackageDescription();
            String guests = aPackage.getPackagePrice();
            mName.setText(menuName);
            food_description.setText(description);
            food_package_guests.setText("Rs. "+guests);

            photoPath = (aPackage.getProfilePath());

            Picasso.get().load(photoPath)
                    .fit()
                    .into(mImageView);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public PackageAdapter(List<Package> aPackages){

        this.aPackages = aPackages;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_row,parent,false);
        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, final int position) {

        holder.bindMenu(aPackages.get(position));
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (position == position){
//                    Intent i = new Intent(c, PackageDetailsActivity.class);
//                    i.putExtra("PackageId",position);
//                    i.putExtra("Package_Name",String.valueOf(aPackages.get(position).getPackageType()));
//                    //i.putExtra("PACKAGE_NAME",menuName);
//                    c.startActivity(i);
//                }
//                else {
//                    Log.d("position", "onClick: Starting activity Failed");
//                }
//                Log.d("position", "onClick: "+ aPackages.get(position));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return aPackages.size();
    }


}