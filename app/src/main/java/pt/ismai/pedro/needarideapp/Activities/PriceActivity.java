package pt.ismai.pedro.needarideapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import pt.ismai.pedro.needarideapp.R;

public class PriceActivity extends AppCompatActivity {

    EditText priceText;
    Button button;

    ParseObject ride;
    String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        priceText = findViewById(R.id.priceText);
        button = findViewById(R.id.button);

        Bundle extra = getIntent().getExtras();
        String whereToValue = extra.getString("whereToVal");
        String whereFromValue = extra.getString("whereFromVal");
        String viagemData = extra.getString("dataDeViagem");
        String inicio = extra.getString("inicioViagem");
        String fim = extra.getString("fimViagem");
        String stops = extra.getString("stops");


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
                ride.put("stop",stops);
                ride.put("price",price);
                ride.put("user_id",ParseUser.getCurrentUser());

                ride.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null){

                            FancyToast.makeText(PriceActivity.this,"Viagem Registada",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                        }

                        else{

                            FancyToast.makeText(PriceActivity.this,"Algo correu mal.\n Tente Novamente",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                    }
                });
            }
        });


    }


}
