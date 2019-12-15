package com.e.hiketogether.Views.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.e.hiketogether.Models.Account;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.hiketogether.Models.ItemOffsetDecoration;
import com.e.hiketogether.Models.TrailList;
import com.e.hiketogether.Presenters.Adapters.TrailAdapter;
import com.e.hiketogether.Presenters.Interfaces.Interact;
import com.e.hiketogether.Presenters.Managers.TrailManager;
import com.e.hiketogether.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements Interact {
    // Static Final VARIABLES
    private static final String TAG = "HOME_FRAGMENT";

    // VARIABLES
    private OnFragmentInteractionListener mListener;
    private String username;
    private List<Integer> favTrails;
    private Bundle settings;
    private RecyclerView recyclerView;
    private TrailAdapter adapter;
    private TrailList tl;
    private View rootView;
    private LocationManager locationManager;
    private Location location;
    private Account account;

    private ProgressBar progressBar;
    private TrailManager tm;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param account Account from the activity
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance(Bundle account) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = account;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account = new Account(getArguments());
            username = getArguments().getString("username");
            favTrails = getArguments().getIntegerArrayList("trails");
            settings = getArguments().getBundle("settings");
        }
        Log.d(TAG, "Account " + username + " received.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = rootView.findViewById(R.id.homeProgressBar);
        setTouchDisabled();

        //Implement everything needed for the recyclerView to work
        tm = new TrailManager(Double.toString(43.826069), Double.toString(-111.789528), getContext());

        locationManager = (LocationManager) getActivity()
                .getSystemService(getContext().LOCATION_SERVICE);
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && getActivity()
                .checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, "Setting location to default location");
            tl = new TrailList();
            tl = tm.getTrails();

            // Inflate the layout for this fragment
            return rootView;

        }

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        tm.setLat(location.getLatitude());
        tm.setLon(location.getLongitude());
        Log.d(TAG, "Setting location to current location.");
        tl = new TrailList();
        tl = tm.getTrails();

        // Inflate the layout for this fragment
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart.");
        recyclerView = rootView.findViewById(R.id.homeRecyclerView);
        recyclerView.setHasFixedSize(true);

        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        adapter = new TrailAdapter(getActivity(), tl, account);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

        setTouchEnabled();
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
        // Called on when there is an interaction between main and fragment class
        void onFragmentInteraction(Uri uri);
    }


    // Display a toast
    @Override
    public void displayToast(String message) {
        Log.d(TAG, "Sending toast: " + message);
        new Toast(getActivity().getApplicationContext())
                .makeText(getActivity().getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    // Display progress bar
    @Override
    public void displayProgressBar() {
        Log.d(TAG, "Displaying ProgressBar.");
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();
    }

    // Hide progress bar
    @Override
    public void  hideProgressBar() {
        Log.d(TAG, "Hiding ProgressBar.");
        progressBar.setVisibility(View.GONE);
    }

    // disable the screen so users cannot touch it and Interact
    @Override
    public void setTouchDisabled() {
        Log.d(TAG, "Setting the touch screen to: DISABLED");
        displayProgressBar();
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    // Enable the screen so users can touch it and Interact
    @Override
    public void setTouchEnabled() {
        Log.d(TAG, "Setting the touch screen to: ENABLED");
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        hideProgressBar();
    }
}
