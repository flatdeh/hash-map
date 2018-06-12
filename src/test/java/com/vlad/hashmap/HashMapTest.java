package com.vlad.hashmap;

import org.junit.Test;

import static org.junit.Assert.*;

public class HashMapTest {
    private HashMap<String, String> hashMap = new HashMap<>(3);

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidBasketsCountZero()
    {
        HashMap<String, String> hashMap = new HashMap<>(0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidBasketsCount()
    {
        HashMap<String, String> hashMap = new HashMap<>(-10);
    }


    @Test
    public void testPut() {
        assertNull(hashMap.put("a", "1"));
        assertNull(hashMap.put("b", "2"));
        assertNull(hashMap.put("c", "3"));
        assertNull(hashMap.put("d", "4"));
        assertNull(hashMap.put(null, "4"));
        assertNull(hashMap.put("f", "4"));
        assertEquals("1", hashMap.put("a", "5"));
        assertEquals("4", hashMap.put("f", "5"));
        assertEquals("4", hashMap.put(null, "5"));
    }

    @Test
    public void testGet() {
        hashMap.put("a", "1");
        hashMap.put("b", "2");
        hashMap.put("c", "3");
        hashMap.put("d", "4");
        hashMap.put("e", "5");
        hashMap.put(null, "6");

        assertEquals("1", hashMap.get("a"));
        assertEquals("2", hashMap.get("b"));
        assertEquals("3", hashMap.get("c"));
        assertEquals("6", hashMap.get(null));
        assertNull(hashMap.get("aa"));
    }

    @Test
    public void tetSize() {
        hashMap.put("a", "1");
        hashMap.put("b", "2");
        hashMap.put("c", "3");
        hashMap.put("d", "4");
        hashMap.put("a", "5");
        hashMap.get("ab");
        hashMap.isEmpty();
        hashMap.containsKey("ab");

        assertEquals(4, hashMap.size());

    }

    @Test
    public void testIsEmpty() {
        assertTrue(hashMap.isEmpty());
        hashMap.put("a", "1");
        assertFalse(hashMap.isEmpty());
    }

    @Test
    public void testContainsKey() {
        hashMap.put("a", "1");
        hashMap.put("b", null);
        hashMap.put("c", "3");
        hashMap.put("d", "4");
        hashMap.put("a", "5");
        assertTrue(hashMap.containsKey("a"));
        assertFalse(hashMap.containsKey("ssss"));
        assertTrue(hashMap.containsKey("b"));
    }
}