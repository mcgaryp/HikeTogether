package com.e.hiketogether.Presenters.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.e.hiketogether.Views.SpecializedViews.FavImageView;

import java.util.concurrent.ExecutionException;
import com.e.hiketogether.Models.Account;

import javax.annotation.Nonnull;
/**
 * PURPOSE: This class will adapt the user interface and interaction of that RecyclerView for the
 *          TrailsList that is pulled from the internet or cached items on the phone. It will easily
 *          display those items and handle small interactions specific to the Recycler View that it
 *          adapts to.
 */
public class TrailAdapter extends RecyclerView.Adapter<TrailAdapter.TrailViewHolder>{
    // VARIABLES
    public static final String TAG = "TRAIL_ADAPTER";

    private Context mCtx;
    private TrailList tl;
    private int clickPosition;
    private int prevClickPosition;
    private Account account;

    // Constructor for our adapter
    public TrailAdapter(Context mCtx, TrailList tl, Account account) {
        this.mCtx = mCtx;
        this.tl = tl;
        clickPosition = -1;
        prevClickPosition = -1;
        this.account = account;
    }

    // This is setting up the pointer to the correct xml layout that we need to use
    @Nonnull
    @Override
    public TrailViewHolder onCreateViewHolder(@Nonnull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_large_trail_card, null);
        return new TrailViewHolder(view);
    }

    /**
     * This function focuses on attaching the information from the trail list to each individual card
     * one at a time. There are two parts to the card. one layout is the short version and the other
     * is the expanded version. depending on the click and prevClickPositions the card will know if
     * it is needed to open or close the cards extended view or not. This is the purpose of the on click
     * listener.
     * @param holder    holder is the connection from the list to each individual views and is set in
     *                  the sub class holder.
     * @param position  position in the trail list. This is each individual cards "id"
     */
    @Override
    public void onBindViewHolder(@NonNull final TrailViewHolder holder, final int position) {
        // Here we are setting the visibility of the expansion to visible or not depending on the
        //  clickPosition. If it is == to the position then set it visable else not.
        holder.getExpandable().setVisibility((position == clickPosition) ? View.VISIBLE : View.GONE);
        // This activates the view it's self
        holder.getExpandable().setActivated(position == clickPosition);
        // Here we set the prevClick to the new position that we have clicked on
        if (position == clickPosition)
            prevClickPosition = position;

        // Getting each individual trail now
        final Trail trail = tl.getTrailList().get(position);

        // Setting the specific text to each of the individual views in the layout
        holder.getTextViewTitle().setText(trail.getName());
        holder.getTextViewDesc().setText(trail.getSummary());
        holder.getTextViewPrice().setText(String.valueOf(trail.getDifficulty()));
        holder.getRatingBar().setRating(trail.getRating());
        // TODO DYLAN here is code that should allow you to set the onclick button for when the user
        //  uses the button to get the trails starting location
//        holder.getGoHikingButton().setOnClickListener(onClick_View -> {
//            Log.d(TAG, "Take the user to the Location!");
//            map(trail.getLatitude(), trail.getLatitude());
//        });


        holder.getfavButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add a condition to detect if it is already a favorite or not
                holder.getfavButton().change();
                if (holder.getfavButton().getChange()) {

                    try {
                        account.addTrail(trail.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "Failed to add trail!!!!!!!!!!!!!!");
                        return;
                    }
                    Log.d(TAG, "adding worked!!!!!!!");
                } else {
                    try {
                        account.removeTrail(trail.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "removal failed!!!!!!!!!");
                        return;
                    }
                    Log.d(TAG, "Successful removal!!");

                }
            }
        });

        // Setting the on click listener to open or not this view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClick_View) {
                Log.d(TAG, "onClick Called position: " + position);
                // set position
                clickPosition = (position == clickPosition) ? -1 : position;
                // We are notifyin the adapter that we need to reCall the on Bind View
                TrailAdapter.this.notifyItemChanged(prevClickPosition);
                TrailAdapter.this.notifyItemChanged(clickPosition);
            }
        });



        // All below is to be hidden unless the item is clicked on!
        // We are just setting the values again here
        holder.getLocation().setText(trail.getLocation());
        holder.getLength().setText(String.valueOf(trail.getLength()) + " Miles");
        holder.getAscent().setText("Ascends " + trail.getAscent() + " ft in Elevation");
        holder.getDescent().setText("Descends " + trail.getDescent() + " ft in Elevation");
        holder.getStatus().setText("Trail status is " + trail.getConditionStatus());
        if (trail.getConditionDetails() == null)
            holder.getStatusDetails().setText("Condition details at this time are unknown.");
        else
            holder.getStatusDetails().setText(trail.getConditionDetails());

        // Trying to replace the picture with place holder or fetch it
        try {
            //The trail has no image, so keep the current image in the set
            if (trail.getImgSmall().equals("")) {
                Log.d(TAG, trail.getName() + " has no image URL");
            }
            else {
                //The trail has its own image, fetch the URL and convert to a drawable
                Log.d(TAG, trail.getName() + " has image URL of: " + trail.getImgSmall());
                Drawable trailImage = new DrawableHTTPHelper().execute(trail.getImgSmall()).get();
                holder.getImageView().setImageDrawable(trailImage);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Gets the length of trailList
    @Override
    public int getItemCount() {
        return tl.getTrailList().size();
    }

    // Sub Class that is a view holder in assisting the usage of the adapter
    class TrailViewHolder extends RecyclerView.ViewHolder {
        private FavImageView favButton;
        private ImageView imageView;
        private TextView textViewTitle, textViewDesc, textViewPrice,
                location, length, ascent, descent, status, statusDetails;
        private RatingBar ratingBar;
        private RelativeLayout expandable;
        private Button goHikingButton;

        public TrailViewHolder(@Nonnull View itemView) {
            super(itemView);

            favButton = itemView.findViewById(R.id.favButton);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            location = itemView.findViewById(R.id.textViewLocation);
            length = itemView.findViewById(R.id.textViewLength);
            ascent = itemView.findViewById(R.id.textViewAscent);
            descent = itemView.findViewById(R.id.textViewDescent);
            status = itemView.findViewById(R.id.textViewConditionStatus);
            statusDetails = itemView.findViewById(R.id.textViewConditionDetails);
            ratingBar = itemView.findViewById(R.id.imageViewStars);
            expandable = itemView.findViewById(R.id.expandable);
            goHikingButton = itemView.findViewById(R.id.goHikingButton);
        }

        // GETTERS
        public FavImageView getfavButton()    { return favButton;       }
        public ImageView getImageView()       { return imageView;       }
        public TextView getTextViewTitle()    { return textViewTitle;   }
        public TextView getTextViewDesc()     { return textViewDesc;    }
        public TextView getTextViewPrice()    { return textViewPrice;   }
        public TextView getLocation()         { return location;        }
        public TextView getLength()           { return length;          }
        public TextView getAscent()           { return ascent;          }
        public TextView getDescent()          { return descent;         }
        public TextView getStatus()           { return status;          }
        public TextView getStatusDetails()    { return statusDetails;   }
        public RatingBar getRatingBar()       { return ratingBar;       }
        public RelativeLayout getExpandable() { return expandable;      }
        public Button getGoHikingButton()     { return goHikingButton;  }
    }
}
