package com.pwc.simple;

public class Blog {
    private final String text;
    private final String date;
    private final String title;
    private final String author;
    private final String id;
    private final String summary;

    public Blog(String id, String author, String date, String title, String text, String summary) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.title = title;
        this.text = text;
        this.summary = summary;
    }
}
