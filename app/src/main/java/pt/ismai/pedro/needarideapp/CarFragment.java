package pt.ismai.pedro.needarideapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarFragment extends Fragment {

    EditText brandText,modelText,seatsText,plateText;
    Switch canSmoke,canTakePets;
    Button saveButton;
    boolean validationError = false;
    boolean can_smoke;
    boolean can_pets;
    String activeUser;


    public CarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car,container,false);

        brandText = view.findViewById(R.id.brandText);
        modelText = view.findViewById(R.id.modelText);
        seatsText = view.findViewById(R.id.seatsText);
        plateText = view.findViewById(R.id.plateText);
        canSmoke = view.findViewById(R.id.canSmoke);
        canTakePets = view.findViewById(R.id.canTakePets);
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder validationMessage = new StringBuilder("Por favor, indique");

                if (isEmpty(brandText)){

                    validationError = true;
                    validationMessage.append(" a marca,");
                }
                if (isEmpty(modelText)){

                    validationError = true;
                    validationMessage.append(" o modelo,");
                }

                if (isEmpty(seatsText)){

                    validationError = true;
                    validationMessage.append(" os lugares,");
                }

                if (isEmpty(seatsText)){

                    validationError = true;
                    validationMessage.append(" a matrícula,");
                }

                validationMessage.append(".");

                if (validationError){

                    Toast.makeText(getActivity(), validationMessage.toString(), Toast.LENGTH_SHORT).show();
                    return;
                }





                ParseObject car = new ParseObject("Car");

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

                            FancyToast.makeText(getActivity(),"Registo de veículo efetuado com sucesso",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                        }
                        else{

                            FancyToast.makeText(getActivity(),"Algo correu mal.\n Tente Novamente",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
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

}