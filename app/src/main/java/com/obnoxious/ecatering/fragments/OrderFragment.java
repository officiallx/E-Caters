package com.obnoxious.ecatering.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.Order;
import com.obnoxious.ecatering.utils.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Bleeding Rain on 4/17/2019.
 */

public class OrderFragment extends Fragment {

    TextView txtEventName, txtEventPackage, txtEventDate, txtEventTime, txtEventVenue;
    String eventdateId;
    Long eventdateid;
    Order orders = new Order();

    Context c;
    TextView txt_toolbar_title;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);

        c = getActivity();

        Typeface face = Typeface.createFromAsset(c.getAssets(), "font/CaviarDreams.ttf");

        txt_toolbar_title = view.findViewById(R.id.toolbar_title);
        txt_toolbar_title.setText("My Orders");
        txt_toolbar_title.setTypeface(face);

        txtEventName = view.findViewById(R.id.eventType);
        txtEventPackage = view.findViewById(R.id.selected_package);
        txtEventDate = view.findViewById(R.id.event_date);
        txtEventTime = view.findViewById(R.id.event_Time);
        txtEventVenue = view.findViewById(R.id.event_venue);

/*        SharedPreferences eventDateId = c.getSharedPreferences("ORDER_ID", MODE_PRIVATE);
        eventdateId = eventDateId.getString("USER_ORDER_ID", null);
        eventdateid = Long.valueOf(eventdateId);
        Log.d("username", "order fragment ko event date id ho yo: "+eventdateId);*/

        final Call<Order> order = RetrofitBuilder
                .getInstance()
                .orderService()
                .getOrderById(eventdateid);

        order.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    orders = response.body();
                    txtEventName.setText(orders.getEventName());
                    txtEventPackage.setText(orders.getPackageName());
                    txtEventTime.setText(orders.getEventDateTime().getEventTime());
                    txtEventDate.setText(orders.getEventDateTime().getEventDate());
                    txtEventVenue.setText(orders.getEventVenue());
                    Log.d("username", "Mero Order haru ho yo: " + orders.toString());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });

        return view;
    }
}
