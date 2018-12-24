package pt.ismai.pedro.needarideapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.parse.GetCallback;
import com.parse.ParseSession;
import com.parse.ParseUser;

public class MainActivity extends Activity {

    private Handler mWaitHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {


             ParseSession session = new ParseSession();
             ParseUser user = session.getParseUser("objectId");


                if (user != null){

                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(),UserActivity.class);
                    intent.putExtra("objectId", ParseUser.getCurrentUser().getObjectId());
                    startActivity(intent);}
            }
        },4000);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        mWaitHandler.removeCallbacksAndMessages(null);
    }
}
