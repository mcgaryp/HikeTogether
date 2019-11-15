package com.e.hiketogether.Presenters;

interface FirebaseListener {
    public void onLoadSuccess();
    public void onLoadFail();
    public void onSaveSuccess();
    public void onSaveFail();
    public void onUpdateSuccess();
    public void onUpdateFail();
    public void onDeleteSuccess();
    public void onDeleteFail();
}
