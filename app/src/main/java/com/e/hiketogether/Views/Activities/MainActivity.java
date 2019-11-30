package com.e.hiketogether.Views.Activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.hiketogether.Models.TrailList;
import com.e.hiketogether.R;
import com.e.hiketogether.Views.Fragments.FavoritesFragment;
import com.e.hiketogether.Views.Fragments.HomeFragment;
import com.e.hiketogether.Views.Fragments.MapTrailFragment;
import com.e.hiketogether.Views.Fragments.SettingsFragment;
import com.e.hiketogether.Views.Fragments.TrailSearchFragment;
import com.e.hiketogether.Views.Fragments.TrailViewFragment;

/**
 * PURPOSE:
 *      This class will display the home screen including a map and buttons that will transfer the
 *      user to different fragments such as the settings, Favorites, Trails, Map and Search Bar
 */
public class MainActivity extends AppCompatActivity
                          implements TrailSearchFragment.OnFragmentInteractionListener,
                                     HomeFragment.OnFragmentInteractionListener,
                                     FavoritesFragment.OnFragmentInteractionListener,
                                     MapTrailFragment.OnFragmentInteractionListener,
                                     TrailViewFragment.OnFragmentInteractionListener {

    // VARIABLES
    private static final String TAG = "MAIN_ACTIVITY"; //Log tag

    private static final int LOGIN_REQUEST = 100; //Request code for LoginActivity
    private static final int LOGIN_FAILED = 0;  //resultCode for MainActivity

    private FragmentManager fm;
    private String currentFragment = null;
    private Bundle account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivityForResult(loginIntent, LOGIN_REQUEST);

        fm = getSupportFragmentManager();
        //Initialize the screen to be on the home fragment initially
        changeView(findViewById(R.id.toolbarHomeButton));
    }

    //When a button in the toolbar is clicked, this will open the correct fragment/ activity
    public void changeView(View view) {
        Fragment template_fragment = new Fragment();

        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.template_fragment, template_fragment);

        //They clicked the home button, open the home fragment
        if (view == findViewById(R.id.toolbarHomeButton) && !currentFragment.equals("HOME")) {
            template_fragment = new HomeFragment();
            fm.findFragmentByTag("HOME");

            ft.replace(R.id.template_fragment, template_fragment);
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            ft.commit();

            currentFragment = "HOME";
        }
        //They clicked the trail search button, open the search fragment
        else if (view == findViewById(R.id.toolbarSearchButton) && !currentFragment.equals("TRAIL_SEARCH")) {
            template_fragment = new TrailSearchFragment();
            ft.replace(R.id.template_fragment, template_fragment);
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            ft.commit();

            currentFragment = "TRAIL_SEARCH";
        }
        //They clicked the favorites button, open the favorites fragment
        else if (view == findViewById(R.id.toolbarFavoritesButton) && !currentFragment.equals("FAVORITES")) {
            template_fragment = new FavoritesFragment();
            ft.replace(R.id.template_fragment, template_fragment);
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            ft.commit();

            currentFragment = "FAVORITES";
        }
        //They clicked the map button, open the map trail fragment
        else if (view == findViewById(R.id.toolbarMapButton) && !currentFragment.equals("MAP_TRAIL")) {
            template_fragment = new TrailViewFragment();
            ft.replace(R.id.template_fragment, template_fragment);
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            ft.commit();

            currentFragment = "MAP_TRAIL";
        }
        //They clicked the settings icon, open the settings activity
        else if (view == findViewById(R.id.toolbarSettingButton) && !currentFragment.equals("SETTINGS")) {
            template_fragment = new SettingsFragment();
            ft.replace(R.id.template_fragment, template_fragment);
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            ft.commit();

            currentFragment = "SETTINGS";
        }
    }

    public void initiateTrailSearch(TrailList tl) {
        Fragment template_fragment = new TrailViewFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.template_fragment, template_fragment);
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.commit();
    }

    //When the Login Activity is closed, it will return information to this function
    //Two codes indicate whether the process was successful, and any important data
    //Is return through the intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Log the status of the result & request
        Log.d(TAG, "on activity result success.");
        Log.d(TAG, "requestCode: " + requestCode + "\nresultCode: " + resultCode);

        if (requestCode == LOGIN_REQUEST) {
            Log.d(TAG, "LOGIN_Succcessful if statement");
            if (resultCode == RESULT_OK) {
                //The user was logged in!
                //The intent will have pertinent information that needs to be passed back in it
                Log.d(TAG, "RESULT oK beginning if.");
                account = data.getBundleExtra("account");
                Log.d(TAG, "Account username: " + account.getString("username"));
            }
            if (resultCode == LOGIN_FAILED) {
                displayToast("Failed to Login");
            }
        }

    }

    private void displayToast(String message) {
        new Toast(getApplicationContext()).makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        return;
    }
}
