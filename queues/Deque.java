/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Node next;
        private Item value;

        public Node(Item item, Node next) {
            value = item;
            this.next = next;
        }
    }

    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (first == null) {
            first = new Node(item, last);
        } else {
            Node oldFirst = first;
            first = new Node(item, oldFirst);
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (last == null) {
            last = new Node(item, first);
        } else {
            Node oldLast = last;
            last = new Node(item, oldLast);
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();


    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {

    }

    public static void main(String[] args) {

    }
}
