package com.pwc.common;

import java.util.Optional;
import java.util.function.Function;

public class Optionals {
    static <V, R> Optional<R> bind(Optional<V> optional, Function<V, R> function) {
        if (optional.isPresent())
            return Optional.ofNullable(function.apply(optional.get()));
        else
            return Optional.empty();
    }
}
