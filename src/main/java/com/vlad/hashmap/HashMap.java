package com.vlad.hashmap;

import java.util.ArrayList;
import java.util.List;

public class HashMap<K, V> implements Map {
    private static final int INITIAL_CAPACITY = 5;

    private List<Entry> baskets = (Entry[]) new ArrayList[INITIAL_CAPACITY];
    private int size;

    public HashMap(){
    }

    public Object put(Object key, Object value) {
        Object oldValue = null;
        boolean updated = false;
        for (ArrayList basket : baskets) {
            baskets[0].val
        }


        return null;
    }

    public Object get(Object value) {
        return null;
    }

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean containsKey(Object key) {
        return false;
    }

    private static class Entry<K, V> {
        private K key;
        private V value;

        public Entry() {

        }
    }
}
