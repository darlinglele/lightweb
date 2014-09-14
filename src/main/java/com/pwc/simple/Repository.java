package com.pwc.simple;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Repository<T> {

    private final Class<T> entityClass;
    private String fileName;
    private String regex=" ";

    public Repository(Class<T> entityClass, String regex, String folder) {
        this.entityClass = entityClass;
        String className = entityClass.getName();
        this.fileName = folder + className.split("\\.")[className.split("\\.").length - 1];
        this.regex = regex;
    }

    public Repository(Class<T> entityClass) {
        this.entityClass = entityClass;
        String className = entityClass.getName();
        this.fileName = "./repository/" + className.split("\\.")[className.split("\\.").length - 1];
        this.regex = "    ";
    }

    public List<T> findAll() throws Exception {
        List<String> records = getLines(fileName, StandardCharsets.UTF_8);
        records.remove(0);
        String[] fields = getColumnNames();
        List<T> entities = new ArrayList<>();
        for (String record : records) {
            T t = this.entityClass.newInstance();
            String[] values = record.split(regex);
            for (int i = 0; i < values.length; i++) {
                Field field = t.getClass().getField(fields[i]);
                if (field.getType() == int.class) {
                    int value = Integer.parseInt(values[i]);
                    field.set(t, value);
                } else
                    field.set(t, values[i]);
            }
            entities.add(t);
        }

        return entities;
    }

    public void save(Collection<T> entities) throws IllegalAccessException, IOException {
        Field[] fields = this.entityClass.getFields();
        String[] fieldNames = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fieldNames.length; i++) {
            stringBuilder.append(fieldNames[i]).append(regex);
        }
        stringBuilder.append("\n");
        for (T entity : entities) {
            for (Field field : fields) {
                Object value = field.get(entity);
                stringBuilder.append(value == null ? "" : value.toString()).append(regex);
            }
            stringBuilder.append("\n");
        }
        writeTextToFile(stringBuilder.toString(), this.fileName);
    }

    public static void writeTextToFile(String text, String fileName) throws IOException {
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "UTF-8"));
        try {
            out.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public static List<String> getLines(String fileName, Charset utf8) {
        Path path = Paths.get(fileName);
        try {
            return Files.readAllLines(path, utf8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAsText(String fileName, Charset utf8) {
        String result = "";
        List<String> lines = Repository.getLines(fileName, utf8);
        for (String line : lines) {
            result += line + "\n";
        }
        return result;
    }

    public String[] getColumnNames() {
        return getFirstRecord(this.fileName).split(regex);
    }

    public static String getFirstRecord(String fileName) {
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

    public static String getAsText(String document) {
        return getAsText(document, StandardCharsets.UTF_8);
    }
}
