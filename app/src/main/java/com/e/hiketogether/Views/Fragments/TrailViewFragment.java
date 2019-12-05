package com.e.hiketogether.Views.Fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.hiketogether.Models.ItemOffsetDecoration;
import com.e.hiketogether.Models.TrailList;
import com.e.hiketogether.Presenters.Adapters.TrailAdapter;
import com.e.hiketogether.Presenters.Managers.TrailManager;
import com.e.hiketogether.R;

import java.util.List;

public class TrailViewFragment extends Fragment {
    // Static VARIABLES
    private static final String TAG = "TRAIL_VIEW_FRAGMENT";

    // VARIABLES
    private String username;
    private List<Integer> favTrails;
    private List<String> settings;
    private RecyclerView recyclerView;
    private TrailAdapter adapter;
    private TrailList tl;
    private View rootView;
    private TrailManager tm;

    private OnFragmentInteractionListener mListener;

    public TrailViewFragment() {
        // Required empty public constructor
    }

    // Create a new instance of a trailView Fragment
    public static TrailViewFragment newInstance(Bundle account) {
        TrailViewFragment fragment = new TrailViewFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Implement everything needed for the recyclerView to work
        tm = new TrailManager();
        tm.setLat("lat=" + 43.826069);
        tm.setLon("lon=" + -111.789528);
        tl = new TrailList();
        tl = tm.getTrails();

        this.rootView = inflater.inflate(R.layout.fragment_trail_view, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
            //adapter.newAddedData(tl);
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
    public void onStart() {
        super.onStart();

        recyclerView = rootView.findViewById(R.id.trailList_recyclerView);
        recyclerView.setHasFixedSize(true);

        adapter = new TrailAdapter(getActivity(), tl);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
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
}
