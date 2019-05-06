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

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.adapters.DinnerAdapter;
import com.obnoxious.ecatering.adapters.LunchAdapter;
import com.obnoxious.ecatering.models.Dinner;
import com.obnoxious.ecatering.models.Lunch;
import com.obnoxious.ecatering.utils.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FragmentDinner extends Fragment {

    DinnerAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView rv_dinner;
    Context c;
    String packageId,tok;
    Long packageid;
    int id;

    List<Dinner> dinners = new ArrayList<>();
    View view;

    public FragmentDinner(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dinner,container,false);
        c = getActivity();

        assert c != null;
        Intent intent = ((FragmentActivity) c).getIntent();
        packageId = intent.getStringExtra("Package_Id");
        id = Integer.parseInt(packageId);

        rv_dinner = view.findViewById(R.id.rv_dinner);
        mLayoutManager = new LinearLayoutManager(c);
        rv_dinner.setLayoutManager(mLayoutManager);
        rv_dinner.setHasFixedSize(true);

        mAdapter = new DinnerAdapter(dinners);
        rv_dinner.setAdapter(mAdapter);

        SharedPreferences use_token = c.getSharedPreferences("USER_TOKEN", MODE_PRIVATE);
        tok = use_token.getString("USER_TOKEN", null);

        getAllDinner();

        return view;
    }

    public void getAllDinner(){

        packageid = (long) id;

        Call<List<Dinner>> dinner = RetrofitBuilder
                .getInstance()
                .dinnerService()
                .getAllDinner(packageid,tok);
        dinner.enqueue(new Callback<List<Dinner>>() {
            @Override
            public void onResponse(Call<List<Dinner>> call, Response<List<Dinner>> response) {
                dinners = response.body();
                mAdapter = new DinnerAdapter(dinners);
                rv_dinner.setAdapter(mAdapter);
                Log.d("lunch", "onResponse: " + dinners.toString());
            }

            @Override
            public void onFailure(Call<List<Dinner>> call, Throwable t) {
                Log.d("lunch", "onFailure: "+t.getMessage());
            }
        });
    }

}
