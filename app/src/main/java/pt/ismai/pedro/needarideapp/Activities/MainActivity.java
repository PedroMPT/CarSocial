package pt.ismai.pedro.needarideapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;

import com.parse.ParseUser;

import pt.ismai.pedro.needarideapp.R;

public class MainActivity extends Activity {

    private Handler mWaitHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Managing the splash screen
        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //Saving the user session
                String token = ParseUser.getCurrentUser().getSessionToken();

                if (token != null){

                    executeActivity(UserActivity.class);

                }else{
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                   }
            }
        },4000);
    }

    private void executeActivity(Class<?> subActivity){

        Intent intent = new Intent(this,subActivity);
        intent.putExtra("objectId", ParseUser.getCurrentUser().getObjectId());
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWaitHandler.removeCallbacksAndMessages(null);
    }
}
