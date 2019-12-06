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

import javax.annotation.Nonnull;

public class ExtendedTrailAdapter extends RecyclerView.Adapter<ExtendedTrailAdapter.TrailViewHolder>{
    public static final String TAG = "EXTENDED_TRAIL_ADAPTER";

    private Context mCtx;
    private TrailList tl;
    private DrawableHTTPHelper drawableHelper;

    public ExtendedTrailAdapter(Context mCtx, TrailList tl) {
        this.mCtx = mCtx;
        this.tl = tl;
    }

    @Nonnull
    @Override
    public TrailViewHolder onCreateViewHolder(@Nonnull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_large_trail_card, null);
        return new TrailViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ExtendedTrailAdapter.TrailViewHolder holder, int position) {
        Trail trail = tl.getTrailList().get(position);

        holder.textViewTitle.setText(trail.getName());
        holder.textViewDesc.setText(trail.getSummary());
        holder.textViewRating.setText(String.valueOf(trail.getRating()));
        holder.textViewPrice.setText(String.valueOf(trail.getDifficulty()));

        try {
            //The trail has no image, fill it with the placeholder instead
            if (trail.getImgSmall().equals("")) {
                Log.d(TAG, trail.getName() + " has no image URL");
                Drawable trailImage = Drawable
                        .createFromPath("../../../../../../res/drawable/trail_placeholder.png");
                holder.imageView.setImageDrawable(trailImage);
            }

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
        return 0;
    }

    class TrailViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle, textViewDesc, textViewRating, textViewPrice;

        public TrailViewHolder(@Nonnull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);

        }
    }
}
