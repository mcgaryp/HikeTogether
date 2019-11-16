package com.e.hiketogether.Presenters.Interfaces;

import com.google.common.base.Optional;

/**
 * SHOULD we make a listener for each of the fails and successes?
 */
public interface FirebaseObserver {
    void notifyListener(boolean message, Optional<Object> objectOptional);
}
