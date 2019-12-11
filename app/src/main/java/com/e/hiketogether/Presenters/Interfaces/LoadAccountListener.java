package com.e.hiketogether.Presenters.Interfaces;

import com.e.hiketogether.Models.Account;

/**
 * PURPOSE
 *      This is a listener interface intended to listen and let managers know if some
 *      thread was successful or not in what ever needs done in the background
 */
public interface LoadAccountListener {
    void onSuccess(Account account);
    void onFail();
}
