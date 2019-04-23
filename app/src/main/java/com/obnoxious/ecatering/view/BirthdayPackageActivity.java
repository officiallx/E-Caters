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
import com.obnoxious.ecatering.models.FoodItem;
import com.obnoxious.ecatering.services.PackageService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BirthdayPackageActivity extends AppCompatActivity implements SwipeBackLayout.SwipeBackListener, PackageAdapter.OnItemClickListener{

    private final String baseUrl = "http://192.168.100.24:8080/"; // base url to connect to server
    PackageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;
    TextView toolbar_title, txtFoodPackage;
    List<FoodItem> foods = new ArrayList<>();
    Intent intent = new Intent();
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday_package);

        Intent intent = getIntent();
        result = intent.getStringExtra("EXTRA_MESSAGE");

        mRecyclerView = findViewById(R.id.rv_drinks_menu);
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

        int eventId = intent.getIntExtra("POSITION",2);
        Long fId = (long) (eventId);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final PackageService packageService = retrofit.create(PackageService.class);
        Call<List<FoodItem>> lists = packageService.getAllMenu(fId);
        lists.enqueue(new Callback<List<FoodItem>>() {
            @Override
            public void onResponse(Call<List<FoodItem>> call, Response<List<FoodItem>> response) {
                if (response.isSuccessful()) {
                    foods = response.body();
                    mAdapter = new PackageAdapter(foods);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnClickListener(BirthdayPackageActivity.this);
                    Log.d("Menu", "onResponse: " + foods);
                }
            }

            @Override
            public void onFailure(Call<List<FoodItem>> call, Throwable t) {
                Log.d("Menu", "onFailure: " + t.getMessage());
                //Toast.makeText(c, "Failed to Load", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(this,PackageDetailsActivity.class);
        i.putExtra("Package_Name",foods.get(position).getPackageType());
        i.putExtra("Package_Price",foods.get(position).getPackagePrice());
        startActivity(i);
    }
}
