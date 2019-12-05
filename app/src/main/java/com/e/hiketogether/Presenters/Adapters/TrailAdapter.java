package com.e.hiketogether.Presenters.Adapters;

import android.content.Context;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.hiketogether.Models.Trail;
import com.e.hiketogether.Models.TrailList;
import com.e.hiketogether.Presenters.Helpers.DrawableHTTPHelper;
import com.e.hiketogether.R;

import java.util.concurrent.ExecutionException;

/*
 * RecyclerView.Adapter
 * RecyclerView.ViewHolder
 */
public class TrailAdapter extends RecyclerView.Adapter<TrailAdapter.TrailViewHolder> {
    public static final String TAG = "TRAIL_ADAPTER";

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

        //The trail has no image, fill it with the placeholder instead
        if (trail.getImgSmall().equals("")) {
            Log.d(TAG, trail.getName() + " has no image URL");
            Drawable trailImage = Drawable.createFromPath("trail_placeholder.jpg");
            holder.imageView.setImageDrawable(trailImage);
            return;
        }

        try {
            //The trail has its own image, fetch the URL and convert to a drawable
                Log.d(TAG, trail.getName() + " has image URL of: " + trail.getImgSmall());
                Drawable trailImage = new DrawableHTTPHelper().execute(trail.getImgSmall()).get();
                holder.imageView.setImageDrawable(trailImage);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return tl.getTrailList().size();
    }

    //This method goes through the new trail list and adds the items to the
    //one owned by the adapter, so they are all visible.
    //
    //This is only used for when the recyclerview has already been created, but
    //needs to be updated with new trails
    public void newAddeddata(TrailList newTl) {

        for (int i = 0; i < newTl.getTrailList().size(); i++) {
            tl.addTrail(newTl.getTrailList().get(i));
        }

        notifyDataSetChanged();
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
