package support;

import java.util.*;

/*
This class creates hashmap objects which ties together Keys and Values, to make possible to store multiple Values
in one place and find or manipulate them via their Key.
*/
public class MyHashMap<Key, Value> {

    private final List<Entry<Key,Value>>[] LIST;
    private int size = 0;

    // Class inside the MyHashMap object to create object of Key and Value pair
    private static class Entry<Key, Value> {
        private final Key key;
        private Value value;

        // Constructor for the entry pair of Key and Value
        public Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        // Getters and setters
        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

        public void setValue(Value value) {
            this.value = value;
        }
    }

    // Constructor fot the MyHashMap object
    public MyHashMap() {
        this.LIST = new ArrayList[1];
    }

    // Put Key and Value pair into the MyHashMap
    public void put(Key key, Value value) {
        int hash = key.hashCode();
        int index = Math.abs(hash % LIST.length);
        if (LIST[index] == null) {
            LIST[index] = new ArrayList<>();
        }
        for (Entry<Key, Value> entry : LIST[index]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        LIST[index].add(new Entry<>(key, value));
        this.size += 1;
    }

    // Get Value, look it up in the MyHashMap object by its Key
    public Value get(Key key) {
        int hash = key.hashCode();
        int index = Math.abs(hash % LIST.length);
        if (LIST[index] != null) {
            for (Entry<Key, Value> entry : LIST[index]) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    // Getting all Keys from the MyHashMap object
    public Set<Key> keySet() {
        Set<Key> set = new HashSet<>();
        for (List<Entry<Key, Value>> object : LIST) {
            if (object != null) {
                for (Entry<Key, Value> entry : object) {
                    set.add(entry.getKey());
                }
            }
        }
        return set;
    }

    // Get size of the MyHashMap object
    public int getSize() {
        return this.size;
    }
}
