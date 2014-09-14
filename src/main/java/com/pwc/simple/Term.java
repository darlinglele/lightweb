package com.pwc.simple;

public class Term {
    public String name;
    public int tagId;
    public int count;

    public Term(String name, int tagId, int count) {
        this.name = name;
        this.tagId = tagId;
        this.count = count;
    }

    public Term() {

    }

    @Override
    public String toString() {
        return String.format("'%s',%d,%d", this.name, this.tagId, this.count);
    }

}


