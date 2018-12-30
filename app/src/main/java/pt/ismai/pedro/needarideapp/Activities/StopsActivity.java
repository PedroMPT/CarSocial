package pt.ismai.pedro.needarideapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pt.ismai.pedro.needarideapp.R;

public class StopsActivity extends AppCompatActivity {

    EditText stopsText;
    Button button;
    String stops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);

        stopsText = findViewById(R.id.stopsText);
        button = findViewById(R.id.button);

        Bundle extra = getIntent().getExtras();
        String whereToValue = extra.getString("whereToVal");
        String whereFromValue = extra.getString("whereFromVal");
        String viagemData = extra.getString("dataDeViagem");
        String inicio = extra.getString("inicioViagem");
        String fim = extra.getString("fimViagem");




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                executeActivity(PriceActivity.class);


            }
        });


        Toast.makeText(this, whereFromValue + " - " + whereToValue + " - " + viagemData + " - " + inicio + " - " + fim + "!", Toast.LENGTH_LONG).show();

    }

    private void executeActivity(Class<?> subActivity){

        Bundle extra = getIntent().getExtras();
        String whereToValue = extra.getString("whereToVal");
        String whereFromValue = extra.getString("whereFromVal");
        String viagemData = extra.getString("dataDeViagem");
        String inicio = extra.getString("inicioViagem");
        String fim = extra.getString("fimViagem");
        stops = stopsText.getText().toString();

        Intent sendIntent = new Intent(this,subActivity);
        sendIntent.putExtra("whereToVal",whereToValue);
        sendIntent.putExtra("whereFromVal",whereFromValue );
        sendIntent.putExtra("dataDeViagem",viagemData);
        sendIntent.putExtra("inicioViagem",inicio);
        sendIntent.putExtra("fimViagem",fim );
        sendIntent.putExtra("stops",stops );
        startActivity(sendIntent);
        finish();
    }
}
