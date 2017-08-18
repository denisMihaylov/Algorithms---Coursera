package programming.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private static final int RESIZE_RATIO = 2;
    private static final int INITIAL_CAPACITY = 4;

    @SuppressWarnings("unchecked")
    private Item[] items = (Item[]) new Object[INITIAL_CAPACITY];
    private int size = 0;
    private int capacity = INITIAL_CAPACITY;
    private int first = 0;
    private int last = 0;

    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        preAdd(item);
        if (isEmpty()) {
            items[first = last = 0] = item;
        } else {
            items[first = decrement(first)] = item;
        }
        size++;
    }

    public void addLast(Item item) {
        preAdd(item);
        if (isEmpty()) {
            items[first = last = 0] = item;
        } else {
            items[last = increment(last)] = item;
        }
        size++;
    }

    private void preAdd(Item item) {
        checkResize();
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    private int decrement(int value) {
        return value == 0 ? capacity - 1 : value - 1;
    }

    private int increment(int value) {
        return (value + 1) % capacity;
    }

    private void checkResize() {
        if (capacity == size) {
            doResize(capacity * RESIZE_RATIO);
        }
    }

    public Item removeFirst() {
        preRemove();
        Item result = items[first];
        items[first] = null;
        first = increment(first);
        postRemove();
        return result;
    }

    public Item removeLast() {
        preRemove();
        Item result = items[last];
        items[last] = null;
        last = decrement(last);
        postRemove();
        return result;
    }

    private void preRemove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    private void postRemove() {
        size--;
        if (size * 2 < capacity / RESIZE_RATIO) {
            doResize(capacity / RESIZE_RATIO);
        }
    }

    private void doResize(int newCapacity) {
        @SuppressWarnings("unchecked")
        Item[] newItems = (Item[]) new Object[newCapacity];
        int i = 0;
        for (Item item : this) {
            newItems[i++] = item;
        }
        items = newItems;
        first = 0;
        last = i - 1;
        capacity = newCapacity;
    }

    private class DequeIterator implements Iterator<Item> {

        private int current;
        private int count;

        public DequeIterator() {
            current = Deque.this.first;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            return count < Deque.this.size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item result = Deque.this.items[current];
            current = Deque.this.increment(current);
            count++;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addFirst(4);
        d.addLast(5);
        d.addFirst(12);
        d.addFirst(24);
        d.addLast(124123);
        d.removeLast();
        d.removeLast();
        d.removeLast();
        d.removeLast();
        for (int a : d) {
            System.out.println(a);
        }
    }

}
