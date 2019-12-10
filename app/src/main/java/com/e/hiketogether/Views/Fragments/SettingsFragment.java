package com.e.hiketogether.Views.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.e.hiketogether.Models.Account;
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

    // VARIABLES
    private Account account;
    private String username;
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String background;
    private String distance;
    private List<Integer> favTrails;
    private List<String> settings;
    private SettingsManager manager;
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
    private Spinner backgroundSpinner;
    private Spinner distanceSpinner;

    private View rootView;
    private OnFragmentInteractionListener mListener;

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
    public static SettingsFragment newInstance(Bundle account) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = account;
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Creating the Right View");
        // Inflate the layout for this fragment
        if (username == null)
            rootView = inflater.inflate(R.layout.fragment_settings_new_login, container, false);
        else
            rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        if (username != null) {
            Log.d(TAG, "Setting up personal settings with Account on file.");
            manager = new SettingsManager(this);
//TODO Fix the DisableEditText class so that I can use it here

//            // TextViews
//            firstNameView = rootView.findViewById(R.id.userFirstName);
//            lastNameView = rootView.findViewById(R.id.userLastName);
//            usernameView = rootView.findViewById(R.id.userUsername);
//            emailView = rootView.findViewById(R.id.userEmail);
//
//            // Buttons
//            changeFname = rootView.findViewById(R.id.changeFirstNameButton);
//            changeFname.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, "Attempting to change the First Name of user");
//                    firstNameView.changeState();
//                    // TODO Send new first name to settings to be saved
//                    manager.addFirstName("New First Name");
//                }
//            });
//            changeLname = rootView.findViewById(R.id.changeLastNameButton);
//            changeLname.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, "Attempting to change the Last Name of user");
//                    lastNameView.changeState()
//                    // TODO Send new first name to settings to be saved
//                    manager.addLastName("New Last Name");
//                }
//            });
//            changeUsername = rootView.findViewById(R.id.changeUsernameButton);
//            changeUsername.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, "Attempting to change the Username of user");
//                    usernameView.changeState()
//                    // TODO Send new username to account to be saved
//                    manager.changeUsername("New Username");
//                }
//            });
//            changeEmail = rootView.findViewById(R.id.changeEmailButton);
//            changeEmail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, "Attempting to change the E-Mail of user");
//                    emailView.changeState()
//                    // TODO Send the new email to account to be saved
//                    manager.changeEmail("New Email");
//                }
//            });
//            changeProfile = rootView.findViewById(R.id.changeProfilePictureButton);
//            changeProfile.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, "Attempting to change the Profile Picture of user");
//
//                    // TODO Send the new picture to the settings to be saved
//                    manager.addPicture("New Picture");
//                }
//            });
//            changePassword = rootView.findViewById(R.id.changePasswordButton);
//            changePassword.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG,"Attempting to change Password of user");
//                    passwordView.changeState()
//                    // TODO Send the new passord to the settings to be saved
//                    manager.changePassword("New Password");
//                }
//            });
//
//            // Spinners
//            backgroundSpinner = rootView.findViewById(R.id.backgroundSpinner);
//            // TODO Select the background from spinner
//    //        backgroundSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//    //            @Override
//    //            public void onItemClick(AdapterView<?> parent, View view, int position,
//    //                                    long id) {
//    //                background = backgroundSpinner.getSelectedItem().toString();
//    //                Log.d(TAG, "Updating the background. Background set to: "
//    //                        + background);
//    //            }
//    //        });
//            distanceSpinner = rootView.findViewById(R.id.distanceSpinner);
//            // TODO Select the distance from spinner
//    //        distanceSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//    //            @Override
//    //            public void onItemClick(AdapterView<?> parent, View view, int position,
//    //                                    long id) {
//    //                distance = distanceSpinner.getSelectedItem().toString();
//    //                Log.d(TAG, "Updating the distance. Distance set to: " + distance);
//    //            }
//    //        });
//
//            // Set user info
//            usernameView.setText(username);
//            emailView.setText(email);
//            firstNameView.setText(firstName);
//            lastNameView.setText(lastName);
        }
        else {
            login = rootView.findViewById(R.id.settingsLoginButton);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Start Login activity with intent to be returned
                    Log.d(TAG, "Starting Login activity from Settings");
                }
            });
            createAccount = rootView.findViewById(R.id.settingsCreateAccountButton);
            createAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Start CreateAccount activity with intent to be returned
                    Log.d(TAG, "Starting CreateAccount activity from Settings");
                }
            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
