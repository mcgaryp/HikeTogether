package com.e.hiketogether.Views.SpecializedViews;

import android.content.Context;
import android.util.AttributeSet;

import com.e.hiketogether.R;

import android.util.Log;
import android.widget.Toast;


public class FavImageView extends androidx.appcompat.widget.AppCompatImageView {
    // VARIABLES
    private final static String TAG = "FAV_IMAGE_VIEW";
    private boolean isFavorite;

    // Constructors
    public FavImageView(Context context) {
        super(context);
        isFavorite = false;
    }

    public FavImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        isFavorite = false;
    }

    public FavImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        isFavorite = false;
    }

    // Change to favorite or not
    public void change() {
        // If not favorite - change to favorite
        if (isFavorite == false) {
            setFavorite();
            // TODO add to fav trail list in account
            isFavorite = true;
            new Toast(getContext()).makeText(getContext(), "Trail added to favorites",
                    Toast.LENGTH_SHORT).show();

        }
        else { //if already favrorite, change to non-favorite
            setNotFavorite();
            // TODO remove from favtrail list in account
            isFavorite = false;
           new Toast(getContext()).makeText(getContext(), "Trail removed from favorites",
                    Toast.LENGTH_SHORT).show();

        }
    }

    public void setFavorite(){
        setImageResource(R.drawable.ic_star_yellow_50dp);
    }
    public void setNotFavorite(){
        setImageResource(R.drawable.ic_star_border_black_24dp);
    }

    public boolean getChange(){
        return isFavorite;
    }
}
