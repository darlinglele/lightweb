package com.pwc.array;
import com.pwc.common.CodeContract;

public abstract class AbstractArrayEx<E> {
    protected final E[] inner;
    public AbstractArrayEx(E[] outer) {
        CodeContract.RequiresArgumentNotNull(outer, "outer");
        this.inner = outer;
    }

    public int length() {
        return inner.length;
    }

    public E get(int index) {
        return inner[index];
    }
}
