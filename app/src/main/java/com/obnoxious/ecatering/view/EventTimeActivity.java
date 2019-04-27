package com.obnoxious.ecatering.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventTimeActivity extends AppCompatActivity {

    private final String baseUrl = "http://192.168.100.24:8080/";
    TextView txtDate, txtTime, txtDateChoose, txtTimeChoose, txt_head;
    Calendar c;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    String format;
    Button btn_wedding_next;
    EditText guest;
    String result, event_date, event_time, guest_count, position;
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

        txt_head.setText("When is the " + result);

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
        datetime.setGuest_count(guest_count);

        btn_wedding_next = findViewById(R.id.btn_wedding_next);
        btn_wedding_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                guest_count = (guest.getText().toString());
                datetime.setGuest_count(guest.getText().toString());

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final EventTimeService eventTimeService = retrofit.create(EventTimeService.class);
        Call<Void> eventTimeCall = eventTimeService.postEventTime(datetime);
        eventTimeCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                openNewActivity();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openNewActivity() {

        if (result.contains("Wedding")) {
            Intent i = new Intent(EventTimeActivity.this, WeddingPackageActivity.class);
            i.putExtra("EXTRA_MESSAGE", result);
            startActivity(i);
            //imageView4.setImageResource(R.drawable.birthday);
        } else if (result.contains("Birthday")) {
            Intent i = new Intent(EventTimeActivity.this, BirthdayPackageActivity.class);
            i.putExtra("EXTRA_MESSAGE", result);
            startActivity(i);
            //imageView4.setImageResource(R.drawable.birthday);
        } else if (result.contains("Party")) {
            Intent i = new Intent(EventTimeActivity.this, PartyPackageActivity.class);
            i.putExtra("EXTRA_MESSAGE", result);
            startActivity(i);
            //imageView4.setImageResource(R.drawable.gathering);
        } else if (result.contains("Funeral")) {
            Intent i = new Intent(EventTimeActivity.this, WeddingPackageActivity.class);
            i.putExtra("EXTRA_MESSAGE", result);
            startActivity(i);
            //imageView4.setImageResource(R.drawable.funeral);
        }
    }
}
