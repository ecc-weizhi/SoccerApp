package com.example.weizhi.playpalassignment.activity;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weizhi.playpalassignment.R;

import java.util.List;

import Model.Player;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */
public class PlayerRecyclerAdapter extends RecyclerView.Adapter<PlayerRecyclerAdapter.ViewHolder> {
    private final String TAG = PlayerRecyclerAdapter.class.getSimpleName();
    private final List<Player> mPlayerList;
    private final PlayerFragment mPlayerFragment;

    private int selectedItem;

    public PlayerRecyclerAdapter(List<Player> playerList, PlayerFragment fragment, int selectedItem) {
        mPlayerList = playerList;
        mPlayerFragment = fragment;
        this.selectedItem = selectedItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_player_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mPlayerList.get(position);

        holder.mIdView.setText(String.valueOf(holder.mItem.getJerseyNumber()));
        holder.mContentView.setText(holder.mItem.getName());
        holder.mView.setTag(holder.mItem.getId());
        holder.mView.setActivated(selectedItem==position);

        if(mPlayerFragment.getResources().getBoolean(R.bool.is_tablet)) {
            holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                // Defines the one method for the interface, which is called when the View is long-clicked
                public boolean onLongClick(View v) {
                    ClipData dragData = ClipData.newPlainText(mPlayerFragment.getString(R.string.from_player), String.valueOf(v.getTag()));
                    View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v.findViewById(R.id.content));
                    v.startDrag(dragData,  // the data to be dragged
                            myShadow,  // the drag shadow builder
                            null,      // no need to use local data
                            0          // flags (not currently used, set to 0)
                    );

                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mPlayerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Player mItem;

        public ViewHolder(View view) {
            super(view);
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "clicked on " + v.getTag());
                    notifyItemChanged(selectedItem);
                    selectedItem = getAdapterPosition();
                    notifyItemChanged(selectedItem);
                    mPlayerFragment.notifySelected(selectedItem);
                }
            });

            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

