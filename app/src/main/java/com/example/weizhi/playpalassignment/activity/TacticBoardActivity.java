package com.example.weizhi.playpalassignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.weizhi.playpalassignment.R;

import java.util.ArrayList;
import java.util.List;

import Model.Player;
import Model.Team;

public class TacticBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        TacticBoardFragment.OnFragmentInteractionListener, PlayerFragment.OnFragmentInteractionListener{
    private final String TAG = TacticBoardActivity.class.getSimpleName();

    private Team mTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tactic_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Title");
        setSupportActionBar(toolbar);

        // Drawer setup
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Navigation menu setup
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null){
            mTeam = Team.newDemoInstance();
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Method to launch PlayerActivity.
     * @param view the view that this onClick event is from.
     */
    public void launchPlayerActivity(View view){
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

        Bundle mBundle = new Bundle();
        mBundle.putIntegerArrayList(getString(R.string.bundle_players_id), idList);
        mBundle.putString(getString(R.string.bundle_team_name), mTeam.getTeamName());
        mBundle.putStringArrayList(getString(R.string.bundle_players_name), nameList);
        mBundle.putIntegerArrayList(getString(R.string.bundle_jersey_number), jerseyList);
        mBundle.putIntegerArrayList(getString(R.string.bundle_field_position), fieldPositionList);

        Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
        intent.putExtra(getString(R.string.bundle_team), mBundle);
        startActivity(intent);
    }

    @Override
    public Team getTeam(){
        return mTeam;
    }

    @Override
    public void swapPlayerFieldPosition(int firstPlayerId, int secondPlayerId){
        mTeam.swapPlayerFieldPosition(firstPlayerId, secondPlayerId);
    }

    @Override
    public String findPlayerNameById(int id){
        return mTeam.findPlayerNameById(id);
    }

    @Override
    public int findPlayerFieldPositionById(int id){
        return mTeam.findPlayerFieldPositionById(id);
    }

    @Override
    public List<Player> getPlayerList(){
        return mTeam.getPlayerList();
    }

}
