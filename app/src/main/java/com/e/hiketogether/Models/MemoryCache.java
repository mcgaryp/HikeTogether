//package com.e.hiketogether.Models;
//
//public class MemoryCache<K, T> {
//    private long timeToLive;
//    //private LRUMap CacheMap
//
//    protected class CacheObject {
//        public long lastAccessed = System.currentTimeMillis();
//        public T value;
//    }
//}
//
///*
//* This functions determines how long items have been in the cache, and prioritizes
//* keeping the more recently created items and getting rid of older ones
// */
//
//public InMemoryCache(long timeToLive, final long TimerInterval, int maxItems) {
//    this.timeToLive = timeToLive * 1000;
//
//    //CacheMap = new LRUMap(maxItems);
//
//    if (timeToLive > 0 && TimerInterval > 0) {}
//}
//
