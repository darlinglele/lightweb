package com.pwc.collect;

import com.pwc.common.CodeContract;

import java.util.Collection;
import java.util.Iterator;

public class AbstractCollectionEx<E> implements Collection<E> {
    protected final Collection<E> inner;

    public AbstractCollectionEx(Collection<E> outer) {
        CodeContract.RequiresArgumentNotNull(outer, "outer");
        this.inner = outer;
    }

    @Override
    public int size() {
        return inner.size();
    }

    @Override
    public boolean isEmpty() {
        return inner.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return inner.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return inner.iterator();
    }

    @Override
    public Object[] toArray() {
        return inner.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return inner.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return inner.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return inner.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return inner.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return inner.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return inner.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return inner.retainAll(c);
    }

    @Override
    public void clear() {
        inner.clear();
    }

    @Override
    public boolean equals(Object o) {
        return inner.equals(o);
    }

    @Override
    public int hashCode() {
        return inner.hashCode();
    }
}
