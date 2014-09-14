package com.pwc.array;
import com.pwc.collect.Commander;
import com.pwc.collect.Reducer;
import com.pwc.common.CodeContract;

public class ArrayEx<E> extends AbstractArrayEx<E> {

    private ArrayEx(E[] outer) {
        super(outer);
    }

    public static int[] toPrimitive(Integer[] integers) {
        CodeContract.RequiresArgumentNotNull(integers, "integers");
        int[] ret = new int[integers.length];
        for (int i = 0; i < ret.length; i++)
            ret[i] = integers[i];
        return ret;
    }

    public static <E> ArrayEx<E> from(E[] array) {
        return new ArrayEx<>(array);
    }

    public <R> R reduce(Reducer<E, R> reducer) {
        CodeContract.RequiresArgumentNotNull(reducer, "reducer");
        R result = null;
        for (E e : this.inner) {
            result = reducer.reduce(e, result);
        }
        return result;
    }

    public ArrayEx<E> each(Commander<E> commander) {
        CodeContract.RequiresArgumentNotNull(commander, "commander");
        for (E e : this.inner) {
            commander.execute(e);
        }
        return this;
    }

    public E first() {
        if (this.inner.length == 0)
            return null;
        return this.inner[0];
    }

    public E last() {
        if (this.inner.length == 0)
            return null;
        else
            return this.inner[this.inner.length - 1];
    }

}
