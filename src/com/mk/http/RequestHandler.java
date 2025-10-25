package com.mk.http;

import java.io.IOException;
import java.util.*;

public class RequestHandler {
    private Queue<Middleware> middlewares;
    public RequestHandler(ArrayList<Middleware> middlewares) {

        this.middlewares = new LinkedList<>(middlewares);
    }
    public void handle(Request req,Response res) throws IOException {

            while(!middlewares.isEmpty()){
                Middleware m = middlewares.poll();
                m.process(req,res);
            }
    }
    public void addMiddlewares(List<Middleware> middlewares){
        this.middlewares.addAll(middlewares);
    }
}
