package com.pwc.collect;

public interface Transformer<E, R> {
    R transform(E e);
}
