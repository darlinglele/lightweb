package com.pwc.simple;

public class Tag {
    public int id;
    public String name;
    public int count;

    @Override
    public String toString() {
        return String.format("%d,'%s',%d", this.id, this.name, this.count);
    }
}
