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

import com.e.hiketogether.Models.Settings;
import com.e.hiketogether.R;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapTrailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapTrailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapTrailFragment extends Fragment {
    // Static VARIABLES
    private static final String TAG = "MAP_TRAILS_FRAGMENT";

    // VARIABLES
    private String username;
    private Bundle bundle;
    private Settings settings;
    private List<Integer> favTrails;
    private View rootView;
    private double latitude;
    private double longitude;
    private LocationManager lm;
    private Location location;
    private ArcGISMap map;
    private OnFragmentInteractionListener mListener;

    // add map view
    private MapView mMapView;

    public MapTrailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param account User Account
     * @return A new instance of fragment MapTrailFragment.
     */
    public static MapTrailFragment newInstance(Bundle account) {
        MapTrailFragment fragment = new MapTrailFragment();
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
            bundle = getArguments().getBundle("settings");
        }

        Log.d(TAG, "Account " + username + " received");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_map_trail, container, false);

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && getActivity()
                .checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            latitude = 43.826069;
            longitude = -111.789528;

            Log.d(TAG, "Made it to onCreateView.");
            // Inflate the layout for this fragment
        }
        else {

            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        // create the map view
        mMapView = rootView.findViewById(R.id.mapview);

        if (mMapView != null) {
            Basemap.Type basemapType = Basemap.Type.STREETS_VECTOR;
            int levelOfDetail = 11;
            map = new ArcGISMap(basemapType, latitude, longitude, levelOfDetail);
            mMapView.setMap(map);
        }

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
        // Called when the fragment and Main activity interact
        void onFragmentInteraction(Uri uri);
    }
}
