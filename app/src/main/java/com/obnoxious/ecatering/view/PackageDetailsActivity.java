package com.obnoxious.ecatering.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

public class PackageDetailsActivity extends AppCompatActivity {

    private final String baseUrl = "http://192.168.100.24:8080/"; // base url to connect to server
    TextView package_name, package_price, package_description;
    ImageView package_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;

    List<FoodItem> foodItems = new ArrayList<>();

    String productName, productPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);

        Intent intent = getIntent();
        productName = intent.getStringExtra("Package_Name");
        productPrice = intent.getStringExtra("Package_Price");
        //Log.d("TEST", "onCreate: " + productName);

        btnCart = findViewById(R.id.btnCart);
        package_description = findViewById(R.id.package_description);
        package_name = findViewById(R.id.package_name);
        package_price = findViewById(R.id.package_price);
        package_image = findViewById(R.id.img_package);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        collapsingToolbarLayout.setTitle("Select Package");

        Typeface face = Typeface.createFromAsset(getAssets(), "font/CaviarDreams.ttf");

        //set typeface to collapsing toolbar title
        collapsingToolbarLayout.setCollapsedTitleTypeface(face);
        package_name.setTypeface(face);
        package_description.setTypeface(face);
        package_price.setTypeface(face);

        package_name.setText(productName);
        package_price.setText(productPrice);

        //Get product id from intent
//        if(getIntent()!= null)
//            ProductId = getIntent().getStringExtra("ProductId");
//        if (!ProductId.isEmpty()){
//        }
    }
}
