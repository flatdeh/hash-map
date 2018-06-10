package com.vlad.hashmap;

public interface Map<K, V> {
    Object put(K key, V value);

    Object get(V value);

    int size();

    boolean isEmpty();

    boolean containsKey(K key);
}
