package pt.ismai.pedro.needarideapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import pt.ismai.pedro.needarideapp.R;

public class OfferActivity extends AppCompatActivity {

    MaterialSearchBar searchBar;
    List<String> cidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        searchBar = findViewById(R.id.searchBar);

        searchBar.setHint("Where to go?");
        loadSugestHint();
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest = new ArrayList<>();
                for (String search:cidades){

                    if (search.toLowerCase().contains(searchBar.getText()))

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

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                startSearch(text.toString());

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


    }

    private void startSearch(String text) {

        getName(text);


    }

    private void loadSugestHint(){

        cidades = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("City");
        query.findInBackground((cities, e) -> {

            if (e == null){

                if (cities.size() > 0 ){

                    for (ParseObject city : cities){

                       final String cidade = (String) city.get("Name");
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
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if (e == null && object != null){

                    String nome = (String) object.get(String.valueOf(String.valueOf("Name").matches(name)));
                    result.add(nome);

                }
            }
        });

        return result;

    }

}



