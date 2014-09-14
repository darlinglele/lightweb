package com.pwc.simple;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractHttpHandler {
    public void response(byte[] content, HttpExchange httpExchange) {
        Headers headers = httpExchange.getResponseHeaders();
        Map<String, String> contentType = new HashMap<>();
        contentType.put("js", "javascript");
        contentType.put("js", "javascript");
        contentType.put("css", "css");

        if (httpExchange.getRequestURI().getPath().endsWith("." + "js")) {
            headers.set("Content-Type", "text/javascript");
        } else if (httpExchange.getRequestURI().getPath().endsWith("." + "css")) {
            headers.set("Content-Type", "text/css");
        } else if (httpExchange.getRequestURI().getPath().endsWith("." + "png")) {
            headers.set("Content-Type", "image/png");
            httpExchange.getResponseHeaders().set("Expires", "Mon, 13 May 2014 13:51:40 GMT");
        } else if (httpExchange.getRequestURI().getPath().endsWith("." + "jpg")) {
            headers.set("Content-Type", "image/jpg");
        } else if (httpExchange.getRequestURI().getPath().endsWith("." + "jpeg")) {
            headers.set("Content-Type", "image/jpeg");
            httpExchange.getResponseHeaders().set("Expires", "Mon, 13 May 2014 13:51:40 GMT");
        } else if (httpExchange.getRequestHeaders().get("Accept").toString().contains("text/html")) {
            headers.set("Content-Type", "text/html");
        }


        try {
            httpExchange.sendResponseHeaders(200, content.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            httpExchange.getResponseBody().write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            httpExchange.getResponseBody().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
