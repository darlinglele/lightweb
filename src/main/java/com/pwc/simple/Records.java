package com.pwc.simple;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Records {
    public static List<String> findAll(String fileName) {
        Path path = Paths.get(fileName);
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getText(String fileName) {
        String result = "";
        List<String> lines = Records.findAll(fileName);
        for (String line : lines) {
            result += line + "\n";
        }
        return result;
    }

    public static String getHeader(String fileName) {
        File file = new File(fileName);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader in = new BufferedReader(fileReader);
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    static void append(String content, String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }

            FileWriter fileWriter = new FileWriter(file.getName(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
