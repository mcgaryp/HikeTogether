package com.e.hiketogether.Presenters.Listeners;

import com.e.hiketogether.Presenters.Interfaces.FirebaseObserver;
import com.google.common.base.Optional;

public class FirebaseListener implements FirebaseObserver {

    public void onSaveSuccess() {
       notifyListener(true, null);
    }

    public void onSaveFail() {
        notifyListener(false, null);
    }

    public void onLoadSuccess() {
        notifyListener(true, null);
    }

    public void onLoadFail() {
        notifyListener(false, null);
    }

    public void onUpdateSuccess() {
        notifyListener(true, null);
    }

    public void onUpdateFail() {
        notifyListener(false, null);
    }

    public void onDeleteSuccess() {
        notifyListener(true,null);
    }

    public void onDeleteFail() {
        notifyListener(false,null);
    }

    @Override
    public void notifyListener(boolean message, Optional<Object> objectOptional) {
        
    }
}
