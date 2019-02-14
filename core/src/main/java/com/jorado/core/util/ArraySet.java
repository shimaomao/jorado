package com.jorado.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class ArraySet<T> extends ArrayList<T> implements Set<T> {

    private static final long serialVersionUID = 4762663840185149857L;

    public ArraySet() {
        super();
    }

    public ArraySet(Collection<? extends T> c) {
        super(c);
    }

    public ArraySet(int initialCapacity) {
        super(initialCapacity);
    }

}