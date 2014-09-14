package com.pwc.collect;

public interface Filter<E> {
    boolean isRetained(E e);
}
