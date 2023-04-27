package modeltest;

import org.junit.Before;
import org.junit.Test;

import model.LRUCache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Class to Test LRU cache's functioning.
 */
public class LRUCacheTest {
  LRUCache cache;

  @Before
  public void setup() {
    cache = LRUCache.getInstance();
  }

  @Test
  public void testPut() {
    cache.put("Hello", new StringBuilder("Hi"));
    assertEquals(String.valueOf(cache.get("Hello")), "Hi");
  }

  @Test
  public void testCacheRepeated() {
    cache.put("Hello", new StringBuilder("Hi"));
    cache.put("Hello", new StringBuilder("Hi"));
    cache.put("Hello", new StringBuilder("Hi"));
    cache.put("Hello", new StringBuilder("Hi"));
    cache.put("Hello", new StringBuilder("Hi"));
    cache.put("Hello", new StringBuilder("Hi"));
    cache.put("Hello", new StringBuilder("Hi"));
    cache.put("Hello", new StringBuilder("Hi"));
    cache.put("Hello", new StringBuilder("Hi"));
    cache.put("Hello", new StringBuilder("Hi"));
    assertEquals(1, cache.getSize());
  }

  @Test
  public void testCacheCapacity() {
    assertEquals(10, cache.getCapacity());
  }

  @Test
  public void testCachedLimit() {
    cache.put("Hello", new StringBuilder("Hi"));
    cache.put("A", new StringBuilder("A"));
    cache.put("AB", new StringBuilder("AB"));
    cache.put("ABC", new StringBuilder("ABC"));
    cache.put("ABCD", new StringBuilder("ABCD"));
    cache.put("ABCDE", new StringBuilder("ABCDE"));
    cache.put("ABCDEF", new StringBuilder("ABCDEF"));
    cache.put("ABCDEFG", new StringBuilder("ABCDEFG"));
    cache.put("ABCDEFGH", new StringBuilder("ABCDEFGH"));
    cache.put("ABCDEFGHI", new StringBuilder("ABCDEFGHI"));
    cache.put("ABCDEFGHIJ", new StringBuilder("ABCDEFGHIJ"));
    assertNotEquals(String.valueOf(cache.get("Hello")), "Hi");
  }
}
