package com.pwc.simple;

import com.sun.net.httpserver.HttpExchange;

//线程安全的实现
public interface IHttpHandler {
    void handle(HttpExchange httpExchange);

    boolean canHandle(HttpExchange httpExchange);
}
