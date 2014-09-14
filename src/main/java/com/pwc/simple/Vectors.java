package com.pwc.simple;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Vectors {
    public static boolean contains(String id) {
        for (IVector article : all("data.txt")) {
            if (article.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static List<IVector> all(String fileName) {
        List<String> records = Records.findAll(fileName);
        ArrayList<IVector> articles = new ArrayList<>();
        for (int j = 1; j < records.size(); j++) {
            String[] items = records.get(j).split(Vector.DELIMITER, 2);
            String id = items[0];
            String vector = items[1];
            articles.add(new Vector(id, vector));
        }
        return articles;
    }

    public static Optional<IVector> get(String id, String fileName) {
        return all(fileName).stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    static List<String> readTextFile(String fileName) {
        Path path = Paths.get(fileName);
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
