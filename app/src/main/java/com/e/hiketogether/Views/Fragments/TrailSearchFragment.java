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
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.hiketogether.Models.ItemOffsetDecoration;
import com.e.hiketogether.Models.Settings;
import com.e.hiketogether.Presenters.Helpers.CurrentLocationHelper;

import com.e.hiketogether.Models.TrailList;
import com.e.hiketogether.Presenters.Adapters.TrailAdapter;
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
    private Bundle bundle;
    private Settings settings;
    LocationManager lm;
    Location location;
    double longitude;
    double latitude;
    private CurrentLocationHelper clh;
    private View rootView;
    private Button search;
    private String address;
    private TrailList tl;

    private RecyclerView recyclerView;
    private TrailAdapter adapter;

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
            bundle = getArguments().getBundle("settings");
        }
        Log.d(TAG, "Account " + username + " received");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        tm = new TrailManager(Double.toString(43.826069), Double.toString(-111.789528),
                getContext());

        this.rootView = inflater.inflate(R.layout.fragment_trail_search, parent, false);

        // variables to get our long and lat instead of hard coding in Rexburg's
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && getActivity()
                .checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.

            latitude = 43.826069;
            longitude = -111.789528;

            Log.d(TAG, "Made it to onCreateView.");
            // Inflate the layout for this fragment
            return rootView;
        }
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();

        // end location code
        Log.d(TAG, "Made it to onCreateView.");
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
        search = rootView.findViewById(R.id.trailSearch_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tm.setLat(latitude);
                tm.setLon(longitude);

                EditText et = rootView.findViewById(R.id.trailSearch_editText);
                address = et.getText().toString();

                System.out.println("Address: " + address);
                tl = tm.getTrails();

                recyclerView = rootView.findViewById(R.id.locationSearchRecyclerView);
                recyclerView.setHasFixedSize(true);

                adapter = new TrailAdapter(getActivity(), tl);
                recyclerView.setAdapter(adapter);

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
                recyclerView.addItemDecoration(itemDecoration);

                //setTouchEnabled();
            }
        });
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
        // Called when the fragment interacts with the Main activity
        void onFragmentInteraction(Uri uri);
    }
}