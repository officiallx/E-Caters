package com.obnoxious.ecatering.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.fragments.HomeFragment;
import com.obnoxious.ecatering.fragments.OrderFragment;
import com.obnoxious.ecatering.fragments.SettingFragment;
import com.obnoxious.ecatering.ui.NoConnectionActivity;

public class EventActivity extends AppCompatActivity {

    ImageView imageView;
    String user_id;
    Bundle bundle;
    Intent intent;

    boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
/*                    bundle = new Bundle();
                    bundle.putString("USER_ID", intent.getStringExtra("user_id"));
                    Log.d("username", "onNavigationItemSelected: "+bundle.toString());*/
                    fragment.setArguments(bundle);
                    break;
                case R.id.navigation_dashboard:
                    fragment = new OrderFragment();
                    break;
                case R.id.navigation_notifications:
                    fragment = new SettingFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("USER_ID");
        Log.d("username", "event activity ko id: "+user_id);

        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("USER_ID", MODE_PRIVATE).edit();
        editor.putString("USER_ORDER_ID", user_id);
        editor.apply();

        //checks if there is internet connection or not if not it throws the alert dialog
        if (isNetworkConnectionAvailable()) {
            //loading the default fragment
            loadFragment(new HomeFragment());
        }
        else{
            startNoInternetActivity();
            //checkNetworkConnection();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    //checks if internet connection is available or not
    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(EventActivity.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            //connection is avlilable
            return true;
        }else{
            //no connection
            //Toast toast = Toast.makeText(c, "No Internet Connection",
            //       Toast.LENGTH_LONG);
            //toast.show();
            return false;
        }
    }

    public void startNoInternetActivity(){

        Intent i = new Intent(EventActivity.this, NoConnectionActivity.class);
        startActivity(i);
    }
}
