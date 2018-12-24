package pt.ismai.pedro.needarideapp.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pt.ismai.pedro.needarideapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    //SETTING VARIABLES
    EditText nameText,usernameText,emailText,phoneText,musicText,lastNameText;
    CircleImageView circleImageView;
    FloatingActionButton fab;
    Button saveButton;
    ImageView changePhoto;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        //BINDING WITH LAYOUT
        nameText = view.findViewById(R.id.nameText);
        lastNameText = view.findViewById(R.id.lastNameText);
        usernameText =  view.findViewById(R.id.usernameText);
        emailText = view.findViewById(R.id.emailText);
        phoneText =  view.findViewById(R.id.phoneText);
        musicText =  view.findViewById(R.id.musicText);
        circleImageView =  view.findViewById(R.id.circleImageView);
        fab = view.findViewById(R.id.fab);
        saveButton = view.findViewById(R.id.saveButton);
        changePhoto = view.findViewById(R.id.changePhoto);


        Intent intent = getActivity().getIntent();
        String activeUser = intent.getStringExtra("objectId");

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId",activeUser);
        if (activeUser != null){

            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {

                    if (e == null){

                        if (objects.size() > 0){

                            for (ParseObject user : objects){

                                final String nomeUtilizaddor = (String) user.get("name");
                                final String ultimoNomeUtilizador = (String) user.get("last_name");
                                final String utilizador = user.get("username").toString();
                                final String phone = user.get("phone").toString();
                                final String email = user.get("email").toString();
                                final String musica = user.get("music").toString();
                                ParseFile file = (ParseFile) user.get("profile_photo");

                                file.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] data, ParseException e) {

                                        if (e == null && data != null){

                                            nameText.setText(nomeUtilizaddor);
                                            usernameText.setText(utilizador);
                                            emailText.setText(email);
                                            phoneText.setText(phone);
                                            musicText.setText(musica);
                                            lastNameText.setText(ultimoNomeUtilizador);
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                            circleImageView.setImageBitmap(bitmap);

                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            });

        }

        saveButton.setTranslationX(-1000f);
        saveButton.setTranslationY(-1000f);
        changePhoto.setTranslationX(-1000f);
        changePhoto.setTranslationY(-1000f);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameText.setEnabled(true);
                lastNameText.setEnabled(true);
                usernameText.setEnabled(true);
                emailText.setEnabled(true);
                phoneText.setEnabled(true);
                musicText.setEnabled(true);

                saveButton.animate()
                        .translationXBy(1000f)
                        .translationYBy(1000f);
                changePhoto.animate()
                        .translationXBy(1000f)
                        .translationYBy(1000f);


            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("objectId",ParseUser.getCurrentUser());

                query.getInBackground(ParseUser.getCurrentUser().getObjectId(), new GetCallback<ParseUser>() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (e == null){

                            user.put("username",usernameText.getText().toString());
                            user.put("name",nameText.getText().toString());
                            user.put("last_name",lastNameText.getText().toString());
                            user.put("email",emailText.getText().toString());
                            user.put("music",musicText.getText().toString());
                            user.put("phone",phoneText.getText().toString());

                            user.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if (e == null){

                                        FancyToast.makeText(getActivity(),"Registo alterado com sucesso",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                                    }else{

                                        FancyToast.makeText(getActivity(),"Algo correu mal. Tente Novamente",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                                    }
                                }
                            });
                        }


                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
