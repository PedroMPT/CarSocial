package pt.ismai.pedro.needarideapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import pt.ismai.pedro.needarideapp.R;

//import com.parse.facebook.ParseFacebookUtils;


public class LoginActivity extends Activity {

      /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }*/

    //SETTING VARIABLES
    CardView login, facebookLogin;
    EditText usernameText, passwordText;
    TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //BINDING WITH LAYOUT
        login = findViewById(R.id.login);
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        facebookLogin = findViewById(R.id.facebookLogin);
        signUpText = findViewById(R.id.signUpText);

        //PASSING INTENT TO THE SIGN UP ACTIVITY
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUpText.setTextColor(Color.parseColor("#FFFFFF"));
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // SETTING LISTENER TO LOGIN
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (usernameText.getText().toString().matches("") || passwordText.getText().toString().matches("")) {

                   FancyToast.makeText(LoginActivity.this,"Username e Password Obrigatórios",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

                }else{

                    ParseUser.logInInBackground(usernameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {

                            if(user != null){

                               executeActivity(UserActivity.class);
                            }
                            else{

                                FancyToast.makeText(LoginActivity.this,"O utlizador não está registado.\n Tente Novamente!",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                            }
                        }
                    });
                }
            }
        });

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        /*final Collection<String> permissions = Arrays.asList("public_profile", "email");

        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Facebook!");
                        } else {
                            Log.d("MyApp", "User logged in through Facebook!");
                        }
                    }
                });
            }
        });*/

    }
    private void executeActivity(Class<?> subActivity){

        Intent intent = new Intent(this,subActivity);
        intent.putExtra("objectId", ParseUser.getCurrentUser().getObjectId());
        startActivity(intent);
        finish();
    }
}