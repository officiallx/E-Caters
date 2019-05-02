package com.obnoxious.ecatering.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.adapters.PackageAdapter;
import com.obnoxious.ecatering.models.Package;
import com.obnoxious.ecatering.services.PackageService;
import com.obnoxious.ecatering.utils.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PackageActivity extends AppCompatActivity implements SwipeBackLayout.SwipeBackListener, PackageAdapter.OnItemClickListener{

    PackageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;
    TextView toolbar_title, txtFoodPackage;
    Intent intent = new Intent();
    List<Package> foods;
    String result,position;
    Integer pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        foods = new ArrayList<>();

        Intent intent = getIntent();
        result = intent.getStringExtra("EXTRA_MESSAGE");
        position = intent.getStringExtra("POSITION");
        try {
            pos = Integer.parseInt(position);
            Log.d("position", "wedding activity ko position : " + pos);
        }catch (NumberFormatException ex){
            Log.d("position", "onCreate: "+ex.getMessage());
        }

        mRecyclerView = findViewById(R.id.rv_food_menu);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PackageAdapter(foods);

        getAllItems();

        //swipe back implementation
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set title to toolbar
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(result+" Package");

        txtFoodPackage = findViewById(R.id.textView7);

        Typeface face = Typeface.createFromAsset(getAssets(), "font/CaviarDreams.ttf");
        toolbar_title.setTypeface(face);
        txtFoodPackage.setTypeface(face);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(getContainer());
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        swipeBackLayout.addView(view);
    }

    //for swipe back
    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setOnSwipeBackListener(this);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.white));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        return container;
    }

    public void setDragEdge(SwipeBackLayout.DragEdge dragEdge) {
        swipeBackLayout.setDragEdge(dragEdge);
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackLayout;
    }

    @Override
    public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
        ivShadow.setAlpha(1 - fractionScreen);
    }

    //this is where it connects to the api
    public void getAllItems(){

        Long fId = (long) pos;
        Log.d("position", "long positon: "+fId);

        Call<List<Package>> lists = RetrofitBuilder
                .getInstance()
                .packageService()
                .getAllMenu(fId+1L);
        lists.enqueue(new Callback<List<Package>>() {
            @Override
            public void onResponse(Call<List<Package>> call, Response<List<Package>> response) {
                if (response.isSuccessful()) {
                    foods = response.body();
                    mAdapter = new PackageAdapter(foods);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnClickListener(PackageActivity.this);
                    Log.d("Menu", "onResponse: " + foods);
                }
            }

            @Override
            public void onFailure(Call<List<Package>> call, Throwable t) {
                Log.d("Menu", "onFailure: " + t.getMessage());
                //Toast.makeText(c, "Failed to Load", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(this,PackageDetailsActivity.class);
        i.putExtra("Package_Id",String.valueOf(foods.get(position).getPackageId()));
        i.putExtra("Package_Name",foods.get(position).getPackageType());
        i.putExtra("Package_Price",foods.get(position).getPackagePrice());
        startActivity(i);
    }
}
