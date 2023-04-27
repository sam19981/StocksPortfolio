package model;

/**
 * DESIGN CHANGES
 * -------------------------------------------------------------------------------
 * This newly added class is solely used by the LRU Cache to hold its data, the key for the node
 * is the stock ticker symbol. The value stored by it is the StringBuilder output provided by the
 * AlphaVantage API.
 * --------------------------------------------------------------------------------
 * Used to store data on the LRU cache.
 */
public class Node {
  private final String key;
  private final StringBuilder val;
  private Node next;
  private Node prev;

  Node(String key, StringBuilder in) {
    this.key = key;
    this.val = in;
    this.next = null;
    this.prev = null;
  }

  public String getKey() {
    return key;
  }

  public StringBuilder getValue() {
    return val;
  }

  public Node getNext() {
    return next;
  }

  public Node getPrev() {
    return prev;
  }

  public void setNext(Node next) {
    this.next = next;
  }

  public void setPrev(Node prev) {
    this.prev = prev;
  }

}
