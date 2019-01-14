package pt.ismai.pedro.needarideapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import pt.ismai.pedro.needarideapp.R;

public class PriceActivity extends AppCompatActivity {

    //Vars
    TextView priceText, routeText;
    Button button;
    ParseObject ride;
    String price, rideFromAddress,rideFromCity,rideToAddress,rideToCity,dataInicioViagem,dataFimViagem,inicio,fim,seats;
    ImageView add, remove;
    Integer numberOfPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        //SETTING TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        priceText = findViewById(R.id.priceText);
        button = findViewById(R.id.button);
        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);
        routeText = findViewById(R.id.routeText);
        numberOfPrice = Integer.parseInt(priceText.getText().toString());

        getExtras();

        routeText.setText(rideFromAddress + " >> " + rideToAddress);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _stringVal;

                if (numberOfPrice < 50){

                    numberOfPrice = numberOfPrice + 1;
                    _stringVal = String.valueOf(numberOfPrice);
                    priceText.setText(_stringVal);

                }

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _stringVal;

                if (numberOfPrice > 10){

                    numberOfPrice = numberOfPrice - 1;
                    _stringVal = String.valueOf(numberOfPrice);
                    priceText.setText(_stringVal);
                }

            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                price = priceText.getText().toString();

                ride = new ParseObject("Ride");

                ride.put("from_address",rideFromAddress);
                ride.put("to_address",rideToAddress);
                ride.put("from_city",rideFromCity);
                ride.put("to_city",rideToCity);
                ride.put("data",dataInicioViagem);

                if (dataFimViagem == null){

                    ride.put("data_fim","");
                }
                else{
                    ride.put("data_fim",dataFimViagem);
                }

                ride.put("start",inicio);
                ride.put("end",fim);
                ride.put("seat",seats);
                ride.put("price",price);
                ride.put("user_id",ParseUser.getCurrentUser());

                ride.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null){

                            FancyToast.makeText(PriceActivity.this,"Viagem Registada",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                            Intent intent = new Intent(getApplicationContext(),UserActivity.class);
                            intent.putExtra("objectId", ParseUser.getCurrentUser().getObjectId());
                            startActivity(intent);
                            finish();
                        }

                        else{

                            FancyToast.makeText(PriceActivity.this,"Algo correu mal.\n Tente Novamente",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                    }
                });
            }
        });


    }

    private void getExtras(){

        Bundle extra = getIntent().getExtras();
        rideFromAddress = extra.getString("rideFromAddress");
        rideFromCity = extra.getString("rideFromCity");
        rideToAddress = extra.getString("rideToAddress");
        rideToCity = extra.getString("rideToCity");
        dataInicioViagem = extra.getString("dataInicioViagem");
        dataFimViagem = extra.getString("dataFimViagem");
        inicio = extra.getString("inicioViagem");
        fim = extra.getString("fimViagem");
        seats = extra.getString("seats");


    }

    private void executeActivity(Class<?> subActivity){

        Intent sendIntent = new Intent(this,subActivity);
        sendIntent.putExtra("rideFromAddress",rideFromAddress);
        sendIntent.putExtra("rideFromCity",rideFromCity);
        sendIntent.putExtra("rideToAddress",rideToAddress);
        sendIntent.putExtra("rideToCity",rideToCity);
        sendIntent.putExtra("dataInicioViagem",dataInicioViagem);
        sendIntent.putExtra("dataFimViagem",dataFimViagem);
        sendIntent.putExtra("inicioViagem",inicio);
        sendIntent.putExtra("fimViagem",fim);
        sendIntent.putExtra("lugares",seats);
        startActivity(sendIntent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        executeActivity(SeatsActivity.class);
        return true;
    }

}
