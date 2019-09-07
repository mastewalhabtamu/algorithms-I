/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item value;

        private Node next;
        private Node prev;

        public Node(Item value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node front;
    private Node back;
    private int size;

    // construct an empty deque
    public Deque() {
        front = null;
        back = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (isEmpty()) {
            front = new Node(item, null, null);
            back = front;
        }
        else {
            Node oldFront = front;

            front = new Node(item, oldFront, null);
            oldFront.prev = front;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (isEmpty()) {
            back = new Node(item, null, null);
            front = back;
        }
        else {
            Node oldBack = back;

            back = new Node(item, null, oldBack);
            oldBack.next = back;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Item value = front.value;
        front = front.next;
        if (front == null) {
            back = null;
        }
        else {
            front.prev = null;
        }

        size--;

        return value;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Item value = back.value;
        back = back.prev;
        if (back == null) {
            front = null;
        }
        else {
            back.next = null;
        }

        size--;

        return value;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator() {
            current = front;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.value;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        /* empty body */
    }
}
