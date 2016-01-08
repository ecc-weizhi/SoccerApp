package com.example.weizhi.playpalassignment.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.weizhi.playpalassignment.R;

import java.util.ArrayList;
import java.util.List;

import Model.Player;
import Model.Team;

public class PlayerActivity extends AppCompatActivity implements PlayerFragment.OnFragmentInteractionListener {
    private final String TAG = PlayerActivity.class.getSimpleName();

    private Team mTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            Bundle mBundle = getIntent().getBundleExtra(getString(R.string.bundle_team));
            ArrayList<Integer> idList = new ArrayList<Integer>();
            ArrayList<String> nameList = new ArrayList<String>();
            ArrayList<Integer> jerseyList = new ArrayList<Integer>();
            ArrayList<Integer> fieldPositionList = new ArrayList<Integer>();

            idList = mBundle.getIntegerArrayList(getString(R.string.bundle_players_id));
            nameList = mBundle.getStringArrayList(getString(R.string.bundle_players_name));
            jerseyList = mBundle.getIntegerArrayList(getString(R.string.bundle_jersey_number));
            fieldPositionList = mBundle.getIntegerArrayList(getString(R.string.bundle_field_position));

            ArrayList<Player> playerList = new ArrayList<Player>();
            for(int i=0; i<idList.size(); i++){
                playerList.add(new Player(idList.get(i), nameList.get(i), jerseyList.get(i), fieldPositionList.get(i)));
            }

            mTeam = new Team(mBundle.getString(getString(R.string.bundle_team_name)), playerList);
        }
        else{
            ArrayList<Integer> idList = new ArrayList<Integer>();
            ArrayList<String> nameList = new ArrayList<String>();
            ArrayList<Integer> jerseyList = new ArrayList<Integer>();
            ArrayList<Integer> fieldPositionList = new ArrayList<Integer>();

            idList = savedInstanceState.getIntegerArrayList(getString(R.string.bundle_players_id));
            nameList = savedInstanceState.getStringArrayList(getString(R.string.bundle_players_name));
            jerseyList = savedInstanceState.getIntegerArrayList(getString(R.string.bundle_jersey_number));
            fieldPositionList = savedInstanceState.getIntegerArrayList(getString(R.string.bundle_field_position));

            ArrayList<Player> playerList = new ArrayList<Player>();
            for(int i=0; i<idList.size(); i++){
                playerList.add(new Player(idList.get(i), nameList.get(i), jerseyList.get(i), fieldPositionList.get(i)));
            }

            mTeam = new Team(savedInstanceState.getString(getString(R.string.bundle_team_name)), playerList);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        // save team
        ArrayList<Integer> idList = new ArrayList<Integer>();
        ArrayList<String> nameList = new ArrayList<String>();
        ArrayList<Integer> jerseyList = new ArrayList<Integer>();
        ArrayList<Integer> fieldPositionList = new ArrayList<Integer>();
        for(Player p: mTeam.getPlayerList()){
            idList.add(p.getId());
            nameList.add(p.getName());
            jerseyList.add(p.getJerseyNumber());
            fieldPositionList.add(p.getFieldPosition());
        }

        outState.putIntegerArrayList(getString(R.string.bundle_players_id), idList);
        outState.putString(getString(R.string.bundle_team_name), mTeam.getTeamName());
        outState.putStringArrayList(getString(R.string.bundle_players_name), nameList);
        outState.putIntegerArrayList(getString(R.string.bundle_jersey_number), jerseyList);
        outState.putIntegerArrayList(getString(R.string.bundle_field_position), fieldPositionList);
    }

    @Override
    public List<Player> getPlayerList() {
        return mTeam.getPlayerList();
    }
}
