package com.example.gaintbomddemo.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gaintbomddemo.R;
import com.example.gaintbomddemo.model.GaintBombResponse;

import java.util.ArrayList;
import java.util.List;


public class GaintResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<GaintBombResponse.Result> mResults;
    private final LayoutInflater inflater;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded;

    public GaintResultsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        mResults = new ArrayList<>();
    }

    public void setList(List<GaintBombResponse.Result> results) {
        if (results == null) {
            return;
        }
        mResults.addAll(results);
        notifyDataSetChanged();
    }

    public void add(GaintBombResponse.Result mc) {
        mResults.add(mc);
        notifyItemInserted(mResults.size() - 1);
    }


    /**
     * Clear the list
     */
    public void clearAll() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            mResults.clear();
        }
    }

    /**
     * Adding the loading bar at the bottom
     */
    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new GaintBombResponse.Result());
    }

    /**
     * Remove the loading bar at the bottom
     */
    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mResults.size() - 1;
        GaintBombResponse.Result item = getItem(position);

        if (item != null) {
            mResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public GaintBombResponse.Result getItem(int position) {
        return mResults.get(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM:
                View view = inflater.inflate(R.layout.item_results_adapter, null, false);
                return new ResultViewHolder(view);
            case LOADING:
                View v = inflater.inflate(R.layout.item_progress, parent, false);
                return new LoadingViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GaintBombResponse.Result movie = mResults.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                ResultViewHolder resultViewHolder = (ResultViewHolder) holder;
                resultViewHolder.textViewName.setText(mResults.get(position).name);
                resultViewHolder.textViewDeck.setText(mResults.get(position).deck);
                Glide.with(resultViewHolder.itemView.getContext())
                        .load(movie.image.mediumUrl)
                        .into(resultViewHolder.imageGaintBomb);
                break;
            case LOADING:
                break;
        }


    }

    @Override
    public int getItemCount() {
        return mResults == null ? 0 : mResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    protected class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDeck;
        ImageView imageGaintBomb;
        TextView textViewName;

        public ResultViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_name);
            textViewDeck = itemView.findViewById(R.id.text_deck);
            imageGaintBomb = itemView.findViewById(R.id.img_gaint_bomb);
        }
    }

    protected class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

}