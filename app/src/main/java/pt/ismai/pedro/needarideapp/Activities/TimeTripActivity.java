package pt.ismai.pedro.needarideapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import pt.ismai.pedro.needarideapp.R;

public class TimeTripActivity extends AppCompatActivity {

    TimePicker starTimePicker, endTimePicker;
    Button button;
    TextView textView;
    Integer hourS;
    private int minuteS;
    private int hourE;
    private int minuteE;
    String startTime , endTime;

    private void executeActivity(Class<?> subActivity){

        Bundle extra = getIntent().getExtras();
        String whereToValue = extra.getString("whereToValor");
        String whereFromValue = extra.getString("whereFromValor");
        String viagemData = extra.getString("dataDeViagem");

        Intent sendIntent = new Intent(this,subActivity);
        sendIntent.putExtra("whereToVal",whereToValue);
        sendIntent.putExtra("whereFromVal",whereFromValue );
        sendIntent.putExtra("dataDeViagem",viagemData);
        sendIntent.putExtra("inicioViagem",startTime);
        sendIntent.putExtra("fimViagem",endTime);
        startActivity(sendIntent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_trip);

        //SETTING TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        starTimePicker = findViewById(R.id.startTimePicker);
        endTimePicker = findViewById(R.id.endTimePicker);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        Calendar c = Calendar.getInstance();
        hourS = c.get(Calendar.HOUR);
        minuteS = c.get(Calendar.MINUTE);

        hourE = c.get(Calendar.HOUR);
        minuteE = c.get(Calendar.MINUTE);
        starTimePicker.setIs24HourView(true);
        endTimePicker.setIs24HourView(true);

        starTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

               TimeTripActivity.this.hourS = hourOfDay;
               TimeTripActivity.this.minuteS = minute;

            }
        });

        endTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                TimeTripActivity.this.hourE = hourOfDay;
                TimeTripActivity.this.minuteE = minute;

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startTime = String.valueOf(hourS) + ":" + String.valueOf(minuteS);
                endTime = String.valueOf(hourE) + ":" + String.valueOf(minuteE);

                executeActivity(SeatsActivity.class);
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        executeActivity(TripDataActivity.class);
        return true;
    }

}
