public class Subset {

    // unit testing
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        RandomizedQueue<String> r = new RandomizedQueue<String>();
        String[] strings = StdIn.readStrings();

        for (int i = 0; i < strings.length; i++)
            r.enqueue(strings[i]);

        for (int i = 0; i < n && !r.isEmpty(); i++)
            System.out.println(r.dequeue());
    }
}