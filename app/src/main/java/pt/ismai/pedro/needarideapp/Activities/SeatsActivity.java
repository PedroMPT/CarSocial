package pt.ismai.pedro.needarideapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pt.ismai.pedro.needarideapp.R;

public class SeatsActivity extends AppCompatActivity {

    TextView seatsText;
    Button button;
    ImageView add, remove;
    String seats;
    Integer numberOfSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats);

        //SETTING TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        seatsText = findViewById(R.id.seatsText);
        button = findViewById(R.id.button);
        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);

        numberOfSeats = Integer.parseInt(seatsText.getText().toString());

        Bundle extra = getIntent().getExtras();
        String whereToValue = extra.getString("whereToVal");
        String whereFromValue = extra.getString("whereFromVal");
        String viagemData = extra.getString("dataDeViagem");
        String inicio = extra.getString("inicioViagem");
        String fim = extra.getString("fimViagem");


       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String _stringVal;

               if (numberOfSeats < 4){

                   numberOfSeats = numberOfSeats+ 1;
                   _stringVal = String.valueOf(numberOfSeats);
                   seatsText.setText(_stringVal);

               }

           }
       });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _stringVal;

                if (numberOfSeats > 1){

                    numberOfSeats = numberOfSeats - 1;
                    _stringVal = String.valueOf(numberOfSeats);
                    seatsText.setText(_stringVal);
                }

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                executeActivity(PriceActivity.class);


            }
        });


        Toast.makeText(this, whereFromValue + " - " + whereToValue + " - " + viagemData + " - " + inicio + " - " + fim + "!", Toast.LENGTH_LONG).show();

    }

    private void executeActivity(Class<?> subActivity) {

        Bundle extra = getIntent().getExtras();
        String whereToValue = extra.getString("whereToVal");
        String whereFromValue = extra.getString("whereFromVal");
        String viagemData = extra.getString("dataDeViagem");
        String inicio = extra.getString("inicioViagem");
        String fim = extra.getString("fimViagem");
        seats = seatsText.getText().toString();

        Intent sendIntent = new Intent(this, subActivity);
        sendIntent.putExtra("whereToVal", whereToValue);
        sendIntent.putExtra("whereFromVal", whereFromValue);
        sendIntent.putExtra("dataDeViagem", viagemData);
        sendIntent.putExtra("inicioViagem", inicio);
        sendIntent.putExtra("fimViagem", fim);
        sendIntent.putExtra("seats", seats);
        startActivity(sendIntent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        executeActivity(TimeTripActivity.class);
        return true;
    }


}
