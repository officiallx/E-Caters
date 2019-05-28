package com.obnoxious.ecatering.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.User;
import com.obnoxious.ecatering.utils.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText txtName, txtContact, txtUsername, txtPassword;
    Button btnRegister;
    User user = new User();
    String name, con, username, password;
    Long contact = 0L;
    TextView txt_toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Typeface face = Typeface.createFromAsset(getAssets(), "font/CaviarDreams.ttf");
        txt_toolbar_title = findViewById(R.id.toolbar_title);
        txt_toolbar_title.setText("Register");
        txt_toolbar_title.setTypeface(face);

        txtName = findViewById(R.id.txtName);
        txtContact = findViewById(R.id.txtContact);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = txtName.getText().toString();
                con = txtContact.getText().toString();
                contact = Long.valueOf(con);
                username = txtUsername.getText().toString();
                password = txtPassword.getText().toString();
                user.setName(name);
                user.setContact(contact);
                user.setUsername(username);
                user.setPassword(password);

                if (name != null && !name.isEmpty() && username != null && !username.isEmpty() && con!=null && contact!=0) {

                    register();

                }
                else {
                    Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void register(){
        Call<Void> call = RetrofitBuilder
                .getInstance()
                .registerService()
                .register(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                    Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
