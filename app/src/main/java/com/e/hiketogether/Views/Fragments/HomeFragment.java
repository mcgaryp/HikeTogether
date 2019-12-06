package com.e.hiketogether.Views.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.hiketogether.Models.ItemOffsetDecoration;
import com.e.hiketogether.Models.TrailList;
import com.e.hiketogether.Presenters.Adapters.TrailAdapter;
import com.e.hiketogether.Presenters.Interfaces.interact;
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

// TODO Has a list of local trails in a drawer and a google map screen that is capable of
//  displaying location of the trail on the map
public class HomeFragment extends Fragment implements interact {
    // Static Final VARIABLES
    private static final String TAG = "HOME_FRAGMENT";

    // VARIABLES
    private OnFragmentInteractionListener mListener;
    private String username;
    private List<Integer> favTrails;
    private List<String> settings;
    private RecyclerView recyclerView;
    private TrailAdapter adapter;
    private TrailList tl;
    private View rootView;
    private LocationManager locationManager;
    private Location location;
    private ProgressBar progressBar;

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
            username = getArguments().getString("username");
            favTrails = getArguments().getIntegerArrayList("trails");
            settings = getArguments().getStringArrayList("settings");
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
        TrailManager tm = new TrailManager();
        locationManager = (LocationManager) getActivity()
                .getSystemService(getContext().LOCATION_SERVICE);
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && getActivity()
                .checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            tm.setLat(43.826069);
            tm.setLon(-111.789528);
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

        adapter = new TrailAdapter(getActivity(), tl);
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
        // TODO: Update argument type and name
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
    @SuppressLint("WrongConstant")
    public void displayProgressBar() {
        Log.d(TAG, "Displaying ProgressBar.");
        progressBar.setVisibility(0);
        progressBar.bringToFront();
    }

    // Hide progress bar
    @Override
    @SuppressLint("WrongConstant")
    public void  hideProgressBar() {
        Log.d(TAG, "Hiding ProgressBar.");
        progressBar.setVisibility(8);
    }

    // disable the screen so users cannot touch it and interact
    @Override
    public void setTouchDisabled() {
        Log.d(TAG, "Setting the touch screen to: DISABLED");
        displayProgressBar();
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    // Enable the screen so users can touch it and interact
    @Override
    public void setTouchEnabled() {
        Log.d(TAG, "Setting the touch screen to: ENABLED");
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        hideProgressBar();
    }
}
