package com.obnoxious.ecatering.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.adapters.BreakfastAdapter;
import com.obnoxious.ecatering.adapters.LunchAdapter;
import com.obnoxious.ecatering.models.Breakfast;
import com.obnoxious.ecatering.models.Lunch;
import com.obnoxious.ecatering.utils.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FragmentLunch extends Fragment {

    LunchAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView rv_lunch;
    Context c;
    String packageId,tok;
    Long packageid;
    int id;

    List<Lunch> lunches = new ArrayList<>();
    View view;

    public FragmentLunch(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lunch,container,false);

        c = getActivity();

        assert c != null;
        Intent intent = ((FragmentActivity) c).getIntent();
        packageId = intent.getStringExtra("Package_Id");
        id = Integer.parseInt(packageId);

        rv_lunch = view.findViewById(R.id.rv_lunch);
        mLayoutManager = new LinearLayoutManager(c);
        rv_lunch.setLayoutManager(mLayoutManager);
        rv_lunch.setHasFixedSize(true);

        mAdapter = new LunchAdapter(lunches);
        rv_lunch.setAdapter(mAdapter);

        SharedPreferences use_token = c.getSharedPreferences("USER_TOKEN", MODE_PRIVATE);
        tok = use_token.getString("USER_TOKEN", null);

        getAllLunch();

        return view;
    }

    public void getAllLunch(){

        packageid = (long) id;

        Call<List<Lunch>> lunch = RetrofitBuilder
                .getInstance()
                .lunchService()
                .getAllLunch(packageid,tok);
        lunch.enqueue(new Callback<List<Lunch>>() {
            @Override
            public void onResponse(Call<List<Lunch>> call, Response<List<Lunch>> response) {
                lunches = response.body();
                mAdapter = new LunchAdapter(lunches);
                rv_lunch.setAdapter(mAdapter);
                Log.d("lunch", "onResponse: " + lunches.toString());
            }

            @Override
            public void onFailure(Call<List<Lunch>> call, Throwable t) {
                Log.d("lunch", "onFailure: "+t.getMessage());
            }
        });
    }

}
