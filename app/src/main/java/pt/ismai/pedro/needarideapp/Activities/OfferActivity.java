package pt.ismai.pedro.needarideapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import pt.ismai.pedro.needarideapp.R;

public class OfferActivity extends AppCompatActivity {

    MaterialSearchBar searchBar;
    List<String> cidades;
    String passingValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        //SETTING TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchBar = findViewById(R.id.searchBar);

        searchBar.setHint("Para onde vamos");
        loadSugestHint();

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest = new ArrayList<>();
                for (String search:cidades){

                    if (search.contains(searchBar.getText()))

                        suggest.add(search);

                    }
                    searchBar.setLastSuggestions(suggest);
                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

                String s = enabled ? "enabled" : "disabled";
                Toast.makeText(getApplicationContext(), "Search " + s, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                startSearch(text.toString());
                passingValue = searchBar.getText();

            }

            @Override
            public void onButtonClicked(int buttonCode) {

                switch (buttonCode){

                    case MaterialSearchBar.BUTTON_NAVIGATION:


                    case MaterialSearchBar.BUTTON_BACK:
                        searchBar.disableSearch();
                        break;



                }

            }
        });


    }

    private void startSearch(String text) {

        getName(text.toLowerCase());


    }

    private void loadSugestHint(){

        cidades = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("City");
        query.findInBackground((cities, e) -> {

            if (e == null){

                if (cities.size() > 0 ){

                    for (ParseObject city : cities){

                       String cidade = (String) city.get("Name");
                       cidades.add(String.valueOf(cidade));

                    }
                }
            }

        });
        searchBar.setLastSuggestions(cidades);
    }

    public List<String> getName(String name){

        List<String> result = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("City");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> cities, ParseException e) {

                if (e == null){

                    for (ParseObject city : cities){

                        String nome = (String) city.get("Name");
                        if (nome.matches(name)){

                            result.add(nome);
                        }

                    }
                }
            }
        });

        return result;

    }
    private void executeActivity(Class<?> subActivity){

        Intent intent = new Intent(this,subActivity);
        intent.putExtra("whereTo",passingValue);
        startActivity(intent);
        finish();
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



