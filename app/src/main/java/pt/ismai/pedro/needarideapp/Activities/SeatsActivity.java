package pt.ismai.pedro.needarideapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

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


    }

    private void executeActivity(Class<?> subActivity) {

        Bundle extra = getIntent().getExtras();
        String rideFromAddress = extra.getString("rideFromAddress");
        String rideFromCity = extra.getString("rideFromCity");
        String rideToAddress = extra.getString("rideToAddress");
        String rideToCity = extra.getString("rideToCity");
        String dataInicioViagem = extra.getString("dataInicioViagem");
        String dataFimViagem = extra.getString("dataFimViagem");
        String inicio = extra.getString("inicioViagem");
        String fim = extra.getString("fimViagem");
        seats = seatsText.getText().toString();

        Intent sendIntent = new Intent(this, subActivity);
        sendIntent.putExtra("rideFromAddress",rideFromAddress);
        sendIntent.putExtra("rideFromCity",rideFromCity);
        sendIntent.putExtra("rideToAddress",rideToAddress);
        sendIntent.putExtra("rideToCity",rideToCity);
        sendIntent.putExtra("dataInicioViagem",dataInicioViagem);
        sendIntent.putExtra("dataFimViagem",dataFimViagem);
        sendIntent.putExtra("inicioViagem",inicio);
        sendIntent.putExtra("fimViagem",fim);
        sendIntent.putExtra("seats", seats);
        startActivity(sendIntent);
        finish();

        FancyToast.makeText(SeatsActivity.this,rideFromAddress + " - "
                + rideFromAddress + " - "
                + rideFromCity + " - "
                + rideToAddress + " - "
                + rideToCity + " - "
                + dataInicioViagem + " - "
                + dataFimViagem + " - "
                + inicio + " - "
                + fim + "!",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        executeActivity(TimeTripActivity.class);
        return true;
    }


}
