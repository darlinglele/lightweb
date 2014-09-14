package com.pwc.common;

import java.util.Optional;
import java.util.function.Function;

public class StreamOptional<V> extends AbstractStreamOptional<V> {

    public StreamOptional(Optional<V> optional) {
        super(optional);
    }

    static <R> StreamOptional<R> from(Optional<R> optional) {
        return new StreamOptional(optional);
    }

    public <R> StreamOptional<R> bind(Function<V, R> function) {
        if (this.isPresent()) {
            return StreamOptional.from(Optional.<R>ofNullable(function.apply(this.get())));
        } else
            return StreamOptional.from(Optional.<R>empty());
    }
}
