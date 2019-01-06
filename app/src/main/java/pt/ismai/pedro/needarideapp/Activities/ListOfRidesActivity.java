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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pt.ismai.pedro.needarideapp.Adapters.RecyclerViewAdapter;
import pt.ismai.pedro.needarideapp.R;

public class ListOfRidesActivity extends AppCompatActivity {

    final ArrayList<Bitmap> avatar = new ArrayList<>();
    final ArrayList<String> avatarName = new ArrayList<>();
    final ArrayList<String> price = new ArrayList<>();
    final ArrayList<String> time = new ArrayList<>();
    final ArrayList<String> rideDate = new ArrayList<>();
    final ArrayList<String> rideEndDate = new ArrayList<>();
    final ArrayList<String> rideFrom = new ArrayList<>();
    final ArrayList<String> rideTo = new ArrayList<>();
    final ArrayList<String> rideFromAddress = new ArrayList<>();
    final ArrayList<String> rideToAddress = new ArrayList<>();
    final ArrayList<String> carInfo = new ArrayList<>();
    final ArrayList<String> seatsAvailable = new ArrayList<>();
    final ArrayList<String> canSmoke = new ArrayList<>();
    final ArrayList<String> canTakePets = new ArrayList<>();
    final ArrayList<String> plate = new ArrayList<>();

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

        Bundle extra = getIntent().getExtras();
        String checkFromCity= extra.getString("rideFromAddress");
        String checkToCity = extra.getString("rideToAddress");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Ride");
        query.whereNotEqualTo("user_id",ParseUser.getCurrentUser());
        query.whereContains("from_address",checkFromCity);
        query.whereContains("to_address",checkToCity);
        query.include("user_id");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> rides, ParseException e) {

                if ( e == null){

                    for (ParseObject ride : rides){

                        price.add(ride.get("price") + "€");
                        time.add((String) ride.get("start"));
                        rideDate.add(changeDataFormat((String) ride.get("data")));
                        rideFrom.add((String) ride.get("from_city"));
                        rideTo.add((String) ride.get("to_city"));
                        rideFromAddress.add((String) ride.get("from_address"));
                        rideToAddress.add((String) ride.get("to_address"));
                        rideEndDate.add((String) ride.get("end"));

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

                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Car");
                        query.whereNotEqualTo("user_id",ParseUser.getCurrentUser());
                        query.include("user_id");

                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> cars, ParseException e) {

                                if (e == null){

                                    for (ParseObject car : cars){

                                        carInfo.add(car.get("brand") + " " + car.get("model"));
                                        seatsAvailable.add( car.get("seats") + " lugares disponíveis");
                                        plate.add((String) car.get("plate"));

                                        if ( car.get("can_smoke") == "true"){

                                            canSmoke.add("Permitido fumar");

                                        }else{
                                            canSmoke.add("Não é permitido fumar");
                                        }

                                        if ( car.get("can_take_pets") == "true"){

                                            canTakePets.add("Permitido transportar animais");

                                        }else{
                                            canTakePets.add("Não são permitidos animais");
                                        }


                                        initiateRecyclerView();
                                    }
                                }


                            }
                        });



                    }


                }


            }


        });


    }

    private void initiateRecyclerView(){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(),avatar,avatarName, price,time,rideDate,
                rideEndDate,rideFrom,rideTo,carInfo, canSmoke, canTakePets, seatsAvailable, plate,
                rideFromAddress,rideToAddress);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent = new Intent(this,SearchForARideActivity.class);
        intent.putExtra("objectId", ParseUser.getCurrentUser().getObjectId());
        startActivity(intent);
        finish();
        return true;
    }

    private String changeDataFormat(String object){

        final String OLD_FORMAT = "dd-MM-yyyy";
        final String NEW_FORMAT = "dd MMM yyyy";
        Locale local = new Locale("pt","PT");

        String newDateString;

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT,local);
        Date d = null;
        try {
            d = sdf.parse(object);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d);

        return  newDateString;
    }


}
