package com.vlad.hashmap;

import java.util.ArrayList;

public class HashMap<K, V> implements Map<K, V> {
    private static final int INITIAL_CAPACITY = 5;
    private static final double LOAD_FACTOR = 0.75;
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
        if (size >= (int) (buskets.length * LOAD_FACTOR)) {
            growCapasity();
        }
        if (key == null) {
            return putNull(value);
        } else {
            int index = getBusketIndex(key, buskets);
            ArrayList<Entry<K, V>> busket = buskets[index];

            for (Entry<K, V> kvEntry : busket) {
                if (kvEntry.key.equals(key)) {
                    V oldValue = kvEntry.value;
                    kvEntry.value = value;
                    return oldValue;
                }
            }
            busket.add(new Entry<>(key, value));
            size++;
        }
        return null;
    }

    public V get(K key) {
        if (key == null) {
            return getNull();
        } else {
            int index = getBusketIndex(key, buskets);

            for (Entry<K, V> kvEntry : buskets[index]) {
                if (kvEntry.key != null) {
                    if (kvEntry.key.equals(key)) {
                        return kvEntry.value;
                    }
                }
            }
            return null;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(K key) {
        int index = getBusketIndex(key, buskets);

        for (Entry<K, V> kvEntry : buskets[index]) {
            if (kvEntry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    private void growCapasity() {
        ArrayList<Entry<K, V>>[] newBuskets = new ArrayList[buskets.length + 5];
        for (int i = 0; i < buskets.length + 5; i++) {
            newBuskets[i] = new ArrayList<>();
        }
        transferEntrys(newBuskets);
        buskets = newBuskets;
    }

    private void transferEntrys(ArrayList<Entry<K, V>>[] newBuskets) {
        for (ArrayList<Entry<K, V>> busket : buskets) {
            for (Entry<K, V> entry : busket) {
                if (entry.key == null) {
                    newBuskets[0].add(new Entry<K, V>(null, entry.value));
                } else {
                    int index = getBusketIndex(entry.key, newBuskets);
                    newBuskets[index].add(new Entry<K, V>(entry.key, entry.value));
                }
            }
        }
    }

    private int getBusketIndex(K key, ArrayList<Entry<K, V>>[] arrayBuskets) {
        return Math.abs(key.hashCode()) % arrayBuskets.length;
    }

    private V getNull() {
        for (Entry<K, V> kvEntry : buskets[0]) {
            if (kvEntry.key == null) {
                return kvEntry.value;
            }
        }
        return null;
    }

    private V putNull(V value) {
        for (Entry<K, V> kvEntry : buskets[0]) {
            if (kvEntry.key == null) {
                V oldValue = kvEntry.value;
                kvEntry.value = value;
                return oldValue;
            }
        }
        buskets[0].add((Entry<K, V>) new Entry<>(null, value));
        size++;

        return null;
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
