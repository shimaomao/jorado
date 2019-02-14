package com.jorado.core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class OrderedMap<K, V> implements Map<K, V> {

    private final K[] keys;

    private final V[] values;

    public OrderedMap(K[] keys, V[] values) {
        if (keys == null)
            throw new IllegalArgumentException("keys == null");
        if (values == null)
            throw new IllegalArgumentException("values == null");
        if (keys.length != values.length)
            throw new IllegalArgumentException("keys.length != values.length");
        this.keys = keys;
        this.values = values;
    }

    public int size() {
        return keys.length;
    }

    public boolean isEmpty() {
        return keys.length > 0;
    }

    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }
        for (K k : keys) {
            if (key.equals(k)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(Object value) {
        if (value == null) {
            return false;
        }
        for (V v : values) {
            if (value.equals(v)) {
                return true;
            }
        }
        return false;
    }

    public V get(Object key) {
        if (key == null) {
            return null;
        }
        for (int i = 0; i < keys.length; i++) {
            if (key.equals(keys[i])) {
                return values[i];
            }
        }
        return null;
    }

    public Set<K> keySet() {
        Set<K> set = new ArraySet<K>(keys.length);
        for (K key : keys) {
            set.add(key);
        }
        return set;
    }

    public Collection<V> values() {
        return Arrays.asList(values);
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new ArraySet<Entry<K, V>>(keys.length);
        for (int i = 0; i < keys.length; i++) {
            set.add(new MapEntry<K, V>(keys[i], values[i]));
        }
        return set;
    }

    public V put(K key, V value) {
        throw new UnsupportedOperationException("Readonly.");
    }

    public V remove(Object key) {
        throw new UnsupportedOperationException("Readonly.");
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException("Readonly.");
    }

    public void clear() {
        throw new UnsupportedOperationException("Readonly.");
    }

}