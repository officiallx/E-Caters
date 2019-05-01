package com.obnoxious.ecatering.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.adapters.HomeAdapter;
import com.obnoxious.ecatering.adapters.PackageAdapter;
import com.obnoxious.ecatering.models.Event;
import com.obnoxious.ecatering.services.EventService;
import com.obnoxious.ecatering.ui.NoConnectionActivity;
import com.obnoxious.ecatering.utils.RetrofitBuilder;
import com.obnoxious.ecatering.view.EventTimeActivity;
import com.obnoxious.ecatering.view.PackageDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bleeding Rain on 4/17/2019.
 */

public class HomeFragment extends Fragment {

    HomeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private ViewFlipper v_flipper;
    Context c;
    private SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout relativeLayout;
    TextView toolbar_title, txt_slogan;
    String user_id;
    CardView cv_home;

    List<Event> events = new ArrayList();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, null);

        relativeLayout = view.findViewById(R.id.rl_main);
        mRecyclerView = view.findViewById(R.id.rv_home);
        cv_home = view.findViewById(R.id.cv_home);

        c = getActivity();

        assert c != null;
        Intent intent = ((FragmentActivity) c).getIntent();
        user_id = intent.getStringExtra("USER_ID");
        Log.d("username", "home fragment ko user id : "+user_id);

        Typeface face = Typeface.createFromAsset(c.getAssets(), "font/CaviarDreams.ttf");

        txt_slogan = view.findViewById(R.id.txt_slogan);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setTypeface(face);
        txt_slogan.setTypeface(face);

        //image switcher or banner
        v_flipper = view.findViewById(R.id.v_flipper);

        int images[] = {R.drawable.banner1, R.drawable.banner8, R.drawable.banner9, R.drawable.banner10, R.drawable.banner11};

        /*for (int i = 0; i < images.length; i++){
            flipperImages((images[i]));
        }*/

        for (int image : images) {
            flipperImages(image);
        }

        //pull to refresh implement
        swipeRefreshLayout = view.findViewById(R.id.swipe_home);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkConnectionAvailable()) {
                    getAllEvents();
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(c, "Refresh Successful", Toast.LENGTH_SHORT).show();
                } else {
                    startNoInternetActivity();
                    //checkNetworkConnection();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        mRecyclerView = view.findViewById(R.id.rv_home);
        mRecyclerView.setHasFixedSize(true);

        //mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 3));
        mAdapter = new HomeAdapter(events);


        //checks if there is internet connection or not if not it throws the alert dialog
        if (isNetworkConnectionAvailable()) {
            getAllEvents();
        } else {
            startNoInternetActivity();
            //checkNetworkConnection();
        }

        //mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    //banner image switcher
    public void flipperImages(int image) {
        ImageView imageView = new ImageView(c);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(c, android.R.anim.fade_in);
        v_flipper.setOutAnimation(c, android.R.anim.fade_out);

    }

    //this is where it connects to the api
    public void getAllEvents() {

        Call<List<Event>> eventCall = RetrofitBuilder
                .getInstance()
                .eventService()
                .getAllMenu();

        eventCall.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    events = (ArrayList) response.body();
                    mAdapter = new HomeAdapter(events);
                    mRecyclerView.setAdapter(mAdapter);
                    Log.d("Events", "onResponse: " + events);
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d("Events", "onFailure: " + t.getMessage());
                //Toast.makeText(c, "Failed to Load", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //checks if internet connection is available or not
    public boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(c.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            //connection is avlilable
            return true;
        } else {
            //no connection
            //Toast toast = Toast.makeText(c, "No Internet Connection",
            //       Toast.LENGTH_LONG);
            //toast.show();
            return false;
        }
    }

    public void startNoInternetActivity() {

        Intent i = new Intent(c, NoConnectionActivity.class);
        startActivity(i);
    }

}
/*
        Log.d("username", "onItemClick: chaliracha");
                Intent i = new Intent(c, EventTimeActivity.class);
        i.putExtra("Event_Name", events.get(position).getEventName());
        startActivity(i);*/
