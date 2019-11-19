package com.e.hiketogether.Views.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.e.hiketogether.Models.Account;
import com.e.hiketogether.R;
import com.e.hiketogether.Views.Fragments.FavoritesFragment;
import com.e.hiketogether.Views.Fragments.MapTrailFragment;
import com.e.hiketogether.Views.Fragments.TrailSearchFragment;

/**
 * PURPOSE:
 *      This class will display the home screen including a map and buttons that will transfer the
 *      user to different fragments such as the settings, Favorites, Trails, Map and Search Bar
 */
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HOME_ACTIVITY"; //Log tag
    private static final int LOGIN_REQUEST = 100; //Request code for LoginActivity
    private static final int LOGIN_FAILED = 0;  //resultCode for HomeActivity
    private static final int LOGIN_SUCCESSFUL = 1; //resultCode for HomeActivity
    private Account account;
    private Fragment template_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //FragmentManager fm = getFragmentManager();
        //FragmentTransaction ft = fm.beginTransaction();
        //ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        //ft.hide(template_fragment);  //make the fragment hidden when the activity starts so we can see the home page
        //ft.commit();
    }

    //When a button in the toolbar is clicked, this will open the correct fragment/ activity
    public void changeView(View view) {
        template_fragment = new Fragment();

        //They clicked the home button, open the home activity
        //TODO- See if it's possible to avoid opening a new HomeActivity if they are already there
        if (view == findViewById(R.id.toolbar_button1)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        //They clicked the trail search button, open the search fragment
        else if (view == findViewById(R.id.toolbar_button2)) {
            //fragment = new TrailSearchFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.template_fragment, template_fragment);
            ft.commit();
        }
        //They clicked the favorites button, open the favorites fragment
        else if (view == findViewById(R.id.toolbar_button3)) {
            //fragment = new MapTrailFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.template_fragment, template_fragment);
            ft.commit();
        }
        //They clicked the map button, open the map trail fragment
        else if (view == findViewById(R.id.toolbar_button4)) {
            //fragment = new FavoritesFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.template_fragment, template_fragment);
            ft.commit();
        }
        //They clicked the settings icon, open the settings activity
        else if (view == findViewById(R.id.toolbar_button5)) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
    }


    //When user clicks "Login", this function will create the Activity and receive the user's account
    public void openLoginActivity(View view) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivityForResult(loginIntent, LOGIN_REQUEST);
    }

    //When the Login Activity is closed, it will return information to this function
    //Two codes indicate whether the process was successful, and any important data
    //Is return through the intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_SUCCESSFUL) {
            if (resultCode == RESULT_OK) {
                //The user was logged in!
                //The intent will have pertinent information that needs to be passed back in it

                //TODO do something with the intent here
                // maybe save the account?
//                account = data.getAccount()
            }
        }
    }
}
