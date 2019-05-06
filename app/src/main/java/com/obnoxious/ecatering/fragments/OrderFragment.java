package com.obnoxious.ecatering.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.adapters.OrderAdapter;
import com.obnoxious.ecatering.adapters.PackageAdapter;
import com.obnoxious.ecatering.models.Order;
import com.obnoxious.ecatering.utils.RetrofitBuilder;
import com.obnoxious.ecatering.view.RegisterActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Bleeding Rain on 4/17/2019.
 */

public class OrderFragment extends Fragment {

    //TextView txtEventName, txtEventPackage, txtEventDate, txtEventTime, txtEventVenue;
    String orderid, tok;
    Long orderId;
    Order orders = new Order();
    private SwipeRefreshLayout swipeRefreshLayout;
    OrderAdapter orderAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    List<Order> orderList;

    Context c;
    TextView txt_toolbar_title;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);

        orderList = new ArrayList<>();
        c = getActivity();

        Typeface face = Typeface.createFromAsset(c.getAssets(), "font/CaviarDreams.ttf");

        txt_toolbar_title = view.findViewById(R.id.toolbar_title);
        txt_toolbar_title.setText("My Orders");
        txt_toolbar_title.setTypeface(face);
        swipeRefreshLayout = view.findViewById(R.id.swipe_home);

        SharedPreferences use_token = c.getSharedPreferences("USER_TOKEN", MODE_PRIVATE);
        tok = use_token.getString("USER_TOKEN", null);

        SharedPreferences eventDateId = c.getSharedPreferences("USER_ORDER_ID", MODE_PRIVATE);
        orderid = eventDateId.getString("USER_ORDER_ID", null);
        orderId = Long.valueOf(orderid);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrders();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(c, "Refresh Successful", Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView = view.findViewById(R.id.rv_orders);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(c);
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setLayoutManager(mLayoutManager);
        orderAdapter = new OrderAdapter(orderList);

        getOrders();

        return view;
    }

    void getOrders(){
        final Call<Order> order = RetrofitBuilder
                .getInstance()
                .orderService()
                .getOrderById(orderId,tok);

        order.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    orders = response.body();
                    orderList = Collections.singletonList(response.body());
                    orderAdapter = new OrderAdapter(orderList);
                    mRecyclerView.setAdapter(orderAdapter);
                    Log.d("username", "Mero Order haru ho yo: " + orders.toString());
                }
                else{
                    Toast.makeText(c, "Order haru chaina tero kunai kei order gar ani feri herna aija", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d("order", "Failed " + t.getMessage());
            }
        });
    }
}
