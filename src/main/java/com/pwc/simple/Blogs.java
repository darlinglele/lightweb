package com.pwc.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Blogs {
    public static List<Blog> getAll() {
        List<String> records = Records.findAll("blogs.txt");
        List<Blog> blogs = new ArrayList<>();
        for (String record : records) {
            String[] attrs = record.split("##");
            String id = attrs[0];
            String date = attrs[1];
            String author = attrs[2];
            String title = attrs[3];
            String summary = attrs[4];
            String text = Records.getText("./blog/" + id);
            blogs.add(new Blog(id, author, date, title, text, summary));
        }
        return blogs;
    }

    public static Blog getById(String id) {
        List<String> records = Records.findAll("blogs.txt");
        for (String record : records) {
            String[] attrs = record.split("##");
            String id1 = attrs[0];
            String date = attrs[1];
            String author = attrs[2];
            String title = attrs[3];
            String text = null;
            if (id.equals(id1)) {
                text = Records.getText("./blog/" + id);
                return new Blog(id, author, date, title, text, "");
            }
        }
        return null;
    }

    public static Blog getLatest() {
        List<String> records = Records.findAll("blogs.txt");
        String record = records.get(records.size() - 1);
        String[] attrs = record.split("##");
        String id = attrs[0];
        String date = attrs[1];
        String author = attrs[2];
        String title = attrs[3];
        String text = null;

        text = Records.getText("./blog/" + id);
        return new Blog(id, author, date, title, text, "");
    }

    public static Blog getSimilar(String id) {
        List<IVector> vectors = Vectors.all("blogs-vector.txt");
        Optional<IVector> optional = Vectors.get(id, "blogs-vector.txt");
        if (!optional.isPresent()) {
            return null;
        }
        double closest = 1000;
        IVector closestVector = null;
        for (IVector vector : vectors) {
            if (vector.getId().equals(optional.get().getId())) {
                continue;
            }
            double distance = Cluster.pearsonDistance(vector.getVector(), optional.get().getVector());
            if (distance < closest) {
                closest = distance;
                closestVector = vector;
            }
        }

        return Blogs.getById(closestVector.getId());
    }
}
