package com.obnoxious.ecatering.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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
import com.obnoxious.ecatering.models.Order;
import com.obnoxious.ecatering.services.OrderService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartActivity extends AppCompatActivity {

    final String baseUrl = "http://192.168.100.24:8080/";
    TextView txt_toolbar_title;
    String packageName, eventVenue, eventAddress;
    TextView cartName, txt_title, txt_title_detail;
    ImageView img_close;
    EditText txtEventVenue, txtEventAddress;
    ArrayList<String> packageChosen;
    ListView lv_order;
    Button btnPlaceOrder, btnDone;
    Order orders = new Order();
    Dialog successDialog;
    int responseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        packageChosen = new ArrayList<>();
        packageChosen = intent.getStringArrayListExtra("checked_boxes"); // checkbox value get from product detail activity

        packageName = intent.getExtras().getString("PACKAGE_NAME"); // get package name from package detail activity

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

        txtEventVenue = findViewById(R.id.txtEventVenue);
        txtEventAddress = findViewById(R.id.txtEventAddress);

        successDialog = new Dialog(this);

        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventVenue = txtEventVenue.getText().toString();
                eventAddress = txtEventAddress.getText().toString();

                orders.setPackageName(packageName);
                orders.setEventVenue(eventVenue);
                orders.setEventAddress(eventAddress);

                saveOrder();

            }
        });

    }

    private void saveOrder() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OrderService orderService = retrofit.create(OrderService.class);
        Call<Void> orderCall = orderService.addOrder(orders);
        orderCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseCode = response.code();
                if (responseCode == 200){
                    showPopup();
                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showPopup(){

        successDialog.setContentView(R.layout.custompopup);
        img_close = successDialog.findViewById(R.id.popup_success_close);
        btnDone = successDialog.findViewById(R.id.btn_success_popup_done);
        txt_title = successDialog.findViewById(R.id.txt_success);
        txt_title_detail = successDialog.findViewById(R.id.txt_success_detail);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successDialog.dismiss();
                Intent i = new Intent(CartActivity.this,EventActivity.class);
                startActivity(i);
            }
        });

        successDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        successDialog.show();

        btnDone = successDialog.findViewById(R.id.btn_success_popup_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this,EventActivity.class);
                startActivity(i);
            }
        });
    }
}
