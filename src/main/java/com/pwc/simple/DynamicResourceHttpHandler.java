package com.pwc.simple;

import com.sun.net.httpserver.HttpExchange;

public class DynamicResourceHttpHandler extends AbstractHttpHandler implements IHttpHandler {

    public void handle(HttpExchange httpExchange) {
        Request request = Request.from(httpExchange);
        String action = request.getAction();
        String[] parameters = request.getParameters();
        Controller controller = Controllers.of(request);
        response(controller.execute(action, parameters), httpExchange);
    }

    @Override
    public boolean canHandle(HttpExchange httpExchange) {
        return true;
    }

}
