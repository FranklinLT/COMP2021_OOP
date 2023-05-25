package hk.edu.polyu.comp.comp2021.assignment4.compset;

import java.util.ArrayList;
import java.util.List;

class CompSet<T> {

    /** Each CompSet uses at most 1023 buckets.   */
    private static final int NUBMER_OF_BUCKETS = 1023;

    /** An array of buckets as the storage for each set. */
    private List<T>[] storage;

    public CompSet() {
        storage = new List[NUBMER_OF_BUCKETS];
    }

    /**
     * Initialize 'this' with the unique elements from 'elements'.
     * Throw IllegalArgumentException if 'elements' is null.
     */
    public CompSet(List<T> elements) {
        // Add missing code here
        if(elements == null)
            throw new IllegalArgumentException();

        storage = new List[NUBMER_OF_BUCKETS];
        for(T element:elements) add(element);
    }

    /**
     * Get the total number of elements stored in 'this'.
     */
    public int getCount() {
        // Add missing code here
        int count = 0;
        for (int i = 0; i < storage.length; i++)
        {
            if(storage[i] == null) continue;
            count += storage[i].size();
        }
        return count;
    }

    public boolean isEmpty() {
        // Add missing code here
        return getCount() == 0;
    }

    /**
     * Whether 'element' is contained in 'this'?
     */
    public boolean contains(T element) {
        // Add missing code here
        if(element == null) return false;

        if(isEmpty()) return false;
        if(storage[getIndex(element)] == null) return false;
        return storage[getIndex(element)].contains(element);
    }

    /**
     * Get all elements of 'this' as a list.
     */
    public List<T> getElements() {
        // Add missing code here
        if(isEmpty()) return null;
        List<T> AllElements = new ArrayList<T>();

        for(int i = 0; i < storage.length; i++)
        {
            if(storage[i] != null)
            {
                for(int n = 0; n < storage[i].size(); n++)
                {
                    AllElements.add(storage[i].get(n));
                }
            }
        }
        return AllElements;
    }

    /**
     * Add 'element' to 'this', if it is not contained in 'this' yet.
     * Throw IllegalArgumentException if 'element' is null.
     */
    public void add(T element) {
        // Add missing code here
        if(element == null)
            throw new IllegalArgumentException();

        if(!contains(element))
        {
            if(storage[getIndex(element)] == null)
            {
                List<T> temp = new ArrayList<>();
                storage[getIndex(element)] = temp;
            }
            storage[getIndex(element)].add(element);
        }
    }

    /**
     * Two CompSets are equivalent is they contain the same elements.
     * The order of the elements inside each CompSet is irrelevant.
     */
    public boolean equals(Object other){
        // Add missing code here
        if(!(other instanceof CompSet<?>)) return false;

        List<T> temp1 = this.getElements();
        List<T> temp2 = ((CompSet<T>) other).getElements();

        return temp1.equals(temp2);
    }

    /**
     * Remove 'element' from 'this', if it is contained in 'this'.
     * Throw IllegalArgumentException if 'element' is null.
     */
    public void remove (T element) {
        // Add missing code here
        if(element == null)
            throw new IllegalArgumentException();

        if(storage[getIndex(element)].contains(element))
        {
            storage[getIndex(element)].remove(element);
        }
    }

    //========================================================================== private methods

    private int getIndex(T element) {
        return element.hashCode() % NUBMER_OF_BUCKETS;
    }

}


