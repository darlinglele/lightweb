package com.pwc.collect;

import com.pwc.common.CodeContract;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionEx<E> extends AbstractCollectionEx<E> {

    private CollectionEx(Collection<E> outer) {
        super(outer);
    }

    public static <E> CollectionEx<E> from(Collection<E> collection) {
        CodeContract.RequiresArgumentNotNull(collection, "collection");
        return new CollectionEx<>(collection);
    }

    public <R> CollectionEx<R> map(Transformer<E, R> transformer) {
        CodeContract.RequiresArgumentNotNull(transformer, "transformer");
        final Collection<R> mappedCollection = new ArrayList<>();
        this.each((E e) -> {
            mappedCollection.add(transformer.transform(e));
        });
        return CollectionEx.from(mappedCollection);
    }

    public <R> R reduce(Reducer<E, R> reducer) {
        CodeContract.RequiresArgumentNotNull(reducer, "reducer");
        R result = null;
        for (E e : this) {
            result = reducer.reduce(e, result);
        }
        return result;
    }

    public CollectionEx<E> filter(Filter<E> filter) {
        CodeContract.RequiresArgumentNotNull(filter, "filter");
        final Collection<E> newCollection = new ArrayList<>();
        this.each((E e) -> {
            if (filter.isRetained(e))
                newCollection.add(e);
        });
        return CollectionEx.from(newCollection);
    }

    public CollectionEx<E> each(Commander<E> commander) {
        CodeContract.RequiresArgumentNotNull(commander, "commander");
        for (E e : this.inner) {
            commander.execute(e);
        }
        return this;
    }

    public E first() {
        CodeContract.Requires(this.size() > 0, new NullPointerException("The collection is empty!"));
        return this.inner.iterator().next();
    }
}
