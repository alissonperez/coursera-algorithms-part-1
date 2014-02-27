import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * Implementação do RandomizedQueue para o "Programming Assignment 2: Randomized Queues and Deques" do
 * curso "Algorithms, Part I" no Coursera.
 *
 * Especificação em {@link http://coursera.cs.princeton.edu/algs4/assignments/queues.html}
 *
 * @author Alisson R. Perez
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        items = (Item[]) new Object[1];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();

        items[size++] = item;

        // Ampliando
        if (size == items.length) resize(items.length * 2);
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int randIndex = StdRandom.uniform(size);
        Item item = items[randIndex];

        // Preencher a lacula com o último elemento
        items[randIndex] = items[--size];
        items[size] = null;

        // Truncando...
        if (size == items.length / 4) resize(items.length / 2);

        return item;
    }

    private void resize(int newSize) {
        Item[] nItems = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            nItems[i] = items[i];
        }

        items = nItems;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        return items[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int[] randomList;
        private int i;

        public ListIterator() {
            i = 0;
            createRandomList();
        }

        private void createRandomList() {
            randomList = new int[size];

            int j;
            for (j = 0; j < size; j++) {
                randomList[j] = j;
            }

            int r, tmp;
            for (j = 1; j < size; j++) {
                r = StdRandom.uniform(j);
                tmp = randomList[r];
                randomList[r] = randomList[j];
                randomList[j] = tmp;
            }
        }

        public boolean hasNext() {
            return i < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return items[randomList[i++]];
        }
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> r = new RandomizedQueue<Integer>();

        r.enqueue(1);
        r.enqueue(10);
        r.enqueue(44);
        r.enqueue(66);
        r.enqueue(88);
        r.enqueue(33);
        r.enqueue(144);
        r.enqueue(6);
        r.enqueue(153);
        r.enqueue(166);

        System.out.println("Tamanho: " + r.size());
        System.out.println("");

        System.out.println("Sample 1: " + r.sample());
        System.out.println("Sample 2: " + r.sample());
        System.out.println("Sample 3: " + r.sample());
        System.out.println("");

        Iterator i = r.iterator();
        while (i.hasNext())
            System.out.println("Iterator: " + i.next());
        System.out.println("");

        System.out.println("Removendo...");
        while (!r.isEmpty())
            System.out.println("Dequeue: " + r.dequeue());
        System.out.println("");

        System.out.println("Tamanho: " + r.size());
    }
}