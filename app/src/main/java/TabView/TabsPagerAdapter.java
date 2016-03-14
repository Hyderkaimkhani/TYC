package TabView;


import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hyder.tuc.AddTask;
import com.example.hyder.tuc.ViewMember;
import com.example.hyder.tuc.ViewTask;
import com.example.hyder.tuc.addmember;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new addmember();
            case 1:
                // Games fragment activity
                return new ViewMember();
            case 2:
                // Movies fragment activity
                return new AddTask();
            case 3:
                return new ViewTask();

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }

}