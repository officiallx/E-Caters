package com.obnoxious.ecatering.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.adapters.BreakfastAdapter;
import com.obnoxious.ecatering.models.Breakfast;
import com.obnoxious.ecatering.services.BreakfastService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentBreakfast extends Fragment {

    private final String baseUrl = "http://192.168.100.24:8080/"; // base url to connect to server
    BreakfastAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView rv_breakfast;
    Context c;
    TextView mName;
    String packageId;
    Long packageid;
    Bundle bundle = this.getArguments();

    List<Breakfast> breakfasts = new ArrayList<>();

    public FragmentBreakfast(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breakfast,container,false);
        c = getActivity();

       /* packageId = bundle.getString("package_id");
        packageid = Long.valueOf(packageId);*/

        rv_breakfast = view.findViewById(R.id.rv_breakfast);
        mLayoutManager = new LinearLayoutManager(c);
        rv_breakfast.setLayoutManager(mLayoutManager);
        rv_breakfast.setHasFixedSize(true);

        mAdapter = new BreakfastAdapter(breakfasts);
        rv_breakfast.setAdapter(mAdapter);

        getAllBreakfast();

        return view;
    }

    public void getAllBreakfast(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final BreakfastService breakfastService = retrofit.create(BreakfastService.class);
        Call<List<Breakfast>> breakfastCall = breakfastService.getAllBreakfast(8L);
        breakfastCall.enqueue(new Callback<List<Breakfast>>() {
            @Override
            public void onResponse(Call<List<Breakfast>> call, Response<List<Breakfast>> response) {
                breakfasts = response.body();
                mAdapter = new BreakfastAdapter(breakfasts);
                rv_breakfast.setAdapter(mAdapter);
                Log.d("Breakfast", "onResponse: " + breakfasts.toString());
            }

            @Override
            public void onFailure(Call<List<Breakfast>> call, Throwable t) {
                Log.d("Breakfast", "onFailure: "+t.getMessage());
            }
        });
    }

}
