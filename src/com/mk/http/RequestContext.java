package com.mk.http;

import java.util.ArrayList;
import java.util.List;

public class RequestContext {
    private List<Middleware> middlewares;
    public RequestContext(List<Middleware> middlewares) {
        this.middlewares = new ArrayList<>(middlewares);
    }

    public List<Middleware> getMiddlewares(){
        return middlewares;
    }
}
