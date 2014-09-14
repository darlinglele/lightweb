package com.pwc.simple;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


public class StaticResourceHttpHandler extends com.pwc.simple.AbstractHttpHandler implements com.pwc.simple.IHttpHandler {
    private static Logger logger = SimpleLogger.getLogger();

    @Override
    public void handle(HttpExchange httpExchange) {
        byte[] content;
        try {
            content = Files.readAllBytes(Paths.get(Server.WWWROOT.toString() + httpExchange.getRequestURI().getPath()));
//            httpExchange.getRequestHeaders().set("Content-Type");

        } catch (IOException e) {
            content = "<b>404 File not found!<b>".getBytes();
            try {
                logger.info(HelperClass.getStackTrace(e));
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, content.length);
            } catch (IOException e1) {
                logger.info(HelperClass.getStackTrace(e1));
            }
            try {
                httpExchange.getResponseBody().write(content);
            } catch (IOException e1) {
                logger.info(HelperClass.getStackTrace(e1));
            }
            try {
                httpExchange.getResponseBody().close();
            } catch (IOException e1) {
                logger.info(HelperClass.getStackTrace(e1));
            }
            return;
        }
        response(content, httpExchange);
    }

    @Override
    public boolean canHandle(HttpExchange httpExchange) {
        List<String> extensions = new ArrayList<>(Arrays.asList("png", "jpg", "gif", "js", "css", "htm", "html", "jpeg"));
        for (String extension : extensions) {
            if (httpExchange.getRequestURI().getPath().endsWith("." + extension)) {
                return true;
            }
        }
        return false;
    }
}
