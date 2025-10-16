package com.mk.http;

import java.io.IOException;

public class RequestHandler {
    int middleware_index;
    private final Middleware[] middlewares;
    private final Controller controller;

    public RequestHandler(Controller controller,Middleware... middlewares) {
        this.controller = controller;
        this.middlewares = middlewares;
    }
    public void handle(Request req,Response res) throws IOException {
        Request request = req;
        while(middleware_index<middlewares.length) {
           request = middlewares[middleware_index].process(request, res);
           middleware_index++;
       }
       controller.handleRequest(request, res);
    }

}
