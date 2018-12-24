package pt.ismai.pedro.needarideapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SignUpActivity extends Activity {

    CardView signup;
    ImageView arrowImage, profilePhoto;
    EditText usernameText, passwordText, nameText, phoneText,musicText, emailText, passwordConfirm,lastNameText;
    ParseUser user = new ParseUser();
    ParseFile file;

    public void getPhoto(){

        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        SignUpActivity.super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null){

            Uri selectedImage = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(SignUpActivity.this.getContentResolver(),selectedImage);
                profilePhoto.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);

                byte[] byteArray = stream.toByteArray();
                file = new ParseFile("image.png",byteArray);
                file.saveInBackground();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                getPhoto();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        nameText = findViewById(R.id.nameText);
        lastNameText = findViewById(R.id.lastNameText);
        phoneText = findViewById(R.id.phoneText);
        musicText = findViewById(R.id.musicText);
        emailText = findViewById(R.id.emailText);
        arrowImage = findViewById(R.id.arrowImage);
        passwordConfirm = findViewById(R.id.passwordConfirm);
        profilePhoto = findViewById(R.id.profilePhoto);
        signup = findViewById(R.id.signUp);


        arrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                    }else {

                        getPhoto();
                    }
                }else{

                    getPhoto();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean validationError = false;
                StringBuilder validationMessage = new StringBuilder("Por favor, indique");

                if (isEmpty(nameText)){

                    validationError = true;
                    validationMessage.append(" o nome,");
                }

                if (isEmpty(lastNameText)){

                    validationError = true;
                    validationMessage.append(" o último nome,");
                }

                if (isEmpty(usernameText)){

                    validationError = true;
                    validationMessage.append(" o username,");
                }

                if (isEmpty(passwordText)){

                    if (validationError){

                        validationMessage.append(" e ");
                    }

                    validationError = true;
                    validationMessage.append(" a password,");
                }

                if (isEmpty(passwordConfirm)){

                    if (validationError){

                        validationMessage.append(" e ");
                    }

                    validationError = true;
                    validationMessage.append(" a password novamente ");

                }else{

                    if (!isMatching(passwordText,passwordConfirm)){

                        if (validationError){

                            validationMessage.append(" e ");
                        }
                        validationError = true;
                        validationMessage.append(" a mesma password");
                    }
                }

                if (isEmpty(emailText)){

                    validationError = true;
                    validationMessage.append(" o email");
                }

                validationMessage.append(".");

                if (validationError){

                    Toast.makeText(SignUpActivity.this, validationMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                user.put("profile_photo",file);
                user.put("name",nameText.getText().toString());
                user.put("last_name",lastNameText.getText().toString());
                user.setUsername(usernameText.getText().toString());
                user.setPassword(passwordText.getText().toString());
                user.setEmail(emailText.getText().toString());
                user.put("phone",phoneText.getText().toString());
                user.put("music",musicText.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null){

                            FancyToast.makeText(SignUpActivity.this,"Registo efetuado com sucesso",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                            Intent intent = new Intent(getApplicationContext(),UserActivity.class);
                            intent.putExtra("objectId", ParseUser.getCurrentUser().getObjectId());
                            startActivity(intent);

                        }else{

                            FancyToast.makeText(SignUpActivity.this,"Algo correu mal.\n Tente Novamente",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                    }
                });
            }
        });

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


    private boolean isEmpty(EditText text){

        if(text.getText().toString().trim().length() > 0){

            return false;
        }else{

            return true;
        }
    }

    private boolean isMatching (EditText e1, EditText e2){

        if (e1.getText().toString().equals(e2.getText().toString())){

            return true;
        }else{

            return false;
        }
    }

    private EditText setBorder(EditText e){

        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setColor(Color.RED);
        shape.getPaint().setStyle(Paint.Style.STROKE);
        shape.getPaint().setStrokeWidth(3);

        e.setBackground(shape);

        return e;

    }
}
