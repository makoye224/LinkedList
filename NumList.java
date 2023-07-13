public interface NumList {
    public abstract void add(double d);

    public abstract int size();

    public abstract int capacity();

    public abstract void insert(int i, double value);

    public abstract void remove(int i);

    public abstract boolean contains(double value);

    public abstract double lookup(int i);

    public abstract boolean equals(NumList otherList);

    public abstract void removeDuplicates();

    public abstract String toString();

    public abstract boolean isSorted();

    public abstract NumList union(NumList list1, NumList list2);

    public abstract void reverse();
}
