package com.pwc.simple;

public class Vector implements IVector {
    public static final String DELIMITER = "   ";
    private final String vector;
    private final String id;


    public Vector(String id, String vector) {
        this.id = id;
        this.vector = vector;
    }

    public double[] getVector() {
        String[] strVector = this.vector.split(DELIMITER);
        double[] vector = new double[strVector.length];
        for (int i = 0; i < strVector.length; i++) {
            vector[i] = Double.parseDouble(strVector[i]);
        }
        return vector;
    }

    public String toString() {
        return this.id + DELIMITER + this.vector;
    }

    public String getId() {
        return id;
    }
}
