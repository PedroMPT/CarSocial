package pt.ismai.pedro.needarideapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import pt.ismai.pedro.needarideapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarFragment extends Fragment {

    //SETTING VARIABLES
    ConstraintLayout constraintLayout;
    EditText brandText,modelText,seatsText,plateText;
    Switch canSmoke,canTakePets;
    boolean can_smoke;
    boolean can_pets;
    String activeUser;
    FloatingActionButton fab;
    Button saveButton;


    public CarFragment() {
        // Required empty public constructor
    }

    public void hideOrNotToHide(RelativeLayout relativeLayout){


        for (int i = 0 ; i < relativeLayout.getChildCount();i++){

            View v = relativeLayout.getChildAt(i);

           v.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_car,container,false);

        //BINDING WITH LAYOUT
        constraintLayout = view.findViewById(R.id.constraintLayout);
        brandText = view.findViewById(R.id.brandText);
        modelText = view.findViewById(R.id.modelText);
        seatsText = view.findViewById(R.id.seatsText);
        plateText = view.findViewById(R.id.plateText);
        canSmoke = view.findViewById(R.id.canSmoke);
        canTakePets = view.findViewById(R.id.canTakePets);
        fab = view.findViewById(R.id.fab);
        saveButton = view.findViewById(R.id.saveButton);

        Intent intent = getActivity().getIntent();
        activeUser = intent.getStringExtra("objectId");

        canSmoke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    can_smoke = true;
                }
                else{
                    can_smoke = false;
                }
            }
        });

        canTakePets.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    can_pets = true;
                }
                else{
                    can_pets = false;
                }
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Car");
        query.whereEqualTo("user_id",ParseUser.getCurrentUser());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> cars, ParseException e) {

                if (e == null){

                    if (cars.size() > 0){

                        for (ParseObject car : cars){

                            brandText.setText(car.get("brand").toString());
                            modelText.setText(car.get("model").toString());
                            seatsText.setText(car.get("seats").toString());
                            plateText.setText(car.get("plate").toString());

                            if (car.get("can_smoke").equals(true)){

                                canSmoke.setChecked(true);

                            }else{

                                canSmoke.setChecked(false);

                            }

                            if (car.get("can_take_pets").equals(true)){

                                canTakePets.setChecked(true);

                            }else{

                                canTakePets.setChecked(false);

                            }

                        }
                    }
                }
            }
        });

        saveButton.setTranslationX(-1000f);
        saveButton.setTranslationY(-1000f);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveButton.animate()
                        .translationXBy(1000f)
                        .translationYBy(1000f);

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean validationError = false;
                StringBuilder validationMessage = new StringBuilder("Por favor, indique");

                if (isEmpty(brandText)){

                    validationError = true;
                    validationMessage.append(" a marca,");
                }

                if (isEmpty(modelText)){

                    validationError = true;
                    validationMessage.append(" o modelo,");
                }
                if (isEmpty(plateText)){

                    validationError = true;
                    validationMessage.append(" a matrícula,");
                }
                if (isEmpty(seatsText)){

                    validationError = true;
                    validationMessage.append(" e os lugares");
                }
                validationMessage.append(".");

                if (validationError){

                    Toast.makeText(getActivity(), validationMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }


                ParseQuery<ParseObject> query = ParseQuery.getQuery("Car");
                query.whereEqualTo("user_id", ParseUser.getCurrentUser());

                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (objects.size() > 0){

                            ParseQuery<ParseObject> queryUpdate = ParseQuery.getQuery("Car");
                            queryUpdate.whereEqualTo("user_id",ParseUser.getCurrentUser());

                            queryUpdate.getFirstInBackground( new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject car, ParseException e) {

                                    car.put("brand",brandText.getText().toString());
                                    car.put("model",modelText.getText().toString());
                                    car.put("seats",seatsText.getText().toString());
                                    car.put("plate",plateText.getText().toString());
                                    car.put("can_smoke",can_smoke);
                                    car.put("can_take_pets",can_pets);
                                    car.put("user_id",ParseUser.getCurrentUser());

                                    car.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if (e == null){

                                                FancyToast.makeText(getActivity(),"Os dados foram atualizados",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                                            }else{

                                                FancyToast.makeText(getActivity(),"Algo correu mal. Tente Novamente!",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                                            }

                                        }
                                    });
                                }
                            });


                        }else{

                            saveObject();
                        }
                    }
                });

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private boolean isEmpty(EditText text){

        if(text.getText().toString().trim().length() > 0){

            return false;
        }else{

            return true;
        }
    }

    public void saveObject(){

        ParseObject carInfo = new ParseObject("Car");

        carInfo.put("brand",brandText.getText().toString());
        carInfo.put("model",modelText.getText().toString());
        carInfo.put("seats",seatsText.getText().toString());
        carInfo.put("plate",plateText.getText().toString());
        carInfo.put("can_smoke",can_smoke);
        carInfo.put("can_take_pets",can_pets);
        carInfo.put("user_id",ParseUser.getCurrentUser());

        carInfo.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null){

                    FancyToast.makeText(getActivity(),"Dados do carro registados",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                }else{

                    FancyToast.makeText(getActivity(),"Algo correu mal. Tente Novamente",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                }
            }
        });


    }

}
