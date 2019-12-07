package com.e.hiketogether.Presenters.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.hiketogether.Models.Trail;
import com.e.hiketogether.Models.TrailList;
import com.e.hiketogether.Presenters.Helpers.DrawableHTTPHelper;
import com.e.hiketogether.R;

import java.util.concurrent.ExecutionException;

import javax.annotation.Nonnull;

public class ExtendedTrailAdapter extends RecyclerView.Adapter<ExtendedTrailAdapter.TrailViewHolder>{
    public static final String TAG = "EXTENDED_TRAIL_ADAPTER";

    private Context mCtx;
    private TrailList tl;
    private DrawableHTTPHelper drawableHelper;
    private int clickPosition;
    private int prevClickPosition;

    public ExtendedTrailAdapter(Context mCtx, TrailList tl) {
        this.mCtx = mCtx;
        this.tl = tl;
        clickPosition = -1;
        prevClickPosition = -1;
    }

    @Nonnull
    @Override
    public TrailViewHolder onCreateViewHolder(@Nonnull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_large_trail_card, null);
        return new TrailViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ExtendedTrailAdapter.TrailViewHolder holder, final int position) {
        holder.expandable.setVisibility((position == clickPosition) ? View.VISIBLE : View.GONE);
        holder.expandable.setActivated(position == clickPosition);
        if (position == clickPosition)
            prevClickPosition = position;

        Trail trail = tl.getTrailList().get(position);

        holder.textViewTitle.setText(trail.getName());
        holder.textViewDesc.setText(trail.getSummary());
        holder.textViewRating.setText(String.valueOf(trail.getRating()));
        holder.textViewPrice.setText(String.valueOf(trail.getDifficulty()));
        holder.ratingBar.setRating(trail.getRating());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClick_View) {
                Log.d(TAG, "onClick Called position: " + position);
                clickPosition = (position == clickPosition) ? -1 : position;
                ExtendedTrailAdapter.this.notifyItemChanged(prevClickPosition);
                ExtendedTrailAdapter.this.notifyItemChanged(clickPosition);
            }
        });
        // All below is to be hidden unless the item is clicked on!
        holder.location.setText(trail.getLocation());
        holder.length.setText(String.valueOf(trail.getLength()) + " Miles");
        holder.ascent.setText("Ascends " + trail.getAscent() + " ft in Elevation");
        holder.descent.setText("Descends " + trail.getDescent() + " ft in Elevation");
        holder.status.setText("Trail status is " + trail.getConditionStatus());
        holder.statusDetails.setText(trail.getConditionDetails());


        // TODO add listeners to this adapter and make things disappear and appear as the item is
        //  clicked on look at brother macbeths scripture journal on github

        try {
            //The trail has no image, fill it with the placeholder instead
            if (trail.getImgSmall().equals("")) {
                Log.d(TAG, trail.getName() + " has no image URL");
            }
            else {
                //The trail has its own image, fetch the URL and convert to a drawable
                Log.d(TAG, trail.getName() + " has image URL of: " + trail.getImgSmall());
                Drawable trailImage = new DrawableHTTPHelper().execute(trail.getImgSmall()).get();
                holder.imageView.setImageDrawable(trailImage);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return tl.getTrailList().size();
    }

    class TrailViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle, textViewDesc, textViewRating, textViewPrice,
                location, length, ascent, descent, status, statusDetails;
        RatingBar ratingBar;
        RelativeLayout expandable;

        public TrailViewHolder(@Nonnull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            location = itemView.findViewById(R.id.textViewLocation);
            length = itemView.findViewById(R.id.textViewLength);
            ascent = itemView.findViewById(R.id.textViewAscent);
            descent = itemView.findViewById(R.id.textViewDescent);
            status = itemView.findViewById(R.id.textViewConditionStatus);
            statusDetails = itemView.findViewById(R.id.textViewConditionDetails);
            ratingBar = itemView.findViewById(R.id.imageViewStars);
            expandable = itemView.findViewById(R.id.expandable);
        }
    }
}
