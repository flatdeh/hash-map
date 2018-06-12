package com.vlad.hashmap;

import java.util.ArrayList;

public class HashMap<K, V> implements Map<K, V> {
    private static final int INITIAL_CAPACITY = 5;
    private ArrayList<Entry<K, V>>[] buskets;
    private int size;

    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    public HashMap(int basketsCount) {
        if (basketsCount <= 0) {
            throw new IllegalArgumentException("basketSize should be > 0 , basketsCount =" + basketsCount);
        }
        this.buskets = new ArrayList[basketsCount];
        for (int i = 0; i < basketsCount; i++) {
            buskets[i] = new ArrayList<>();
        }
    }

    public V put(K key, V value) {
        validateKey(key);
        int index = getBasketIndex(key);

        for (Entry<K, V> kvEntry : buskets[index]) {
            if (kvEntry.key.equals(key)) {
                V oldValue = kvEntry.value;
                kvEntry.value = value;
                return oldValue;
            }
        }

        buskets[index].add(new Entry<>(key, value));
        size++;

        return null;
    }

    public V get(K key) {
        validateKey(key);
        int index = getBasketIndex(key);

        for (Entry<K, V> kvEntry : buskets[index]) {
            if (kvEntry.key.equals(key)) {
                return kvEntry.value;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    private int getBasketIndex(K key) {
        return key.hashCode() % buskets.length;
    }

    private void validateKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key can't be null!");
        }
    }

    private static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
