package pt.ismai.pedro.needarideapp.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


import pt.ismai.pedro.needarideapp.R;

public class TripDataActivity extends AppCompatActivity {

    TextView textView;
    DateRangeCalendarView calendar;
    Button button;
    String dataInicioViagem;
    String dataFimViagem;
    StringBuilder tripMessage = new StringBuilder("Data da viagem:");


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
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        textView = findViewById(R.id.textView);
        calendar = findViewById(R.id.calendar);
        button = findViewById(R.id.button);

        Bundle extra = getIntent().getExtras();
        String rideFromAddress = extra.getString("rideFromAddress");
        String rideFromCity = extra.getString("rideFromCity");
        String rideToAddress = extra.getString("rideToAddress");
        String rideToCity = extra.getString("rideToCity");

        Toast.makeText(this, rideFromAddress + rideFromCity + rideToAddress + rideToCity, Toast.LENGTH_LONG).show();

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

                dataInicioViagem = day + "-" + month + "-" + year;


            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {

                String day;
                String endDay;
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd",Locale.getDefault());

                String month;
                String endMonth;
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM",Locale.getDefault());

                String year;
                String endYear;
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.getDefault());

                day = dayFormat.format(startDate.getTime());
                month = monthFormat.format(startDate.getTime());
                year = yearFormat.format(startDate.getTime());

                endDay = dayFormat.format(endDate.getTime());
                endMonth = monthFormat.format(endDate.getTime());
                endYear = yearFormat.format(endDate.getTime());

                if (endDay == null && endMonth == null && endYear == null){

                    dataInicioViagem = day + "-" + month + "-" + year;
                }else{

                    dataFimViagem = endDay + "-" + endMonth + "-" + endYear;

                }

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
        String rideFromAddress = extra.getString("rideFromAddress");
        String rideFromCity = extra.getString("rideFromCity");
        String rideToAddress = extra.getString("rideToAddress");
        String rideToCity = extra.getString("rideToCity");

        Intent sendIntent = new Intent(this,subActivity);
        sendIntent.putExtra("rideFromAddress",rideFromAddress);
        sendIntent.putExtra("rideFromCity",rideFromCity);
        sendIntent.putExtra("rideToAddress",rideToAddress);
        sendIntent.putExtra("rideToCity",rideToCity);
        sendIntent.putExtra("dataInicioViagem", dataInicioViagem);
        sendIntent.putExtra("dataFimViagem", dataFimViagem);
        startActivity(sendIntent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        executeActivity(RideFromActivity.class);
        return true;
    }

}
