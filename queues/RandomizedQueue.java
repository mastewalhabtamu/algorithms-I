/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
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

    // construct an empty randomized queue
    public RandomizedQueue() {
        front = null;
        back = null;

        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
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

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int randomIndex = StdRandom.uniform(size);

        Node current = front;
        for (int i = 0; i < randomIndex; i++) {
            current = current.next;
        }
        Item value = current.value;

        if (front == back) {
            front = null;
            back = null;
        }
        else {
            if (current == front) {
                front = current.next;
                front.prev = null;
            }
            else if (current == back) {
                back = current.prev;
                back.next = null;
            }
            else {
                current.prev.next = current.next;
                current.next.prev = current.prev;
            }
        }

        size--;

        return value;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        int randomIndex = StdRandom.uniform(size);

        Node current = front;
        for (int i = 0; i < randomIndex; i++) {
            current = current.next;
        }

        return current.value;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] indexes;
        private int current;
        private Node currentNode;

        public RandomizedQueueIterator() {
            indexes = new int[size];
            for (int i = 0; i < size; i++) {
                indexes[i] = i;
            }

            StdRandom.shuffle(indexes);

            current = 0;
            currentNode = front;
            if (size > 0) {
                for (int i = 0; i < indexes[current]; i++) {
                    currentNode = currentNode.next;
                }
            }
        }

        public boolean hasNext() {
            return (current < size);
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            if (current != 0) {
                int indexDiff = indexes[current - 1] - indexes[current];
                if (indexDiff > 0) {
                    for (int i = 0; i < indexDiff; i++) {
                        currentNode = currentNode.prev;
                    }
                }
                else {
                    for (int i = 0; i > indexDiff; i--) {
                        currentNode = currentNode.next;
                    }
                }
            }

            current++;
            return currentNode.value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        /* empty body */
    }
}
