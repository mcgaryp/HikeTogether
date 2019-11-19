package com.e.hiketogether.Presenters.Interfaces;

import com.e.hiketogether.Models.Account;

public interface FirebaseListener {
    void onSaveSuccess();
    void onSaveFail();
    void onLoadSuccess(Account account);
    void onLoadFail();
    void onDeleteSuccess();
    void onDeleteFail();
    void onUpdateSuccess();
    void onUpdateFail();
}
