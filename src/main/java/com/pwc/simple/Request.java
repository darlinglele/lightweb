package com.pwc.simple;

import com.pwc.array.ArrayEx;
import com.sun.net.httpserver.HttpExchange;

import java.net.URI;
import java.util.Arrays;

public class Request {

    private final HttpExchange httpExchange;
    private final URI requestURI;

    public Request(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
        this.requestURI = httpExchange.getRequestURI();
    }

    public String getController() {
        if (this.requestURI.getPath().equals("/")) {
            return Controllers.HOME;
        }
        return this.requestURI.getPath().split("/")[1];
    }


    public String getAction() {
        if (this.requestURI.getPath().equals("/") || this.requestURI.getPath().split("/").length == 2) {
            return Controllers.INDEX;
        }
        return this.requestURI.getPath().split("/")[2];
    }

    public String[] getParameters() {
        String path = this.requestURI.getPath();

        if (path.equals("/") || path.split("[/\\?]").length < 4) {
            return new String[]{};
        }
        if (this.requestURI.toString().contains("?")) {
            return Arrays.copyOfRange(this.requestURI.toString().split("([\\?&])([^&])*="), 1, this.requestURI.toString().split("([\\?&])([^&])*=").length);
        } else {
            if (path.split("/").length == 3) {
                return new String[]{};
            }
            return new String[]{ArrayEx.from(path.split("/")).last()};
        }
    }

    public static Request from(HttpExchange httpExchange) {
        return new Request(httpExchange);
    }
}
