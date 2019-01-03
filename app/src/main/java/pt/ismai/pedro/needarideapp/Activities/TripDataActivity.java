package pt.ismai.pedro.needarideapp.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


import pt.ismai.pedro.needarideapp.R;

public class TripDataActivity extends AppCompatActivity {

    TextView textView;
    DateRangeCalendarView calendar;
    Button button;
    String dataViagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale locale = new Locale("pt", "PT");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_trip_data);

        //SETTING TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textView = findViewById(R.id.textView);
        calendar = findViewById(R.id.calendar);
        button = findViewById(R.id.button);

        calendar.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {

                String day;
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd",Locale.getDefault());

                String month;
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM",Locale.getDefault());

                String year;
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.getDefault());

                day = dayFormat.format(startDate.getTime());
                month = monthFormat.format(startDate.getTime());
                year = yearFormat.format(startDate.getTime());

                dataViagem = day + "-" + month + "-" + year;

                textView.setText(day + "-" + month + "-" + year);

            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {

                textView.setText(startDate.getTime().toString() + endDate.getTime().toString());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                executeActivity(TimeTripActivity.class);
            }
        });


    }
    private void executeActivity(Class<?> subActivity){

        Bundle extra = getIntent().getExtras();
        String whereToValue = extra.getString("whereToVal");
        String whereFromValue = extra.getString("whereFrom");

        Intent sendIntent = new Intent(this,subActivity);
        sendIntent.putExtra("whereToValor",whereToValue);
        sendIntent.putExtra("whereFromValor",whereFromValue);
        sendIntent.putExtra("dataDeViagem",dataViagem);
        startActivity(sendIntent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        executeActivity(FromActivity.class);
        return true;
    }

}
