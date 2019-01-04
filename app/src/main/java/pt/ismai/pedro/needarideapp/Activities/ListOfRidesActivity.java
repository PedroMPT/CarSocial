package pt.ismai.pedro.needarideapp.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
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

    RecyclerView recyclerView;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_rides);


        //SETTING TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.mt_recycler);
        initRideInfo();
    }

    private void initRideInfo(){


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Ride");
        query.include("user_id");
        query.whereNotEqualTo("user_id",ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> rides, ParseException e) {

                if ( e == null){

                    for (ParseObject ride : rides){

                        price.add(ride.get("price") + "€");
                        time.add((String) ride.get("start"));
                        rideDate.add((String) ride.get("data"));
                        rideFrom.add((String) ride.get("from"));
                        rideTo.add((String) ride.get("to"));

                        ParseObject user = ride.getParseObject("user_id");
                        String fullName = user.get("name") + " " + user.get("last_name");
                        avatarName.add(fullName);

                        byte[] file;
                        try {
                            file = user.getParseFile("profile_photo").getData();
                            bitmap = BitmapFactory.decodeByteArray(file,0,file.length);
                            avatar.add(bitmap);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                        initiateRecyclerView();


                    }


                }


            }


        });


    }

    private void initiateRecyclerView(){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(),avatar,avatarName, price,time,rideDate,rideFrom,rideTo);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent = new Intent(this,UserActivity.class);
        intent.putExtra("objectId", ParseUser.getCurrentUser().getObjectId());
        startActivity(intent);
        finish();
        return true;
    }


}
