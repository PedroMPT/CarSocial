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

    TextView priceText, routeText;
    Button button;

    ParseObject ride;
    String price;
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

        Bundle extra = getIntent().getExtras();
        String rideFromAddress = extra.getString("rideFromAddress");
        String rideFromCity = extra.getString("rideFromCity");
        String rideToAddress = extra.getString("rideToAddress");
        String rideToCity = extra.getString("rideToCity");
        String dataInicioViagem = extra.getString("dataInicioViagem");
        String dataFimViagem = extra.getString("dataFimViagem");
        String inicio = extra.getString("inicioViagem");
        String fim = extra.getString("fimViagem");
        String seats = extra.getString("seats");

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

    private void executeActivity(Class<?> subActivity){

        Bundle extra = getIntent().getExtras();
        String rideFromAddress = extra.getString("rideFromAddress");
        String rideFromCity = extra.getString("rideFromCity");
        String rideToAddress = extra.getString("rideToAddress");
        String rideToCity = extra.getString("rideToCity");
        String dataInicioViagem = extra.getString("dataInicioViagem");
        String dataFimViagem = extra.getString("dataFimViagem");
        String inicio = extra.getString("inicioViagem");
        String fim = extra.getString("fimViagem");
        String seats = extra.getString("seats");

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

    private String splitToGetCity(String city){

        String result;
        String[] parts = city.split(",");

        result = parts[1];
        return result;
    }

}
