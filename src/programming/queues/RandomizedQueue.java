package programming.queues;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int RESIZE_RATIO = 2;
    private static final int INITIAL_CAPACITY = 4;

    private Item[] items;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        items = (Item[]) new Object[INITIAL_CAPACITY];
        capacity = INITIAL_CAPACITY;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            items[0] = item;
            size++;
        } else {
            if (capacity == size) {
                doResize(capacity * RESIZE_RATIO);
            }
            items[size++] = item;
        }
    }

    public Item dequeue() {
        checkNotEmpty();
        int index = StdRandom.uniform(size);
        Item result = items[index];
        items[index] = items[--size];
        items[size] = null;
        if (size * 2 < capacity / RESIZE_RATIO) {
            doResize(capacity / RESIZE_RATIO);
        }
        return result;
    }

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    public Item sample() {
        checkNotEmpty();
        return items[StdRandom.uniform(size)];
    }

    private void doResize(int newCapacity) {
        @SuppressWarnings("unchecked")
        Item[] newItems = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
        capacity = newCapacity;
    }

    private class RandomQueueIterator implements Iterator<Item> {

        private Item[] iteratorItems = Arrays.copyOf(RandomizedQueue.this.items, RandomizedQueue.this.size);
        private int size = RandomizedQueue.this.size;

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int index = StdRandom.uniform(size);
            Item result = iteratorItems[index];
            Item temp = iteratorItems[index];
            iteratorItems[index] = iteratorItems[--size];
            iteratorItems[size] = temp;
            return result;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> r = new RandomizedQueue<>();
        r.enqueue(2);
        r.enqueue(3);
        r.enqueue(4);
        for (Integer i : r) {
            System.out.println(i);
        }
    }

}
