package com.e.hiketogether.Presenters.Helpers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class CurrentLocationHelper {
    private Geocoder coder;
    private List<Address> addressList;
    private LatLng latLong;
    private String address;

    public CurrentLocationHelper(Context context, String address) {
        coder = new Geocoder(context);
        this.address = address;
    }

    public LatLng getLocationFromAddress() {
        try {
            addressList = coder.getFromLocationName(address, 1);
            if (address == null) {
                return null;
            }

            Address location = addressList.get(0);
            latLong = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return latLong;
    }
}
