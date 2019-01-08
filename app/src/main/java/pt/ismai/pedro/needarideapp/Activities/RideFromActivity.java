package pt.ismai.pedro.needarideapp.Activities;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.parse.ParseUser;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.ismai.pedro.needarideapp.Adapters.PlaceAutocompleteAdapter;
import pt.ismai.pedro.needarideapp.Model.PlaceInfo;
import pt.ismai.pedro.needarideapp.R;

public class RideFromActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    //Global Vars
    private static final String TAG = "RideFromActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_RESQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40,-168), new LatLng(71,136));
    private PlaceInfo mPlace;


    //widgets

    private AutoCompleteTextView mSearchText;
    private AutoCompleteTextView mSearchTextTo;
    private FloatingActionButton mGps;

    //Vars
    private GoogleMap mMap;
    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlaceAutocompleteAdapter placeAutocompleteAdapter;
    private GoogleApiClient googleApiClient;
    private static String rideFromAddress;
    private static String rideFromCity;
    private static String rideToAddress;
    private static String rideToCity;
    Button button;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "Map is Ready!!");

        mMap = googleMap;

        if (mLocationPermissionGranted) {

            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_from);

        //SETTING TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mSearchText = findViewById(R.id.inputSearch);
        mGps = findViewById(R.id.ic_gps);
        button = findViewById(R.id.button);
        mSearchTextTo = findViewById(R.id.inputSearch2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                executeActivity(TripDataActivity.class);
            }
        });
        getLocationPermission();

    }

    private void init(){

        googleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mSearchText.setOnItemClickListener(mAutoCompleteClickListener);
        mSearchTextTo.setOnItemClickListener(mAutoCompleteClickListenerForDestinationRide);

        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(this.getApplicationContext(),
                Places.getGeoDataClient(this.getApplicationContext(), null), LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(placeAutocompleteAdapter);
        mSearchTextTo.setAdapter(placeAutocompleteAdapter);

        Log.d(TAG,"Init!!");
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == event.ACTION_DOWN
                        || event.getAction() == event.KEYCODE_ENTER){

                    //execute method for searching

                    geoLocate();
                }

                return false;

            }

        });
        mSearchTextTo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == event.ACTION_DOWN
                        || event.getAction() == event.KEYCODE_ENTER){

                    //execute method for searching

                    geoLocateforDestinationRide();
                }

                return false;

            }
        });

        hideSoftKeyboard();
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Click GPS icon");
                setDeviceLocation();
            }
        });
    }

    private void geoLocate(){

        Log.d(TAG, "geoLocate: GeoLocation");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(RideFromActivity.this);
        List<Address> list = new ArrayList<>();

        try{

            list = geocoder.getFromLocationName(searchString,1);


        }catch (IOException e){

            Log.d(TAG, "IOException: " + e.getMessage());
        }

        if (list.size() > 0){

          Address address = list.get(0);

          Log.d(TAG, "geoLocate: Found a Location" + address.toString());
          moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }

    private void geoLocateforDestinationRide(){

        Log.d(TAG, "geoLocate: geoLocationg");

        String searchString = mSearchTextTo.getText().toString();

        Geocoder geocoder = new Geocoder(RideFromActivity.this);
        List<Address> list = new ArrayList<>();

        try{

            list = geocoder.getFromLocationName(searchString,1);


        }catch (IOException e){

            Log.d(TAG, "IOException: " + e.getMessage());
        }

        if (list.size() > 0){

            Address address = list.get(0);

            Log.d(TAG, "geoLocate: Found a Location" + address.toString());
            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }

    private void getDeviceLocation(){

        Log.d(TAG,"Getting device current location!!");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{

            if (mLocationPermissionGranted){

                fusedLocationProviderClient.getLastLocation()
                        .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {

                        if(task.isSuccessful() && task.getResult() != null){

                            Log.d(TAG,"Find Location!!");

                            Location currentLocation = task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(),
                                    currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,"My Location");
                        }else{

                            Log.d(TAG,"Location is null!!");

                            Toast.makeText(RideFromActivity.this, "Could not find current location", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

        }catch (SecurityException e){

            Log.d(TAG,"Security Exception" + e.getMessage());
        }


    }

    private void setDeviceLocation(){

        Log.d(TAG,"Getting device current location!!");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{

            if (mLocationPermissionGranted){

                fusedLocationProviderClient.getLastLocation()
                        .addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {

                                if(task.isSuccessful() && task.getResult() != null){

                                    Log.d(TAG,"Find Location!!");

                                    Location currentLocation = task.getResult();
                                    Geocoder geocoder = new Geocoder(getApplicationContext());
                                    List<Address> list = new ArrayList<>();

                                    try {
                                        list = geocoder.getFromLocation(currentLocation.getLatitude(),
                                                currentLocation.getLongitude(),1);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    if (list.size() > 0){

                                        Address address = list.get(0);

                                        mSearchText.setText(address.getAddressLine(0));
                                    }

                                    moveCamera(new LatLng(currentLocation.getLatitude(),
                                                    currentLocation.getLongitude()),
                                            DEFAULT_ZOOM,"My Location");

                                }else{

                                    Log.d(TAG,"Location is null!!");

                                    Toast.makeText(RideFromActivity.this, "Could not find current location", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }

        }catch (SecurityException e){

            Log.d(TAG,"Security Exception" + e.getMessage());
        }



    }

    private void moveCamera(LatLng latLng, float zoom,String title){

        Log.d(TAG,"Moving camera to " + latLng.latitude + ", " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        if (!title.equals("My Location")){

            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }
        hideSoftKeyboard();
    }

    private void initMap(){

        Log.d(TAG,"Initializaing Map!!");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(RideFromActivity.this);
    }

    private void getLocationPermission() {

        Log.d(TAG,"Getting location Permisions!!");

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                mLocationPermissionGranted = true;
                initMap();
            }else{

                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_RESQUEST_CODE);
            }
        }else{

            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_RESQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.d(TAG,"OnRequestPermision Called!!");

        switch (requestCode){

            case LOCATION_PERMISSION_RESQUEST_CODE:
                if (grantResults.length > 0){

                    for (int i = 0 ; i < grantResults.length; i++){

                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){

                            mLocationPermissionGranted = false;

                            Log.d(TAG,"Permision Failed");
                            return;
                        }

                    }
                    mLocationPermissionGranted = true;

                    Log.d(TAG,"Permision Granted!");
                    // Initialize Map

                    initMap();
                }
        }
    }

    private void hideSoftKeyboard(){

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /*
        »»»»»»»»»»»»»»»»»»»»»»»»»»»»» GOOGLE PLACES API - AUTOCOMPLETE FOR LOCATION RIDE ««««««««««««««««««««««««««««««
     */

    private AdapterView.OnItemClickListener mAutoCompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            hideSoftKeyboard();

            final AutocompletePrediction item = placeAutocompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(googleApiClient, placeId);

            placeResult.setResultCallback( mUpdatePlaceDetailsCallback);


        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {

            if (!places.getStatus().isSuccess()){

                Log.d(TAG, "onResult: Place query did not complete successfuly: " +
                        places.getStatus().toString());
                places.release();
                return;
            }

            final Place place = places.get(0);

            try{
                mPlace = new PlaceInfo();
                mPlace.setName(place.getName().toString());
                rideFromAddress = place.getName().toString();
                mPlace.setAddress(place.getAddress().toString());
                rideFromCity = place.getAddress().toString();
                mPlace.setId(place.getId());
                mPlace.setLatLng(place.getLatLng());

            }catch (NullPointerException e){
                Log.d(TAG, "onResult: " + e.getMessage());

            }

            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude), DEFAULT_ZOOM, mPlace.getName());
            places.release();

        }
    };

        /*
        »»»»»»»»»»»»»»»»»»»»»»»»»»»»» GOOGLE PLACES API - AUTOCOMPLETE FOR DESTINATION RIDE ««««««««««««««««««««««««««««««
     */

    private AdapterView.OnItemClickListener mAutoCompleteClickListenerForDestinationRide = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            hideSoftKeyboard();

            final AutocompletePrediction item = placeAutocompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(googleApiClient, placeId);

            placeResult.setResultCallback(mUpdatePlaceDetailsCallbackForDestinationRide);


        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallbackForDestinationRide = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {

            if (!places.getStatus().isSuccess()){

                Log.d(TAG, "onResult: Place query did not complete successfuly: " +
                        places.getStatus().toString());
                places.release();
                return;
            }

            final Place place = places.get(0);

            try{
                mPlace = new PlaceInfo();
                mPlace.setName(place.getName().toString());
                rideToAddress = place.getName().toString();
                mPlace.setAddress(place.getAddress().toString());
                rideToCity = place.getAddress().toString();
                mPlace.setId(place.getId());
                mPlace.setLatLng(place.getLatLng());

            }catch (NullPointerException e){
                Log.d(TAG, "onResult: " + e.getMessage());

            }

            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude), DEFAULT_ZOOM, mPlace.getName());
            places.release();

        }
    };


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent = new Intent(this,UserActivity.class);
        intent.putExtra("objectId", ParseUser.getCurrentUser().getObjectId());
        startActivity(intent);
        finish();
        return true;
    }

    private void executeActivity(Class<?> subActivity){

        Intent intent = new Intent(this,subActivity);
        intent.putExtra("rideFromAddress", rideFromAddress);
        intent.putExtra("rideFromCity", rideFromCity);
        intent.putExtra("rideToAddress", rideToAddress);
        intent.putExtra("rideToCity", rideToCity);
        startActivity(intent);
        finish();
    }
}
