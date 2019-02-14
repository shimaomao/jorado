package com.jorado.core.util.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class DoubleArrayIterator implements Iterator<Double> {

    private final double[] array;

    private final int length;

    private int index;

    public DoubleArrayIterator(double[] array) {
        this.array = array;
        this.length = array == null ? 0 : array.length;
    }

    public Object getArray() {
        return array;
    }

    public boolean hasNext() {
        return index < length;
    }

    public Double next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return array[index++];
    }

    public void remove() {
        throw new UnsupportedOperationException("remove() method is not supported");
    }
}