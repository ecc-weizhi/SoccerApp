package com.example.weizhi.playpalassignment.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhi.playpalassignment.R;

import java.util.List;

import Model.Player;

public class PlayerFragment extends Fragment {
    private final String TAG = PlayerFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private View rootView;
    private int selectedPosition;

    public PlayerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_player, container, false);

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
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);

        if(bundle == null){
            selectedPosition = 0;
        }
        else{
            selectedPosition = bundle.getInt(getString(R.string.bundle_player_selected_position), 0);
        }

        RecyclerView playerListView = (RecyclerView) rootView.findViewById(R.id.list);
        playerListView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        playerListView.setAdapter(new PlayerRecyclerAdapter(mListener.getPlayerList(), this, selectedPosition));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.bundle_player_selected_position), selectedPosition);
    }

    public void notifySelected(int position){
        selectedPosition = position;
    }

    public interface OnFragmentInteractionListener {
        List<Player> getPlayerList();
    }
}
