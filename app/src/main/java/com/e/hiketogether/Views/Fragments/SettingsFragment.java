package com.e.hiketogether.Views.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.e.hiketogether.Models.Account;
import com.e.hiketogether.Presenters.Managers.SettingsManager;
import com.e.hiketogether.R;
import com.e.hiketogether.Views.SpecializedViews.UniqueEditText;

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
    private static final int CREATE_REQUEST = 200;  // Request code or CreateLogin

    // VARIABLES
    private Bundle bundle;
    private Account account;
    private SettingsManager manager;
    private ImageView profilePicture;
    private Button changeFname;
    private Button changeLname;
    private Button changeEmail;
    private Button changeProfile;
    private Button changePassword;
    private Button login;
    private Button createAccount;
    private UniqueEditText firstNameView;
    private UniqueEditText lastNameView;
    private UniqueEditText usernameView;
    private UniqueEditText emailView;
    private UniqueEditText passwordView;
    private UniqueEditText verifyPasswordView;
    private Spinner backgroundSpinner;
    private Spinner distanceSpinner;
    private RelativeLayout hiddenView;
    private boolean loggedIn;

    private View rootView;
    private OnFragmentInteractionListener mListener;
//    private Bundle state;
//    private ViewGroup group;
//    private LayoutInflater inflater;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param account User Account in bundle form
     * @return A new instance of fragment SettingsFragment.
     */
    public static SettingsFragment newInstance(Bundle account, boolean loggedIn) {
        SettingsFragment fragment = new SettingsFragment();
        // Set arguments for fragment
        Bundle args = account;
        args.putBoolean("loggedIn", loggedIn);
        Log.d(TAG, "Making new fragment with account: " + account.getString("username"));
        fragment.setArguments(args);
        return fragment;
    }

    // Sets the a few key variables in the class almost like a constructor
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the variables from the arguments
        if (getArguments() != null) {
            Log.d(TAG,"Setting Variables from bundle");
            setAccount(new Account(getArguments()));
            setLoggedIn(getArguments().getBoolean("loggedIn"));
            Log.d(TAG, "First Name: " + getAccount().getSettings().getFirstName()
                    + "\nLast Name: " + getAccount().getSettings().getLastName() + "\nDistance: "
                    + getAccount().getSettings().getDistance() + "\nBackground: "
                    + getAccount().getSettings().getBackground());
        }
    }

    // Sets up the view for the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Creating the Right View");
        // Know what kind of view needs added
//        setInflater(inflater);
//        setViewGroup(container);
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

    // No idea
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

    // Set the variables. Acts like a constructor
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Account " + getAccount().getUsername() + " received");
//        if (loggedIn) {
            Log.d(TAG, "Setting up personal settings with Account on file.");
            manager = new SettingsManager(this);

            // Set Relative Layout
            hiddenView = rootView.findViewById(R.id.expandedPassword);
            hiddenView.setVisibility(View.GONE);

            // Set ImageView
            profilePicture = rootView.findViewById(R.id.profileImage);

            // Set TextViews
            firstNameView = rootView.findViewById(R.id.userFirstName);
            lastNameView = rootView.findViewById(R.id.userLastName);
            usernameView = rootView.findViewById(R.id.userUsername);
            emailView = rootView.findViewById(R.id.userEmail);
            passwordView = rootView.findViewById(R.id.passwordSettings);
            verifyPasswordView = rootView.findViewById(R.id.verifyPasswordSettings);

            // Set Buttons
            changeFname = rootView.findViewById(R.id.changeFirstNameButton);
            changeLname = rootView.findViewById(R.id.changeLastNameButton);
            changeEmail = rootView.findViewById(R.id.changeEmailButton);
            changeProfile = rootView.findViewById(R.id.changeProfilePictureButton);
            changePassword = rootView.findViewById(R.id.changePasswordButton);

            // Set Spinners
            backgroundSpinner = rootView.findViewById(R.id.backgroundSpinner);
            distanceSpinner = rootView.findViewById(R.id.distanceSpinner);

            // Set OnClickListeners
            manager.setClick(changeFname, firstNameView, "first");
            manager.setClick(changeLname, lastNameView, "last");
            manager.setClick(changeEmail, emailView, "email");
            manager.setClick(changePassword, passwordView, verifyPasswordView, hiddenView);
            manager.setClick(changeProfile, profilePicture);

            // Set Spinner OnSelectListeners
            // TODO Select the background from spinner
            backgroundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                /**
                 * <p>Callback method to be invoked when an item in this view has been
                 * selected. This callback is invoked only when the newly selected
                 * position is different from the previously selected position or if
                 * there was no selected item.</p>
                 * <p>
                 * Implementers can call getItemAtPosition(position) if they need to access the
                 * data associated with the selected item.
                 *
                 * @param parent   The AdapterView where the selection happened
                 * @param view     The view within the AdapterView that was clicked
                 * @param position The position of the view in the adapter
                 * @param id       The row id of the item that is selected
                 */
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getAccount().getSettings().setBackground(backgroundSpinner.getSelectedItem()
                            .toString());
                    Log.d(TAG, "Updating the background. Background set to: "
                            + getAccount().getSettings().getBackground());
                }

                /**
                 * Callback method to be invoked when the selection disappears from this
                 * view. The selection can disappear for instance when touch is activated
                 * or when the adapter becomes empty.
                 *
                 * @param parent The AdapterView that now contains no selected item.
                 */
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // TODO Select the distance from spinner
            distanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                /**
                 * <p>Callback method to be invoked when an item in this view has been
                 * selected. This callback is invoked only when the newly selected
                 * position is different from the previously selected position or if
                 * there was no selected item.</p>
                 * <p>
                 * Implementers can call getItemAtPosition(position) if they need to access the
                 * data associated with the selected item.
                 *
                 * @param parent   The AdapterView where the selection happened
                 * @param view     The view within the AdapterView that was clicked
                 * @param position The position of the view in the adapter
                 * @param id       The row id of the item that is selected
                 */
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getAccount().getSettings().setDistance(distanceSpinner.getSelectedItem().toString());
                    Log.d(TAG, "Updating the distance. Distance set to: " + getAccount().getSettings().getDistance());
                }

                /**
                 * Callback method to be invoked when the selection disappears from this
                 * view. The selection can disappear for instance when touch is activated
                 * or when the adapter becomes empty.
                 *
                 * @param parent The AdapterView that now contains no selected item.
                 */
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Log.d(TAG, "Nothing is selected");
                }
            });

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
        // Username
        if (getAccount().getUsername().isEmpty()) usernameView.setText("No Username");
        else usernameView.setText(getAccount().getUsername());
        // Email
        if (getAccount().getEmail().isEmpty()) emailView.setText("No E-Mail");
        else emailView.setText(getAccount().getEmail());
        // First Name
        if (getAccount().getSettings().getFirstName().isEmpty())
            firstNameView.setText("No First Name");
        else firstNameView.setText(getAccount().getSettings().getFirstName());
        // Last Name
        if (getAccount().getSettings().getLastName().isEmpty())
            lastNameView.setText("No Last Name");
        else lastNameView.setText(getAccount().getSettings().getLastName());

        // Spinners
        int position = getPosition(getAccount().getSettings().getBackground());
        backgroundSpinner.setSelection(position);
    }

    private int getPosition(String position) {
        int pos;
        if (position == "blueBackground") pos = 0;
        else if (position == "pinkBackground") pos = 1;
        else if (position == "orangeBackground") pos = 2;
        else if (position == "greenBackground") pos = 3;
        else pos = 0;
        return pos;
    }

    //When the Login Activity is closed, it will return information to this function
    //Two codes indicate whether the process was successful, and any important data
    //Is return through the intent
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        //Log the status of the result & request
//        Log.d(TAG, "on activity result success.");
//        Log.d(TAG, "requestCode: " + requestCode + "\nresultCode: " + resultCode);
//
//        if (requestCode == LOGIN_REQUEST) {
//            if (resultCode == getActivity().RESULT_OK) {
//                //The user was logged in!
//                //The intent will have pertinent information that needs to be passed back in it
//                bundle = data.getBundleExtra("account");
//                loggedIn = true;
//                Log.d(TAG, "Account username: " + bundle.getString("username"));
////                onCreateView(inflater,group,state);
//                newInstance(bundle, true);
//                rootView = inflater.inflate(R.layout.fragment_settings, group, false);
////                new SettingsFragment().newInstance(account, true);
//            }
//            if (resultCode == LOGIN_FAILED) {
//                new Toast(getContext()).makeText(getContext(), "Failed to Login",
//                        Toast.LENGTH_LONG).show();
//                loggedIn = false;
//            }
//        }
//
//        if (requestCode == CREATE_REQUEST) {
//            if (resultCode == getActivity().RESULT_OK) {
//                //The user's account was created!
//                //The intent will have pertinent information that needs to be passed back in it
//                Log.d(TAG, "Attempting to automatically login user");
//                loggedIn = true;
//                // do something with the intent here
//                bundle = data.getBundleExtra("account");
//                // Log the user in AUTOMATICALLY with the account they just created
//                Log.d(TAG, "Login in from Create Account Screen.");
////                newInstance(account);
//            }
//            if (resultCode == LOGIN_FAILED) {
//                new Toast(getContext()).makeText(getContext(),
//                        "Login Failed While Attempting to Create Account", Toast.LENGTH_LONG)
//                        .show();
//                loggedIn = false;
//            }
//        }
//    }

    /** Set the focus to this Specific
     * @param view
     */
    public void setFocus(String view) {
        if (view == "password") {

        }
        else if (view == "verifyPassword") {

        }
        else Log.d(TAG, "Focus was not set.");
    }

    // No idea
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // SETTERS
//    private void setInflater(LayoutInflater inflater) { this.inflater = inflater; }

//    private void setViewGroup(ViewGroup container)    { this.group = container;   }
    private void setAccount(Account account)          { this.account = account;   }
    private void setLoggedIn(boolean loggedIn)        { this.loggedIn = loggedIn; }

    // Display somthing to the user through a Toast
    public void displayToast(String message) {
        new Toast(getContext()).makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    // GETTERS
    public Account getAccount() {
        return account;
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
