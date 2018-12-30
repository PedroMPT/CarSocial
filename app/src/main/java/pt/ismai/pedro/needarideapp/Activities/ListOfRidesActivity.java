package pt.ismai.pedro.needarideapp.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import pt.ismai.pedro.needarideapp.Adapters.RecyclerViewAdapter;
import pt.ismai.pedro.needarideapp.R;

public class ListOfRidesActivity extends AppCompatActivity {

    final ArrayList<Bitmap> avatar = new ArrayList<>();
    final ArrayList<String> avatarName = new ArrayList<>();
    final ArrayList<String> price = new ArrayList<>();
    final ArrayList<String> time = new ArrayList<>();
    final ArrayList<String> rideDate = new ArrayList<>();
    final ArrayList<String> rideFrom = new ArrayList<>();
    final ArrayList<String> rideTo = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_rides);

        initRideInfo();
        initiateRecyclerView();

    }

    private void initRideInfo(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Ride");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> rides, ParseException e) {

                if ( e == null){

                    for (ParseObject ride : rides){

                        price.add((String) ride.get("price"));
                        time.add((String) ride.get("time"));
                        rideDate.add((String) ride.get("data"));
                        rideFrom.add((String) ride.get("from"));
                        rideTo.add((String) ride.get("to"));

                    }
                }

            }
        });

        ParseObject ride = new ParseObject("Ride");

        ParseQuery<ParseUser> queryU = ParseUser.getQuery();
        queryU.whereEqualTo("objectId",ride.get("user_id"));

        queryU.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {

                if (e == null){

                    for (ParseUser user: users){

                        String firstName = (String) user.get("name");
                        String lastName = (String) user.get("lat_name");
                        avatarName.add(firstName + " " + lastName);

                        ParseFile file = (ParseFile) user.get("profile_photo");

                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                avatar.add(bitmap);
                            }
                        });

                    }
                }
            }
        });


    }



    private void initiateRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.mt_recycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(),avatar,avatarName,price,time,rideDate,rideFrom,rideTo);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));


    }


}
