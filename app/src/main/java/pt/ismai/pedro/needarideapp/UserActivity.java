package pt.ismai.pedro.needarideapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity  {

    private DrawerLayout drawerLayout;

    CircleImageView circleImageView;
    TextView nome;
    Toolbar toolbar;
    CardView offerATrip;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        circleImageView = findViewById(R.id.circleImageView);
        nome = findViewById(R.id.nome);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        offerATrip = findViewById(R.id.offer);

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String activeUser = intent.getStringExtra("objectId");
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId",activeUser);

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null){

                    if (objects.size() > 0){

                        for (ParseObject user : objects){

                            ParseFile file = (ParseFile) user.get("profile_photo");
                            final String nomeUtilizador = (String) user.get("name");
                            final String ultimoNomeUtilizaddor = (String) user.get("last_name");

                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {

                                    if (e == null && data != null){

                                        View headerView = navigationView.getHeaderView(0);
                                        TextView navUsername = headerView.findViewById(R.id.menu_name);
                                        ImageView navProfileImage = headerView.findViewById(R.id.menu_profile);

                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                        circleImageView.setImageBitmap(bitmap);
                                        nome.setText("Olá, " + nomeUtilizador);
                                        navProfileImage.setImageBitmap(bitmap);
                                        navUsername.setText(nomeUtilizador + " " + ultimoNomeUtilizaddor);

                                    }
                                }
                            });
                        }
                    }
                }
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.nav_my_profile:

                        Intent profileIntent = new Intent(getApplicationContext(),MyProfileActivity.class);
                        profileIntent.putExtra("objectId", ParseUser.getCurrentUser().getObjectId());
                        startActivity(profileIntent);
                        finish();

                        break;

                    case R.id.nav_notifications:


                    case R.id.nav_my_trips:


                        break;

                    case R.id.nav_logout:

                        ParseUser.logOut();
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(UserActivity.this, "Sessão Terminada", Toast.LENGTH_SHORT).show();
                        finish();

                        break;
                }
                drawerLayout.closeDrawer(Gravity.START);
                return true;
            }
        });

        offerATrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Car");
                query.whereEqualTo("user_id",ParseUser.getCurrentUser());

                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> cars, ParseException e) {

                        if (e == null){

                            if (cars.size() > 0){

                                FancyToast.makeText(UserActivity.this,"Boa!! Tens Um carro associado",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                            }
                            else{

                                new AlertDialog.Builder(UserActivity.this)

                                        .setTitle("Alerta de Carro")
                                        .setMessage("Necessita de um carro para oferecer viagens. Deseja associar?")
                                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Intent carIntent = new Intent(getApplicationContext(),MyProfileActivity.class);
                                                carIntent.putExtra("objectId", ParseUser.getCurrentUser().getObjectId());
                                                startActivity(carIntent);

                                            }
                                        })
                                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.cancel();
                                            }
                                        })
                                        .show();

                            }
                        }


                    }
                });
            }
        });


    }

    @Override
    public void onBackPressed(){

        if (drawerLayout.isDrawerOpen(Gravity.START)){

            drawerLayout.closeDrawer(Gravity.START);
        }else{

            super.onBackPressed();
        }
    }


}
