package pt.ismai.pedro.needarideapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pt.ismai.pedro.needarideapp.Fragments.CarFragment;
import pt.ismai.pedro.needarideapp.Fragments.ProfileFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;


    public PageAdapter(FragmentManager fm, int numOfTabs) {

        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){

            case 0:
                return new ProfileFragment();
            case 1:
                return new CarFragment();
            default:
                return null;
        }
    }



}
