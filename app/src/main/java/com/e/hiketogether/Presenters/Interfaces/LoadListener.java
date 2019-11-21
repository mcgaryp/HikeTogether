package com.e.hiketogether.Presenters.Interfaces;

import com.e.hiketogether.Models.Account;

/**
 * PURPOSE
 *      This listener is a specific listener to let those that implement it
 *      know that the account was successfully found and can be passed to the
 *      manager with ease.
 */
public interface LoadListener extends Listener {
    void onLoadSuccess(Account account);
}
