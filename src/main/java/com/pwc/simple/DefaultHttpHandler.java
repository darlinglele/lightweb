package com.pwc.simple;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.ArrayList;
import java.util.List;

public class   DefaultHttpHandler implements HttpHandler {

    public void handle(HttpExchange httpExchange) {
        IHttpHandler httpHandler = distribute(httpExchange);
        httpHandler.handle(httpExchange);
    }

    public IHttpHandler distribute(HttpExchange httpExchange) {
        List<IHttpHandler> httpHandlers = new ArrayList<>();
        httpHandlers.add(new StaticResourceHttpHandler());
        httpHandlers.add(new DynamicResourceHttpHandler());
        for (IHttpHandler handler : httpHandlers) {
            if (handler.canHandle(httpExchange)) {
                return handler;
            }
        }
        return null;
    }
}