package com.codejune.sutaekhighschool;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.codejune.sutaekhighschool.meal.FridayMeal;
import com.codejune.sutaekhighschool.meal.MondayMeal;
import com.codejune.sutaekhighschool.meal.ThursdayMeal;
import com.codejune.sutaekhighschool.meal.TuesdayMeal;
import com.codejune.sutaekhighschool.meal.WednsdayMeal;
import com.gc.materialdesign.widgets.Dialog;
import java.util.Calendar;
import java.util.Locale;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Meal extends ActionBarActivity implements ActionBar.TabListener {
    
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        if (!isNetworkConnected(this)) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_error)
                    .setTitle("네트워크 연결")
                    .setMessage("\n네트워크 연결 상태 확인 후 다시 시도해 주십시요\n")
                    .setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }
                            }).show();
            Log.d("Network", "Network Connection Fail");
        } else {

            final ActionBar actionBar = getSupportActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    actionBar.setSelectedNavigationItem(position);
                }
            });

            for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
                actionBar.addTab(
                        actionBar.newTab()
                                .setText(mSectionsPagerAdapter.getPageTitle(i))
                                .setTabListener(this));
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Calendar Cal = Calendar.getInstance();

            int TODAY = Cal.get(Calendar.DAY_OF_WEEK);
            Log.d("TODAY", "TODAY is -" + TODAY);
            switch (TODAY) {
                case 0:
                    actionBar.setSelectedNavigationItem(0);
                    break;
                case 1:
                    actionBar.setSelectedNavigationItem(0);
                    break;
                case 3:
                    actionBar.setSelectedNavigationItem(1);
                    break;
                case 4:
                    actionBar.setSelectedNavigationItem(2);
                    break;
                case 5:
                    actionBar.setSelectedNavigationItem(3);
                    break;
                case 6 | 7:
                    actionBar.setSelectedNavigationItem(4);
            }
            findViewById(R.id.mealinfo).setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            Dialog dialog = new Dialog(Meal.this, "알레르기 정보",
                                    "①난류\n②우유\n③메밀\n④땅콩\n⑤대두\n⑥밀\n⑦고등어\n⑧게\n⑨새우\n⑩돼지고기\n⑪복숭아\n⑫토마토\n⑬아황산염");
                            dialog.show();
                        }
                    });
            Crouton.makeText(this, R.string.meal_info, Style.INFO).show();
        }

    }
        
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment frag = PlaceholderFragment.newInstance(position + 1);
            switch(position){
                case 0:
                    frag = MondayMeal.newInstance(0);
                    break;
                case 1:
                    frag = TuesdayMeal.newInstance(1);
                    break;
                case 2:
                    frag = WednsdayMeal.newInstance(2);
                    break;
                case 3:
                    frag = ThursdayMeal.newInstance(3);
                    break;
                case 4:
                    frag = FridayMeal.newInstance(4);
                    break;

            }
            return frag;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.monday).toUpperCase(l);
                case 1:
                    return getString(R.string.tuesday).toUpperCase(l);
                case 2:
                    return getString(R.string.wednsday).toUpperCase(l);
                case 3:
                    return getString(R.string.thursday).toUpperCase(l);
                case 4:
                    return getString(R.string.friday).toUpperCase(l);
            }
            return null;
        }
    }

    public static class PlaceholderFragment extends Fragment {    
        
        private static final String ARG_SECTION_NUMBER = "section_number";
        
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_day_meal, container, false);
            return rootView;
        }
    }

    // 인터넷 연결 상태 체크
    public boolean isNetworkConnected(Context context) {
        
        boolean isConnected = false;

        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mobile.isConnected() || wifi.isConnected()) {
            isConnected = true;
        } else {
            isConnected = false;
        }
        return isConnected;
    }
}
