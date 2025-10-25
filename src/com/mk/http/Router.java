package com.mk.http;

import java.util.HashMap;
import java.util.List;

public abstract class Router {
private final RouteTree mRouteTree = RouteTree.createInstance();
    public void get(String uri, List<Middleware> middlewares)
    {
        mRouteTree.registerRoute(HttpMethod.GET,uri,middlewares);
    }
    public void post(String uri, List<Middleware> middlewares)
    {
       mRouteTree.registerRoute(HttpMethod.POST,uri,middlewares);
    }
    public  void delete(String uri)
    {
        mRouteTree.registerRoute(HttpMethod.DELETE,uri,null);
    }
    public void patch(String uri, RequestHandler handler)
    {
       mRouteTree.registerRoute(HttpMethod.PATCH,uri,null);
    }
    public void put(String uri, RequestHandler handler)
    {
      mRouteTree.registerRoute(HttpMethod.PUT,uri,null);
    }
    public void use(String uri , List<Middleware> middlewares)
    {
        mRouteTree.registerMiddlewares(uri,middlewares);
    }
    public void use(String uri  , SubRouter router)
    {

        mRouteTree.registerSubRouter(uri,router,mRouteTree.root);
    }
    public RouteTree getRouteTree()
    {
        return mRouteTree;
    }

}
