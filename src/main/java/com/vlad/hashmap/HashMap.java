package com.vlad.hashmap;

import java.util.ArrayList;
import java.util.Iterator;

public class HashMap<K, V> implements Map<K, V>, Iterable<K> {
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
        if (size > (int) (buskets.length * LOAD_FACTOR)) {
            growCapacity();
        }
        if (key == null) {
            return putNull(value);
        } else {
            int index = getBusketIndex(key, buskets);
            ArrayList<Entry<K, V>> busket = buskets[index];

            for (Entry<K, V> entry : busket) {
                if (entry.key != null) {
                    if (entry.key.equals(key)) {
                        V oldValue = entry.value;
                        entry.value = value;
                        return oldValue;
                    }
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

            for (Entry<K, V> entry : buskets[index]) {
                if (entry.key != null) {
                    if (entry.key.equals(key)) {
                        return entry.value;
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

        for (Entry<K, V> entry : buskets[index]) {
            if (entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public void putAll(HashMap<K, V> hashMap) {
        for (K key : hashMap) {
            put(key, hashMap.get(key));
        }
    }

    public void putAllIfAbsent(HashMap<K, V> hashMap) {
        for (K key : hashMap) {
            putIfAbsent(key, hashMap.get(key));
        }
    }

    public V putIfAbsent(K key, V value) {
        if (size > (int) (buskets.length * LOAD_FACTOR)) {
            growCapacity();
        }
        if (key == null) {
            return putNullIfAbsent(value);
        } else {
            int index = getBusketIndex(key, buskets);
            ArrayList<Entry<K, V>> busket = buskets[index];

            for (Entry<K, V> entry : busket) {
                if (entry.key != null) {
                    if (entry.key.equals(key)) {
                        return entry.value;
                    }
                }
            }
            busket.add(new Entry<>(key, value));
            size++;
        }
        return null;
    }

    private V putNullIfAbsent(V value) {
        for (Entry<K, V> entry : buskets[0]) {
            if (entry.key == null) {
                return entry.value;
            }
        }
        buskets[0].add((Entry<K, V>) new Entry<>(null, value));
        size++;

        return null;
    }


    private void growCapacity() {
        int newCapacity = buskets.length * 2;
        ArrayList<Entry<K, V>>[] newBuskets = new ArrayList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newBuskets[i] = new ArrayList<>();
        }
        transferEntries(newBuskets);
        buskets = newBuskets;
    }

    private void transferEntries(ArrayList<Entry<K, V>>[] newHashMap) {
        for (ArrayList<Entry<K, V>> busket : buskets) {
            for (Entry<K, V> entry : busket) {
                if (entry.key == null) {
                    newHashMap[0].add(new Entry<K, V>(null, entry.value));
                } else {
                    int index = getBusketIndex(entry.key, newHashMap);
                    newHashMap[index].add(new Entry<>(entry.key, entry.value));
                }
            }
        }
    }

    private int getBusketIndex(K key, ArrayList<Entry<K, V>>[] arrayBuskets) {
        int keyHashCode = key.hashCode();
        return keyHashCode == Integer.MIN_VALUE ? 0 : Math.abs(keyHashCode) % arrayBuskets.length;
    }

    private V getNull() {
        for (Entry<K, V> entry : buskets[0]) {
            if (entry.key == null) {
                return entry.value;
            }
        }
        return null;
    }

    private V putNull(V value) {
        for (Entry<K, V> entry : buskets[0]) {
            if (entry.key == null) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }
        buskets[0].add((Entry<K, V>) new Entry<>(null, value));
        size++;

        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new HashMapIterator();
    }

    private static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }

    private class HashMapIterator implements Iterator<K> {
        private int index;
        private int row = 0;
        private int column = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public K next() {
            for (; row < buskets.length; ) {
                for (; column < buskets[row].size(); ) {
                    index++;
                    return buskets[row].get(column++).key;
                }
                row++;
                column = 0;
            }
            index++;
            return null;
        }

        @Override
        public void remove() {

        }
    }


}
