package com.vlad.hashmap;

import java.util.ArrayList;

public class HashMap<K, V> implements Map<K, V> {
    private static final int INITIAL_CAPACITY = 5;

    private ArrayList<Entry<K, V>>[] baskets;
    private int size;

    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    public HashMap(int basketsCount) {
        if (basketsCount <= 0) {
            throw new IllegalArgumentException("basketSize should be > 0 , basketsCount =" + basketsCount);
        }
        this.baskets = new ArrayList[basketsCount];
        for (int i = 0; i < basketsCount; i++) {
            baskets[i] = new ArrayList<>();
        }
    }

    public V put(K key, V value) {
        int index = getBasketIndex(key);

        for (Entry<K, V> kvEntry : baskets[index]) {
            if (kvEntry.key.equals(key)) {
                V oldValue = kvEntry.value;
                kvEntry.value = value;
                return oldValue;
            }
        }

        baskets[index].add(new Entry<>(key, value));
        size++;

        return null;
    }

    public V get(K key) {
        int index = getBasketIndex(key);

        for (Entry<K, V> kvEntry : baskets[index]) {
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
        return key.hashCode() % baskets.length;
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
