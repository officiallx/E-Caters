package com.obnoxious.ecatering.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.obnoxious.ecatering.Interface.ClickListener;
import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.adapters.EventAdapter;
import com.obnoxious.ecatering.models.EventItem;

import java.util.ArrayList;
import java.util.List;

public class EventDashboardActivity extends AppCompatActivity implements ClickListener {
    private List<EventItem> itemList = new ArrayList<>();
    private RecyclerView recyclerview;
    private EventAdapter mAdapter;
    private LinearLayout main;
    TextView txt_Event_Name;
    ImageView imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dashboard);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        txt_Event_Name = findViewById(R.id.textView5);
        Intent intent = getIntent();
        final String result = intent.getStringExtra("EXTRA_MESSAGE");
        txt_Event_Name.setText(result + " Event");

        //image view change dynamically
        imageView4 = findViewById(R.id.imageView4);

        if (result.contains("Birthday")) {
            imageView4.setImageResource(R.drawable.birthday);
        }
        else if (result.contains("Party")) {
            imageView4.setImageResource(R.drawable.gathering);
        }
        else if (result.contains("Funeral")) {
            imageView4.setImageResource(R.drawable.funeral);
        }

        //back button on action bar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerview=(RecyclerView)findViewById(R.id.recyclerView);
        prepareItem();

        mAdapter = new EventAdapter(this,itemList);
        mAdapter.setClickListener(this);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //recyclerview.setLayoutManager(new GridLayoutManager(this,2));
        recyclerview.setAdapter(mAdapter);
    }

    private void prepareItem() {
        EventItem item = new EventItem(R.drawable.food,"Food Menu","This is a description of the product. It could be long and multiple lines");
        itemList.add(item);
        item = new EventItem(R.drawable.drinks,"Drinks Menu","This is a description of the product. It could be long and multiple lines");
        itemList.add(item);
        item = new EventItem(R.drawable.entertainment,"Entertainment","This is a description of the product. It could be long and multiple lines");
        itemList.add(item);
        item= new EventItem(R.drawable.waiter,"Staffs","This is a description of the product. It could be long and multiple lines");
        itemList.add(item);
        item = new EventItem(R.drawable.table,"Tableware","This is a description of the product. It could be long and multiple lines");
        itemList.add(item);

    }

    @Override
    public void itemClicked(View view, int position) {

        if (position == 0){
            Intent i = new Intent(EventDashboardActivity.this, FoodMenuActivity.class);
            i.putExtra("FOOD_ID",position);
            startActivity(i);
        }
        else if (position == 1){
            Intent i = new Intent(EventDashboardActivity.this, DrinksMenuActivity.class);
            i.putExtra("FOOD_ID",position);
            startActivity(i);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
