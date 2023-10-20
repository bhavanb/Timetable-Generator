package lib;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

//sourced from https://stackoverflow.com/a/10299689 and modified to suit my needs
public class HashMap2D<K1, K2, V> {

    private final Map<K1, Map<K2, V>> mMap;

    public HashMap2D() {
        mMap = new LinkedHashMap<K1, Map<K2, V>>();
    }

    /**
     * Associates the specified value with the specified keys in this map (optional
     * operation). If the map previously
     * contained a mapping for the key, the old value is replaced by the specified
     * value.
     * 
     * @param key1
     *              the first key
     * @param key2
     *              the second key
     * @param value
     *              the value to be set
     * @return the value previously associated with (key1,key2), or
     *         <code>null</code> if none
     * @see Map#put(Object, Object)
     */
    public V put(K1 key1, K2 key2, V value) {
        Map<K2, V> map;
        if (mMap.containsKey(key1)) {
            map = mMap.get(key1);
        } else {
            map = new LinkedHashMap<K2, V>();
            mMap.put(key1, map);
        }

        return map.put(key2, value);
    }

    /**
     * Returns the value to which the specified key is mapped, or <code>null</code>
     * if this map contains no mapping for
     * the key.
     * 
     * @param key1
     *             the first key whose associated value is to be returned
     * @param key2
     *             the second key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or <code>null</code>
     *         if this map contains no mapping for
     *         the key
     * @see Map#get(Object)
     */
    public V get(K1 key1, K2 key2) {
        if (mMap.containsKey(key1)) {
            return mMap.get(key1).get(key2);
        } else {
            return null;
        }
    }

    /**
     * Returns <code>true</code> if this map contains a mapping for the specified
     * key
     * 
     * @param key1
     *             the first key whose presence in this map is to be tested
     * @param key2
     *             the second key whose presence in this map is to be tested
     * @return Returns true if this map contains a mapping for the specified key
     * @see Map#containsKey(Object)
     */
    public boolean containsKeys(K1 key1, K2 key2) {
        return mMap.containsKey(key1) && mMap.get(key1).containsKey(key2);
    }

    /**
     * Returns the primary keys of the HashMap2D in a Set
     * 
     * @return A Set of the primary keys in the HashMap2D
     * @see Set
     */
    public Set<K1> primaryKeySet() {
        return mMap.keySet();
    }

    /**
     * Returns the secondary keys of the HashMap2D in a Set
     * 
     * @return A Set of the secondary keys in the HashMap2D
     * @see Set
     */
    public Set<K2> secondaryKeySet(K1 key) {
        return mMap.get(key).keySet();
    }

    /**
     * Clears the HashMap2D
     */
    public void clear() {
        mMap.clear();
    }

}