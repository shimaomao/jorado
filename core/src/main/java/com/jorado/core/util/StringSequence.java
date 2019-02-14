package com.jorado.core.util;

import java.io.Serializable;
import java.util.*;


public class StringSequence implements List<String>, Serializable {

    private static final long serialVersionUID = 1L;

    private final List<String> sequence;

    private final boolean cycle;

    private final boolean ignoreCase;

    public StringSequence(List<String> sequence) {
        this(sequence, true);
    }

    public StringSequence(List<String> sequence, boolean ignoreCase) {
        if (sequence == null || sequence.size() == 0) {
            throw new IllegalArgumentException("sequence == null");
        }
        List<String> list = new ArrayList<String>(sequence);
        boolean cycle = false;
        if (list.size() > 1 && list.get(0).equals(list.get(list.size() - 1))) {
            cycle = true;
            list.remove(list.size() - 1);
        }
        this.sequence = list;
        this.cycle = cycle;
        this.ignoreCase = ignoreCase;
    }

    public List<String> getSequence() {
        return Collections.unmodifiableList(sequence);
    }

    public boolean isCycle() {
        return cycle;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public boolean containSequence(String begin, String end) {
        return indexOf(begin) != -1 && indexOf(end) != -1;
    }

    public List<String> getSequence(String begin, String end) {
        int beginIndex = indexOf(begin);
        int endIndex = indexOf(end);

        if (beginIndex == -1 || endIndex == -1) {
            return Arrays.asList(new String[0]);
        }

        if (beginIndex <= endIndex)
            return Collections.unmodifiableList(sequence.subList(beginIndex, endIndex + 1));
        if (cycle)
            return Collections.unmodifiableList(cycleList(beginIndex, endIndex));
        return Collections.unmodifiableList(reverseList(beginIndex, endIndex));
    }

    private List<String> cycleList(int beginIndex, int endIndex) {
        List<String> afterSub = sequence.subList(beginIndex, sequence.size());
        List<String> beforeSub = sequence.subList(0, endIndex + 1);
        List<String> sub = new ArrayList<String>(afterSub.size() + beforeSub.size());
        sub.addAll(afterSub);
        sub.addAll(beforeSub);
        sub = Collections.unmodifiableList(sub);
        return sub;
    }

    private List<String> reverseList(int beginIndex, int endIndex) {
        List<String> sub = sequence.subList(endIndex, beginIndex + 1);
        Collections.reverse(sub);
        return sub;
    }

    private int indexOf(String item) {
        if (ignoreCase) {
            for (int i = 0, n = sequence.size(); i < n; i++) {
                if (((String) sequence.get(i)).equalsIgnoreCase(item)) {
                    return i;
                }
            }
            return -1;
        }
        return sequence.indexOf(item);
    }

    public void add(int index, String element) {
        sequence.add(index, element);
    }

    public boolean add(String o) {
        return sequence.add(o);
    }

    public boolean addAll(Collection<? extends String> c) {
        return sequence.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends String> c) {
        return sequence.addAll(index, c);
    }

    public void clear() {
        sequence.clear();
    }

    public boolean contains(Object o) {
        return sequence.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        return sequence.containsAll(c);
    }

    public boolean equals(Object o) {
        return sequence.equals(o);
    }

    public String get(int index) {
        return sequence.get(index);
    }

    public int hashCode() {
        return sequence.hashCode();
    }

    public int indexOf(Object o) {
        return sequence.indexOf(o);
    }

    public boolean isEmpty() {
        return sequence.isEmpty();
    }

    public Iterator<String> iterator() {
        return sequence.iterator();
    }

    public int lastIndexOf(Object o) {
        return sequence.lastIndexOf(o);
    }

    public ListIterator<String> listIterator() {
        return sequence.listIterator();
    }

    public ListIterator<String> listIterator(int index) {
        return sequence.listIterator(index);
    }

    public String remove(int index) {
        return sequence.remove(index);
    }

    public boolean remove(Object o) {
        return sequence.remove(o);
    }

    public boolean removeAll(Collection<?> c) {
        return sequence.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return sequence.retainAll(c);
    }

    public String set(int index, String element) {
        return sequence.set(index, element);
    }

    public int size() {
        return sequence.size();
    }

    public List<String> subList(int fromIndex, int toIndex) {
        return Collections.unmodifiableList(sequence.subList(fromIndex, toIndex));
    }

    public Object[] toArray() {
        return sequence.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return sequence.toArray(a);
    }

    @Override
    public String toString() {
        return sequence.toString();
    }

}