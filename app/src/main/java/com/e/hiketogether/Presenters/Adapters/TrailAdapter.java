package com.e.hiketogether.Presenters.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.hiketogether.Models.Trail;
import com.e.hiketogether.Models.TrailList;
import com.e.hiketogether.R;

/*
 * RecyclerView.Adapter
 * RecyclerView.ViewHolder
 */
public class TrailAdapter extends RecyclerView.Adapter<TrailAdapter.TrailViewHolder> {

    private Context mCtx;
    private TrailList tl;

    public TrailAdapter(Context mCtx, TrailList tl) {
        this.mCtx = mCtx;
        this.tl = tl;
    }

    @NonNull
    @Override
    public TrailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_trail_card, null);
        return new TrailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailViewHolder holder, int position) {
        Trail trail = tl.getTrailList().get(position);

        holder.textViewTitle.setText(trail.getName());
        holder.textViewDesc.setText(trail.getSummary());
        holder.textViewRating.setText(String.valueOf(trail.getRating()));
        holder.textViewPrice.setText(String.valueOf(trail.getDifficulty()));

        //TODO- Figure out how to convert this image URL to a Drawable
        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(trail.getImgSmall(), null));
    }

    @Override
    public int getItemCount() {
        return tl.getTrailList().size();
    }

    class TrailViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewTitle, textViewDesc, textViewRating, textViewPrice;

        public TrailViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);

        }
    }
}
