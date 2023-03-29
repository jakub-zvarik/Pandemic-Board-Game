package support;

import java.util.*;

/*
This class does create hashmap - it ties together key and value.
*/
public class MyHashMap<K, V> {

    private final List<Entry<K,V>>[] LIST;

    private static class Entry<K, V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    public MyHashMap(int initialCapacity) {
        this.LIST = new ArrayList[initialCapacity];
    }

    public void put(K key, V value) {
        int hash = key.hashCode();
        int index = Math.abs(hash % LIST.length);
        if (LIST[index] == null) {
            LIST[index] = new ArrayList<>();
        }
        for (Entry<K, V> entry : LIST[index]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        LIST[index].add(new Entry<>(key, value));
    }

    public V get(K key) {
        int hash = key.hashCode();
        int index = Math.abs(hash % LIST.length);
        if (LIST[index] != null) {
            for (Entry<K, V> entry : LIST[index]) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (List<Entry<K, V>> bucket : LIST) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    set.add(entry.getKey());
                }
            }
        }
        return set;
    }
}
