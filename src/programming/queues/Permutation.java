package programming.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> r = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        while (true) {
            try {
                r.enqueue(StdIn.readString());
            } catch (NoSuchElementException e) {
                break;
            }
        }
        Iterator<String> iterator = r.iterator();
        while (iterator.hasNext() && k != 0) {
            System.out.println(iterator.next());
            k--;
        }
    }
}
