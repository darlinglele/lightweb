package com.pwc.common;

import java.util.Optional;

public class AbstractStreamOptional<V> {
    protected final Optional<V> optional;

    public AbstractStreamOptional(Optional<V> optional) {
        this.optional = optional;
    }

    public boolean isPresent() {
        return optional.isPresent();
    }

    public V get() {
        return optional.get();
    }
}
