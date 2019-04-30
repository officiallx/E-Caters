package com.obnoxious.ecatering.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.User;
import com.obnoxious.ecatering.services.EventTimeService;
import com.obnoxious.ecatering.services.LoginService;
import com.obnoxious.ecatering.utils.RetrofitBuilder;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin;
    User user = new User();
    int responseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.setUsername(txtUsername.getText().toString());
                user.setPassword(txtPassword.getText().toString());

                Call<Void> call = RetrofitBuilder
                        .getInstance()
                        .loginService()
                        .login(user);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        responseCode = response.code();
                        if (responseCode == 200) {
                            startNewActivity();
                            Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Incorrect Credential", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(LoginActivity.this,"Cannot find username or password",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void startNewActivity(){
        Intent i = new Intent(LoginActivity.this,EventActivity.class);
        startActivity(i);
    }
}
