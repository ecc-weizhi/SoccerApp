package com.example.weizhi.playpalassignment.activity;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.weizhi.playpalassignment.R;

import java.util.List;

import Model.Player;
import Model.Team;

public class TacticBoardFragment extends Fragment implements View.OnDragListener, View.OnClickListener{
    private final String TAG = TacticBoardFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    private LinearLayout mFieldGrid;
    private RelativeLayout[][] mField = new RelativeLayout[4][5];
    private RelativeLayout p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11;
    private EditText mHeaderEdit;
    private ImageButton mEditButton;
    private ImageButton mCheckButton;
    private boolean isEditing;

    public TacticBoardFragment() {
    }


    public static TacticBoardFragment newInstance(){
        TacticBoardFragment myFragment = new TacticBoardFragment();

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tactic_board, container, false);

        if(getResources().getBoolean(R.bool.is_tablet))
            rootView.findViewById(R.id.tactic_fragment_players_layout).setVisibility(View.GONE);

        setupField(rootView);

        p1 = (RelativeLayout) inflater.inflate(R.layout.fragment_player_node, null);
        p2 = (RelativeLayout) inflater.inflate(R.layout.fragment_player_node, null);
        p3 = (RelativeLayout) inflater.inflate(R.layout.fragment_player_node, null);
        p4 = (RelativeLayout) inflater.inflate(R.layout.fragment_player_node, null);
        p5 = (RelativeLayout) inflater.inflate(R.layout.fragment_player_node, null);
        p6 = (RelativeLayout) inflater.inflate(R.layout.fragment_player_node, null);
        p7 = (RelativeLayout) inflater.inflate(R.layout.fragment_player_node, null);
        p8 = (RelativeLayout) inflater.inflate(R.layout.fragment_player_node, null);
        p9 = (RelativeLayout) inflater.inflate(R.layout.fragment_player_node, null);
        p10 = (RelativeLayout) inflater.inflate(R.layout.fragment_player_node, null);
        p11 = (RelativeLayout) inflater.inflate(R.layout.fragment_player_node, null);

        mHeaderEdit = (EditText) rootView.findViewById(R.id.header_edit);
        mEditButton = (ImageButton) rootView.findViewById(R.id.tactic_edit_button);
        mEditButton.setOnClickListener(this);
        mCheckButton = (ImageButton) rootView.findViewById(R.id.tactic_check_button);
        mCheckButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
        setupPlayerNode();
        if(bundle!=null){
            isEditing = bundle.getBoolean(getString(R.string.bundle_is_editing));
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(isEditing){
            mHeaderEdit.setFocusableInTouchMode(true);
            mHeaderEdit.requestFocus();
            mHeaderEdit.setSelection(mHeaderEdit.getText().length());
            mEditButton.setVisibility(View.GONE);
            mCheckButton.setVisibility(View.VISIBLE);
            isEditing = true;
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
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putBoolean(getString(R.string.bundle_is_editing), isEditing);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view){
        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        switch(view.getId()){
            case R.id.tactic_edit_button:
                mHeaderEdit.setFocusableInTouchMode(true);
                mHeaderEdit.requestFocus();
                inputMethodManager.showSoftInput(mHeaderEdit, InputMethodManager.SHOW_IMPLICIT);
                mHeaderEdit.setSelection(mHeaderEdit.getText().length());
                mEditButton.setVisibility(View.GONE);
                mCheckButton.setVisibility(View.VISIBLE);
                isEditing = true;
                break;

            case R.id.tactic_check_button:
                mHeaderEdit.setFocusableInTouchMode(false);
                mHeaderEdit.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                mEditButton.setVisibility(View.VISIBLE);
                mCheckButton.setVisibility(View.GONE);
                isEditing = false;
                break;
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        // Note to self: the View v parameter is the view that your shadow is hovering over.

        // Defines a variable to store the action type for the incoming event
        final int action = event.getAction();

        String label;
        switch(action) {
            case DragEvent.ACTION_DRAG_STARTED:
                label = event.getClipDescription().getLabel().toString();
                if(label.equals(getString(R.string.from_tactical)) || label.equals(getString(R.string.from_player)))
                    return true;
                else
                    return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                ((ImageView)v.findViewById(R.id.player_node_icon)).setColorFilter(Color.RED);
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                ((ImageView)v.findViewById(R.id.player_node_icon)).clearColorFilter();
                return true;

            case DragEvent.ACTION_DROP:
                ((ImageView)v.findViewById(R.id.player_node_icon)).clearColorFilter();
                label = event.getClipDescription().getLabel().toString();
                if(label.equals(getString(R.string.from_tactical))){
                    // originate from tactical board.
                    int originatingPlayerId = Integer.valueOf(event.getClipData().getItemAt(0).coerceToText(getContext()).toString());
                    int destinationPlayerId = (int) v.getTag();

                    if(destinationPlayerId == originatingPlayerId){
                        // nothing to do here.
                        return true;
                    }
                    else{
                        // swap tag
                        View originatingView = mFieldGrid.findViewWithTag(originatingPlayerId);
                        originatingView.setTag(destinationPlayerId);
                        v.setTag(originatingPlayerId);

                        // swap name
                        String originatingName = mListener.findPlayerNameById(originatingPlayerId);
                        String destinationName = mListener.findPlayerNameById(destinationPlayerId);
                        ((TextView)originatingView.findViewById(R.id.player_name)).setText(destinationName);
                        ((TextView)v.findViewById(R.id.player_name)).setText(originatingName);

                        mListener.swapPlayerFieldPosition(originatingPlayerId, destinationPlayerId);

                        return true;
                    }
                }
                else if(label.equals(getString(R.string.from_player))){
                    // originate from player list
                    int originatingPlayerId = Integer.valueOf(event.getClipData().getItemAt(0).coerceToText(getContext()).toString());
                    int destinationPlayerId = (int) v.getTag();

                    if(destinationPlayerId == originatingPlayerId){
                        // nothing to do here.
                        return true;
                    }
                    else{
                        String originatingName = mListener.findPlayerNameById(originatingPlayerId);
                        String destinationName = mListener.findPlayerNameById(destinationPlayerId);

                        if(mListener.findPlayerFieldPositionById(originatingPlayerId) != Player.NOT_FIELDED){
                            // Originating player is fielded. Therefore we can find the originatingView.
                            View originatingView = mFieldGrid.findViewWithTag(originatingPlayerId);
                            originatingView.setTag(destinationPlayerId);
                            ((TextView)originatingView.findViewById(R.id.player_name)).setText(destinationName);
                        }
                        v.setTag(originatingPlayerId);
                        ((TextView)v.findViewById(R.id.player_name)).setText(originatingName);
                        mListener.swapPlayerFieldPosition(originatingPlayerId, destinationPlayerId);

                        return true;
                    }
                }
                else{
                    return false;
                }

            case DragEvent.ACTION_DRAG_ENDED:
                return true;

            default:
                Log.e(TAG, "Unknown action type received.");
                break;
        }

        return false;
    }

    private void setupField(View rootView){
        mFieldGrid = (LinearLayout) rootView.findViewById(R.id.field_grid);
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                mField[i][j] = ((RelativeLayout)((LinearLayout) mFieldGrid.getChildAt(i)).getChildAt(j));
            }
        }
    }

    private void setupPlayerNode(){
        Team team = mListener.getTeam();
        List<Player> playerList = team.getPlayerList();
        for(Player p: playerList){
            switch(p.getFieldPosition()){
                case 1:
                    p1.setTag(p.getId());
                    ((TextView)p1.findViewById(R.id.player_name)).setText(p.getName());
                    break;
                case 2:
                    p2.setTag(p.getId());
                    ((TextView)p2.findViewById(R.id.player_name)).setText(p.getName());
                    break;
                case 3:
                    p3.setTag(p.getId());
                    ((TextView)p3.findViewById(R.id.player_name)).setText(p.getName());
                    break;
                case 4:
                    p4.setTag(p.getId());
                    ((TextView)p4.findViewById(R.id.player_name)).setText(p.getName());
                    break;
                case 5:
                    p5.setTag(p.getId());
                    ((TextView)p5.findViewById(R.id.player_name)).setText(p.getName());
                    break;
                case 6:
                    p6.setTag(p.getId());
                    ((TextView)p6.findViewById(R.id.player_name)).setText(p.getName());
                    break;
                case 7:
                    p7.setTag(p.getId());
                    ((TextView)p7.findViewById(R.id.player_name)).setText(p.getName());
                    break;
                case 8:
                    p8.setTag(p.getId());
                    ((TextView)p8.findViewById(R.id.player_name)).setText(p.getName());
                    break;
                case 9:
                    p9.setTag(p.getId());
                    ((TextView)p9.findViewById(R.id.player_name)).setText(p.getName());
                    break;
                case 10:
                    p10.setTag(p.getId());
                    ((TextView)p10.findViewById(R.id.player_name)).setText(p.getName());
                    break;
                case 11:
                    p11.setTag(p.getId());
                    ((TextView)p11.findViewById(R.id.player_name)).setText(p.getName());
                    break;

                default:
                    break;
            }
        }

        PlayerNodeOnLongClickListener mPlayerNodeOnLongClickListener = new PlayerNodeOnLongClickListener();
        p1.setOnLongClickListener(mPlayerNodeOnLongClickListener);
        p2.setOnLongClickListener(mPlayerNodeOnLongClickListener);
        p3.setOnLongClickListener(mPlayerNodeOnLongClickListener);
        p4.setOnLongClickListener(mPlayerNodeOnLongClickListener);
        p5.setOnLongClickListener(mPlayerNodeOnLongClickListener);
        p6.setOnLongClickListener(mPlayerNodeOnLongClickListener);
        p7.setOnLongClickListener(mPlayerNodeOnLongClickListener);
        p8.setOnLongClickListener(mPlayerNodeOnLongClickListener);
        p9.setOnLongClickListener(mPlayerNodeOnLongClickListener);
        p10.setOnLongClickListener(mPlayerNodeOnLongClickListener);
        p11.setOnLongClickListener(mPlayerNodeOnLongClickListener);

        p1.setOnDragListener(this);
        p2.setOnDragListener(this);
        p3.setOnDragListener(this);
        p4.setOnDragListener(this);
        p5.setOnDragListener(this);
        p6.setOnDragListener(this);
        p7.setOnDragListener(this);
        p8.setOnDragListener(this);
        p9.setOnDragListener(this);
        p10.setOnDragListener(this);
        p11.setOnDragListener(this);

        mField[0][1].addView(p1);
        mField[0][2].addView(p2);
        mField[0][3].addView(p3);
        mField[1][0].addView(p4);
        mField[1][1].addView(p5);
        mField[1][3].addView(p6);
        mField[1][4].addView(p7);
        mField[2][1].addView(p8);
        mField[2][2].addView(p9);
        mField[2][3].addView(p10);
        mField[3][2].addView(p11);
    }

    public interface OnFragmentInteractionListener {
        Team getTeam();
        String findPlayerNameById(int id);
        int findPlayerFieldPositionById(int id);
        void swapPlayerFieldPosition(int firstPlayerId, int secondPlayerId);
    }

    private class PlayerNodeOnLongClickListener implements View.OnLongClickListener{
        @Override
        public boolean onLongClick(View v) {
            ClipData dragData = ClipData.newPlainText(getString(R.string.from_tactical), String.valueOf( v.getTag() ));

            // Instantiates the drag shadow builder.
            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);

            // Starts the drag
            v.startDrag(dragData,   // the data to be dragged
                    myShadow,       // the drag shadow builder
                    null,           // no need to use local data
                    0               // flags (not currently used, set to 0)
            );

            return false;
        }
    }
}
