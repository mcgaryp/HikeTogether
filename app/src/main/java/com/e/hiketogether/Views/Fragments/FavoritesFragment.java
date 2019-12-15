package com.e.hiketogether.Views.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.hiketogether.Models.Account;
import com.e.hiketogether.Models.ItemOffsetDecoration;
import com.e.hiketogether.Models.Trail;
import com.e.hiketogether.Models.TrailList;
import com.e.hiketogether.Presenters.Adapters.TrailAdapter;
import com.e.hiketogether.Presenters.Helpers.FireBaseHelper;
import com.e.hiketogether.Presenters.Interfaces.LoadAccountListener;
import com.e.hiketogether.Presenters.Interfaces.UpdateAccountListener;
import com.e.hiketogether.Presenters.Managers.TrailManager;
import com.e.hiketogether.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoritesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {
    // Static VARIABLES
    private static final String TAG = "FAVORITE_TRAILS_FRAGMENT";

    // Private VARIABLES
    private String username;
    private List<Integer> favTrails;
    private Bundle settings;
    private OnFragmentInteractionListener mListener;
    private Account account;
    private RecyclerView recyclerView;
    private View rootView;
    private TrailAdapter adapter;
    private TrailList tl;
    private ProgressBar progressBar;
    private TrailManager tm;
    private LocationManager locationManager;
    private Trail trail;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param account user acount.
     * @return A new instance of fragment FavoritesFragment.
     */
    public static FavoritesFragment newInstance(Bundle account) {
        FavoritesFragment fragment = new FavoritesFragment();
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
        Log.d(TAG, "Account " + username + " recieved.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        new FireBaseHelper(username).loadAccount(new LoadAccountListener() {
            @Override
            public void onSuccess(Account account) {
            }

            @Override
            public void onFail() {
            }
            });


           // tl = new TrailList();
            Log.d(TAG, "print size again: " + account.getFavTrails());
            tl = (TrailList) account.getFavTrails();


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
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart.");
        recyclerView = rootView.findViewById(R.id.favoriteRecyclerView);
        recyclerView.setHasFixedSize(true);

        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        adapter = new TrailAdapter(getActivity(), tl, account);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

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
        // Interaction between fragment and Main Activity
        void onFragmentInteraction(Uri uri);
    }
}
