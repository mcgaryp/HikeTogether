package com.e.hiketogether.Views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.hiketogether.R;
import com.e.hiketogether.Views.Activities.LoginActivity;
import com.e.hiketogether.Views.Activities.MainActivity;

import java.util.List;

import static android.app.Activity.RESULT_OK;

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
public class HomeFragment extends Fragment {
    // Static Final VARIABLES
    private static final String TAG = "HOME_FRAGMENT";
    private static final int LOGIN_REQUEST = 100; //Request code for LoginActivity
    private static final int LOGIN_FAILED = 0;  //resultCode for MainActivity
    private static final int LOGIN_SUCCESSFUL = 1; //resultCode for MainActivity

    // VARIABLES
    private OnFragmentInteractionListener mListener;
    private String username;
    private List<Integer> favTrails;
    private List<String> settings;

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
//        Log.d(TAG, "Made it.");
//        //Implement everything needed for the recyclerView to work
//        tm = new TrailManager();
//        // Get local lat and long
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener( getActivity(), new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location != null) {
//                            // Try and get their current location
//                            tm.setLat("lat=" + location.getLatitude());
//                            tm.setLon("lon=" + location.getLongitude());
//                            Log.d(TAG, "Got current location:\n\tlong: "
//                                    + tm.getLon() + "\n\tlat: " + tm.getLat());
//                        }
//                        else {
//                            // This is a default location
//                            tm.setLat("lat=" + 43.826069);
//                            tm.setLon("lon=" + -111.789528);
//                            Log.d(TAG, "Set default location:\n\tlong: "
//                                    + tm.getLon() + "\n\tlat: " + tm.getLat());
//                        }
//                    }
//                });

        // This is a default location
//         tm.setLat("lat=" + 43.826069);
//         tm.setLon("lon=" + -111.789528);
//         Log.d(TAG, "Set default location:\n\tlong: "
//                 + tm.getLon() + "\n\tlat: " + tm.getLat());
//        tl = new TrailList();
//        tl = tm.getTrails();
//        this.rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
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

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart.");

//        recyclerView = rootView.findViewById(R.id.homeTrailListRecyclerView);
//        if (recyclerView != null)
//            recyclerView.setHasFixedSize(true);
//        else {
//            Log.d(TAG, "Recycler View has failed to findById.");
////            return;
//        }
//
//        adapter = new TrailAdapter(getActivity(), tl);
//        recyclerView.setAdapter(adapter);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
//        recyclerView.addItemDecoration(itemDecoration);
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


}
