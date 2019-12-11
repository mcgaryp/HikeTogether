package com.e.hiketogether.Presenters.Interfaces;

import com.e.hiketogether.Models.Account;

public interface SaveAccountListener {
    void onSuccess(Account account);
    void onFail();
}
