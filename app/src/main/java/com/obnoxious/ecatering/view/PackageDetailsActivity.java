package com.obnoxious.ecatering.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.adapters.ViewPagerAdapter;
import com.obnoxious.ecatering.fragments.FragmentBreakfast;
import com.obnoxious.ecatering.fragments.FragmentDinner;
import com.obnoxious.ecatering.fragments.FragmentLunch;
import com.obnoxious.ecatering.models.Package;

import java.util.ArrayList;
import java.util.List;

public class PackageDetailsActivity extends AppCompatActivity {

    TextView package_name, package_price, package_description;
    ImageView package_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewFlipper v_flipper;
    FragmentTransaction transaction;

    List<Package> aPackages = new ArrayList<>();

    String productId, productName, productPrice;
    CheckBox chkBreakfast, chkLunch, chkDinner;
    Intent i;

    ArrayList<String> checkedBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);

        checkedBox = new ArrayList<>();

        Intent intent = getIntent();
        productId = intent.getStringExtra("Package_Id");
        productName = intent.getStringExtra("Package_Name");
        productPrice = intent.getStringExtra("Package_Price");

//        Bundle id = new Bundle();
//        id.putString("package_id", productId);

        btnCart = findViewById(R.id.btnCart);
        package_name = findViewById(R.id.package_name);
        package_price = findViewById(R.id.package_price);
        package_image = findViewById(R.id.img_package);
        chkBreakfast = findViewById(R.id.chkBreakfast);
        chkLunch = findViewById(R.id.chkLunch);
        chkDinner = findViewById(R.id.chkDinner);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        collapsingToolbarLayout.setTitle("Select Package");

        Typeface face = Typeface.createFromAsset(getAssets(), "font/CaviarDreams.ttf");

        //set typeface to collapsing toolbar title
        collapsingToolbarLayout.setCollapsedTitleTypeface(face);
        package_name.setTypeface(face);
        package_price.setTypeface(face);

        package_name.setText(productName);
        package_price.setText(productPrice);

        SharedPreferences.Editor price = getApplicationContext().getSharedPreferences("ORDER_PRICE", MODE_PRIVATE).edit();
        price.putString("ORDER_PRICE", productPrice);
        price.apply();

        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //adding fragments
        adapter.AddFragment(new FragmentBreakfast(), "Breakfast");
        adapter.AddFragment(new FragmentLunch(), "Lunch");
        adapter.AddFragment(new FragmentDinner(), "Dinner");

        //adapter setp
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        getCheckBoxValue();

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedBox!=null) {
                    i = new Intent(PackageDetailsActivity.this, CartActivity.class);
                    i.putExtra("PACKAGE_NAME", productName); // package name lai arko activity ma pass garney
                    i.putStringArrayListExtra("checked_boxes", checkedBox); // check box ko value cart activity ma array list ma halera lageko
                    startActivity(i);
                    //Toast.makeText(getApplicationContext(), "Cart Clicked", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void getCheckBoxValue() {

        chkBreakfast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkBreakfast.isChecked()) {
                    checkedBox.add(chkBreakfast.getText().toString());
                }
                else {
                    checkedBox.remove(chkBreakfast.getText().toString());
                }
            }
        });

        chkLunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkLunch.isChecked()) {
                    checkedBox.add(chkLunch.getText().toString());
                }
                else {
                    checkedBox.remove(chkLunch.getText().toString());
                }
            }
        });

        chkDinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkDinner.isChecked()) {
                    checkedBox.add(chkDinner.getText().toString());
                }
                else {
                    checkedBox.remove(chkDinner.getText().toString());
                }
            }
        });

    }
}
