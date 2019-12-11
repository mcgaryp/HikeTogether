package com.e.hiketogether.Views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.e.hiketogether.Presenters.Helpers.DisableEditText;
import com.e.hiketogether.Presenters.Managers.SettingsManager;
import com.e.hiketogether.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // Static VARIABLES
    private static final String TAG = "SETTINGS_FRAGMENT";
    private static final int LOGIN_REQUEST = 100;   //Request code for LoginActivity
    private static final int LOGIN_FAILED = 0;      //resultCode for MainActivity
    private static final int CREATE_RERQUEST = 200; // Request code for CreateAccount Activity

    // VARIABLES
    private Bundle account;
    private String username = "";
//    private String profilePicture = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String password = "";
    private String background = "";
    private String distance = "";
    private List<Integer> favTrails;
    private List<String> settings;
    private SettingsManager manager;
    private ImageView profilePicture;
    private Button changeFname;
    private Button changeLname;
    private Button changeUsername;
    private Button changeEmail;
    private Button changeProfile;
    private Button changePassword;
    private Button login;
    private Button createAccount;
    private DisableEditText firstNameView;
    private DisableEditText lastNameView;
    private DisableEditText usernameView;
    private DisableEditText emailView;
    private DisableEditText passwordView;
    private DisableEditText verifyPasswordView;
    private Spinner backgroundSpinner;
    private Spinner distanceSpinner;
    private RelativeLayout hiddenView;
    private boolean loggedIn;

    private View rootView;
    private OnFragmentInteractionListener mListener;
    private Bundle state;
    private ViewGroup group;
    private LayoutInflater inflater;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param account User Account
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    // Not sure this needs parameters right now...
    public static SettingsFragment newInstance(Bundle account, boolean loggedIn) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = account;
        args.putBoolean("loggedIn", loggedIn);
        Log.d(TAG, "Making new fragment with account: " + account.getString("username"));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d(TAG,"Setting Variables from bundle");
            username = getArguments().getString("username");
            email = getArguments().getString("email");
            password = getArguments().getString("password");
            favTrails = getArguments().getIntegerArrayList("trails");
            settings = getArguments().getStringArrayList("settings");
            loggedIn = getArguments().getBoolean("loggedIn");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Creating the Right View");

        setInflater(inflater);
        setViewGroup(container);
        // Inflate the layout for this fragment
//        if (loggedIn != null)
//            Log.d(TAG, loggedIn.toString());
//        if (!loggedIn) {
//            Log.d(TAG, "Creating new login screen");
//            rootView = inflater.inflate(R.layout.fragment_settings_new_login, container, false);
//        }
//        else {
            Log.d(TAG, "Creating settings screen");
            rootView = inflater.inflate(R.layout.fragment_settings, container, false);
//        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    // Set the variables and stuff acts like a constructor
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Account " + username + " received");
//        if (loggedIn) {
            Log.d(TAG, "Setting up personal settings with Account on file.");
            manager = new SettingsManager(this);

            // Set ImageView
            profilePicture = rootView.findViewById(R.id.profileImage);

            // Set TextViews
            firstNameView = rootView.findViewById(R.id.userFirstName);
            lastNameView = rootView.findViewById(R.id.userLastName);
            usernameView = rootView.findViewById(R.id.userUsername);
            emailView = rootView.findViewById(R.id.userEmail);

            // Set Buttons
            changeFname = rootView.findViewById(R.id.changeFirstNameButton);
            changeLname = rootView.findViewById(R.id.changeLastNameButton);
            changeUsername = rootView.findViewById(R.id.changeUsernameButton);
            changeEmail = rootView.findViewById(R.id.changeEmailButton);
            changeProfile = rootView.findViewById(R.id.changeProfilePictureButton);
            changePassword = rootView.findViewById(R.id.changePasswordButton);

            // Set OnClickListeners
            manager.setClick(changeFname, firstNameView, "first");
            manager.setClick(changeLname, lastNameView, "last");
            manager.setClick(changeUsername, usernameView, "username");
            manager.setClick(changeEmail, emailView, "email");
            manager.setClick(changePassword, passwordView, verifyPasswordView, hiddenView);
            manager.setClick(changeProfile, profilePicture);

            // Spinners
            backgroundSpinner = rootView.findViewById(R.id.backgroundSpinner);
            // TODO Select the background from spinner
    //        backgroundSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    //            @Override
    //            public void onItemClick(AdapterView<?> parent, View view, int position,
    //                                    long id) {
    //                background = backgroundSpinner.getSelectedItem().toString();
    //                Log.d(TAG, "Updating the background. Background set to: "
    //                        + background);
    //            }
    //        });
            distanceSpinner = rootView.findViewById(R.id.distanceSpinner);
            // TODO Select the distance from spinner
    //        distanceSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    //            @Override
    //            public void onItemClick(AdapterView<?> parent, View view, int position,
    //                                    long id) {
    //                distance = distanceSpinner.getSelectedItem().toString();
    //                Log.d(TAG, "Updating the distance. Distance set to: " + distance);
    //            }
    //        });

            // Set user info
            setViews();
        }
//        else {
//            login = rootView.findViewById(R.id.settingsLoginButton);
//            login.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // TODO Start Login activity with intent to be returned
//                    Log.d(TAG, "Starting Login activity from Settings");
//                    // Start the login
//                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
//                    startActivityForResult(loginIntent, LOGIN_REQUEST);
//                }
//            });
//            createAccount = rootView.findViewById(R.id.settingsCreateAccountButton);
//            createAccount.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // TODO Start CreateAccount activity with intent to be returned
//                    Log.d(TAG, "Starting CreateAccount activity from Settings");
//                    Intent createIntent = new Intent(getActivity(), CreateAccountActivity.class);
//                    startActivityForResult(createIntent, CREATE_RERQUEST);
//                }
//            });
//        }
//    }

    // Method for setting the initial values of the views
    private void setViews() {
        if (username == null || username.isEmpty()) usernameView.setText("No Username");
        else usernameView.setText(username);
        if (email == null || email.isEmpty()) emailView.setText("No E-Mail");
        else emailView.setText(email);
        if (firstName == null || firstName.isEmpty()) firstNameView.setText("No First Name");
        else firstNameView.setText(firstName);
        if (lastName == null || lastName.isEmpty()) lastNameView.setText("No Last Name");
        else lastNameView.setText(lastName);
    }

    //When the Login Activity is closed, it will return information to this function
    //Two codes indicate whether the process was successful, and any important data
    //Is return through the intent
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Log the status of the result & request
        Log.d(TAG, "on activity result success.");
        Log.d(TAG, "requestCode: " + requestCode + "\nresultCode: " + resultCode);

        if (requestCode == LOGIN_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                //The user was logged in!
                //The intent will have pertinent information that needs to be passed back in it
                account = data.getBundleExtra("account");
                loggedIn = true;
                Log.d(TAG, "Account username: " + account.getString("username"));
//                onCreateView(inflater,group,state);
                newInstance(account, true);
                rootView = inflater.inflate(R.layout.fragment_settings, group, false);
//                new SettingsFragment().newInstance(account, true);
            }
            if (resultCode == LOGIN_FAILED) {
                new Toast(getContext()).makeText(getContext(), "Failed to Login",
                        Toast.LENGTH_LONG).show();
                loggedIn = false;
            }
        }

        if (requestCode == CREATE_RERQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                //The user's account was created!
                //The intent will have pertinent information that needs to be passed back in it
                Log.d(TAG, "Attempting to automatically login user");
                loggedIn = true;
                // do something with the intent here
                account = data.getBundleExtra("account");
                // Log the user in AUTOMATICALLY with the account they just created
                Log.d(TAG, "Login in from Create Account Screen.");
//                newInstance(account);
            }
            if (resultCode == LOGIN_FAILED) {
                new Toast(getContext()).makeText(getContext(),
                        "Login Failed While Attempting to Create Account", Toast.LENGTH_LONG)
                        .show();
                loggedIn = false;
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // SETTERS
    private void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }
    private void setViewGroup(ViewGroup container) {
        this.group = container;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
