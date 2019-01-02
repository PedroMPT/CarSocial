package pt.ismai.pedro.needarideapp.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.ParseUser;

import pt.ismai.pedro.needarideapp.Adapters.PageAdapter;
import pt.ismai.pedro.needarideapp.R;

public class MyProfileActivity extends AppCompatActivity {

    //SETTING VARIABLES
    TabLayout tabLayout;
    TabItem perfilTab,carTab;
    ViewPager viewPager;
    PageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        //SETTING TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //BINDING WITH LAYOUT
        tabLayout = findViewById(R.id.tab_layout);
        perfilTab = findViewById(R.id.pefilTab);
        carTab = findViewById(R.id.carTab);
        viewPager = findViewById(R.id.viewPager);

        //GETTING THE TABS FROM PAGE_ADAPTER AND SETTING IN THE VIEWPAGER
        pageAdapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        //ADD A LISTENER TO THE VIEW PAGER FOR TABS CHANGE
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //ADD A LISTENER FOR THE TAB_LAYOUT TO CHANGE THE BACKGROUND COLOR AND THE STATUS_BAR
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 1) {
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MyProfileActivity.this,
                            R.color.colorPrimaryDark));
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FBFCFC"));

                } else {
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MyProfileActivity.this,
                            R.color.colorPrimary));
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FBFCFC"));

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void executeActivity(Class<?> subActivity){

        Intent intent = new Intent(this,subActivity);
        intent.putExtra("objectId", ParseUser.getCurrentUser().getObjectId());
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        executeActivity(UserActivity.class);
        return true;
    }
}
