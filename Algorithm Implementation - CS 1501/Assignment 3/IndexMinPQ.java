/******************************************************************************
 *  Compilation:  javac IndexMinPQ.java
 *  Execution:    java IndexMinPQ
 *  Dependencies: StdOut.java
 *
 *  Minimum-oriented indexed PQ implementation using a binary heap.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 *  The <tt>IndexMinPQ</tt> class represents an indexed priority queue of generic keys.
 *  It supports the usual <em>insert</em> and <em>delete-the-minimum</em>
 *  operations, along with <em>delete</em> and <em>change-the-key</em>
 *  methods. In order to let the client refer to keys on the priority queue,
 *  an integer between 0 and maxN-1 is associated with each key&mdash;the client
 *  uses this integer to specify which key to delete or change.
 *  It also supports methods for peeking at the minimum key,
 *  testing if the priority queue is empty, and iterating through
 *  the keys.
 *  <p>
 *  This implementation uses a binary heap along with an array to associate
 *  keys with integers in the given range.
 *  The <em>insert</em>, <em>delete-the-minimum</em>, <em>delete</em>,
 *  <em>change-key</em>, <em>decrease-key</em>, and <em>increase-key</em>
 *  operations take logarithmic time.
 *  The <em>is-empty</em>, <em>size</em>, <em>min-index</em>, <em>min-key</em>, and <em>key-of</em>
 *  operations take constant time.
 *  Construction takes time proportional to the specified capacity.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 *  @param <Key> the generic type of key on this priority queue
 */
public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer>
{
    private int maxN;        // maximum number of elements on PQ
    private int N;           // number of elements on PQ
    private int[] pq;        // binary heap using 1-based indexing
    private int[] qp;        // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
    private int[] pq2;
    private int[] qp2;
    private Key[][] keys;    // keys[i][0] = priority of i for Price
                             // keys[i][1] = priority of i for Mileage
    private Stack<Integer> indexGaps; // Holds indices of deleted elements

    /**
     * Initializes an empty indexed priority queue with indices between <tt>0</tt>
     * and <tt>maxN - 1</tt>.
     * @param  maxN the keys on this priority queue are index from <tt>0</tt>
     *         <tt>maxN - 1</tt>
     * @throws IllegalArgumentException if <tt>maxN</tt> &lt; <tt>0</tt>
     */
    public IndexMinPQ(int maxN)
    {
        if (maxN < 0) throw new IllegalArgumentException();
        this.maxN = maxN;
        keys = (Key[][]) new Comparable[maxN + 1][2];    // make this of length maxN??
        pq   = new int[maxN + 1];
        qp   = new int[maxN + 1];                   // make this of length maxN??
        pq2   = new int[maxN + 1];
        qp2   = new int[maxN + 1];                   // make this of length maxN??
        indexGaps   = new Stack<Integer>();
        for (int i = 0; i <= maxN; i++)
        {
            qp[i] = -1;
            qp2[i] = -1;
        }
    }

    /**
     * Returns true if this priority queue is empty.
     *
     * @return <tt>true</tt> if this priority queue is empty;
     *         <tt>false</tt> otherwise
     */
    public boolean isEmpty()
    {
        return N == 0;
    }

    /**
     * Is <tt>i</tt> an index on this priority queue?
     *
     * @param  i an index
     * @return <tt>true</tt> if <tt>i</tt> is an index on this priority queue;
     *         <tt>false</tt> otherwise
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     */
    public boolean contains(int i)
    {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        return qp[i] != -1;
    }

    /**
     * Returns the number of keys on this priority queue.
     *
     * @return the number of keys on this priority queue
     */
    public int size()
    {
        return N;
    }

    /**
     * Associates key with index <tt>i</tt>.
     *
     * @param  i an index
     * @param  key the key to associate with index <tt>i</tt>
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws IllegalArgumentException if there already is an item associated
     *         with index <tt>i</tt>
     */
    public void insertPrice(int i, Key key)
    {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        N++;
        if (!indexGaps.isEmpty())
        {
            i = indexGaps.peek().intValue();
        }
        qp[i] = N;
        pq[N] = i;
        qp2[i] = N;
        pq2[N] = i;
        keys[i][0] = key;
        swimPrice(N);
    }
    // Need two inserts because I have to change the priceOrMileage int in the Car object in between insert calls.
    public void insertMileage(int i, Key key)
    {
        if (!indexGaps.isEmpty())
        {
            i = indexGaps.pop().intValue();
        }
        keys[i][1] = key;
        swimMileage(N);
    }

    /**
     * Returns an index associated with a minimum key.
     *
     * @return an index associated with a minimum key
     * @throws NoSuchElementException if this priority queue is empty
     */
    public int minIndexPrice()
    {
        if (N == 0) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }
    public int minIndexMileage()
    {
        if (N == 0) throw new NoSuchElementException("Priority queue underflow");
        return pq2[1];
    }

    public int indexPrice(int i)
    {
        return pq[i];
    }
    public int indexMileage(int i)
    {
        return pq2[i];
    }

    /**
     * Returns a minimum key.
     *
     * @return a minimum key
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key minKeyPrice()
    {
        if (N == 0) throw new NoSuchElementException("Priority queue underflow");
        return keys[pq[1]][0];
    }
    public Key minKeyMileage()
    {
        if (N == 0) throw new NoSuchElementException("Priority queue underflow");
        return keys[pq2[1]][1];
    }

    public Key traversePQPrice(int i)
    {
        if (N == 0) throw new NoSuchElementException("Priority queue underflow");
        return keys[pq[i]][0];
    }
    public Key traversePQMileage(int i)
    {
        if (N == 0) throw new NoSuchElementException("Priority queue underflow");
        return keys[pq2[i]][1];
    }

    /**
     * Removes a minimum key and returns its associated index.
     * @return an index associated with a minimum key
     * @throws NoSuchElementException if this priority queue is empty
     */
    public int delMinPrice()
    {
        if (N == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        exchPrice(1, N--);
        sinkPrice(1);
        qp[min] = -1;            // delete
        pq[N+1] = -1;            // not needed
        return min;
    }
    public int finishDelMinPrice(int min)
    {
        int min2 = 0;
        int loc = qp2[min];
        /*for (int i = 1; i < N + 2; i++)
        {
            if (pq2[i] == min)
            {
                loc = i;
                break;
            }
        }*/
        exchMileage(loc, N+1);
        qp2[min2] = -1;
        keys[pq2[N+1]][0] = null;    // to help with garbage collection
        keys[pq2[N+1]][1] = null;
        indexGaps.push(new Integer(pq2[N+1]));
        pq2[N+1] = -1;            // not needed
        if (loc != N + 1)
        {
            if (!sinkMileage(loc))
            {
                swimMileage(loc);
            }
        }
        return min;
    }


    public int delMinMileage()
    {
        if (N == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq2[1];
        exchMileage(1, N--);
        sinkMileage(1);
        qp2[min] = -1;            // delete
        pq2[N+1] = -1;            // not needed
        return min;
    }
    public int finishDelMinMileage(int min)
    {
        int min2 = 0;
        int loc = qp[min];

        /*for (int i = 1; i < N + 2; i++)
        {
            if (pq[i] == min)
            {
                loc = i;
                break;
            }
        }*/
        exchPrice(loc, N+1);
        qp[min2] = -1;
        keys[pq[N+1]][0] = null;    // to help with garbage collection
        keys[pq[N+1]][1] = null;
        indexGaps.push(new Integer(pq2[N+1]));
        pq[N+1] = -1;            // not needed
        if (loc != N + 1)
        {
            if (!sinkPrice(loc))
            {
                swimPrice(loc);
            }
        }
        return min;
    }

    /**
     * Returns the key associated with index <tt>i</tt>.
     *
     * @param  i the index of the key to return
     * @return the key associated with index <tt>i</tt>
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws NoSuchElementException no key is associated with index <tt>i</tt>
     */
    public Key keyOfPrice(int i)
    {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        else return keys[i][0];
    }
    public Key keyOfMileage(int i)
    {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        else return keys[i][1];
    }

    /**
     * Change the key associated with index <tt>i</tt> to the specified value.
     *
     * @param  i the index of the key to change
     * @param  key change the key assocated with index <tt>i</tt> to this key
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws NoSuchElementException no key is associated with index <tt>i</tt>
     */
    public void changeKeyPrice(int i, Key key)
    {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        keys[i][0] = key;
        if (!sinkPrice(pq[i]))
        {
            swimPrice(pq[i]);
        }
    }
    public void changeKeyMileage(int i, Key key)
    {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        keys[i][1] = key;
        if (!sinkMileage(pq2[i]))
        {
            swimMileage(pq2[i]);
        }
    }

    /**
     * Change the key associated with index <tt>i</tt> to the specified value.
     *
     * @param  i the index of the key to change
     * @param  key change the key assocated with index <tt>i</tt> to this key
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @deprecated Replaced by {@link #changeKey(int, Key)}.
     */
    public void changePrice(int i, Key key)
    {
        changeKeyPrice(i, key);
    }
    public void changeMileage(int i, Key key)
    {
        changeKeyMileage(i, key);
    }

    /**
     * Decrease the key associated with index <tt>i</tt> to the specified value.
     *
     * @param  i the index of the key to decrease
     * @param  key decrease the key assocated with index <tt>i</tt> to this key
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws IllegalArgumentException if key &ge; key associated with index <tt>i</tt>
     * @throws NoSuchElementException no key is associated with index <tt>i</tt>
     */
    public void decreaseKeyPrice(int i, Key key)
    {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        if (keys[i][1].compareTo(key) <= 0)
            throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
        keys[i][0] = key;
        swimPrice(qp[i]);
    }
    public void decreaseKeyMileage(int i, Key key)
    {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        if (keys[i][1].compareTo(key) <= 0)
            throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
        keys[i][1] = key;
        swimMileage(qp2[i]);
    }

    /**
     * Increase the key associated with index <tt>i</tt> to the specified value.
     *
     * @param  i the index of the key to increase
     * @param  key increase the key assocated with index <tt>i</tt> to this key
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws IllegalArgumentException if key &le; key associated with index <tt>i</tt>
     * @throws NoSuchElementException no key is associated with index <tt>i</tt>
     */
    public void increaseKeyPrice(int i, Key key)
    {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        if (keys[i][0].compareTo(key) >= 0)
            throw new IllegalArgumentException("Calling increaseKey() with given argument would not strictly increase the key");
        keys[i][0] = key;
        sinkPrice(qp[i]);
    }
    public void increaseKeyMileage(int i, Key key)
    {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        if (keys[i][1].compareTo(key) >= 0)
            throw new IllegalArgumentException("Calling increaseKey() with given argument would not strictly increase the key");
        keys[i][1] = key;
        sinkMileage(qp2[i]);
    }

    /**
     * Remove the key associated with index <tt>i</tt>.
     *
     * @param  i the index of the key to remove
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws NoSuchElementException no key is associated with index <t>i</tt>
     */
    public void deletePrice(int i)
    {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        exchPrice(index, N);
        if (!sinkPrice(index))
        {
            swimPrice(index);
        }
        pq[N] = -1;
        qp[i] = -1;
        keys[i][0] = null;
        indexGaps.push(new Integer(i));
    }
    public void deleteMileage(int i)
    {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        int index = qp2[i];
        exchMileage(index, N);
        if (!sinkMileage(index))
        {
            swimMileage(index);
        }
        pq2[N] = -1;
        qp2[i] = -1;
        keys[i][1] = null;
        indexGaps.push(new Integer(i));
        N--;
    }


   /***************************************************************************
    * General helper functions.
    ***************************************************************************/
    private boolean greaterPrice(int i, int j)
    {
        return keys[pq[i]][0].compareTo(keys[pq[j]][0]) > 0;
    }
    private boolean greaterMileage(int i, int j)
    {
        return keys[pq2[i]][1].compareTo(keys[pq2[j]][1]) > 0;
    }

    private void exchPrice(int i, int j)
    {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }
    private void exchMileage(int i, int j)
    {
        int swap = pq2[i];
        pq2[i] = pq2[j];
        pq2[j] = swap;
        qp2[pq2[i]] = i;
        qp2[pq2[j]] = j;
    }


   /***************************************************************************
    * Heap helper functions.
    ***************************************************************************/
    private boolean swimPrice(int k)
    {
        boolean returnVal = false;
        while (k > 1 && greaterPrice(k/2, k))
        {
            returnVal = true;
            exchPrice(k, k/2);
            k = k/2;
        }
        return returnVal;
    }
    private boolean swimMileage(int k)
    {
        boolean returnVal = false;
        while (k > 1 && greaterMileage(k/2, k))
        {
            returnVal = true;
            exchMileage(k, k/2);
            k = k/2;
        }
        return returnVal;
    }

    private boolean sinkPrice(int k)
    {
        boolean returnVal = false;
        while (2*k <= N)
        {
            int j = 2*k;
            if (j < N && greaterPrice(j, j+1)) j++;
            if (!greaterPrice(k, j)) break;
            returnVal = true;
            exchPrice(k, j);
            k = j;
        }
        return returnVal;
    }
    private boolean sinkMileage(int k)
    {
        boolean returnVal = false;
        while (2*k <= N)
        {
            int j = 2*k;
            if (j < N && greaterMileage(j, j+1)) j++;
            if (!greaterMileage(k, j)) break;
            returnVal = true;
            exchMileage(k, j);
            k = j;
        }
        return returnVal;
    }


   /***************************************************************************
    * Iterators.
    ***************************************************************************/

    /**
     * Returns an iterator that iterates over the keys on the
     * priority queue in ascending order.
     * The iterator doesn't implement <tt>remove()</tt> since it's optional.
     *
     * @return an iterator that iterates over the keys in ascending order
     */
    public Iterator<Integer> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Integer> {
        // create a new pq
        private IndexMinPQ<Key> copy;

        // add all elements to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            copy = new IndexMinPQ<Key>(pq.length - 1);
            for (int i = 1; i <= N; i++)
                copy.insertPrice(pq[i], keys[pq[i]][1]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMinPrice();
        }
    }


    /**
     * Unit tests the <tt>IndexMinPQ</tt> data type.
     */
    public static void main(String[] args)
    {

        Car a = new Car("fdaffaslflk", "Honda", "Accord", 15000, 80000, "black");
        Car b = new Car("fdaffaslflk", "Toyota", "Accord", 10000, 100, "black");
        Car c = new Car("fdaffaslflk", "Ford", "Accord", 12000, 500, "black");
        Car d = new Car("fdaffaslflk", "Dodge", "Accord", 56000, 78000, "black");
        Car e = new Car("fdaffaslflk", "Kia", "Accord", 4, 56000, "black");
        Car f = new Car("fdaffaslflk", "Hyundai", "Accord", 5000, 12345, "black");

        IndexMinPQ<Car> pq = new IndexMinPQ<Car>(50);
        pq.insertPrice(1, a);
        pq.insertMileage(1, a.cloneToMileage());
        pq.insertPrice(2, b);
        pq.insertMileage(2, b.cloneToMileage());
        pq.insertPrice(3, c);
        pq.insertMileage(3, c.cloneToMileage());
        pq.insertPrice(4, d);
        pq.insertMileage(4, d.cloneToMileage());
        pq.insertPrice(5, e);
        pq.insertMileage(5, e.cloneToMileage());
        pq.insertPrice(6, f);
        pq.insertMileage(6, f.cloneToMileage());
        System.out.println(pq.traversePQPrice(1).getMake());
        System.out.println(pq.traversePQPrice(2).getMake());
        System.out.println(pq.traversePQPrice(3).getMake());
        System.out.println(pq.traversePQPrice(4).getMake());
        System.out.println(pq.traversePQPrice(5).getMake());
        System.out.println(pq.traversePQPrice(6).getMake() +"\n\n");
        System.out.println(pq.traversePQMileage(1).getMake());
        System.out.println(pq.traversePQMileage(2).getMake());
        System.out.println(pq.traversePQMileage(3).getMake());
        System.out.println(pq.traversePQMileage(4).getMake());
        System.out.println(pq.traversePQMileage(5).getMake());
        System.out.println(pq.traversePQMileage(6).getMake() + "\n\n");
        pq.deletePrice(3);
        pq.deleteMileage(3);
        System.out.println(pq.traversePQMileage(1).getMake());
        System.out.println(pq.traversePQMileage(2).getMake());
        System.out.println(pq.traversePQMileage(3).getMake());
        System.out.println(pq.traversePQMileage(4).getMake());
        System.out.println(pq.traversePQMileage(5).getMake());
        System.out.println(pq.traversePQMileage(6).getMake() + "\n\n");
    }
}
