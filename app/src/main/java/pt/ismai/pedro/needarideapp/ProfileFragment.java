package pt.ismai.pedro.needarideapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView nameText,usernameText,emailText,phoneText,musicText;
    CircleImageView circleImageView;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        nameText = view.findViewById(R.id.nameText);
        usernameText =  view.findViewById(R.id.lastNameText);
        emailText = view.findViewById(R.id.emailText);
        phoneText =  view.findViewById(R.id.phoneText);
        musicText =  view.findViewById(R.id.musicText);
        circleImageView =  view.findViewById(R.id.circleImageView);

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


        // Inflate the layout for this fragment
        return view;
    }

}
