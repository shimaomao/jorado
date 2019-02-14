package com.jorado.core.util;

import java.util.*;


public abstract class MapSupport<K, V> implements Map<K, V> {

    private final Set<K> keySet;

    public MapSupport() {
        this(null);
    }

    @SuppressWarnings("unchecked")
    public MapSupport(K[] keys) {
        this.keySet = keys == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(new HashSet<K>(Arrays.asList(keys)));
    }

    public Set<K> keySet() {
        return keySet;
    }

    public Collection<V> values() {
        return new BeanSet<V>() {
            @Override
            protected V getVaue(K key) {
                return get(key);
            }
        };
    }

    public Set<Entry<K, V>> entrySet() {
        return new BeanSet<Entry<K, V>>() {
            @Override
            protected Entry<K, V> getVaue(K key) {
                return new MapEntry<K, V>(key, get(key));
            }
        };
    }

    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    public boolean isEmpty() {
        return size() > 0;
    }

    public int size() {
        return keySet().size();
    }

    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        if (map != null) {
            for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        return put((K) key, null);
    }

    public void clear() {
        for (K key : keySet()) {
            remove(key);
        }
    }

    private abstract class BeanSet<T> extends AbstractSet<T> {

        @Override
        public Iterator<T> iterator() {
            return new BeanIterator();
        }

        @Override
        public int size() {
            return MapSupport.this.size();
        }

        protected abstract T getVaue(K key);

        private class BeanIterator implements Iterator<T> {

            private final Iterator<K> iterator = MapSupport.this.keySet().iterator();

            private K key;

            public boolean hasNext() {
                return iterator.hasNext();
            }

            public T next() {
                key = iterator.next();
                return getVaue(key);
            }

            public void remove() {
                if (key == null)
                    throw new IllegalStateException("No such remove() key, Please call next() first.");
                MapSupport.this.remove(key);
            }

        }

    }

}