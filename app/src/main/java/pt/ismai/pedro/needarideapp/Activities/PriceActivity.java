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

        priceText = findViewById(R.id.priceText);
        button = findViewById(R.id.button);
        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);
        routeText = findViewById(R.id.routeText);
        numberOfPrice = Integer.parseInt(priceText.getText().toString());

        Bundle extra = getIntent().getExtras();
        String whereToValue = extra.getString("whereToVal");
        String whereFromValue = extra.getString("whereFromVal");
        String viagemData = extra.getString("dataDeViagem");
        String inicio = extra.getString("inicioViagem");
        String fim = extra.getString("fimViagem");
        String seats = extra.getString("seats");

        routeText.setText(whereFromValue + " >> " + whereToValue);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _stringVal;

                if (numberOfPrice < 50){

                    numberOfPrice = numberOfPrice + 1;
                    _stringVal = String.valueOf(numberOfPrice + "€");
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
                    _stringVal = String.valueOf(numberOfPrice + "€");
                    priceText.setText(_stringVal);
                }

            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                price = priceText.getText().toString();

                ride = new ParseObject("Ride");

                ride.put("from",whereFromValue);
                ride.put("to",whereToValue);
                ride.put("data",viagemData);
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
                            executeActivity(UserActivity.class);
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

        Intent intent = new Intent(this,subActivity);
        intent.putExtra("objectId", ParseUser.getCurrentUser().getObjectId());
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent sendIntent = new Intent(this, PriceActivity.class);
        startActivity(sendIntent);
        return true;
    }



}
