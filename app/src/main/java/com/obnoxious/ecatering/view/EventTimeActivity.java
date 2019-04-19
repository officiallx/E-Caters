    package com.obnoxious.ecatering.view;

    import android.app.DatePickerDialog;
    import android.app.TimePickerDialog;
    import android.content.Intent;
    import android.graphics.Typeface;
    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.DatePicker;
    import android.widget.EditText;
    import android.widget.NumberPicker;
    import android.widget.TextView;
    import android.widget.TimePicker;
    import android.widget.Toast;

    import com.obnoxious.ecatering.R;
    import com.obnoxious.ecatering.adapters.HomeAdapter;
    import com.obnoxious.ecatering.models.Event;
    import com.obnoxious.ecatering.services.EventService;

    import java.util.Calendar;
    import java.util.List;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;
    import retrofit2.Retrofit;
    import retrofit2.converter.gson.GsonConverterFactory;

    public class EventTimeActivity extends AppCompatActivity {

        //List<Event> events;
        Event event = new Event();
        //private final String baseUrl = "http://192.168.100.24:8080/";
        //EventTime eventTime = new EventTime();
        TextView txtDate, txtTime, txtDateChoose, txtTimeChoose, txt_head;
        Calendar c;
        DatePickerDialog datePickerDialog;
        TimePickerDialog timePickerDialog;
        String format;
        Button btn_wedding_next;
        EditText guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding);

        txt_head = findViewById(R.id.txt_head);

        txtDate = findViewById(R.id.txtDate);
        txtDateChoose = findViewById(R.id.txtDateChoose);
        txtTime = findViewById(R.id.txtTime);
        txtTimeChoose = findViewById(R.id.txtTimeChoose);

        guest = findViewById(R.id.editText3);

        Intent intent = getIntent();
        final String result = intent.getStringExtra("EXTRA_MESSAGE");

        txt_head.setText("When is the " + result);

        Typeface face = Typeface.createFromAsset(getAssets(), "font/CaviarDreams.ttf");
        txt_head.setTypeface(face);
        guest.setTypeface(face);

        //String menuName = menu.getMenuName();
        //txt_head.setText("When is the " + menuName);

        txtDateChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                final int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(EventTimeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        txtDate.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                        //eventTime.setEventDate(mDay + "/" + (mMonth + 1) + "/" + mYear);
                    }
                },day,month,year);
                datePickerDialog.show();
            }
        });

        txtTimeChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                final int hour = c.get(Calendar.DAY_OF_MONTH);
                int minute = c.get(Calendar.MONTH);

                timePickerDialog = new TimePickerDialog(EventTimeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int mHour, int mMinute) {
                        selectedTimeFormat(hour);
                        txtTime.setText(mHour + ":" + mMinute + " " + format);
                        //eventTime.setEventTime(mHour + ":" + mMinute + " " + format);
                    }
                },hour,minute, true);
                timePickerDialog.show();
            }
        });

        btn_wedding_next = findViewById(R.id.btn_wedding_next);
        btn_wedding_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(EventTimeActivity.this, EventDashboardActivity.class);
                i.putExtra("EXTRA_MESSAGE",result);
                startActivity(i);
                //Toast.makeText(getApplicationContext(),"Save Successful", Toast.LENGTH_SHORT).show();

                //saveEventTime(); // EventTime ko id null bhayera save ta huncha tara error aairacha.
            }
        });
    }

    public void selectedTimeFormat(int hour){
        if(hour == 0){
            hour+=12;
            format = "AM";
        }
        else if (hour == 12){
            format = "PM";
        }
        else if(hour > 12){
            hour-=12;
            format = "PM";
        }
        else{
            format = "AM";
        }
    }

//        public void saveEventTime(){
//            int i = 20;
//            i++;
//            eventTime.setEventId(i);
//            eventTime.getEventDate().toString();
//            eventTime.getEventTime().toString();
//
//            final Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(baseUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            final EventTimeService eventTimeService = retrofit.create(EventTimeService.class);
//            Call<EventTime> lists = eventTimeService.saveEventTime(eventTime);
//            lists.enqueue(new Callback<EventTime>() {
//                @Override
//                public void onResponse(Call<EventTime> call, Response<EventTime> response) {
//                    if (response.isSuccessful()) {
//                        Intent i = new Intent(EventTimeActivity.this, EventDashboardActivity.class);
//                        startActivity(i);
//                        Toast.makeText(getApplicationContext(),"Save Successful", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<EventTime> call, Throwable t) {
//                    Log.d("Menu", "onFailure: "+t.getMessage());
//                    Toast.makeText(getApplicationContext(),"Check your internet", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
}
