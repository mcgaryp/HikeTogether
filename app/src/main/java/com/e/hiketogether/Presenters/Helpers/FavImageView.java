package com.e.hiketogether.Presenters.Helpers;

import android.content.Context;
import android.util.AttributeSet;

import com.e.hiketogether.R;

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
        // If not favorite
        if (isFavorite == false) {
            setImageResource(R.drawable.ic_star_border_black_24dp);
            // TODO Delete from fav trail list in account
            isFavorite = true;
        }
        else {
            setImageResource(R.drawable.ic_star_yellow_50dp);
            // TODO add to favtrail list in account
            isFavorite = false;
        }
    }
}
