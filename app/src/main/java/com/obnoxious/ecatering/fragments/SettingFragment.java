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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.User;
import com.obnoxious.ecatering.utils.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Bleeding Rain on 4/17/2019.
 */

public class SettingFragment extends Fragment {

    Context c;
    TextView txt_toolbar_title;
    EditText txtName, txtContact, txtUsername, txtPassword;
    String username, userName, name, password, contact;
    Long cont;
    User user = new User();
    Button btnProfile;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null);

        c = getActivity();

        SharedPreferences prefs = c.getSharedPreferences("USERNAME", MODE_PRIVATE);
        username = prefs.getString("USERNAME", null);
        Log.d("username", "username setting ko : "+username);

        Typeface face = Typeface.createFromAsset(c.getAssets(), "font/CaviarDreams.ttf");

        txt_toolbar_title = view.findViewById(R.id.toolbar_title);
        txt_toolbar_title.setText("Settings");
        txt_toolbar_title.setTypeface(face);

        txtName = view.findViewById(R.id.txtProfileName);
        txtContact = view.findViewById(R.id.txtProfileContact);
        txtUsername = view.findViewById(R.id.txtProfileUsername);
        txtPassword = view.findViewById(R.id.txtProfilePassword);
        btnProfile = view.findViewById(R.id.btnProfileUpdate);

        txtPassword.setEnabled(false);
        txtUsername.setEnabled(false);
        txtContact.setEnabled(false);
        txtName.setEnabled(false);

        getUserByUsername();

        return view;
    }

    public void getUserByUsername() {

        Call<User> listCall = RetrofitBuilder
                .getInstance()
                .loginService()
                .getUserbyUsername(username);
        listCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                userName = user.getUsername();
                name = user.getName();
                cont = user.getContact();
                contact = Long.toString(cont);
                password = user.getPassword();
                feedData();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    void feedData(){
        txtName.setText(name);
        txtContact.setText(contact);
        txtUsername.setText(userName);
        txtPassword.setText(password);
    }
}
