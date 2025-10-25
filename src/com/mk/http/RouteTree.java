package com.mk.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteTree {

    private static volatile RouteTree instance; // volatile for thread safety
    public final RouteNode root;

    // private constructor so no one can do new RouteTree()
    private RouteTree() {
        this.root = new RouteNode("");
    }


    public static RouteTree createInstance()
    {
        return new RouteTree();
    }
    public void registerMiddlewares(String path,List<Middleware> middlewares)
    {
        if ( middlewares == null) {
            throw new IllegalArgumentException("Path and middlewares cannot be null");
        }
        if(path==null)
        {
            registerMiddlewaresInternal(root,new String[]{},0,middlewares);
        }
        else {
            String[] segments = path.split("/");
            registerMiddlewaresInternal(root,segments,0,middlewares);
        }

    }
    private void registerMiddlewaresInternal(RouteNode root,String[] path,int index,List<Middleware> middlewares)
    {
        if(path.length == 0)
        {
            root.middlewares.addAll(middlewares);
        }
        if (index >= path.length) return;
        String key = path[index];
        RouteNode routeNode = root.childrens.computeIfAbsent(key, RouteNode::new);

        if (index == path.length - 1) {
            routeNode.middlewares.addAll(middlewares);
        }
        registerMiddlewaresInternal(routeNode,path,index+1,middlewares);
    }
    public void registerRoute(HttpMethod httpMethod,String path, List<Middleware> middlewares) {
        if (path == null || middlewares == null) {
            throw new IllegalArgumentException("Path and middlewares cannot be null");
        }

        String[] segments = path.split("/");

            registerRouteInternal(root, segments, 0,httpMethod, middlewares);

    }
    public void registerSubRouter(String path,SubRouter subRouter,RouteNode root)
    {
        if (path == null || subRouter == null) {
            throw new IllegalArgumentException("Path and subRouter cannot be null");
        }
        String[] segments = path.split("/");
        registerSubRouterInternal(root,segments,0,subRouter);
    }
    private void registerSubRouterInternal(RouteNode root , String[] path,int index,SubRouter subRouter)
    {
        if (index >= path.length) return;
        String key = path[index];
        RouteNode routeNode = root.childrens.computeIfAbsent(key, RouteNode::new);
        if (index == path.length - 1) {
            routeNode.childrens = subRouter.getRouteTree().root.childrens;
        }
        registerSubRouterInternal(routeNode,path,index+1,subRouter);
    }
    private void registerRouteInternal(RouteNode root, String[] paths, int index, HttpMethod method,List<Middleware> middlewareList) {

        if (index >= paths.length) return;

        String key = paths[index];
        RouteNode routeNode = root.childrens.computeIfAbsent(key, RouteNode::new);

        if (index == paths.length - 1) {
            routeNode.methods.put(method,new RequestContext(middlewareList));
        }
        registerRouteInternal(routeNode, paths, index + 1, method,middlewareList);
    }

    public RequestHandler getMatchingRoute(HttpMethod method,String[] path) {
        RequestHandler requestHandler = new RequestHandler(new ArrayList<>());
        RouteNode traverse = root;
        requestHandler.addMiddlewares(root.middlewares);
        for (String key : path) {
            RouteNode routeNode = traverse.childrens.get(key);
            if (routeNode == null) {
              return null;
            }

            traverse = routeNode;
            requestHandler.addMiddlewares(traverse.middlewares);
        }
        if(traverse.methods.get(method)==null){
            return null;
        }
        requestHandler.addMiddlewares(traverse.methods.get(method).getMiddlewares());
        return requestHandler;
    }
}
