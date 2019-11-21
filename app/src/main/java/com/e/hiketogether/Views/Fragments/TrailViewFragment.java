package com.e.hiketogether.Views.Fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.hiketogether.Models.DividerItemDecoration;
import com.e.hiketogether.Models.Trail;
import com.e.hiketogether.Models.TrailList;
import com.e.hiketogether.Presenters.Adapters.TrailAdapter;
import com.e.hiketogether.Presenters.Managers.TrailManager;
import com.e.hiketogether.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrailViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrailViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrailViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private TrailAdapter adapter;
    private TrailList tl;
    private View rootView;

    private OnFragmentInteractionListener mListener;

    public TrailViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrailViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrailViewFragment newInstance(String param1, String param2) {
        TrailViewFragment fragment = new TrailViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Implement everything needed for the recyclerView to work

//        TrailManager tm = new TrailManager();
//        tm.setLat("lat=" + 43.826069);
//        tm.setLon("lon=" + -111.789528);
//        Log.d("TRAIL_VIEW_FRAGMENT", "Getting Trails");
//        tl = tm.getTrails();

        tl = new TrailList();
        for (int i = 0; i < 10; i++) {
            tl.addTrail(new Trail(
                "Pioneer Cabin Loop",
                "A rugged, steep climb and descent taking you to and from the Pioneer Cabin",
                "Intermediate/Difficult",
                5.0f,
                4,
                "Hyndman Peak",
                8.8f,
                "https://www.hikingproject.com/photo/7028488/lupine-are-abundant-along-the-pioneer-cabin-trail"));


            //Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
            //RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
            //recyclerView.addItemDecoration(dividerItemDecoration);


        }

        this.rootView = inflater.inflate(R.layout.fragment_trail_view, container, false);

        // Inflate the layout for this fragment
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

    @Override
    public void onStart() {
        super.onStart();

        recyclerView = rootView.findViewById(R.id.trailList_recyclerView);
        recyclerView.setHasFixedSize(true);

        adapter = new TrailAdapter(getActivity(), tl);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
