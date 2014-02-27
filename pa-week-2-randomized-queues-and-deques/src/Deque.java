import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação do Deque para o "Programming Assignment 2: Randomized Queues and Deques" do
 * curso "Algorithms, Part I" no Coursera.
 *
 * Especificação em {@link http://coursera.cs.princeton.edu/algs4/assignments/queues.html}
 *
 * @author Alisson R. Perez
 */
public class Deque<Item> implements Iterable<Item> {

    private int size;

    private Node first;
    private Node last;

    // Uma "linked list" me pareceu a melhor escolha devido às
    // operações externas no início e fim da estrutura, apesar do overhead
    // para as informações do nó (próximo e anterior), é menos custoso que a
    // implementação com um array em número de operações.
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // Adiciona um item ao início
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();

        size++;

        Node n = new Node();
        n.item = item;

        if (first == null && last == null) {
            first = n;
            last = n;
            return;
        }

        first.prev = n; // Apontar o anterior do primeiro nó para o novo
        n.next = first; // O próximo nó do novo será o primeiro atual
        first = n; // Por fim, o primeiro agora é o novo
    }

    // Adiciona um item ao fim
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();

        size++;

        Node n = new Node();
        n.item = item;

        if (first == null && last == null) {
            first = n;
            last = n;
            return;
        }

        last.next = n; // Apontar o próximo nó do último para o novo nó
        n.prev = last; // O nó anterior do novo deve ser o último nó atual
        last = n; // Por fim, o último nó agora é o novo
    }

    // Remove e retorna o item do início
    public Item removeFirst() {
        if (first == null)
            throw new NoSuchElementException();

        size--;

        Item i = first.item;

        if (first == last) {
            first = null;
            last = null;
            return i;
        }

        first = first.next;
        first.prev = null;

        return i;
    }

    // Remove e retorna o item do fim
    public Item removeLast() {
        if (last == null)
            throw new NoSuchElementException();

        size--;

        Item i = last.item;

        if (first == last) {
            first = null;
            last = null;
            return i;
        }

        last = last.prev;
        last.next = null;

        return i;
    }

    // Iterator para os itens
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // Testes
    public static void main(String[] args) {
        testSimpleAddRemove();
        testAddRemoveallItems();
    }

    // Testa a adição e remoção de forma geral
    private static void testSimpleAddRemove() {
        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(10);
        d.addFirst(20);
        d.addFirst(30);
        d.addLast(100);
        d.addLast(101);
        d.addLast(102);

        assert d.size() == 6;
        System.out.println(d.size());

        Iterator i = d.iterator();
        while (i.hasNext()) {
            System.out.print(i.next() + " ");
        }
        System.out.println("");

        assert d.removeFirst() == 30;
        assert d.removeLast() == 102;

        assert d.size() == 4;
        System.out.println(d.size());

        i = d.iterator();
        while (i.hasNext()) {
            System.out.print(i.next() + " ");
        }
        System.out.println("");
    }

    // Testa a adição de remoção total dos items
    private static void testAddRemoveallItems() {
        Deque<Integer> d = new Deque<Integer>();

        d.addFirst(10);
        d.addLast(20);
        assert d.size() == 2;

        assert d.removeFirst() == 10;
        assert d.removeLast() == 20;
        assert d.size() == 0;

        d.addFirst(30);
        d.addLast(50);
        assert d.size() == 2;

        assert d.removeFirst() == 30;
        assert d.removeLast() == 50;
        assert d.size() == 0;
    }
}
















