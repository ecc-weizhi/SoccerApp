package com.example.weizhi.playpalassignment.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhi.playpalassignment.R;

public class TacticBoardFragment extends Fragment {
    private final String TAG = TacticBoardFragment.class.getSimpleName();

    private OnListFragmentInteractionListener mListener;

    public TacticBoardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tactic_board, container, false);

        if(getResources().getBoolean(R.bool.is_tablet))
            rootView.findViewById(R.id.tactic_fragment_players_layout).setVisibility(View.GONE);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO add methods for communication between fragment and activity
    }
}
