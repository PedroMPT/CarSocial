package pt.ismai.pedro.needarideapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import pt.ismai.pedro.needarideapp.R;

public class ForgotPasswordActivity extends Activity {

    EditText emailText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailText = findViewById(R.id.emailText);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ParseUser.requestPasswordResetInBackground(emailText.getText().toString(), new RequestPasswordResetCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null){

                            FancyToast.makeText(ForgotPasswordActivity.this,"O email foi enviado com as devidas instruções",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                        }else{

                            FancyToast.makeText(ForgotPasswordActivity.this,"Algo correu mal.\n Tente Novamente!",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                    }
                });
            }
        });
    }
}
