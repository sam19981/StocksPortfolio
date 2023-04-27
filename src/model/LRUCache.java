package model;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of the popular LRU cache using a doubly linked list with left and
 * right pointers and a Hashmap to store and fetch the values faster.
 * The capacity of this cache is set to 10 at the moment, i.e. information of upto 10
 * different stocks can be added after which the least recently used cached stocked data
 * is deleted.
 * The capacity limit of the cache helps in not overloading the memory with a lot of data.
 * But however, we could alter the size of the cache based on the type of user and the load
 * that is taken by the application.
 * -------------------------
 * LRU cache for caching all the Stock values queried so far.
 */
public class LRUCache {
  private static final LRUCache lru = new LRUCache(10);
  private final int capacity;
  private final Map<String, Node> cache;
  private final Node left;
  private final Node right;

  private LRUCache(int capacity) {
    this.capacity = capacity;
    cache = new HashMap<>();
    left = new Node("0", null);
    right = new Node("0", null);
    left.setNext(right);
    right.setPrev(left);
  }

  /**
   * Returns a LRU cache object to use.
   *
   * @return - LRU cache type object to use.
   */
  public static LRUCache getInstance() {
    return lru;
  }

  /**
   * Removes the specified node form the cache.
   *
   * @param node - node to be removed from the cache.
   */
  public void remove(Node node) {
    Node prev = node.getPrev();
    Node next = node.getNext();
    prev.setNext(next);
    next.setPrev(prev);
  }

  /**
   * Insert new node to the cache.
   *
   * @param node - the new node to be inserted to the cache.
   */
  public void insert(Node node) {
    Node prev = right.getPrev();
    Node next = right;
    prev.setNext(node);
    next.setPrev(node);
    node.setPrev(prev);
    node.setNext(next);
  }

  /**
   * Gets all the details of the given stock.
   *
   * @param key - The stock whose value need to fetched.
   * @return - string builder object containing all the details related to the stock.
   */

  public StringBuilder get(String key) {
    if (cache.containsKey(key)) {
      remove(cache.get(key));
      insert(cache.get(key));
      return cache.get(key).getValue();
    }
    return null;
  }

  /**
   * Add the deatils of the given stock to the LRU cache.
   *
   * @param key - Name of the stock to be added to the cache.
   * @param val - Details of the stock need to be added to the cache.
   */
  public void put(String key, StringBuilder val) {
    cache.remove(key);
    cache.put(key, new Node(key, val));
    insert(cache.get(key));
    if (cache.size() > capacity) {
      Node lru = left.getNext();
      remove(lru);
      cache.remove(lru.getKey());
    }
  }

  /**
   * Returns the current size of the cache.
   *
   * @return -  returns a integer value denoting the current size of the cache.
   */
  public int getSize() {
    return cache.size();
  }

  /**
   * Returns the current capacity of the cache.
   *
   * @return - integer represent maximum entries which can be stored in the cache.
   */
  public int getCapacity() {
    return capacity;
  }
}
