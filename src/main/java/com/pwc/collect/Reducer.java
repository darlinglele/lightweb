package com.pwc.collect;

public interface Reducer<E, R> {
    R reduce(E e, R r);
}
