package com.pwc.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cluster implements IVector {
    private final Cluster left;
    private final Cluster right;
    private String id;
    private double[] vector;

    public Cluster(IVector vector) {
        this.left = null;
        this.right = null;
        this.vector = vector.getVector();
        this.id = vector.getId();
    }

    public Cluster(Cluster left, Cluster right) {
        this.left = left;
        this.right = right;
        this.vector = merge(left, right);
        this.id = left.getId().hashCode() + ":" + right.getId().hashCode();
    }

    private double[] merge(Cluster left, Cluster right) {
        double[] merge = new double[left.getVector().length];
        for (int i = 0; i < merge.length; i++) {
            merge[i] = (left.getVector()[i] + right.getVector()[i]) / 2;
        }
        return merge;
    }

    public static Cluster sort(List<IVector> vectors) {
        List<Cluster> clusters = iniClustersFromRawVectors(vectors);
        HashMap<String, Double> distances = new HashMap<>();
        return sort(clusters, distances);
    }

    private static List<Cluster> iniClustersFromRawVectors(List<IVector> vectors) {
        List<Cluster> clusters = new ArrayList<>();
        for (int i = 0; i < vectors.size(); i++) {
            clusters.add(new Cluster(vectors.get(i)));
        }
        return clusters;
    }

    private static Cluster sort(List<Cluster> clusters, HashMap<String, Double> distances) {

        if (clusters.size() == 1) {
            return clusters.get(0);
        } else {
            int[] closest = getClosest(distances, clusters);
            Cluster newCluster = new Cluster(clusters.get(closest[0]), clusters.get(closest[1]));
            clusters.remove(closest[1]);
            clusters.remove(closest[0]);
            clusters.add(newCluster);
            return sort(clusters, distances);
        }
    }

    private static int[] getClosest(HashMap<String, Double> distances, List<Cluster> clusters) {
        double closestDistance = 2;
        int[] closest = new int[]{0, 0};
        for (int i = 0; i < clusters.size(); i++) {
            for (int j = i + 1; j < clusters.size(); j++) {
                String key = clusters.get(i).id + ":" + clusters.get(j).id;
                if (!distances.containsKey(key)) {
                    distances.put(key, pearsonDistance(clusters.get(i).getVector(), clusters.get(j).getVector()));
                }
                if (distances.get(key) < closestDistance) {
                    closestDistance = distances.get(key);
                    closest[0] = i;
                    closest[1] = j;
                }
            }
        }
        return closest;
    }

    static double pearsonDistance(double[] vector, double[] vector2) {
        double lengthOfVector = vector.length;
        //sum
        double sumOfFirst = sum(vector);
        double sumOfSecond = sum(vector2);
        //squar sum
        double sqSumOfFirst = squareSum(vector);
        double sqSumOfSecond = squareSum(vector2);

        //product sum
        double productSum = productSum(vector, vector2);

        //pearson  score
        double num = productSum - (sumOfFirst * sumOfSecond / lengthOfVector);
        double den = Math.sqrt((sqSumOfFirst - (sumOfFirst * sumOfFirst / lengthOfVector)) * (sqSumOfSecond - (sumOfSecond * sumOfSecond) / lengthOfVector));
        return den == 0 ? 0 : 1.0 - num / den;
    }

    public static double productSum(double[] vector, double[] vector1) {
        double sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum += vector[i] * vector1[i];
        }
        return sum;
    }

    public static double squareSum(double[] vector) {
        double sum = 0;
        for (double n : vector) {
            sum += n * n;
        }
        return sum;
    }

    public static double sum(double[] vector) {
        double sum = 0;
        for (double n : vector) {
            sum += n;
        }
        return sum;
    }

    @Override
    public double[] getVector() {
        return this.vector;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public Cluster getLeft() {
        return left;
    }

    public Cluster getRight() {
        return right;
    }

    public Cluster simple() {
        return this;
    }
}
