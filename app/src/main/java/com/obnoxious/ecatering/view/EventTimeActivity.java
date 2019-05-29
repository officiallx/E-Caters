package com.obnoxious.ecatering.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.EventTime;
import com.obnoxious.ecatering.services.EventTimeService;
import com.obnoxious.ecatering.utils.RetrofitBuilder;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventTimeActivity extends AppCompatActivity {

    TextView txtDate, txtTime, txtDateChoose, txtTimeChoose, txt_head;
    Calendar c;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    String format, userId;
    Button btn_wedding_next;
    EditText guest;
    String result, event_date, event_time, guest_count, position, eventdateId, tok;
    int event_dat_id;
    EventTime datetime = new EventTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_time);

        c = Calendar.getInstance();

        txt_head = findViewById(R.id.txt_head);

        txtDate = findViewById(R.id.txtDate);
        txtDateChoose = findViewById(R.id.txtDateChoose);
        txtTime = findViewById(R.id.txtTime);
        txtTimeChoose = findViewById(R.id.txtTimeChoose);

        guest = findViewById(R.id.editText3);

        Intent intent = getIntent();
        result = intent.getStringExtra("EXTRA_MESSAGE");
        position = intent.getStringExtra("POSITION");
        userId = intent.getStringExtra("USER_ID");


        txt_head.setText("When is the " + result);

        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("EVENT_NAME", MODE_PRIVATE).edit();
        editor.putString("SELECTED_EVENT_NAME", result);
        editor.apply();

        SharedPreferences use_token = this.getSharedPreferences("USER_TOKEN", MODE_PRIVATE);
        tok = use_token.getString("USER_TOKEN", null);

        Typeface face = Typeface.createFromAsset(getAssets(), "font/CaviarDreams.ttf");
        txt_head.setTypeface(face);
        guest.setTypeface(face);

        //String menuName = menu.getMenuName();
        //txt_head.setText("When is the " + menuName);

        txtDateChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int year = c.get(Calendar.YEAR);
                final int month = c.get(Calendar.MONTH);
                final int day = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(EventTimeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        txtDate.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                        CharSequence date = txtDate.getText();
                        event_date = String.valueOf(date);
                        datetime.setEventDate(event_date);
                    }
                }, day, month, year);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // get system current date
                datePickerDialog.show();
            }
        });

        txtTimeChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int hour = c.get(Calendar.DAY_OF_MONTH);
                int minute = c.get(Calendar.MONTH);

                timePickerDialog = new TimePickerDialog(EventTimeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int mHour, int mMinute) {
                        selectedTimeFormat(hour);
                        txtTime.setText(mHour + ":" + mMinute + " " + format);
                        CharSequence time = txtTime.getText();
                        event_time = String.valueOf(time);
                        datetime.setEventTime(event_time);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        //set guest count to eventtime
        guest_count = (guest.getText().toString());
        datetime.setGuestCount(guest_count);

        btn_wedding_next = findViewById(R.id.btn_wedding_next);
        btn_wedding_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                guest_count = (guest.getText().toString());
                datetime.setGuestCount(guest.getText().toString());

                SharedPreferences.Editor guest = getApplicationContext().getSharedPreferences("GUEST_COUNT", MODE_PRIVATE).edit();
                guest.putString("GUEST_COUNT", guest_count);
                guest.apply();

                try {
                    if (event_date != null && event_time != null && guest_count != null && Integer.valueOf(guest_count) > 0) {

                        saveEventTime();

                    } else {
                        Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e) {
                } catch (NumberFormatException ex) {
                    Toast.makeText(getApplicationContext(), "Guest Count must be greater than 0 peoples", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void selectedTimeFormat(int hour) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
    }

    public void saveEventTime() {

        Call<EventTime> eventTimeCall = RetrofitBuilder
                .getInstance()
                .eventTimeService()
                .postEventTime(datetime,tok);
        eventTimeCall.enqueue(new Callback<EventTime>() {
            @Override
            public void onResponse(Call<EventTime> call, Response<EventTime> response) {

                if (response.isSuccessful()) {
                    datetime = response.body();
                    event_dat_id = response.body().getEventId();
                    eventdateId = Integer.toString(event_dat_id);

                    SharedPreferences.Editor eventDatId = getApplicationContext().getSharedPreferences("EVENT_DATE_TIME", MODE_PRIVATE).edit();
                    eventDatId.putString("SELECTED_EVENT_TIME", eventdateId);
                    eventDatId.apply();

                    //Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                    openNewActivity();
                }
            }

            @Override
            public void onFailure(Call<EventTime> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openNewActivity() {

        if (result.contains("Wedding")) {
            Intent i = new Intent(EventTimeActivity.this, PackageActivity.class);
            i.putExtra("EXTRA_MESSAGE", result);
            i.putExtra("POSITION",position);
            startActivity(i);
            //imageView4.setImageResource(R.drawable.birthday);
        } else if (result.contains("Birthday")) {
            Intent i = new Intent(EventTimeActivity.this, PackageActivity.class);
            i.putExtra("EXTRA_MESSAGE", result);
            i.putExtra("POSITION",position);
            startActivity(i);
            //imageView4.setImageResource(R.drawable.birthday);
        } else if (result.contains("Party")) {
            Intent i = new Intent(EventTimeActivity.this, PackageActivity.class);
            i.putExtra("EXTRA_MESSAGE", result);
            i.putExtra("POSITION",position);
            startActivity(i);
            //imageView4.setImageResource(R.drawable.gathering);
        } else if (result.contains("Funeral")) {
            Intent i = new Intent(EventTimeActivity.this, PackageActivity.class);
            i.putExtra("EXTRA_MESSAGE", result);
            i.putExtra("POSITION",position);
            startActivity(i);
            //imageView4.setImageResource(R.drawable.funeral);
        } else if (result.contains("Baby Shower")) {
            Intent i = new Intent(EventTimeActivity.this, PackageActivity.class);
            i.putExtra("EXTRA_MESSAGE", result);
            i.putExtra("POSITION",position);
            startActivity(i);
            //imageView4.setImageResource(R.drawable.funeral);
        } else if (result.contains("Gathering")) {
            Intent i = new Intent(EventTimeActivity.this, PackageActivity.class);
            i.putExtra("EXTRA_MESSAGE", result);
            i.putExtra("POSITION",position);
            startActivity(i);
            //imageView4.setImageResource(R.drawable.funeral);
        } else if (result.contains("Festival")) {
            Intent i = new Intent(EventTimeActivity.this, PackageActivity.class);
            i.putExtra("EXTRA_MESSAGE", result);
            i.putExtra("POSITION",position);
            startActivity(i);
            //imageView4.setImageResource(R.drawable.funeral);
        } else if (result.contains("Business")) {
            Intent i = new Intent(EventTimeActivity.this, PackageActivity.class);
            i.putExtra("EXTRA_MESSAGE", result);
            i.putExtra("POSITION",position);
            startActivity(i);
            //imageView4.setImageResource(R.drawable.funeral);
        } else if (result.contains("Other")) {
            Intent i = new Intent(EventTimeActivity.this, PackageActivity.class);
            i.putExtra("EXTRA_MESSAGE", result);
            i.putExtra("POSITION",position);
            startActivity(i);
            //imageView4.setImageResource(R.drawable.funeral);
        }
    }
}
