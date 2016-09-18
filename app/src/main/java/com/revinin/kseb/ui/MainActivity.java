package com.revinin.kseb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.revinin.kseb.R;
import com.revinin.kseb.ui.dummy.DummyContent;
import com.revinin.kseb.util.PrefUtil;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnHomeInteractionListener, RechargePackagesFragment.OnListFragmentInteractionListener {

    private static final int RC_SIGN_IN = 1;
    private static final int RC_RECHARGE = 2;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            case R.id.action_sign_in:
                signIn();
                return true;
            case R.id.action_sign_out:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void signIn() {
        this.startActivityForResult(new Intent(this, LoginActivity.class), RC_SIGN_IN);
    }

    @Override
    public void recharge() {
        mViewPager.setCurrentItem(1);
    }

    private void signOut() {

        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PrefUtil.getInstance(MainActivity.this).setIsLoggedIn(false);
                        Toast.makeText(MainActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                        MainActivity.this.invalidateOptionsMenu();
                        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(new Intent(LoginActivity.ACTION_SIGNED_OUT));
                    }
                }, 500);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_SIGN_IN:
                if (resultCode == RESULT_OK) {
//                    showMainActivity();
                    invalidateOptionsMenu();
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(LoginActivity.ACTION_SIGNED_IN));
                } else {
//                    this.finish();
                }
                break;

            case RC_RECHARGE:
                if(resultCode == RESULT_OK){
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(RechargeActivity.ACTION_RECHARGE));
                    mViewPager.setCurrentItem(0);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean loggedIn = PrefUtil.getInstance(this).isLoggedIn();
        menu.findItem(R.id.action_sign_in).setVisible(!loggedIn);
        menu.findItem(R.id.action_sign_out).setVisible(loggedIn);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.RechargePackage item) {
        Intent intent = new Intent(this, RechargeActivity.class);
        intent.putExtra(RechargeActivity.EXTRA_PACKAGE, item);
        this.startActivityForResult(intent, RC_RECHARGE);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return HomeFragment.newInstance();
                case 1:
                    return RechargePackagesFragment.newInstance(1);
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "HOME";
                case 1:
                    return "RECHARGES";
            }
            return null;
        }
    }
}
