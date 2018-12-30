package pt.ismai.pedro.needarideapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import pt.ismai.pedro.needarideapp.Adapters.RecyclerViewAdapter;
import pt.ismai.pedro.needarideapp.R;

public class ListOfRidesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_rides);


        initiateRecyclerView();

    }

    private void initiateRecyclerView(){

        //RecyclerView recyclerView = findViewById(R.id.mt_recycler);
        //RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,images,context);
        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager( new LinearLayoutManager(this));


    }
}
