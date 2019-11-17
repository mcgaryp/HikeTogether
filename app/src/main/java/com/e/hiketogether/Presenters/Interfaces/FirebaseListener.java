package com.e.hiketogether.Presenters.Interfaces;

import com.google.common.base.Optional;

/**
 * SHOULD we make a listener for each of the fails and successes?
 */
public interface FirebaseListener {
    void onSaveSuccess();
    void onSaveFail();
    void onLoadSuccess();
    void onLoadFail();
    void onDeleteSuccess();
    void onDeleteFail();
    void onUpdateSuccess();
    void onUpdateFail();
}
