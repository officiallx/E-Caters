package com.obnoxious.ecatering.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.User;
import com.obnoxious.ecatering.utils.RetrofitBuilder;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin, btnNewRegister;
    User user = new User();
    int responseCode;
    String userId, username;
    SharedPreferences sp;
    LinearLayout loginLinear;

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
                login();

            }
        });

        btnNewRegister = findViewById(R.id.btnNewRegister);
        btnNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }


    public void login() {

        username = txtUsername.getText().toString();
        user.setUsername(username);
        user.setPassword(txtPassword.getText().toString());

        getUserByUsername();

        Call<Void> call = RetrofitBuilder
                .getInstance()
                .loginService()
                .login(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseCode = response.code();
                if (response.isSuccessful()) {

                    Headers headers = response.headers();
                    headers.toString();
                    Log.d("username", "login ko token: " + headers.get("Authorization"));


                    if (userId != null) {
                        startNewActivity();
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect Credential", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Cannot find username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startNewActivity() {
        Intent i = new Intent(LoginActivity.this, EventActivity.class);
        userId = Integer.toString(user.getId());
        i.putExtra("USER_ID", userId);
        startActivity(i);
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
                userId = Integer.toString(user.getId());

                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("USER_ID", MODE_PRIVATE).edit();
                editor.putString("USER_ID", userId);
                editor.apply();

                Log.d("username", "login ko id: " + userId);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}
