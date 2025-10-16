package com.mk.http;

import java.util.HashMap;

public  class Router {
    private HashMap<String,RequestHandler> routes;
    private HashMap<String,Router> nestedRouters;

    public Router()
    {
        this.routes = new HashMap<>();
    }
    public void get(String uri, RequestHandler handler)
    {
        this.routes.put(uri,handler);
        RouteTree rt =  RouteTree.getInstance();
       rt.registerRouteRequestHandler(rt.root,uri.split("/"),0,handler);
    }
    public void post(String uri, RequestHandler handler)
    {
        this.routes.put(uri,handler);
    }
    public  void delete(String uri)
    {
        this.routes.remove(uri);
    }
    public void patch(String uri, RequestHandler handler)
    {
        this.routes.put(uri,handler);
    }
    public void put(String uri, RequestHandler handler)
    {
        this.routes.put(uri,handler);
    }
    public void use(String uri , Router router)
    {
        nestedRouters.put(uri,router);
    }
    public HashMap<String, RequestHandler> getRoutes() {
        return routes;
    }
    public HashMap<String, Router> getNestedRouters() {
        return nestedRouters;
    }


}
