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

import androidx.fragment.app.Fragment;

import com.e.hiketogether.Models.TrailList;
import com.e.hiketogether.Presenters.Managers.TrailManager;
import com.e.hiketogether.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrailSearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrailSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrailSearchFragment extends Fragment {
    // Static VARIABLES
    private static final String TAG = "TRAIL_VIEW_FRAGMENT";

    // VARIABLES
    private String username;
    private List<Integer> favTrails;
    private List<String> settings;
    LocationManager lm;
    Location location;
    double longitude;
    double latitude;

    TrailManager tm;

    private OnFragmentInteractionListener mListener;

    public TrailSearchFragment() {
        // empty constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param account User Account.
     * @return A new instance of fragment TrailSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrailSearchFragment newInstance(Bundle account) {
        TrailSearchFragment fragment = new TrailSearchFragment();
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
        Log.d(TAG, "Account " + username + " received");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        tm = new TrailManager(Double.toString(43.826069), Double.toString(-111.789528), getContext());

        // variables to get our long and lat instead of hard coding in Rexburg's
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.


            Log.d(TAG, "Made it to onCreateView.");
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_trail_search, parent, false);
        }
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();

        // end location code
        Log.d(TAG, "Made it to onCreateView.");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trail_search, parent, false);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //This is called when the search button is pressed
    public void onSearch(View view) {
        tm.setLat(latitude /*43.826069*/);
        tm.setLon(longitude /*-111.789528*/);


        TrailList tl = tm.getTrails();
    }
}
