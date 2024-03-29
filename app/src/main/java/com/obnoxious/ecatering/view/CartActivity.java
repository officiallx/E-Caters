package com.obnoxious.ecatering.view;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.EventTime;
import com.obnoxious.ecatering.models.Order;
import com.obnoxious.ecatering.models.User;
import com.obnoxious.ecatering.utils.RetrofitBuilder;

import java.util.ArrayList;
import java.util.function.IntToLongFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    TextView txt_toolbar_title;
    String packageName, eventVenue, eventAddress, userId, eventName, eventdateId, tok, venue, address, loadedString;
    TextView cartName, txt_title, txt_title_detail, txtOrderId, orderGuest, orderPrice, orderTotal;
    ImageView img_close, popup_event_close;
    //EditText txtEventVenue, txtEventAddress;
    ArrayList<String> packageChosen;
    ListView lv_order;
    Button btnPlaceOrder, btnDone, btn_event_done;
    Order orders = new Order();
    Dialog successDialog, eventDialog;
    int eventdateid, orderId, guestc, pricec;
    double total;
    EditText txtEventAddress, txtEventVenue;
    String listString, orderguest, orderprice, ordertotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        packageChosen = new ArrayList<>();
        packageChosen = intent.getStringArrayListExtra("checked_boxes"); // checkbox value get from product detail activity

        packageName = intent.getExtras().getString("PACKAGE_NAME"); // get package name from package detail activity

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            listString = String.join(",", packageChosen);
            //Log.d("username", "cart ko selected packages: "+listString);
        }

        Typeface face = Typeface.createFromAsset(getAssets(), "font/CaviarDreams.ttf");

        txt_toolbar_title = findViewById(R.id.toolbar_title);
        txt_toolbar_title.setText("My Order");
        txt_toolbar_title.setTypeface(face);

        cartName = findViewById(R.id.cartName);

        cartName.setText(packageName);
        cartName.setTypeface(face);

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                packageChosen);

        //list view ma arraylist haleko
        lv_order = findViewById(R.id.lv_order);
        lv_order.setAdapter(arrayAdapter);

        //txtEventVenue = findViewById(R.id.txtEventVenue);
        //txtEventAddress = findViewById(R.id.txtEventAddress);

        successDialog = new Dialog(this);
        eventDialog = new Dialog(this);

        // To load the data at a later time
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_ID", MODE_PRIVATE);
        loadedString = prefs.getString("USER_ID", null);
        userId = loadedString;
        //Log.d("username", "cart ko user id woo hoo hooo ooo: " + loadedString);

        //From EventTime activity
        SharedPreferences eventname = getApplicationContext().getSharedPreferences("EVENT_NAME", MODE_PRIVATE);
        eventName = eventname.getString("SELECTED_EVENT_NAME", null);

        //From EventTime Activity
        SharedPreferences eventDateId = getApplicationContext().getSharedPreferences("EVENT_DATE_TIME", MODE_PRIVATE);
        eventdateId = eventDateId.getString("SELECTED_EVENT_TIME", null);
        eventdateid = Integer.valueOf(eventdateId);
        //Log.d("username", "cart activity ko event date ko id : "+eventdateId);

        SharedPreferences use_token = this.getSharedPreferences("USER_TOKEN", MODE_PRIVATE);
        tok = use_token.getString("USER_TOKEN", null);

        //From EventTime Activity
        SharedPreferences guest = getApplicationContext().getSharedPreferences("GUEST_COUNT", MODE_PRIVATE);
        orderguest = guest.getString("GUEST_COUNT", null);
        guestc = Integer.valueOf(orderguest);

        //From PackageDetails Activity
        SharedPreferences price = getApplicationContext().getSharedPreferences("ORDER_PRICE", MODE_PRIVATE);
        orderprice = price.getString("ORDER_PRICE", null);
        pricec = Integer.valueOf(orderprice);

        total = (double) (guestc * pricec); //total calculation
        ordertotal = String.valueOf(total);

        orderGuest = findViewById(R.id.orderGuest);
        orderPrice = findViewById(R.id.orderPrice);
        orderTotal = findViewById(R.id.orderTotal);

        orderGuest.setText(orderguest);
        orderPrice.setText(orderprice);
        orderTotal.setText("Rs. "+ordertotal);

        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showEventPopup();

            }
        });

    }

    private void saveOrder() {

        Call<Order> order = RetrofitBuilder
                .getInstance()
                .orderService()
                .addOrder(orders,tok);

        order.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    orders = response.body();
                    orderId = orders.getOrderId();

                    SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("USER_ORDER_ID", MODE_PRIVATE).edit();
                    editor.putString("USER_ORDER_ID", Integer.toString(orderId));
                    editor.apply();

                    Log.d("username", "User ko orderId: "+orderId);
                    showPopup();
                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showPopup() {

        successDialog.setContentView(R.layout.custompopup);
        img_close = successDialog.findViewById(R.id.popup_success_close);
        btnDone = successDialog.findViewById(R.id.btn_success_popup_done);
        txt_title = successDialog.findViewById(R.id.txt_success);
        txt_title_detail = successDialog.findViewById(R.id.txt_success_detail);
        txtOrderId = successDialog.findViewById(R.id.txtOrderId);

        txtOrderId.setText("Your Order Id is : "+orderId);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successDialog.dismiss();
                Intent i = new Intent(CartActivity.this, EventActivity.class);
                i.putExtra("USER_ID", userId);
                startActivity(i);
            }
        });

        successDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        successDialog.show();

        btnDone = successDialog.findViewById(R.id.btn_success_popup_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this, EventActivity.class);
                i.putExtra("USER_ID", userId);
                i.putExtra("ORDER_ID", orderId);
                startActivity(i);
            }
        });
    }

    public void showEventPopup() {

        eventDialog.setContentView(R.layout.custome_event_address_popup);
        popup_event_close = eventDialog.findViewById(R.id.popup_event_close);
        btn_event_done = eventDialog.findViewById(R.id.btn_event_popup_done);
        txtEventAddress = eventDialog.findViewById(R.id.txtEventAddress);
        txtEventVenue = eventDialog.findViewById(R.id.txtEventVenue);

        popup_event_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventDialog.dismiss();
            }
        });

        eventDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        eventDialog.show();

        btn_event_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address = txtEventAddress.getText().toString();
                venue = txtEventVenue.getText().toString();

/*                SharedPreferences.Editor addresses = getApplicationContext().getSharedPreferences("EVENT_ADDRESS", MODE_PRIVATE).edit();
                addresses.putString("EVENT_ADDRESS", address);
                addresses.apply();

                SharedPreferences.Editor venues = getApplicationContext().getSharedPreferences("EVENT_VENUE", MODE_PRIVATE).edit();
                venues.putString("EVENT_VENUE", venue);
                venues.apply();*/

                int id = Integer.valueOf(loadedString);

                orders.setPackageName(packageName);
                orders.setEventVenue(venue);
                orders.setEventAddress(address);
                //orders.setUserId(Long.valueOf(loadedString));
                User user = new User();
                user.setId(id);
                user.setUsername("");
                user.setPassword("");
                user.setContact(null);
                user.setName("");
                orders.setUserId(user);
                orders.setEventName(eventName);

                EventTime eventTime = new EventTime();
                eventTime.setEventId(eventdateid);
                eventTime.setEventDate("");
                eventTime.setEventTime("");
                eventTime.setGuestCount("");
                orders.setEventDateTime(eventTime);
                orders.setSelectedService(listString);

                saveOrder();
            }
        });
    }
}
