package com.pwc.simple;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server {
    private static Logger logger = com.pwc.simple.SimpleLogger.getLogger();

    public static void main(String[] args) {
        start(Integer.parseInt(String.valueOf(8080)));
    }

    public static Path WWWROOT = Paths.get(System.getProperty("user.dir"));

    private static void start(int port) {
        HttpServer httpServer;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            logger.severe(HelperClass.getStackTrace(e));
            return;
        }

        httpServer.createContext("/", new DefaultHttpHandler());
        httpServer.setExecutor(Executors.newCachedThreadPool());

        httpServer.start();

        System.out.println("http server start : " + port);
    }
}
